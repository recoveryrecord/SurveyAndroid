package com.recoveryrecord.surveyandroid.example;

import android.os.Bundle;

import com.recoveryrecord.surveyandroid.R;
import com.recoveryrecord.surveyandroid.SurveyActivity;

public class ExampleSurveyActivity extends SurveyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String getSurveyTitle() {
        return getString(R.string.example_survey);
    }

    @Override
    protected String getJsonFilename() {
        return "ExampleQuestions.json";
    }
}
