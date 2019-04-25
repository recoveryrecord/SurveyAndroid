package com.recoveryrecord.surveyandroid;

import com.recoveryrecord.surveyandroid.Answer;

public interface AnswerProvider {
    Answer answerFor(String questionId);
    String allAnswersJson();
}
