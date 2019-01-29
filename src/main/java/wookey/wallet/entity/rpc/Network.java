package wookey.wallet.entity.rpc;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Network implements Serializable {

    //network (ipv4, ipv6 or onion)
    @JsonProperty("name")
    private String name;

    //is the network limited using -onlynet?
    @JsonProperty("limited")
    private Boolean limited;

    //is the network reachable?
    @JsonProperty("reachable")
    private Boolean reachable;

    //the proxy that is used for this network, or empty if none
    @JsonProperty("proxy")
    private String proxy;

    //the proxy that is used for this network, or empty if none
    @JsonProperty("proxy_randomize_credentials")
    private boolean proxyRandomizeCredentials;

}
