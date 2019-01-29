package wookey.wallet.entity.rpc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UTXODetail {

    // the block hash
    @JsonProperty("bestblock")
    private String bestblock;

    // The number of confirmations
    @JsonProperty("confirmations")
    private int confirmations;

    // The transaction value in zencash
    @JsonProperty("value")
    private String value;

    // Script Public Key
    @JsonProperty("scriptPubKey")
    private ScriptPubKey scriptPubKey;

    // The version
    @JsonProperty("version")
    private int version;

    // Coinbase or not
    @JsonProperty("coinbase")
    private boolean coinbase;

}
