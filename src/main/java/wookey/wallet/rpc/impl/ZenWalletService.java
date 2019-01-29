package wookey.wallet.rpc.impl;

import wookey.wallet.builder.TransactionBuilder;
import wookey.wallet.config.Config;
import wookey.wallet.entity.Recipient;
import wookey.wallet.entity.TXHistory;
import wookey.wallet.entity.TXUnspent;
import wookey.wallet.entity.Transaction;
import wookey.wallet.entity.insight.InsightInfo;
import wookey.wallet.exception.ZenCashException;
import wookey.wallet.rpc.ZenWalletRpc;
import wookey.wallet.utils.http.RetrofitClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ZEN Wallet service
 */
public class ZenWalletService {

    private RetrofitClient retrofitClient;

    private ZenWalletRpc zenWalletRpc;

    private Config config;

    private InsightService insightService;

    public ZenWalletService() {
        init();
    }

    private void init() {
        config = Config.getInstance();
        final String rpcuser = config.getProperty("rpcuser");
        final String rpcpassword = config.getProperty("rpcpassword");
        final String serverurl = config.getProperty("serverurl");

        retrofitClient = new RetrofitClient();
        zenWalletRpc = retrofitClient.createService(ZenWalletRpc.class, serverurl, rpcuser, rpcpassword);
        insightService = new InsightService();
    }

    /**
     * sendrawtransaction
     *
     * @param privateKey
     * @param fromAddress
     * @param toAddress
     * @param amount
     * @param fee
     * @return
     * @throws Exception
     */
    public String sendrawtransaction(String privateKey, String fromAddress, String toAddress, double amount, double fee) throws Exception {

        if (toAddress.length() != 35) {
            throw new ZenCashException("invalidAddress");
        }

        if (amount <= 0) {
            throw new ZenCashException("invalidAmount");
        }
        if (fee < 0 && fee != 0.000001) {
            throw new ZenCashException("invalidFee");
        }

        // Convert how much we wanna send
        // to satoshis
        long satoshisToSend = Math.round(amount * 100000000);
        long satoshisfeesToSend = Math.round(fee * 100000000); // fees already in satoshis, multiply by 2 so faster tx confirmation

        // Building our transaction TXOBJ
        // How many satoshis do we have so far
        long satoshisSoFar = 0;
        List<TXHistory> histories = new ArrayList<>();
        List<Recipient> recipients = new ArrayList<>();
        recipients.add(new Recipient(toAddress, satoshisToSend));

        // Get previous transactions
        List<TXUnspent> txUnspents = insightService.getUnspent(fromAddress);

        // Get blockheight and hash
        InsightInfo insightInfo = insightService.getInfo();
        long blockHeight = insightInfo.getInfo().getBlocks() - 300;
        String blockHash = insightService.getBlockHash(blockHeight).getBlockHash();

        // Iterate through each utxo
        // append it to history
        TXUnspent obj = null;
        for (int i = 0, length = txUnspents.size(); i < length; i++) {
            obj = txUnspents.get(i);
            if (obj.getConfirmations() == 0) {
                continue;
            }
            histories.add(new TXHistory(obj.getTxid(), obj.getVout(), obj.getScriptPubKey()));

            // How many satoshis do we have so far
            satoshisSoFar = satoshisSoFar + Math.round(obj.getAmount() * 100000000);
            if (satoshisSoFar >= satoshisToSend + satoshisfeesToSend) {
                break;
            }
        }

        // If we don't have enough address
        // fail and tell user
        if (satoshisSoFar < satoshisToSend + satoshisfeesToSend) {
            throw new ZenCashException("notEnoughZEN");
        }

        // If we don't have exact amount
        // Refund remaining to current address
        if (satoshisSoFar != satoshisToSend + satoshisfeesToSend) {
            long refundSatoshis = satoshisSoFar - satoshisToSend - satoshisfeesToSend;
            // Refunding 'dust' (<54 satoshis will result in unconfirmed txs)
            if (refundSatoshis > 60) {
                recipients.add(new Recipient(fromAddress, refundSatoshis));
            }
        }

        // Create transaction
        Transaction txObj = TransactionBuilder.createRawTx(histories, recipients, blockHeight, blockHash);

        // Sign each history transcation
        for (int i = 0, length = histories.size(); i < length; i++) {
            txObj = TransactionBuilder.signTx(txObj, i, privateKey);
        }

        // Convert it to hex string
        String txHexString = TransactionBuilder.serializeTx(txObj);

        // Post it to the api
        if (config.getIntProperty("sendType") == 1) {
            Map<String, Object> map = new HashMap<>();
            map.put("method", "sendrawtransaction");
            map.put("params", new Object[]{txHexString});
            return retrofitClient.execute(zenWalletRpc.sendrawtransaction(map)).getResult();
        } else {
            return insightService.sendRawTransaction(txHexString);
        }
    }

    public String signrawtransaction(Transaction transaction) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("method", "createrawtransaction");
        map.put("params", new Object[]{transaction});
        return retrofitClient.execute(zenWalletRpc.signrawtransaction(map)).getResult();
    }

    public List<TXUnspent> listunspent(String url) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("method", "listunspent");
        return retrofitClient.execute(zenWalletRpc.listunspent(map)).getResult();
    }
}
