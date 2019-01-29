package wookey.wallet.entity.rpc;

import lombok.Data;

@Data
public class RpcResult<T> {
    private T result;
    private String id;
    private String error;
}
