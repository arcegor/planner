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
@ToString(exclude = "requestFile")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CheckRequest implements Serializable {

    @JsonProperty(value = "requestType")
    private String requestType;

    @JsonProperty(value = "requestRules")
    private String requestRules;

    @JsonProperty(value = "requestFile")
    private MultipartFile requestFile;

    @JsonProperty(value = "resultType")
    private ResultType resultType;

    @JsonProperty(value = "result")
    private String result;

    public CheckRequest(String requestType, String requestRules) {
        this.requestType = requestType;
        this.requestRules = requestRules;
    }

    public enum ResultType{
        SUCCESS,
        FAILED
    }
}
