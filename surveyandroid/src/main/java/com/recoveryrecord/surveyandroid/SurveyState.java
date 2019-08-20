package com.recoveryrecord.surveyandroid;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.recoveryrecord.surveyandroid.condition.ConditionEvaluator;
import com.recoveryrecord.surveyandroid.condition.CustomConditionHandler;
import com.recoveryrecord.surveyandroid.condition.FilteredQuestions;
import com.recoveryrecord.surveyandroid.condition.FilteredQuestions.QuestionAdapterPosition;
import com.recoveryrecord.surveyandroid.condition.OnQuestionSkipStatusChangedListener;
import com.recoveryrecord.surveyandroid.question.Question;
import com.recoveryrecord.surveyandroid.question.QuestionsWrapper.SubmitData;
import com.recoveryrecord.surveyandroid.validation.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// Tracks the current state of our survey.
public class SurveyState implements OnQuestionStateChangedListener, AnswerProvider {
    private static final String TAG = SurveyState.class.getSimpleName();

    private SurveyQuestions mSurveyQuestions;
    private Map<String, QuestionState> mQuestionStateMap;
    private Validator mValidator;
    private ConditionEvaluator mConditionEvaluator;
    private FilteredQuestions mFilteredQuestions;
    private SubmitSurveyHandler mSubmitSurveyHandler;
    private List<OnSurveyStateChangedListener> mSurveyStateListeners = new ArrayList<>();

    private int mVisibleQuestionCount = 1;
    private boolean mIsSubmitButtonShown = false;

    private ObjectMapper mObjectMapper;

    public SurveyState(SurveyQuestions surveyQuestions) {
        mSurveyQuestions = surveyQuestions;
        mQuestionStateMap = new HashMap<>();
        mConditionEvaluator = new ConditionEvaluator(this);
    }

    public SurveyState setValidator(Validator validator) {
        mValidator = validator;
        return this;
    }

    protected Validator getValidator() {
        return mValidator;
    }

    public SurveyState setCustomConditionHandler(CustomConditionHandler handler) {
        mConditionEvaluator.setCustomConditionHandler(handler);
        return this;
    }

    public ConditionEvaluator getConditionEvaluator() {
        return mConditionEvaluator;
    }

    public SurveyState setSubmitSurveyHandler(SubmitSurveyHandler submitSurveyHandler) {
        mSubmitSurveyHandler = submitSurveyHandler;
        return this;
    }

    protected SubmitSurveyHandler getSubmitSurveyHandler() {
        return mSubmitSurveyHandler;
    }

    public Integer getVisibleQuestionCount() {
        return mVisibleQuestionCount;
    }

    public boolean isSubmitButtonShown() {
        return mIsSubmitButtonShown;
    }

    public SurveyState initFilter() {
        mFilteredQuestions = new FilteredQuestions(mSurveyQuestions, getConditionEvaluator());
        mFilteredQuestions.setOnQuestionSkipStatusChangedListener(new OnQuestionSkipStatusChangedListener() {
            @Override
            public void skipStatusChanged(Set<QuestionAdapterPosition> newlySkippedQuestionIds, Set<QuestionAdapterPosition> newlyShownQuestionIds) {
                Set<Integer> removedPositions = new HashSet<>();
                Set<Integer> addedPositions = new HashSet<>();
                for (QuestionAdapterPosition skippedQ: newlySkippedQuestionIds) {
                    removedPositions.add(skippedQ.adapterPosition);
                }
                for (QuestionAdapterPosition shownQ: newlyShownQuestionIds) {
                    addedPositions.add(shownQ.adapterPosition);
                }
                // Always remove the submit button, if present.  It will get added later if necessary
                if (isSubmitButtonShown()) {
                    mIsSubmitButtonShown = false;
                    questionRemoved(mFilteredQuestions.size());
                }
                int visibleQuestionCount = mVisibleQuestionCount;
                for (int i = 0; i < visibleQuestionCount; i++) {
                    String addedId = getIdByPosition(newlyShownQuestionIds, i);
                    if (removedPositions.contains(i) && addedPositions.contains(i)) {
                        questionChanged(i);
                    } else if (addedPositions.contains(i)) {
                        mVisibleQuestionCount++;
                        questionInserted(i);
                    } else if (removedPositions.contains(i)) {
                        mVisibleQuestionCount--;
                        questionRemoved(i);
                    }

                    if (addedId != null && !isQuestionAnswered(addedId)) {
                        mVisibleQuestionCount = i + 1;
                        questionsRemoved(visibleQuestionCount, visibleQuestionCount - i);
                        break;
                    }
                }
            }

            private String getIdByPosition(Set<QuestionAdapterPosition> questionPositions, int position) {
                for (QuestionAdapterPosition questionAdapterPosition : questionPositions) {
                    if (questionAdapterPosition.adapterPosition == position) {
                        return questionAdapterPosition.questionId;
                    }
                }
                return null;
            }
        });
        return this;
    }

