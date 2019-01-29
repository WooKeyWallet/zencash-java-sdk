package wookey.wallet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TXHistory implements Serializable {
    private String txid;
    private long vout;
    private String scriptPubKey;
}
