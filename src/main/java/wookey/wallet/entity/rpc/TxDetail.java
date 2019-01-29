package wookey.wallet.entity.rpc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TxDetail {

    // transaction size in bytes
    @JsonProperty("size")
    private int size;

    // transaction fee in zencash
    @JsonProperty("fee")
    private double fee;

    //  local time transaction entered pool in seconds since 1 Jan 1970 GMT
    @JsonProperty("time")
    private long time;

    // block height when transaction entered pool
    @JsonProperty("height")
    private int height;

    // priority when transaction entered pool
    @JsonProperty("startingpriority")
    private double startingpriority;

    // transaction priority now
    @JsonProperty("currentpriority")
    private double currentpriority;

    // unconfirmed transactions used as inputs for this transaction (parent transaction id)
    @JsonProperty("depends")
    private List<String> depends;
}
