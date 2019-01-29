package wookey.wallet.entity.rpc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class NodeAddress implements Serializable {

    // The bitcoin server host and port
    @JsonProperty("address")
    private String address;

    //connection, inbound or outbound
    @JsonProperty("connected")
    private String connected;

}
