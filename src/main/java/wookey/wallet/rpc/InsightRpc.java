package wookey.wallet.rpc;

import wookey.wallet.entity.TXUnspent;
import wookey.wallet.entity.insight.Balance;
import wookey.wallet.entity.insight.BlockHash;
import wookey.wallet.entity.insight.InsightInfo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;
import java.util.Map;

public interface InsightRpc {

    @GET("status?q=getInfo")
    Call<InsightInfo> getInfo();

    @GET("block-index/{blockHeight}")
    Call<BlockHash> getBlockHash(@Path("blockHeight") long blockHeight);

    @GET("addr/" + "{address}" + "/?noTxList=1")
    Call<Balance> getBalance(@Path("address") String address);

    @GET("addr/" + "{address}" + "/utxo")
    Call<List<TXUnspent>> getUnspent(@Path("address") String address);

    @POST("tx/send")
    Call<String> sendRawTransaction(@Body Map<String, Object> queryMap);

}
