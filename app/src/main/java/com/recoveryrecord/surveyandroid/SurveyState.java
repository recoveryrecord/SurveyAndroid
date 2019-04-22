package com.recoveryrecord.surveyandroid;

import com.recoveryrecord.surveyandroid.question.Question;
import com.recoveryrecord.surveyandroid.validation.AnswerProvider;
import com.recoveryrecord.surveyandroid.validation.Validator;

import java.util.HashMap;
import java.util.Map;

// Tracks the current state of our survey.
public class SurveyState implements OnQuestionStateChangedListener, AnswerProvider {

    private SurveyQuestions mSurveyQuestions;
    private Map<String, QuestionState> mQuestionStateMap;
    private Validator mValidator;

    private int mVisibleQuestionCount = 1;

    public SurveyState(SurveyQuestions surveyQuestions) {
        mSurveyQuestions = surveyQuestions;
        mQuestionStateMap = new HashMap<>();
    }

    public void setValidator(Validator validator) {
        mValidator = validator;
    }

    protected Validator getValidator() {
        return mValidator;
    }

    public Integer getVisibleQuestionCount() {
        //return mVisibleQuestionCount;
        return mSurveyQuestions.size();
    }

    public Question getQuestionFor(int position) {
        return mSurveyQuestions.getQuestionFor(position);
    }

    public QuestionState getStateFor(String questionId) {
        if (mQuestionStateMap.containsKey(questionId)) {
            return mQuestionStateMap.get(questionId);
        } else {
            QuestionState questionState = new QuestionState(questionId, this);
            mQuestionStateMap.put(questionId, questionState);
            return questionState;
        }
    }

    @Override
    public void questionStateChanged(QuestionState newQuestionState) {
        mQuestionStateMap.put(newQuestionState.id(), newQuestionState);
    }

    @Override
    public void questionAnswered(QuestionState newQuestionState) {
        mQuestionStateMap.put(newQuestionState.id(), newQuestionState);
        // TODO: changes for answered
    }

    @Override
    public Answer answerFor(String questionId) {
        QuestionState questionState = getStateFor(questionId);
        return questionState.getAnswer();
    }
}
