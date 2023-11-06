package vkr.planner.service;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import vkr.planner.exception.ConvertToDtoException;
import vkr.planner.model.CheckRequest;
import vkr.planner.model.schedule.Plan;
import vkr.planner.model.schedule.PlanValidationRule;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public interface CheckService<T> {
    boolean isPlanValidByRule(Plan plan, @NotNull PlanValidationRule planValidationRule);

    String check(CheckRequest checkRequest) throws IOException, InvalidFormatException, ConvertToDtoException;
    String getRequestType();
    default T convertToModel(CheckRequest checkRequest, T obj) throws IOException, InvalidFormatException {
        return obj;
    }
}
