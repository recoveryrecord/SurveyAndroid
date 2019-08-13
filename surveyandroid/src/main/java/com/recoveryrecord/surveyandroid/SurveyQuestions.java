package com.recoveryrecord.surveyandroid;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recoveryrecord.surveyandroid.question.Question;
import com.recoveryrecord.surveyandroid.question.QuestionsWrapper;
import com.recoveryrecord.surveyandroid.question.QuestionsWrapper.SubmitData;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SurveyQuestions {
    private static final String TAG = SurveyQuestions.class.getSimpleName();

    private Context mContext;
    private ArrayList<Question> mQuestions;
    private SubmitData mSubmitData;

    public static SurveyQuestions load(Context context, String jsonFileName) {
        return createSurveyQuestionsFromFile(context, jsonFileName);
    }

    public static SurveyQuestions createSurveyQuestionsFromFile(Context context, String jsonFileName) {
        ArrayList<Question> questions = new ArrayList<>();
        SubmitData submitData = null;
        AssetManager assetManager = context.getAssets();
        ObjectMapper mapper = new ObjectMapper()
                .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            InputStream inputStream = assetManager.open(jsonFileName);
            QuestionsWrapper wrapper = mapper.readValue(inputStream, new TypeReference<QuestionsWrapper>(){});
            if (wrapper.questions != null) {
                questions = wrapper.questions;
            }
            submitData = wrapper.submit;
            inputStream.close();
        } catch (IOException ioe) {
            Log.e(TAG, "Error while parsing " + jsonFileName, ioe);
        }
        return new SurveyQuestions(context, questions, submitData);
    }

    public static SurveyQuestions createSurveyQuestionsFromJsonString(Context context, String jsonString) {
        ArrayList<Question> questions = new ArrayList<>();
        SubmitData submitData = null;
        ObjectMapper mapper = new ObjectMapper()
                .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            QuestionsWrapper wrapper = mapper.readValue(jsonString, new TypeReference<QuestionsWrapper>(){});
            if (wrapper.questions != null) {
                questions = wrapper.questions;
            }
            submitData = wrapper.submit;
        } catch (IOException ioe) {
            Log.e(TAG, "Error while parsing jsonString: " + jsonString, ioe);
        }
        return new SurveyQuestions(context, questions, submitData);
    }

    public SurveyQuestions(Context context, ArrayList<Question> questions, SubmitData submitData) {
        mContext = context;
        mQuestions = questions;
        mSubmitData = submitData;
    }

    public Question getQuestionFor(int position) {
        if (position >= mQuestions.size()) {
            return null;
        }
        return mQuestions.get(position);
    }

    public SubmitData getSubmitData() {
        return mSubmitData;
    }

    public int size() {
        return mQuestions.size();
    }
}
