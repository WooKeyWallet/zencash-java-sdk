package wookey.wallet.entity.rpc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class BlockHeader {

    // the block hash (same as provided)
    @JsonProperty("hash")
    private String hash;

    // The number of confirmations, or -1 if the block is not on the main chain
    @JsonProperty("confirmations")
    private int confirmations;

    // he block height or index
    @JsonProperty("height")
    private int height;

    // The block version
    @JsonProperty("version")
    private int version;

    // The merkle root
    @JsonProperty("merkleroot")
    private String merkleroot;

    // The block time in seconds since epoch (Jan 1 1970 GMT)
    @JsonProperty("time")
    private long time;

    // The nonce
    @JsonProperty("nonce")
    private String nonce;

    // e.g:"1d00ffff"  The bits
    @JsonProperty("bits")
    private String bits;

    // The difficulty
    @JsonProperty("difficulty")
    private double difficulty;

    // The difficulty
    @JsonProperty("chainwork")
    private String chainwork;

    // The hash of the previous block
    @JsonProperty("previousblockhash")
    private String previousblockhash;

    // The hash of the next block
    @JsonProperty("nextblockhash")
    private String nextblockhash;

    // The solution
    @JsonProperty("solution")
    private String solution;
}
