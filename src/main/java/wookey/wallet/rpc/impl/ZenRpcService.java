package wookey.wallet.rpc.impl;

import wookey.wallet.config.Config;
import wookey.wallet.entity.rpc.*;
import wookey.wallet.rpc.ZenRpc;
import wookey.wallet.utils.http.RetrofitClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ZenRpcService {

    private RetrofitClient retrofitClient;

    private ZenRpc zenRpc;

    public ZenRpcService() {
        init();
    }

    public Info getInfo() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("method", "getinfo");
        return retrofitClient.execute(zenRpc.getInfo(map)).getResult();
    }


    public NetworkInfo getNetworkInfo() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("method", "getnetworkinfo");
        return retrofitClient.execute(zenRpc.getNetworkInfo(map)).getResult();
    }


    public NetTotals getNetTotals() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("method", "getnettotals");
        return retrofitClient.execute(zenRpc.getNetTotals(map)).getResult();
    }


    public List<PeerInfo> getPeerInfo() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("method", "getpeerinfo");
        return retrofitClient.execute(zenRpc.getPeerInfo(map)).getResult();
    }


    public Integer getConnectionCount() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("method", "getconnectioncount");
        return retrofitClient.execute(zenRpc.getConnectionCount(map)).getResult();
    }


    public String stop() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("method", "stop");
        return retrofitClient.execute(zenRpc.stop(map)).getResult();
    }


    public BlockchainInfo getBlockchainInfo() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("method", "getblockchaininfo");
        return retrofitClient.execute(zenRpc.getBlockchainInfo(map)).getResult();
    }


    public String getBlockhash(int blockHeight) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("method", "getblockhash");
        map.put("params", new Object[]{blockHeight});
        return retrofitClient.execute(zenRpc.getBlockhash(map)).getResult();
    }


    public String getBestBlockhash() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("method", "getbestblockhash");
        return retrofitClient.execute(zenRpc.getBestBlockhash(map)).getResult();
    }


    public Integer getBlockcount() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("method", "getblockcount");
        return retrofitClient.execute(zenRpc.getBlockcount(map)).getResult();
    }


    public String getBlockHeader(String hash) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("method", "getblockheader");
        map.put("params", new Object[]{hash});
        return retrofitClient.execute(zenRpc.getBlockHeader(map)).getResult();
    }


    public BlockHeader getBlockHeaderVerbose(String hash) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("method", "getblockheader");
        return retrofitClient.execute(zenRpc.getBlockHeaderVerbose(map)).getResult();
    }


    public List<Chaintip> getChaintips() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("method", "getchaintips");
        return retrofitClient.execute(zenRpc.getChaintips(map)).getResult();
    }


    public Double getDifficulty() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("method", "getdifficulty");
        return retrofitClient.execute(zenRpc.getDifficulty(map)).getResult();
    }


    public MempoolInfo getMempoolInfo() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("method", "getmempoolinfo");
        return retrofitClient.execute(zenRpc.getMempoolInfo(map)).getResult();
    }


    public List<String> getRawMempool() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("method", "getrawmempool");
        return retrofitClient.execute(zenRpc.getRawMempool(map)).getResult();
    }


    public Map<String, TxDetail> getRawMempoolVerbose() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("method", "getrawmempool");
        return retrofitClient.execute(zenRpc.getRawMempoolVerbose(map)).getResult();
    }


    public Optional<UTXODetail> getTxOut(String txid, int vout) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("method", "gettxout");
        return retrofitClient.execute(zenRpc.getTxOut(map)).getResult();
    }


    public UTXOsetInfo getTxoutSetInfo() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("method", "gettxoutsetinfo");
        return retrofitClient.execute(zenRpc.getTxoutSetInfo(map)).getResult();
    }

    private void init() {

        Config config = Config.getInstance();
        final String rpcuser = config.getProperty("rpcuser");
        final String rpcpassword = config.getProperty("rpcpassword");
        final String serverurl = config.getProperty("serverurl");

        retrofitClient = new RetrofitClient();
        zenRpc = retrofitClient.createService(ZenRpc.class, serverurl, rpcuser, rpcpassword);

    }
}
