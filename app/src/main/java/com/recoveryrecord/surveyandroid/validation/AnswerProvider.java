package com.recoveryrecord.surveyandroid.validation;

import com.recoveryrecord.surveyandroid.Answer;

public interface AnswerProvider {
    Answer answerFor(String questionId);
    String allAnswersJson();
}
