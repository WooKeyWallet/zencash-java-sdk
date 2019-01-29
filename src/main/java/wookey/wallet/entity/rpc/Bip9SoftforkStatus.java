package wookey.wallet.entity.rpc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Bip9SoftforkStatus {

    // one of "defined", "started", "lockedin", "active", "failed"
    @JsonProperty("status")
    private String status;

    // the bit, 0-28, in the block version field used to signal this soft fork
    @JsonProperty("bit")
    private int bit;

    // the minimum median time past of a block at which the bit gains its meaning
    @JsonProperty("startTime")
    private long startTime;

    // the median time past of a block at which the deployment is considered failed if not yet locked in
    @JsonProperty("timeout")
    private int timeout;

}
