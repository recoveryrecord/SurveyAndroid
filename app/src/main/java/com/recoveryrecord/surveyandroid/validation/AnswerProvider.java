package com.recoveryrecord.surveyandroid.validation;

import com.recoveryrecord.surveyandroid.Answer;

import java.util.ArrayList;

public interface AnswerProvider {
    Answer answerFor(String questionId);
}
