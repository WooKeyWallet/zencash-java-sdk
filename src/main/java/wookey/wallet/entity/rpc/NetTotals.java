package wookey.wallet.entity.rpc;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Represents information about network traffic, including bytes in, bytes out
 * and current time
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class NetTotals implements Serializable {

    // total bytes recieved
    @JsonProperty("totalbytesrecv")
    private Long totalbytesrecv;

    // total bytes sent
    @JsonProperty("totalbytessent")
    private Long totalbytessent;

    // time in miliseconds
    @JsonProperty("timemillis")
    private Long timemillis;

}
