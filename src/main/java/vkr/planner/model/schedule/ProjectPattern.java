package vkr.planner.model.schedule;

import lombok.Data;

import java.util.List;

@Data
public abstract class ProjectPattern {
    protected ProjectType projectType;
    protected List<TaskType> taskTypes;
    protected List<RuleType> ruleTypes;
    protected TechnicalDescription technicalDescription;
}
