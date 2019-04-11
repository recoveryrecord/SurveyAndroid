package com.recoveryrecord.surveyandroid;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recoveryrecord.surveyandroid.question.Question;
import com.recoveryrecord.surveyandroid.question.QuestionsWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = null;
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

    public static String readJsonFromFile(Context context, String jsonFileName) {
        AssetManager assetManager = context.getAssets();
        StringBuilder sb = new StringBuilder();

        try {
            InputStream inputStream = assetManager.open(jsonFileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            br.close();
        } catch (IOException ioe) {
            Log.e(TAG, "Unable to open " + jsonFileName, ioe);
        }
        return sb.toString();
    }

    public SurveyQuestions(Context context, ArrayList<Question> questions) {
        mContext = context;
        mQuestions = questions;
    }
}
