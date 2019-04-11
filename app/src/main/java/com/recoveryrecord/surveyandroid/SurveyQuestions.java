package com.recoveryrecord.surveyandroid;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recoveryrecord.surveyandroid.question.Question;
import com.recoveryrecord.surveyandroid.question.QuestionsWrapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SurveyQuestions {
    private static final String TAG = SurveyQuestions.class.getSimpleName();

    private Context mContext;
    private ArrayList<Question> mQuestions;

    public static SurveyQuestions load(Context context, String jsonFileName) {
        ArrayList<Question> questions = readQuestionsFromFile(context, jsonFileName);
        return new SurveyQuestions(context, questions);
    }

    public static ArrayList<Question> readQuestionsFromFile(Context context, String jsonFileName) {
        ArrayList<Question> questions = new ArrayList<>();
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
            inputStream.close();
        } catch (IOException ioe) {
            Log.e(TAG, "Error while parsing " + jsonFileName, ioe);
        }
        return questions;
    }

    public SurveyQuestions(Context context, ArrayList<Question> questions) {
        mContext = context;
        mQuestions = questions;
    }
}
