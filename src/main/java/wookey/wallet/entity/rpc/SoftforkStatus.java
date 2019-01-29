package wookey.wallet.entity.rpc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SoftforkStatus {

    // one of "defined", "started", "lockedin", "active", "failed"
    // true if threshold reached
    @JsonProperty("status")
    private boolean status;

    // number of blocks with the new version found
    @JsonProperty("found")
    private int found;

    // number of blocks required to trigger
    @JsonProperty("required")
    private int required;

    // maximum size of examined window of recent blocks
    @JsonProperty("window")
    private int window;
}
