package wookey.wallet.entity.insight;


import lombok.Data;

import java.io.Serializable;

@Data
public class Balance implements Serializable {

    private String addrStr;

    private Double balance;

    private Double balanceSat;

    private Double totalReceived;

    private Double totalReceivedSat;

    private Double totalSent;

    private Double totalSentSat;

    private Integer txApperances;

    private Integer unconfirmedBalance;

    private Integer unconfirmedBalanceSat;

    private Integer unconfirmedTxApperances;
}
