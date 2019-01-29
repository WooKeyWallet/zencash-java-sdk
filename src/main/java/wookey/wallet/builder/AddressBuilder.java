package wookey.wallet.builder;

import wookey.wallet.config.Config;
import wookey.wallet.crypto.Base58Check;
import wookey.wallet.crypto.ECKeyPair;
import wookey.wallet.crypto.Utils;
import wookey.wallet.enums.Zopcodes;
import org.apache.commons.lang3.StringUtils;
import org.bitcoinj.core.Sha256Hash;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AddressBuilder
 */
public class AddressBuilder {

    private static Config config = Config.getInstance();

    private static String net = config.getProperty("net");

    /**
     * makes a private key
     *
     * @param phrase Password phrase
     * @return Private key
     */
    public static String mkPrivkey(String phrase) {
        byte[] bytes = new byte[0];
        try {
            bytes = phrase.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] hash = Sha256Hash.hash(bytes);
        String hashAddress = Utils.HEX.encode(hash);
        return hashAddress;
    }

    /**
     * Converts a private key to WIF format
     * toCompressed (Convert to WIF compressed key or nah)
     *
     * @param privKey
     * @param arguments
     * @return
     */
    public static String privKeyToWIF(String privKey, Object... arguments) {
        boolean toCompressed = false;
        String wif = config.getProperty(net + ".wif");

        if (arguments != null) {
            if (arguments.length > 0) {
                toCompressed = (boolean) arguments[0];
            }
            if (arguments.length > 1) {
                wif = (String) arguments[1];
            }
        }
        if (toCompressed) {
            privKey = privKey + "01";
        }
        return Base58Check.encode(Utils.HEX.decode(wif + privKey));
    }

    /**
     * Given a WIF format pk, convert it back to the original pk
     *
     * @param wifPk
     * @return
     */
    public static String WIFToPrivKey(String wifPk) {
        String og = Utils.HEX.encode(Base58Check.decode(wifPk));
        og = og.substring(2);

        if (og.length() > 64) {
            og = og.substring(0, 64);
        }
        return og;

    }

    /**
     * Returns private key's public Key
     *
     * @param privKey
     * @param arguments
     * @return
     */
    public static String privKeyToPubKey(String privKey, Object... arguments) {
        byte[] pkBuffer = Utils.HEX.decode(privKey);
        return Utils.HEX.encode(ECKeyPair.create(pkBuffer, arguments).getPublicKey().toByteArray());
    }

    /**
     * Converts public key to zencash address
     *
     * @param pubKey
     * @param arguments
     * @return
     */
    public static String pubKeyToAddr(String pubKey, String... arguments) {
        String pubKeyHash = config.getProperty(net + ".pubKeyHash");
        if (arguments != null) {
            if (arguments.length > 0) {
                pubKeyHash = arguments[0];
            }
        }
        String hash160 = Utils.HEX.encode(Utils.sha256hash160(Utils.HEX.decode(pubKey)));
        return Base58Check.encode(Utils.HEX.decode((pubKeyHash + hash160)));
    }

    /**
     * Given a list of public keys, create a M-of-N redeemscript
     *
     * @param pubKeys (array of public keys, NOT ADDRESS)
     * @param M       [2 or 3] in M-of-N multisig
     * @param N       [3 or 4] in M-of-N multisig
     * @return
     */
    public static String mkMultiSigRedeemScript(List<String> pubKeys, int M, int N) {
        if (M > N && M <= 1) {
            throw new Error("Invalid Multi Sig Type");
        }

        byte[] OP_1 = Utils.HEX.decode(Zopcodes.OP_1.getValue());

        String OP_START = Long.toHexString(Utils.readInt8(OP_1, 0) + (M - 1));
        String OP_END = Long.toHexString(Utils.readInt8(OP_1, 0) + (N - 1));

        List<String> collect = pubKeys.stream().map(x -> Utils.getPushDataLength(x) + x).collect(Collectors.toList());

        return OP_START + String.join("", collect) + OP_END + Zopcodes.OP_CHECKMULTISIG.getValue();
    }

    /**
     * Given the multi sig redeem script, return the corresponding address
     *
     * @param redeemScript
     * @param arguments
     * @return
     */
    public static String multiSigRSToAddress(String redeemScript, String... arguments) {
        String scriptHash = config.getProperty(net + ".scriptHash");
        if (arguments != null) {
            if (arguments.length > 0 && StringUtils.isNotEmpty(arguments[0])) {
                scriptHash = arguments[0];
            }
        }

        // Protocol: RIPEMD160(SHA256(script))
        String s256 = Utils.HEX.encode(Sha256Hash.hash(Utils.HEX.decode(redeemScript)));
        String r160 = Utils.HEX.encode(Utils.ripemd160Digest(Utils.HEX.decode(s256)));
        return Base58Check.encode(Utils.HEX.decode(scriptHash + r160));
    }
}
