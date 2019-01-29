package wookey.wallet.builder;

import org.apache.commons.lang3.StringUtils;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.VarInt;
import wookey.wallet.config.Config;
import wookey.wallet.crypto.*;
import wookey.wallet.entity.*;
import wookey.wallet.enums.SigHashCodes;
import wookey.wallet.enums.Zopcodes;
import wookey.wallet.utils.dozer.BeanMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TransactionBuilder
 */
public class TransactionBuilder {


    /**
     * Creates a raw transaction
     *
     * @param histories   history type, array of transaction history
     * @param recipients  recipient type, array of address on where to send coins to
     * @param blockHeight blockHeight
     * @param blockHash   blockHash of blockHeight
     * @return Transction Object (see TXOBJ type for info about structure)
     */
    public static Transaction createRawTx(List<TXHistory> histories, List<Recipient> recipients, long blockHeight, String blockHash) {
        Transaction txObj = new Transaction();
        List<TxInput> ins = new ArrayList();
        histories.forEach(h -> {
            TxLastOut txOut = new TxLastOut(h.getTxid(), h.getVout());
            ins.add(new TxInput(txOut, "", h.getScriptPubKey(), "ffffffff"));
        });
        txObj.setIns(ins);
        List outs = new ArrayList();
        recipients.forEach(o -> {
            outs.add(new TxOut(addressToScript(o.getAddress(), blockHeight, blockHash, o.getData()), o.getSatoshis()));
        });
        ;
        txObj.setOuts(outs);
        return txObj;
    }

    /**
     * Serializes a TXOBJ into hex string
     *
     * @param txObj
     * @return hex string of txObj
     */
    public static String serializeTx(Transaction txObj) {
        String serializedTx = "";
        byte[] _buf16 = new byte[4];

        // Version
        Utils.uint16ToByteArrayLE(txObj.getVersion(), _buf16, 0);

        serializedTx += Utils.HEX.encode(_buf16);

        // History
        serializedTx += Utils.numToVarInt(txObj.getIns().size());


        for (TxInput i : txObj.getIns()) {
            Utils.uint16ToByteArrayLE(i.getOut().getVout(), _buf16, 0);
            serializedTx += Utils.HEX.encode(Utils.reverseBytes(Utils.HEX.decode(i.getOut().getHash())));
            serializedTx += Utils.HEX.encode(_buf16);

            // Script Signature
            // Doesn't work for length > 253 ....

            serializedTx += Utils.getPushDataLength(i.getScript());
            serializedTx += i.getScript();

            // Sequence
            serializedTx += i.getSequence();
        }

        // Outputs
        serializedTx += Utils.numToVarInt(txObj.getOuts().size());
        for (TxOut o : txObj.getOuts()) {
            // Write 64bit buffers
            // JS only supports 56 bit
            // https://github.com/bitcoinjs/bitcoinjs-lib/blob/master/src/bufferutils.js#L25
            byte[] _buf32 = new byte[8];

            // Satoshis
            Utils.uint32ToByteArrayLE(o.getSatoshis() & -1, _buf32, 0);
            Utils.uint32ToByteArrayLE(o.getSatoshis() / 0x100000000L, _buf32, 4);

            // ScriptPubKey
            serializedTx += Utils.HEX.encode(_buf32);
            serializedTx += Utils.getPushDataLength(o.getScript());
            serializedTx += o.getScript();
        }

        // Locktime
        Utils.uint16ToByteArrayLE(txObj.getLocktime(), _buf16, 0);
        serializedTx += Utils.HEX.encode(_buf16);
        return serializedTx;
    }

