package vkr.planner.service.mapper;

import jakarta.annotation.PostConstruct;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vkr.planner.exception.UnknownTypeException;
import vkr.planner.service.CheckService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RequestTypeMapper {
    public static final String UNKNOWN_TYPE_MESSAGE = "Неизвестный тип объекта запроса!";
    @Autowired
    private final List<CheckService> requestTypes;

    private Map<String, CheckService> requestTypeMappingCheckService;

    public RequestTypeMapper(List<CheckService> requestTypes) {
        this.requestTypes = requestTypes;
    }

    @PostConstruct
    public void init() {
        requestTypeMappingCheckService = requestTypes.stream()
                .collect(Collectors.toMap(CheckService::getRequestType, Function.identity()));
    }
    @NotNull
    public CheckService getCheckServiceByRequestType(String requestType) throws UnknownTypeException {
        return Optional.ofNullable(requestTypeMappingCheckService.get(requestType)).orElseThrow(() ->
                new UnknownTypeException(UNKNOWN_TYPE_MESSAGE));
    }
}
