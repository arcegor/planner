package vkr.planner.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
@Data
@ToString(exclude = "requestFile")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CheckRequest implements Serializable {

    @JsonProperty(value = "projectType")
    private String projectType;
    @JsonProperty(value = "requestRules")
    private String requestRules;

    @JsonProperty(value = "requestFile")
    private MultipartFile[] requestFile;

    @JsonProperty(value = "resultType")
    private ResultType resultType;

    @JsonProperty(value = "result")
    private String result;

    public CheckRequest(String projectType, String requestRules) {
        this.projectType = projectType;
        this.requestRules = requestRules;
    }

    public enum ResultType{
        SUCCESS,
        FAILED
    }
}
