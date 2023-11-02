package vkr.planner.service;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import vkr.planner.exception.ConvertToDtoException;
import vkr.planner.model.CheckRequest;
import vkr.planner.model.DocumentDto;
import vkr.planner.model.Plan;
import vkr.planner.model.Rule;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public interface CheckService<T> {
    boolean checkPlanByRule(Plan plan, @NotNull Rule rule);

    String check(CheckRequest checkRequest) throws IOException, InvalidFormatException, ConvertToDtoException;
    String getRequestType();
    default T convertToModel(Map<Integer, List<String>> data, T obj){
        return obj;
    }

}
