package com.recoveryrecord.surveyandroid;

public interface OnQuestionStateChangedListener {
    void questionStateChanged(QuestionState newQuestionState);
    void questionAnswered(QuestionState newQuestionState);
}
