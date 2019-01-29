package wookey.wallet.entity.insight;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import wookey.wallet.entity.rpc.Info;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class InsightInfo implements Serializable {

    @JsonProperty("info")
    private Info info;
}
