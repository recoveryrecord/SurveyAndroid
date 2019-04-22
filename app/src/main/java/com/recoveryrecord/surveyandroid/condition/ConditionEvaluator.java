package com.recoveryrecord.surveyandroid.condition;

import com.recoveryrecord.surveyandroid.validation.AnswerProvider;

public class ConditionEvaluator {

    private AnswerProvider mAnswerProvider;
    private CustomConditionHandler mCustomConditionHandler;

    public ConditionEvaluator(AnswerProvider answerProvider) {
        mAnswerProvider = answerProvider;
    }

    public void setCustomConditionHandler(CustomConditionHandler handler) {
        mCustomConditionHandler = handler;
    }

    public boolean isConditionMet(SimpleCondition simpleCondition) {
        // TODO
        return false;
    }

    public boolean isConditionMet(DecisionCondition decisionCondition) {
        // TODO
        return false;
    }

    public boolean isConditionMet(CustomCondition customCondition) {
        if (mCustomConditionHandler == null) {
            throw new IllegalStateException("CustomConditionHandler must be set!");
        }
        // TODO
        return false;
    }
}
