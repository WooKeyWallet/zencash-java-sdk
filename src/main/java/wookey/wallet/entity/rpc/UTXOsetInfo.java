package wookey.wallet.entity.rpc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UTXOsetInfo {


    /**
     * "height": 309970,
     * "bestblock": "00000000057884c488677751eeaedf2b363e0774f171189e5dc5f2e0e8ccd422",
     * "transactions": 382765,
     * "txouts": 2632600,
     * "bytes_serialized": 177155528,
     * "hash_serialized": "05443c49b8bd26c3f379ca6e68ce353ef3b12d4b64ceacd59e79be6a27e51e9f",
     * "total_amount": 3732325.18933754
     */
    // The current block height (index)
    @JsonProperty("height")
    private long height;

    // the best block hash hex
    @JsonProperty("bestblock")
    private String bestblock;

    // The number of transactions
    @JsonProperty("transactions")
    private long transactions;

    // The number of output transactions
    @JsonProperty("txouts")
    private long txouts;

    // The serialized size
    @JsonProperty("bytes_serialized")
    private long bytesSerialized;

    // The serialized hash
    @JsonProperty("hash_serialized")
    private String hashSerialized;

    // The total amount
    @JsonProperty("total_amount")
    private double totalAmount;
}
