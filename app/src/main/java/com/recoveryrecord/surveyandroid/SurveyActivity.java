package com.recoveryrecord.surveyandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.recoveryrecord.surveyandroid.condition.CustomConditionHandler;
import com.recoveryrecord.surveyandroid.validation.AnswerProvider;
import com.recoveryrecord.surveyandroid.validation.DefaultValidator;
import com.recoveryrecord.surveyandroid.validation.FailedValidationListener;
import com.recoveryrecord.surveyandroid.validation.Validator;

public abstract class SurveyActivity extends AppCompatActivity implements FailedValidationListener {
    private static final String TAG = SurveyActivity.class.getSimpleName();

    public static final String JSON_FILE_NAME_EXTRA = "json_filename";
    public static final String SURVEY_TITLE_EXTRA = "survey_title";

    private RecyclerView recyclerView;
    private SurveyQuestionAdapter mAdapter;
    private SurveyState mState;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        setTitle(getSurveyTitle());

        SurveyQuestions surveyQuestions = SurveyQuestions.load(this, getJsonFilename());
        Log.d(TAG, "Questions = " + surveyQuestions);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mState = new SurveyState(surveyQuestions);
        mState.setValidator(getValidator());
        mAdapter = new SurveyQuestionAdapter(this, mState);
        recyclerView.setAdapter(mAdapter);
    }

    protected String getSurveyTitle() {
        if (getIntent().hasExtra(SURVEY_TITLE_EXTRA)) {
            return getIntent().getStringExtra(SURVEY_TITLE_EXTRA);
        }
        return null;
    }

    protected String getJsonFilename() {
        if (getIntent().hasExtra(JSON_FILE_NAME_EXTRA)) {
            return getIntent().getStringExtra(JSON_FILE_NAME_EXTRA);
        }
        return null;
    }

    protected Validator getValidator() {
        return new DefaultValidator(this, getAnswerProvider());
    }

    // Subclasses should return a non-null if they are using custom show_if conditions.
    protected CustomConditionHandler getCustomConditionHandler() {
        return null;
    }

    protected AnswerProvider getAnswerProvider() {
        return mState;
    }

    public void validationFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
