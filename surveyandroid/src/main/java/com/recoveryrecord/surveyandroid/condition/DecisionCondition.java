package com.recoveryrecord.surveyandroid.condition;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class DecisionCondition extends Condition {
    public ArrayList<Condition> subconditions;

    @NonNull
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("DecisionCondition: Evaluating subconditions for operation: ")
                .append(operation)
                .append(":\n");
        for (Condition condition : subconditions) {
            stringBuilder.append(condition)
                    .append("\n");
        }
        return stringBuilder.toString();
    }
}
