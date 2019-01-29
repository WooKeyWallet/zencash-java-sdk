package wookey.wallet.rpc.impl;

import wookey.wallet.config.Config;
import wookey.wallet.entity.TXUnspent;
import wookey.wallet.entity.insight.Balance;
import wookey.wallet.entity.insight.BlockHash;
import wookey.wallet.entity.insight.InsightInfo;
import wookey.wallet.rpc.InsightRpc;
import wookey.wallet.utils.http.RetrofitClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InsightService {

    private RetrofitClient retrofitClient;
    private Config config;
    private InsightRpc insightRpc;

    public InsightService() {
        init();
    }

    private void init() {
        config = Config.getInstance();
        String net = config.getProperty("net");
        final String insightUrl = config.getProperty(net + ".insightUrl");

        retrofitClient = new RetrofitClient();
        insightRpc = retrofitClient.createService(InsightRpc.class, insightUrl);
    }

    public Balance getBalance(String address) throws Exception {
        return retrofitClient.execute(insightRpc.getBalance(address));
    }

    public List<TXUnspent> getUnspent(String address) throws Exception {
        return retrofitClient.execute(insightRpc.getUnspent(address));
    }

    public InsightInfo getInfo() throws Exception {
        return retrofitClient.execute(insightRpc.getInfo());
    }

    public BlockHash getBlockHash(long blockHeight) throws Exception {
        return retrofitClient.execute(insightRpc.getBlockHash(blockHeight));
    }

    public String sendRawTransaction(String txHex) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("rawtx", txHex);
        return retrofitClient.execute(insightRpc.sendRawTransaction(map));
    }
}
