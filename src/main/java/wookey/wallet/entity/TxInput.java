package wookey.wallet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TxInput implements Serializable {
    private TxLastOut out;
    private String script;
    private String prevScriptPubKey;
    private String sequence;
}
