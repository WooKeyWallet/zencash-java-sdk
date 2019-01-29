package wookey.wallet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipient {

    private String address;
    private long satoshis;
    private byte[] data;

    public Recipient(String address, long satoshis) {
        this.address = address;
        this.satoshis = satoshis;
    }
}
