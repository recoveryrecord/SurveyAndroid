package com.recoveryrecord.surveyandroid;

import com.recoveryrecord.surveyandroid.question.Question;

// Tracks the current state of our survey.
public class SurveyQuestionsState {

    private SurveyQuestions mSurveyQuestions;
    private int mVisibleQuestionCount = 1;

    public SurveyQuestionsState(SurveyQuestions surveyQuestions) {
        mSurveyQuestions = surveyQuestions;
    }

    public Integer getVisibleQuestionCount() {
        return mVisibleQuestionCount;
    }

    public Question getQuestionFor(int position) {
        return mSurveyQuestions.getQuestionFor(position);
    }
}
