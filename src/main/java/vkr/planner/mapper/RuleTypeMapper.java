package vkr.planner.mapper;

import jakarta.annotation.PostConstruct;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vkr.planner.exception.UnknownTypeException;
import vkr.planner.service.RuleService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
@Service
public class RuleTypeMapper {
    public static final String UNKNOWN_TYPE_MESSAGE = "Неизвестное правило!";
    @Autowired
    private final List<RuleService> ruleServices;
    private Map<String, RuleService> rulesMappingRulesCheckService;

    public RuleTypeMapper(List<RuleService> ruleServices) {
        this.ruleServices = ruleServices;
    }
    @PostConstruct
    public void init() {
        rulesMappingRulesCheckService = ruleServices.stream()
                .collect(Collectors.toMap(RuleService::getRuleType, Function.identity()));
    }
    @NotNull
    public RuleService getRuleServiceByRuleType(String ruleType) throws UnknownTypeException {
        return Optional.ofNullable(rulesMappingRulesCheckService.get(ruleType)).orElseThrow(() ->
                new UnknownTypeException(UNKNOWN_TYPE_MESSAGE));
    }
}
