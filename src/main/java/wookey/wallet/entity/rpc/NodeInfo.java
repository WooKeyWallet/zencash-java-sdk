package wookey.wallet.entity.rpc;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class NodeInfo implements Serializable {

    // total bytes recieved
    @JsonProperty("addednode")
    private String addednode;

    // total bytes sent
    @JsonProperty("connected")
    private String connected;

    // time in miliseconds
    @JsonProperty("addresses")
    private List<NodeAddress> addresses;

}
