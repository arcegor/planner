package vkr.planner.service;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.stereotype.Service;
import vkr.planner.exception.UnknownTypeException;
import vkr.planner.model.Request;

import java.io.IOException;

@Service
public interface ValidateService {
    String validateProject(Request request) throws IOException, InvalidFormatException, UnknownTypeException;
}
