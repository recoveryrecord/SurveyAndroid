package com.recoveryrecord.surveyandroid;

import com.recoveryrecord.surveyandroid.condition.ConditionEvaluator;
import com.recoveryrecord.surveyandroid.condition.CustomConditionHandler;
import com.recoveryrecord.surveyandroid.question.Question;
import com.recoveryrecord.surveyandroid.validation.AnswerProvider;
import com.recoveryrecord.surveyandroid.validation.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Tracks the current state of our survey.
public class SurveyState implements OnQuestionStateChangedListener, AnswerProvider {

    private SurveyQuestions mSurveyQuestions;
    private Map<String, QuestionState> mQuestionStateMap;
    private Validator mValidator;
    private ConditionEvaluator mConditionEvaluator;
    private List<OnSurveyStateChangedListener> mSurveyStateListeners = new ArrayList<>();

    private int mVisibleQuestionCount = 1;

    public SurveyState(SurveyQuestions surveyQuestions) {
        mSurveyQuestions = surveyQuestions;
        mQuestionStateMap = new HashMap<>();
        mConditionEvaluator = new ConditionEvaluator(this);
    }

    public void setValidator(Validator validator) {
        mValidator = validator;
    }

    protected Validator getValidator() {
        return mValidator;
    }

    public void setCustomConditionHandler(CustomConditionHandler handler) {
        mConditionEvaluator.setCustomConditionHandler(handler);
    }

    public ConditionEvaluator getConditionEvaluator() {
        return mConditionEvaluator;
    }

    public Integer getVisibleQuestionCount() {
        return mVisibleQuestionCount;
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
        if (newQuestionState.id().equals(getQuestionFor(mVisibleQuestionCount - 1).id)) {
            increaseVisibleQuestionCount();
        }
    }

    @Override
    public Answer answerFor(String questionId) {
        QuestionState questionState = getStateFor(questionId);
        return questionState.getAnswer();
    }

    public void increaseVisibleQuestionCount() {
        // We may change this max depending on how the submit button is done
        if (mVisibleQuestionCount <= mSurveyQuestions.size() - 1) {
            mVisibleQuestionCount += 1;
            questionInserted(mVisibleQuestionCount - 1);
        }
    }

    public void addOnSurveyStateChangedListener(OnSurveyStateChangedListener listener) {
        mSurveyStateListeners.add(listener);
    }

    public void removeOnSurveyStateChangedListener(OnSurveyStateChangedListener listener) {
        mSurveyStateListeners.remove(listener);
    }

    private void questionInserted(int adapterPosition) {
        for (OnSurveyStateChangedListener listener : mSurveyStateListeners) {
            listener.questionInserted(adapterPosition);
        }
    }

    public void questionRemoved(int adapterPosition) {
        for (OnSurveyStateChangedListener listener : mSurveyStateListeners) {
            listener.questionRemoved(adapterPosition);
        }
    }

    public void submitButtonInserted(int adapterPosition) {
        for (OnSurveyStateChangedListener listener : mSurveyStateListeners) {
            listener.submitButtonInserted(adapterPosition);
        }
    }
}