    /**
     * Deserializes a hex string into a TXOBJ
     *
     * @param hexStr
     * @return
     */
    public static Transaction deserializeTx(String hexStr) {
        byte[] buf = Utils.HEX.decode(hexStr);
        int offset = 0;
        // Out txobj
        Transaction txobj = new Transaction();
        // Version
        txobj.setVersion(Utils.readUint32(buf, 0));
        offset += 4;

        // Vins
        VarInt varInt = new VarInt(buf, offset);
        long vinLen = varInt.value;
        offset += varInt.getOriginalSizeInBytes();
        List<TxInput> ins = new ArrayList<>();
        for (int i = 0; i < vinLen; i++) {
            // Else its
            byte[] hash = Utils.subarray(buf, offset, offset + 32);
            offset += 32;

            long vout = Utils.readUint32(buf, offset);
            offset += 4;

            VarInt scriptVarInt = new VarInt(buf, offset);
            Long scriptLen = scriptVarInt.value;
            offset += scriptVarInt.getOriginalSizeInBytes();

            byte[] script = Utils.subarray(buf, offset, offset + scriptLen.intValue());
            offset += scriptLen;

            String sequence = Utils.HEX.encode(Utils.subarray(buf, offset, offset + 4));
            offset += 4;
            TxLastOut txLastOut = new TxLastOut(Utils.HEX.encode(Utils.reverseBytes(hash)), vout);
            ins.add(new TxInput(txLastOut, Utils.HEX.encode(script), "", sequence));
        }
        txobj.setIns(ins);

        // Vouts
        VarInt voutVarInt = new VarInt(buf, offset);
        long voutLen = voutVarInt.value;
        offset += voutVarInt.getOriginalSizeInBytes();
        List<TxOut> outs = new ArrayList<>();
        for (int i = 0; i < voutLen; i++) {
            long satoshis = Utils.readInt64(buf, offset);
            offset += 8;

            VarInt _scriptVarInt = new VarInt(buf, offset);
            Long _scriptLen = _scriptVarInt.value;
            offset += _scriptVarInt.getOriginalSizeInBytes();


            byte[] _script = Utils.subarray(buf, offset, offset + _scriptLen.intValue());
            offset += _scriptLen;

            TxOut txOut = new TxOut(Utils.HEX.encode(_script), satoshis);
            outs.add(txOut);
        }
        txobj.setOuts(outs);

        // Locktime
        txobj.setLocktime(Utils.readUint32(buf, offset));
        offset += 4;
        return txobj;
    }

    /**
     * Signs the raw transaction
     *
     * @param _txObj
     * @param i
     * @param privKey
     * @param arguments
     * @return
     */
    public static Transaction signTx(Transaction _txObj, int i, String privKey, Object... arguments) {
        boolean compressPubKey = false;
        int hashcode = SigHashCodes.SIGHASH_ALL.getValue();
        if (arguments != null) {
            if (arguments.length > 0 && (boolean) arguments[0]) {
                compressPubKey = true;
            }
            if (arguments.length > 1) {
                hashcode = (int) arguments[1];
            }
        }

        // Make a copy
        Transaction txObj = BeanMapper.map(_txObj, Transaction.class);

        // Prepare our signature
        // Get script from the current tx input
        String script = txObj.getIns().get(i).getPrevScriptPubKey();

        // Populate current tx in with the prevScriptPubKey
        Transaction signingTx = signatureForm(txObj, i, script, hashcode);

        // Get script signature
        String scriptSig = getScriptSignature(privKey, signingTx, hashcode);

        // Chuck it back into txObj and add pubkey
        // Protocol:
        // PUSHDATA
        // signature data and SIGHASH_ALL
        // PUSHDATA
        // public key data

        String pubKey = AddressBuilder.privKeyToPubKey(privKey, compressPubKey);

        txObj.getIns().get(i).setScript(Utils.getPushDataLength(scriptSig) + scriptSig + Utils.getPushDataLength(pubKey) + pubKey);

        return txObj;
    }

