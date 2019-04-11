package com.recoveryrecord.surveyandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public abstract class SurveyActivity extends AppCompatActivity {
    private static final String TAG = SurveyActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SurveyQuestions surveyQuestions = SurveyQuestions.load(this, "ExampleQuestions.json");
        Log.d(TAG, "Questions = " + surveyQuestions);
    }
}
