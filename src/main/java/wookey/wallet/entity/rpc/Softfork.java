package wookey.wallet.entity.rpc;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Softfork {

    // name of the softfork
    @JsonProperty("id")
    private String id;

    // Peer index
    @JsonProperty("version")
    private int version;

    // Peer index
    @JsonProperty("enforce")
    private SoftforkStatus enforce;

    // Peer index
    @JsonProperty("reject")
    private SoftforkStatus reject;

}