    /**
     * Signature hashing for TXOBJ
     *
     * @param txObj
     * @param i
     * @param script
     * @param hashcode
     * @return
     */
    public static Transaction signatureForm(Transaction txObj, int i, String script, int hashcode) {
        // Copy object so we don't rewrite it
        Transaction newTx = BeanMapper.map(txObj, Transaction.class);

        // Only sign the specified index
        List<TxInput> ins = newTx.getIns();
        for (int j = 0; j < ins.size(); j++) {
            ins.get(j).setScript("");
        }
        ins.get(i).setScript(script);
        List<TxOut> outs = newTx.getOuts();
        if (hashcode == SigHashCodes.SIGHASH_NONE.getValue()) {
            outs.clear();
        } else if (hashcode == SigHashCodes.SIGHASH_SINGLE.getValue()) {
            outs = outs.subList(0, ins.size() + 1);
            for (int j = 0; j < ins.size() - 1; ++j) {
                outs.get(j).setSatoshis(new Double(Math.pow(2, 64)).longValue() - 1);
                outs.get(j).setScript("");
            }
        } else if (hashcode == SigHashCodes.SIGHASH_ANYONECANPAY.getValue()) {
            ins = Arrays.asList(ins.get(i));
        }
        return newTx;
    }

    /**
     * Gets signature for the vin script
     *
     * @param privKey
     * @param signingTx
     * @param hashcode
     * @return
     */
    public static String getScriptSignature(String privKey, Transaction signingTx, int hashcode) {
        // Buffer
        byte[] buf16 = new byte[4];
        Utils.uint16ToByteArrayLE(hashcode, buf16, 0);

        String signingTxHex = serializeTx(signingTx);

        String signingTxWithHashcode = signingTxHex + Utils.HEX.encode(buf16);

        // Sha256 it twice, according to spec
        String msg = Utils.HEX.encode(Sha256Hash.hashTwice(Utils.HEX.decode(signingTxWithHashcode)));

        // Signing it
        ECKeyPair ecKeyPair = ECKeyPair.create(Utils.HEX.decode(privKey));
        ECDSASignature ecdsaSignature = ecKeyPair.sign(Utils.HEX.decode(msg));

        // Convert it to DER format
        // Appending 01 to it cause
        // ScriptSig = <varint of total sig length> <SIG from code, including appended 01 SIGNHASH> <length of pubkey (0x21 or 0x41)> <pubkey>
        // https://bitcoin.stackexchange.com/a/36481
        byte[] signatureDer = Sign.toDER(ecdsaSignature);
        String signatureDER = Utils.HEX.encode(signatureDer) + "01";

        return signatureDER;
    }

    /**
     * Given an address, generates an output script
     *
     * @param address
     * @param blockHeight
     * @param blockHash
     * @param data
     * @return output script
     */
    public static String addressToScript(String address, long blockHeight, String blockHash, byte[] data) {
        // NULL transaction
        if (StringUtils.isEmpty(address)) {
            return mkNullDataReplayScript(data, blockHeight, blockHash);
        }

        // P2SH replay starts with a 's', or 'r'
        if (address.charAt(1) == 's' || address.charAt(1) == 'r') {
            return mkScriptHashReplayScript(address, blockHeight, blockHash);
        }

        // P2PKH-replay is a replacement for P2PKH
        return mkPubkeyHashReplayScript(address, blockHeight, blockHash);
    }

    /**
     * Given an address, generates a script hash replay type script needed for the transaction
     *
     * @param data
     * @param blockHeight
     * @param blockHash
     * @return scriptHash script
     */
    public static String mkNullDataReplayScript(byte[] data, long blockHeight, String blockHash) {
        String dataHex = Utils.HEX.encode(data);

        // Minimal encoding
        byte[] blockHeightBuffer = new byte[4];
        Utils.uint32ToByteArrayLE(blockHeight, blockHeightBuffer, 0);
        if (blockHeightBuffer[3] == 0x00) {
            byte[] temp = new byte[3];
            System.arraycopy(blockHeightBuffer, 0, temp, 0, 3);
            blockHeightBuffer = temp;
        }

        String blockHeightHex = Utils.HEX.encode(blockHeightBuffer);

        // Block hash is encoded in little indian
        String blockHashHex = Utils.HEX.encode(Utils.reverseBytes(Utils.HEX.decode(blockHash)));
        return Zopcodes.OP_RETURN.getValue() + Utils.getPushDataLength(dataHex) + dataHex
                + Utils.getPushDataLength(blockHashHex) + blockHashHex
                + Utils.getPushDataLength(blockHeightHex) + blockHeightHex + Zopcodes.OP_CHECKBLOCKATHEIGHT.getValue();
    }

