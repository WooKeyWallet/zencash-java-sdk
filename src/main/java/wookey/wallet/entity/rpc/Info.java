package wookey.wallet.entity.rpc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Info {

    @JsonProperty("version")
    private Long version;

    @JsonProperty("protocolversion")
    private Long protocolversion;

    @JsonProperty("walletversion")
    private Long walletversion;

    @JsonProperty("balance")
    private Double balance;

    @JsonProperty("blocks")
    private Long blocks;

    @JsonProperty("timeoffset")
    private Integer timeoffset;

    @JsonProperty("connections")
    private int connections;

    @JsonProperty("proxy")
    private String proxy;

    @JsonProperty("difficulty")
    private Double difficulty;

    @JsonProperty("testnet")
    private Boolean testnet;

    @JsonProperty("keypoololdest")
    private Long keypoololdest;

    @JsonProperty("keypoolsize")
    private Long keypoolsize;

    @JsonProperty("paytxfee")
    private Double paytxfee;

    @JsonProperty("relayfee")
    private Double relayfee;

    @JsonProperty("errors")
    private String errors;
}
