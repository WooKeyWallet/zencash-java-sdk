package wookey.wallet.entity.rpc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ScriptPubKey {

    @JsonProperty("asm")
    private String asm;

    @JsonProperty("hex")
    private String hex;

    // Number of required signatures
    @JsonProperty("reqSigs")
    private int reqSigs;

    // The type, eg pubkeyhash
    @JsonProperty("type")
    private String type;

    // array of zencash addresses
    @JsonProperty("addresses")
    private List<String> addresses;
}