    private boolean isQuestionAnswered(String questionId) {
        return getStateFor(questionId).isAnswered();
    }

    public Question getQuestionFor(int adapterPosition) {
        if (mFilteredQuestions == null) {
            throw new IllegalStateException("Please call initFilter on SurveyState!");
        }
        if (isSubmitPosition(adapterPosition)) {
            return null;
        }
        return mFilteredQuestions.getQuestionFor(adapterPosition);
    }

    public boolean isSubmitPosition(int adapterPosition) {
        return mFilteredQuestions.size() == adapterPosition;
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
        mFilteredQuestions.questionAnswered(newQuestionState);
        Question lastQuestion = getQuestionFor(mVisibleQuestionCount - 1);
        while (!isSubmitButtonShown() && lastQuestion != null && isAnswered(lastQuestion)) {
            increaseVisibleQuestionCount();
            lastQuestion = getQuestionFor(mVisibleQuestionCount - 1);
        }
    }

    public boolean isAnswered(Question question) {
        QuestionState questionState = getStateFor(question.id);
        return questionState.isAnswered();
    }

    @Override
    public Answer answerFor(String questionId) {
        QuestionState questionState = getStateFor(questionId);
        return questionState.getAnswer();
    }

    @Override
    public String allAnswersJson() {
        ObjectNode topNode = getObjectMapper().createObjectNode();
        ObjectNode answersNode = getObjectMapper().createObjectNode();
        for (String questionId : mQuestionStateMap.keySet()) {
            answersNode = putAnswer(answersNode, questionId, answerFor(questionId));
        }
        topNode.set("answers", answersNode);
        return topNode.toString();
    }

    private ObjectMapper getObjectMapper() {
        if (mObjectMapper == null) {
            mObjectMapper = new ObjectMapper();
        }
        return mObjectMapper;
    }

    private ObjectNode putAnswer(ObjectNode objectNode, String key, Answer answer) {
        if (answer == null) {
            Log.e(TAG, "Answer is null for key: " + key);
            return objectNode;
        }
        if (answer.isString()) {
            objectNode.put(key, answer.getValue());
        } else if (answer.isList()) {
            ArrayList<String> answersList = answer.getValueList();
            ArrayNode answerListNode = objectNode.putArray(key);
            for (String answerStr : answersList) {
                answerListNode.add(answerStr);
            }
        } else {
            for (String valueKey: answer.getValueMap().keySet()) {
                ObjectNode valueObject = getObjectMapper().createObjectNode();
                valueObject = putAnswer(valueObject, valueKey, answer.getValueMap().get(valueKey));
                objectNode.set(key, valueObject);
            }
        }
        return objectNode;
    }

    public SubmitData getSubmitData() {
        return mSurveyQuestions.getSubmitData();
    }

    public void increaseVisibleQuestionCount() {
        if (mVisibleQuestionCount < mFilteredQuestions.size()) {
            mVisibleQuestionCount += 1;
            questionInserted(mVisibleQuestionCount - 1);
        } else if (mVisibleQuestionCount == mFilteredQuestions.size()){
            mIsSubmitButtonShown = true;
            submitButtonInserted(mVisibleQuestionCount);
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

    public void questionsRemoved(int startAdapterPosition, int itemCount) {
        for (OnSurveyStateChangedListener listener : mSurveyStateListeners) {
            listener.questionsRemoved(startAdapterPosition, itemCount);
        }
    }

    public void questionChanged(int adapterPosition) {
        for (OnSurveyStateChangedListener listener : mSurveyStateListeners) {
            listener.questionChanged(adapterPosition);
        }
    }

    public void submitButtonInserted(int adapterPosition) {
        for (OnSurveyStateChangedListener listener : mSurveyStateListeners) {
            listener.submitButtonInserted(adapterPosition);
        }
    }
}