    /**
     * Given an address, generates a script hash replay type script needed for the transaction
     *
     * @param address
     * @param blockHeight
     * @param blockHash
     * @return scriptHash script
     */
    public static String mkScriptHashReplayScript(String address, long blockHeight, String blockHash) {
        String addrHex = Utils.HEX.encode(Base58Check.decode(address));
        String subAddrHex = addrHex.substring(4, addrHex.length()); // Cut out the '00' (we also only want 14 bytes instead of 16)

        byte[] blockHeightBuffer = new byte[4];
        Utils.uint32ToByteArrayLE(blockHeight, blockHeightBuffer, 0);
        if (blockHeightBuffer[3] == 0x00) {
            byte[] temp = new byte[3];
            System.arraycopy(blockHeightBuffer, 0, temp, 0, 3);
            blockHeightBuffer = temp;
        }
        String blockHeightHex = Utils.HEX.encode(blockHeightBuffer);

        // Block hash is encoded in little indian
        String blockHashHex = Utils.HEX.encode(Utils.reverseBytes(Utils.HEX.decode(blockHash)));

        return Zopcodes.OP_HASH160.getValue() + Utils.getPushDataLength(subAddrHex) + subAddrHex + Zopcodes.OP_EQUAL.getValue()
                + Utils.getPushDataLength(blockHashHex) + blockHashHex + Utils.getPushDataLength(blockHeightHex)
                + blockHeightHex + Zopcodes.OP_CHECKBLOCKATHEIGHT.getValue();
    }

    /**
     * Given an address, generates a pubkeyhash replay type script needed for the transaction
     *
     * @param address
     * @param blockHeight
     * @param blockHash
     * @return pubKeyScript
     */
    public static String mkPubkeyHashReplayScript(String address, long blockHeight, String blockHash, Object... arguments) {
        Config config = Config.getInstance();
        String net = config.getProperty("net");
        String pubKeyHash = config.getProperty(net + ".pubKeyHash");
        if (arguments != null) {
            if (arguments.length == 1) {
                pubKeyHash = (String) arguments[0];
            }
        }

        // Get lengh of pubKeyHash (so we know where to substr later on)
        String addrHex = Utils.HEX.encode(Base58Check.decode(address));

        // Cut out pubKeyHash
        String subAddrHex = addrHex.substring(pubKeyHash.length(), addrHex.length());

        // Minimal encoding
        byte[] blockHeightBuffer = new byte[4];
        Utils.uint32ToByteArrayLE(blockHeight, blockHeightBuffer, 0);
        if (blockHeightBuffer[3] == 0x00) {
            byte[] temp = new byte[3];
            System.arraycopy(blockHeightBuffer, 0, temp, 0, 3);
            blockHeightBuffer = temp;
        }
        String blockHeightHex = Utils.HEX.encode(blockHeightBuffer);

        // Block hash is encoded in little indian
        String blockHashHex = Utils.HEX.encode(Utils.reverseBytes(Utils.HEX.decode(blockHash)));

        return Zopcodes.OP_DUP.getValue() + Zopcodes.OP_HASH160.getValue() + Utils.getPushDataLength(subAddrHex) + subAddrHex + Zopcodes.OP_EQUALVERIFY.getValue()
                + Zopcodes.OP_CHECKSIG.getValue() + Utils.getPushDataLength(blockHashHex) + blockHashHex + Utils.getPushDataLength(blockHeightHex)
                + blockHeightHex + Zopcodes.OP_CHECKBLOCKATHEIGHT.getValue();
    }

}
