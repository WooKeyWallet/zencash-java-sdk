package wookey.wallet.entity.rpc;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class BlockchainInfo {

    // current network name as defined in BIP70 (main, test, regtest)
    @JsonProperty("chain")
    private String chain;

    //  the current number of blocks processed in the server
    @JsonProperty("blocks")
    private long blocks;

    // the current number of headers we have validated
    @JsonProperty("headers")
    private int headers;

    // the hash of the currently best block
    @JsonProperty("bestblockhash")
    private String bestblockhash;

    // the current difficulty
    @JsonProperty("difficulty")
    private double difficulty;

    // estimate of verification progress [0..1]
    @JsonProperty("verificationprogress")
    private double verificationprogress;

    // total amount of work in active chain, in hexadecimal
    @JsonProperty("chainwork")
    private String chainwork;

    // True if we're running in -prune mode.
    @JsonProperty("pruned")
    private boolean pruned;

    // the current number of note commitments in the commitment tree
    @JsonProperty("commitments")
    private int commitments;

    // status of softforks in progress
    @JsonProperty("softforks")
    private List<Softfork> softforks;

    // status of BIP9 softforks in progress
    @JsonProperty("bip9_softforks")
    private Bip9SoftforkStatus bip9Softforks;

}
