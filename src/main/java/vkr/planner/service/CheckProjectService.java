package vkr.planner.service;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.stereotype.Service;
import vkr.planner.convert.TechnicalDescriptionBuilder;
import vkr.planner.exception.ConvertToDtoException;
import vkr.planner.exception.UnknownTypeException;
import vkr.planner.model.CheckRequest;
import vkr.planner.model.schedule.TechnicalDescription;
import vkr.planner.model.tea.TechnicalDescriptionTea;

import java.io.IOException;

@Service
public interface CheckProjectService {
    String check(CheckRequest checkRequest) throws IOException, InvalidFormatException, UnknownTypeException;
}
