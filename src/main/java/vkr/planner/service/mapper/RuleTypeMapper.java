package vkr.planner.service.mapper;

import jakarta.annotation.PostConstruct;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vkr.planner.exception.UnknownTypeException;
import vkr.planner.service.ValidateByRuleService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
@Service
public class RuleTypeMapper {
    public static final String UNKNOWN_TYPE_MESSAGE = "Неизвестное правило!";
    @Autowired
    private final List<ValidateByRuleService> validateByRuleServices;
    private Map<String, ValidateByRuleService> rulesMappingRulesCheckService;

    public RuleTypeMapper(List<ValidateByRuleService> validateByRuleServices) {
        this.validateByRuleServices = validateByRuleServices;
    }
    @PostConstruct
    public void init() {
        rulesMappingRulesCheckService = validateByRuleServices.stream()
                .collect(Collectors.toMap(ValidateByRuleService::getRuleType, Function.identity()));
    }
    @NotNull
    public ValidateByRuleService getRulesCheckServiceByRuleType(String ruleType) throws UnknownTypeException {
        return Optional.ofNullable(rulesMappingRulesCheckService.get(ruleType)).orElseThrow(() ->
                new UnknownTypeException(UNKNOWN_TYPE_MESSAGE));
    }
}
