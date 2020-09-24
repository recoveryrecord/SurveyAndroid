package com.recoveryrecord.surveyandroid;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;

// An answer can be a string, a list of strings, or a map with String keys.
// Only one of these values should be set
public class Answer {
    private String mValue;
    private ArrayList<String> mValueList;
    private HashMap<String, Answer> mValueMap;

    public Answer(@NonNull String value) {
        mValue = value;
    }

    public Answer(@NonNull ArrayList<String> valueList) {
        mValueList = valueList;
    }

    public Answer(@NonNull HashMap<String, Answer> valueMap) {
        mValueMap = valueMap;
    }

    public boolean isString() {
        return mValue != null;
    }

    public String getValue() {
        return mValue;
    }

    public boolean isList() {
        return mValueList != null;
    }

    public ArrayList<String> getValueList() {
        return mValueList;
    }

    public boolean isMap() {
        return mValueMap != null;
    }

    public HashMap<String, Answer> getValueMap() {
        return mValueMap;
    }
}
