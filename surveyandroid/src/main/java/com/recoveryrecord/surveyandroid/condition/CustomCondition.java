package com.recoveryrecord.surveyandroid.condition;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomCondition extends Condition {
    public ArrayList<String> ids;
    public HashMap<String, String> extra;

    @NonNull
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("CustomCondition: Evaluating ids: ");
        for (String id : ids) {
            stringBuilder.append(id)
                    .append("\n");
        }
        stringBuilder.append("for operation: ")
                .append(operation)
                .append(":\n");
        stringBuilder.append("With extras:\n");
        for (Map.Entry<String, String> entry : extra.entrySet()) {
            stringBuilder.append(entry.getKey())
                    .append(" : ")
                    .append(entry.getValue())
                    .append("\n");
        }
        return stringBuilder.toString();
    }
}
