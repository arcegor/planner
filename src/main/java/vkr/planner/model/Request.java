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
public class Request implements Serializable {

    @JsonProperty(value = "projectType")
    private String projectType;

    @JsonProperty(value = "requestFile")
    private MultipartFile[] requestFile;

    public Request(String projectType) {
        this.projectType = projectType;
    }
}
