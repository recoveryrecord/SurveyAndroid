package com.recoveryrecord.surveyandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.recoveryrecord.surveyandroid.condition.CustomConditionHandler;
import com.recoveryrecord.surveyandroid.validation.AnswerProvider;
import com.recoveryrecord.surveyandroid.validation.DefaultValidator;
import com.recoveryrecord.surveyandroid.validation.FailedValidationListener;
import com.recoveryrecord.surveyandroid.validation.Validator;

public abstract class SurveyActivity extends AppCompatActivity implements FailedValidationListener, OnSurveyStateChangedListener {
    private static final String TAG = SurveyActivity.class.getSimpleName();

    public static final String JSON_FILE_NAME_EXTRA = "json_filename";
    public static final String SURVEY_TITLE_EXTRA = "survey_title";

    private RecyclerView recyclerView;
    private SurveyQuestionAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private SurveyState mState;
    private LinearSmoothScroller mSmoothScroller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        setTitle(getSurveyTitle());

        SurveyQuestions surveyQuestions = SurveyQuestions.load(this, getJsonFilename());
        Log.d(TAG, "Questions = " + surveyQuestions);

        recyclerView = findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        recyclerView.addItemDecoration(new SpaceOnLastItemDecoration(displayMetrics.heightPixels));
        recyclerView.setItemAnimator(new SurveyQuestionItemAnimator());
        mState = new SurveyState(surveyQuestions);
        mState.setValidator(getValidator());
        mAdapter = new SurveyQuestionAdapter(this, mState);
        recyclerView.setAdapter(mAdapter);
        mSmoothScroller = new LinearSmoothScroller(this) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }

            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return 100F  / (float)displayMetrics.densityDpi;
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mState.addOnSurveyStateChangedListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mState.removeOnSurveyStateChangedListener(this);
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

    @Override
    public void questionInserted(final int adapterPosition) {
        if (adapterPosition > 0) {
            // This fixes the ItemDecoration "footer"
            mAdapter.notifyItemChanged(adapterPosition - 1, Boolean.FALSE);
        }
        mAdapter.notifyItemInserted(adapterPosition);
        mSmoothScroller.setTargetPosition(adapterPosition);
        // This ensures that the keyboard finishes hiding before we start scrolling
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                mLayoutManager.startSmoothScroll(mSmoothScroller);
            }
        });
    }

    @Override
    public void questionRemoved(int adapterPosition) {
        if (adapterPosition > 0) {
            // This fixes the ItemDecoration "footer"
            mAdapter.notifyItemChanged(adapterPosition - 1, Boolean.FALSE);
        }
        mAdapter.notifyItemRemoved(adapterPosition);
    }

    @Override
    public void submitButtonInserted(int adapterPosition) {
        if (adapterPosition > 0) {
            // This fixes the ItemDecoration "footer"
            mAdapter.notifyItemChanged(adapterPosition - 1, Boolean.FALSE);
        }
        mAdapter.notifyItemInserted(adapterPosition);
    }
}
