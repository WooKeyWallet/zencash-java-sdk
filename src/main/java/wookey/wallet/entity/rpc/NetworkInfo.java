package wookey.wallet.entity.rpc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Returns an object containing various state info regarding P2P networking.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class NetworkInfo implements Serializable {

    // the server version
    @JsonProperty("version")
    private Integer version;

    //MagicBean:x.y.z[-v] the server subversion string
    @JsonProperty("subversion")
    private String subversion;

    // format: xxxxx - the protocol version
    @JsonProperty("protocolversion")
    private Integer protocolversion;

    // format:xxxxxxxxxxxxxxxx - the services we offer to the network
    @JsonProperty("localservices")
    private Integer localservices;

    // format: xxxxx - time offset
    @JsonProperty("timeoffset")
    private Integer timeoffset;

    // format: xxxxx - the number of connections
    @JsonProperty("connections")
    private Integer connections;

    // true if the certificate of the current node is verified
    @JsonProperty("tls_cert_verified")
    private Boolean tlsCertVerified;

    @JsonProperty("networks")
    private List<Network> networks;

    // format: x.xxxxxxxx - minimum relay fee for non-free transactions in btc/kb
    @JsonProperty("relayfee")
    private Double relayfee;

    // list of local addresses
    @JsonProperty("localaddresses")
    private NetworkAddress[] localaddresses;

    // any network warnings (such as alert messages)
    @JsonProperty("warnings")
    private String warnings;

}
