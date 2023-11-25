package vkr.planner.service.mapper;

import jakarta.annotation.PostConstruct;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vkr.planner.exception.UnknownTypeException;
import vkr.planner.service.CheckRuleService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
@Service
public class RuleTypeMapper {
    public static final String UNKNOWN_TYPE_MESSAGE = "Неизвестное правило!";
    @Autowired
    private final List<CheckRuleService> checkRuleServices;
    private Map<String, CheckRuleService> rulesMappingRulesCheckService;

    public RuleTypeMapper(List<CheckRuleService> checkRuleServices) {
        this.checkRuleServices = checkRuleServices;
    }
    @PostConstruct
    public void init() {
        rulesMappingRulesCheckService = checkRuleServices.stream()
                .collect(Collectors.toMap(CheckRuleService::getRuleType, Function.identity()));
    }
    @NotNull
    public CheckRuleService getRulesCheckServiceByRuleType(String ruleType) throws UnknownTypeException {
        return Optional.ofNullable(rulesMappingRulesCheckService.get(ruleType)).orElseThrow(() ->
                new UnknownTypeException(UNKNOWN_TYPE_MESSAGE));
    }
}
