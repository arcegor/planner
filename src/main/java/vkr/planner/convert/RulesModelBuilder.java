package vkr.planner.convert;

import org.springframework.stereotype.Component;
import vkr.planner.model.woods.RuleType;
import vkr.planner.model.woods.RulesModel;
import vkr.planner.model.woods.WoodsRuleSet;

import java.util.ArrayList;

@Component
public class RulesModelBuilder {
    public RulesModel build(WoodsRuleSet woodsRuleSet){
        RulesModel rulesModel = new RulesModel();
        rulesModel.setRuleTypes(new ArrayList<>()); // Порядок обязателен !!!
        if (woodsRuleSet.getIsValidateKksToInsulate()){
            rulesModel.setKksToInsulate(woodsRuleSet.getKksToInsulate());
            rulesModel.getRuleTypes().add(RuleType.KKS);
        }
        if (woodsRuleSet.getNeighboringAreasToCheck() != null){
            rulesModel.setNeighboringAreasToCheck(woodsRuleSet.getNeighboringAreasToCheck());
            rulesModel.getRuleTypes().add(RuleType.AREA);
        }
        if (woodsRuleSet.getMinHeightOfWoodsToCreate() != null){
            rulesModel.setMinHeightOfWoodsToCreate(woodsRuleSet.getMinHeightOfWoodsToCreate());
            rulesModel.getRuleTypes().add(RuleType.LEVEL);
        }
        if (rulesModel.getRuleTypes().isEmpty())
            rulesModel.setIsEmpty(Boolean.TRUE);
        return rulesModel;
    }
}
