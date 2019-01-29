package wookey.wallet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction implements Serializable {
    private Long locktime=new Long(0);
    private Long version=new Long(1);
    private List<TxInput> ins;
    private List<TxOut> outs;
}
