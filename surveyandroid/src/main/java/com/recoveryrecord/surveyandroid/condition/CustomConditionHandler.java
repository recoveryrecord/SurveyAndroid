package com.recoveryrecord.surveyandroid.condition;

import com.recoveryrecord.surveyandroid.Answer;

import java.util.Map;

public interface CustomConditionHandler {
    boolean isConditionMet(Map<String, Answer> answers, Map<String, String> extra);
}
