package wookey.wallet.entity.rpc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class MempoolInfo {

    // Current tx count
    @JsonProperty("size")
    private long size;

    // Sum of all tx sizes
    @JsonProperty("bytes")
    private long bytes;

    // Total memory usage for the mempool
    @JsonProperty("usage")
    private long usage;
}
