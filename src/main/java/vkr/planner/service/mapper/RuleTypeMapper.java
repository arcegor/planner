package vkr.planner.service.mapper;

import jakarta.annotation.PostConstruct;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vkr.planner.exception.UnknownTypeException;
import vkr.planner.model.woods.RuleType;
import vkr.planner.service.RulesCheckService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
@Service
public class RuleTypeMapper {
    public static final RuleType UNKNOWN_TYPE_MESSAGE = RuleType.UNKNOWN;
    @Autowired
    private final List<RulesCheckService> rulesCheckServices;
    private Map<RuleType, RulesCheckService> rulesMappingRulesCheckService;

    public RuleTypeMapper(List<RulesCheckService> rulesCheckServices) {
        this.rulesCheckServices = rulesCheckServices;
    }
    @PostConstruct
    public void init() {
        rulesMappingRulesCheckService = rulesCheckServices.stream()
                .collect(Collectors.toMap(RulesCheckService::getRuleType, Function.identity()));
    }
    @NotNull
    public RulesCheckService getRulesCheckServiceByRuleType(RuleType requestType) throws UnknownTypeException {
        return Optional.ofNullable(rulesMappingRulesCheckService.get(requestType)).orElseThrow(() ->
                new UnknownTypeException(UNKNOWN_TYPE_MESSAGE.description));
    }
}
