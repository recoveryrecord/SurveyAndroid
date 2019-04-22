package com.recoveryrecord.surveyandroid.question;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "operation", visible = true)
@JsonSubTypes({

        @JsonSubTypes.Type(value = SimpleCondition.class, name = "equals"),
        @JsonSubTypes.Type(value = SimpleCondition.class, name = "not equals"),
        @JsonSubTypes.Type(value = SimpleCondition.class, name = "greater than"),
        @JsonSubTypes.Type(value = SimpleCondition.class, name = "greater than or equal to"),
        @JsonSubTypes.Type(value = SimpleCondition.class, name = "less than"),
        @JsonSubTypes.Type(value = SimpleCondition.class, name = "less than or equal to"),
        @JsonSubTypes.Type(value = SimpleCondition.class, name = "contains"),
        @JsonSubTypes.Type(value = SimpleCondition.class, name = "not contains"),

        @JsonSubTypes.Type(value = DecisionCondition.class, name = "and"),
        @JsonSubTypes.Type(value = DecisionCondition.class, name = "or"),

        @JsonSubTypes.Type(value = CustomCondition.class, name = "custom"),
})
public abstract class Condition {
    public String operation;
}
