package vkr.planner.service;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.stereotype.Service;
import vkr.planner.exception.UnknownTypeException;
import vkr.planner.model.Request;
import vkr.planner.model.Plan;

import java.io.IOException;

@Service
public interface ValidationService {
    Plan validateProject(Request request) throws IOException, InvalidFormatException, UnknownTypeException;
}
