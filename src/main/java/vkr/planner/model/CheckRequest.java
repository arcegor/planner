package vkr.planner.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CheckRequest implements Serializable {

    @JsonProperty(value = "requestType")
    private String requestType;

    @JsonProperty(value = "requestData")
    private String requestData;

    @JsonProperty(value = "requestFile")
    private MultipartFile requestFile;

    public CheckRequest(String requestType, String requestData) {
        this.requestType = requestType;
        this.requestData = requestData;
    }
}
