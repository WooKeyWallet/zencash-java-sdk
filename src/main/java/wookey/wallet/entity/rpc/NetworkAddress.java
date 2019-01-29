package wookey.wallet.entity.rpc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class NetworkAddress implements Serializable {

    @JsonProperty("address")
    private String address;

    @JsonProperty("port")
    private Integer port;

    @JsonProperty("score")
    private Integer score;

}
