package vkr.planner.service;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import vkr.planner.exception.ConvertToDtoException;
import vkr.planner.exception.UnknownTypeException;
import vkr.planner.model.CheckRequest;
import vkr.planner.model.schedule.Plan;
import vkr.planner.model.schedule.PlanValidationRule;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
public interface CheckService<T> {
    String check(CheckRequest checkRequest) throws IOException, InvalidFormatException, ConvertToDtoException, UnknownTypeException;
    String getRequestType();
    default T convertToModel(InputStream inputStream, T obj) throws IOException, InvalidFormatException {
        return obj;
    }
}
