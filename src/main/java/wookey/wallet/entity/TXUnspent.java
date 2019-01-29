package wookey.wallet.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TXUnspent implements Serializable {
    private String txid;
    private long vout;
    private String address;
    private String scriptPubKey;
    private Double amount;
    private Boolean spendable;
    private Boolean generated;
    private Integer confirmations;
}
