package com.recoveryrecord.surveyandroid.condition;

import com.recoveryrecord.surveyandroid.Answer;

import java.util.List;
import java.util.Map;

public interface CustomConditionHandler {
    boolean isConditionMet(List<Answer> answers, Map<String, String> extra);
}
