package wookey.wallet.rpc;

import wookey.wallet.entity.TXUnspent;
import wookey.wallet.entity.rpc.RpcResult;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import java.util.List;
import java.util.Map;

public interface ZenWalletRpc {

    @POST("/")
    Call<RpcResult<String>> sendrawtransaction(@Body Map<String, Object> queryMap);

    @POST("/")
    Call<RpcResult<String>> signrawtransaction(@Body Map<String, Object> queryMap);

    @POST("/")
    Call<RpcResult<String>> createrawtransaction(@Body Map<String, Object> queryMap);

    @POST("/")
    Call<RpcResult<List<TXUnspent>>> listunspent(@Body Map<String, Object> queryMap);
}
