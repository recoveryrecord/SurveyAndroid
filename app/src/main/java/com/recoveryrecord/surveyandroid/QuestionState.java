package com.recoveryrecord.surveyandroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuestionState {
    private static final String ANSWER_KEY = "answer";
    private static final String QUESTION_ID_KEY = "question_id";

    private Map<String, String> mQuestionStringData;
    private Map<String, ArrayList<String>> mQuestionStringListData;
    private OnQuestionStateChangedListener mListener;

    QuestionState(String questionId, OnQuestionStateChangedListener listener) {
        mQuestionStringData = new HashMap<>();
        mQuestionStringData.put(QUESTION_ID_KEY, questionId);
        mQuestionStringListData = new HashMap<>();
        mListener = listener;
    }

    public void put(String key, String value) {
        if (key.equals(QUESTION_ID_KEY)) {
            throw new IllegalArgumentException("The QuestionId cannot be updated!");
        } else if (key.equals(ANSWER_KEY)) {
            throw new IllegalArgumentException("The answer should be updated via setAnswer!");
        }
        mQuestionStringData.put(key, value);
        if (mListener != null) {
            mListener.questionStateChanged(this);
        }
    }

    public void put(String key, boolean value) {
        mQuestionStringData.put(key, String.valueOf(value));
    }

    public void put(String key, ArrayList<String> value) {
        mQuestionStringListData.put(key, value);
    }

    public void addStringToList(String key, String value) {
        if (mQuestionStringListData.containsKey(key)) {
            mQuestionStringListData.get(key).add(value);
        } else {
            ArrayList<String> newList = new ArrayList<>();
            newList.add(value);
            mQuestionStringListData.put(key, newList);
        }
    }

    public void removeStringFromList(String key, String value) {
        if (mQuestionStringListData.containsKey(key)) {
            mQuestionStringListData.get(key).remove(value);
        }
    }

    private boolean containsKey(String key) {
        return mQuestionStringData.containsKey(key) || mQuestionStringListData.containsKey(key);
    }

    public void setAnswer(String answer) {
        mQuestionStringData.put(ANSWER_KEY, answer);
        if (mListener != null) {
            mListener.questionAnswered(this);
        }
    }
    public void setAnswer(ArrayList<String> answerList) {
        mQuestionStringListData.put(ANSWER_KEY, answerList);
        if (mListener != null) {
            mListener.questionAnswered(this);
        }
    }

    public String getString(String key) {
        return mQuestionStringData.get(key);
    }

    public String getString(String key, String defaultValue) {
        return containsKey(key) ? getString(key) : defaultValue;
    }

    public boolean getBool(String key, boolean defaultValue) {
        return containsKey(key) ? Boolean.valueOf(getString(key)) : defaultValue;
    }

    public ArrayList<String> getList(String key) {
        return mQuestionStringListData.get(key);
    }

    public ArrayList<String> getList(String key, ArrayList<String> defaultValue) {
        return mQuestionStringListData.containsKey(key) ? mQuestionStringListData.get(key) : defaultValue;
    }

    public String answer() {
        return mQuestionStringData.get(ANSWER_KEY);
    }

    public ArrayList<String> answerList() {
        return mQuestionStringListData.get(ANSWER_KEY);
    }

    public boolean isAnswered() {
        return mQuestionStringData.containsKey(ANSWER_KEY) || mQuestionStringListData.containsKey(ANSWER_KEY);
    }

    public String id() {
        return mQuestionStringData.get(QUESTION_ID_KEY);
    }
}
