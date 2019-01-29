package wookey.wallet.entity.rpc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Chaintip {

    // height of the chain tip
    @JsonProperty("height")
    private int height;

    // block hash of the tip
    @JsonProperty("hash")
    private String hash;

    // zero for main chain | length of branch connecting the tip to the main chain
    @JsonProperty("branchlen")
    private int branchlen;

    /**
     * status of the chain (active, valid-fork, valid-headers, headers-only, invalid)
     * 1.  "invalid"               This branch contains at least one invalid block
     * 2.  "headers-only"          Not all blocks for this branch are available, but the headers are valid
     * 3.  "valid-headers"         All blocks are available for this branch, but they were never fully validated
     * 4.  "valid-fork"            This branch is not part of the active chain, but is fully validated
     * 5.  "active"                This is the tip of the active main chain, which is certainly valid
     */
    @JsonProperty("status")
    private String status;
}
