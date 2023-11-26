package vkr.planner.service.mapper;

import jakarta.annotation.PostConstruct;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vkr.planner.convert.TechnicalDescriptionBuilder;
import vkr.planner.exception.UnknownTypeException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TechnicalDescriptionMapper {
        public static final String UNKNOWN_TYPE_MESSAGE = "Неизвестный тип проекта!";
        @Autowired
        private final List<TechnicalDescriptionBuilder> technicalDescriptionBuilders;
        private Map<String, TechnicalDescriptionBuilder> technicalDescriptionBuilderMap;

        public TechnicalDescriptionMapper(List<TechnicalDescriptionBuilder> technicalDescriptionBuilders) {
            this.technicalDescriptionBuilders = technicalDescriptionBuilders;
        }
        @PostConstruct
        public void init() {
            technicalDescriptionBuilderMap = technicalDescriptionBuilders.stream()
                    .collect(Collectors.toMap(TechnicalDescriptionBuilder::getBuilderType, Function.identity()));
        }
        @NotNull
        public TechnicalDescriptionBuilder getBuilderByProjectType(String projectType) throws UnknownTypeException {
            return Optional.ofNullable(technicalDescriptionBuilderMap.get(projectType)).orElseThrow(() ->
                    new UnknownTypeException(UNKNOWN_TYPE_MESSAGE));
        }
}
