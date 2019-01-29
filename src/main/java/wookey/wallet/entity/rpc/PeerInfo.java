package wookey.wallet.entity.rpc;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class PeerInfo {

    // Peer index
    @JsonProperty("id")
    private int id;

    // he ip address and port of the peer (ip:port)
    @JsonProperty("addr")
    private String address;

    // local address
    @JsonProperty("addrlocal")
    private String localAddress;

    // the services offered
    @JsonProperty("services")
    private String services;

    // SoftforkStatus of TLS connection
    @JsonProperty("tls_established")
    private boolean tlsEstablished;

    // SoftforkStatus of peer certificate. Will be true if a peer certificate
    // can be verified with some trusted root certs
    @JsonProperty("tls_verified")
    private boolean tlsVerified;

    // The time in seconds since epoch (Jan 1 1970 GMT) of the last send
    @JsonProperty("lastsend")
    private long lastsend;

    // The time in seconds since epoch (Jan 1 1970 GMT) of the last receive
    @JsonProperty("lastrecv")
    private long lastrecv;

    // The total bytes sent
    @JsonProperty("bytessent")
    private long bytessent;

    // The total bytes receive
    @JsonProperty("bytesrecv")
    private long bytesrecv;

    // tThe connection time in seconds since epoch (Jan 1 1970 GMT)
    @JsonProperty("conntime")
    private long conntime;

    // The time offset in seconds
    @JsonProperty("timeoffset")
    private String timeoffset;

    // ping time
    @JsonProperty("pingtime")
    private double pingtime;

    // ping wait
    @JsonProperty("pingwait")
    private double pingwait;

    // The peer version, such as 170002
    @JsonProperty("version")
    private int version;

    // The string version
    @JsonProperty("subver")
    private String subver;

    // Inbound (true) or Outbound (false)
    @JsonProperty("inbound")
    private boolean inbound;

    // The starting height (block) of the peer
    @JsonProperty("startingheight")
    private int startingheight;

    // The ban score
    @JsonProperty("banscore")
    private int banscore;

    // The last header we have in common with this peer
    @JsonProperty("synced_headers")
    private int syncedHeaders;

    // The last block we have in common with this peer
    @JsonProperty("synced_blocks")
    private int syncedBlocks;

    // The heights of blocks we're currently asking from this peer
    @JsonProperty("inflight")
    private List<Integer> inflight;

    // The heights of blocks we're currently asking from this peer
    @JsonProperty("whitelisted")
    private boolean whitelisted;

}
