package vkr.planner.service;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.stereotype.Service;
import vkr.planner.exception.ConvertToDtoException;
import vkr.planner.exception.UnknownTypeException;
import vkr.planner.model.CheckRequest;
import vkr.planner.model.schedule.ObjectType;

import java.io.IOException;
import java.io.InputStream;

@Service
public interface CheckService<T> {
    String check(CheckRequest checkRequest) throws IOException, InvalidFormatException, ConvertToDtoException, UnknownTypeException;
    ObjectType getObjectType();
    default T convertToModel(InputStream inputStream, T obj) throws IOException, InvalidFormatException {
        return obj;
    }
}
