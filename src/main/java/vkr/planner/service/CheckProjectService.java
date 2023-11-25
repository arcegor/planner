package vkr.planner.service;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.stereotype.Service;
import vkr.planner.exception.ConvertToDtoException;
import vkr.planner.exception.UnknownTypeException;
import vkr.planner.model.CheckRequest;

import java.io.IOException;

@Service
public interface CheckProjectService {
    String check(CheckRequest checkRequest) throws IOException, InvalidFormatException, ConvertToDtoException, UnknownTypeException;
    String getProjectType();
}
