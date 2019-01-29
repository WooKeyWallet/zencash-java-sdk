package wookey.wallet.rpc;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import wookey.wallet.entity.rpc.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ZenRpc {

    @POST("/")
    Call<RpcResult<Info>> getInfo(@Body Map<String, Object> queryMap);

    @POST("/")
    Call<RpcResult<NetworkInfo>> getNetworkInfo(@Body Map<String, Object> queryMap);

    @POST("/")
    Call<RpcResult<NetTotals>> getNetTotals(@Body Map<String, Object> queryMap);

    @POST("/")
    Call<RpcResult<List<NodeInfo>>> getAddedNodeInfo(@Body Map<String, Object> queryMap);

    @POST("/")
    Call<RpcResult<List<PeerInfo>>> getPeerInfo(@Body Map<String, Object> queryMap);

    @POST("/")
    Call<RpcResult<Integer>> getConnectionCount(@Body Map<String, Object> queryMap);

    @POST("/")
    Call<RpcResult<String>> stop(@Body Map<String, Object> queryMap);

    @POST("/")
    Call<RpcResult<BlockchainInfo>> getBlockchainInfo(@Body Map<String, Object> queryMap);

    @POST("/")
    Call<RpcResult<String>> getBlockhash(@Body Map<String, Object> queryMap);

    @POST("/")
    Call<RpcResult<String>> getBestBlockhash(@Body Map<String, Object> queryMap);

    @POST("/")
    Call<RpcResult<Integer>> getBlockcount(@Body Map<String, Object> queryMap);

    @POST("/")
    Call<RpcResult<String>> getBlockHeader(@Body Map<String, Object> queryMap);

    @POST("/")
    Call<RpcResult<BlockHeader>> getBlockHeaderVerbose(@Body Map<String, Object> queryMap);

    @POST("/")
    Call<RpcResult<List<Chaintip>>> getChaintips(@Body Map<String, Object> queryMap);

    @POST("/")
    Call<RpcResult<Double>> getDifficulty(@Body Map<String, Object> queryMap);

    @POST("/")
    Call<RpcResult<MempoolInfo>> getMempoolInfo(@Body Map<String, Object> queryMap);

    @POST("/")
    Call<RpcResult<List<String>>> getRawMempool(@Body Map<String, Object> queryMap);

    @POST("/")
    Call<RpcResult<Map<String, TxDetail>>> getRawMempoolVerbose(@Body Map<String, Object> queryMap);

    @POST("/")
    Call<RpcResult<Optional<UTXODetail>>> getTxOut(@Body Map<String, Object> queryMap);

    @POST("/")
    Call<RpcResult<UTXOsetInfo>> getTxoutSetInfo(@Body Map<String, Object> queryMap);


}
