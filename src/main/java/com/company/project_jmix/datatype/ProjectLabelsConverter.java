package com.company.project_jmix.datatype;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;
@Converter(autoApply = true)

public class ProjectLabelsConverter implements AttributeConverter<ProjectLabels, String> {
    @Override
    public String convertToDatabaseColumn(ProjectLabels attribute) {
        if (attribute == null){
            return null;
        }
            if (attribute instanceof ProjectLabels) {
                return Joiner.on(", ")
                        .join(((ProjectLabels) attribute).getLabels());
            }
            return "";
        }

    @Override
    public ProjectLabels convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        List<String> labels = Splitter.on(",")
                .omitEmptyStrings()
                .trimResults()
                .splitToList(dbData);

        return new ProjectLabels(labels);
    }
}
