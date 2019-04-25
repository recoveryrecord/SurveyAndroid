package com.recoveryrecord.surveyandroid.condition;

import com.recoveryrecord.surveyandroid.OnQuestionStateChangedListener;
import com.recoveryrecord.surveyandroid.QuestionState;
import com.recoveryrecord.surveyandroid.SurveyQuestions;
import com.recoveryrecord.surveyandroid.question.Question;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FilteredQuestions implements OnQuestionStateChangedListener {
    private SurveyQuestions mSurveyQuestions;
    private ConditionEvaluator mConditionEvaluator;
    private ArrayList<Integer> mAdapterToRealPositions;
    private Set<QuestionAdapterPosition> mSkippedQuestions = new HashSet<>();

    private OnQuestionSkipStatusChangedListener mSkipStatusChangedListener;

    public FilteredQuestions(SurveyQuestions surveyQuestions, ConditionEvaluator conditionEvaluator) {
        mSurveyQuestions = surveyQuestions;
        mConditionEvaluator = conditionEvaluator;
        updateAdapterToRealPositions();
    }

    public void setOnQuestionSkipStatusChangedListener(OnQuestionSkipStatusChangedListener listener) {
        mSkipStatusChangedListener = listener;
    }

    public Question getQuestionFor(int adapterPosition) {
        return mSurveyQuestions.getQuestionFor(adapterToReal(adapterPosition));
    }

    private Integer adapterToReal(int adapterPosition) {
        return mAdapterToRealPositions.get(adapterPosition);
    }

    public int size() {
        return mAdapterToRealPositions.size();
    }

    private boolean isSkipped(Question question) {
        return mConditionEvaluator.isConditionMet(question.showIf);
    }

    private void updateAdapterToRealPositions() {
        Set<QuestionAdapterPosition> skippedQuestions = new HashSet<>();
        Set<QuestionAdapterPosition> oldSkippedQuestions = mSkippedQuestions;
        Set<QuestionAdapterPosition> newlySkipped = new HashSet<>();
        Set<QuestionAdapterPosition> newlyShown = new HashSet<>();

        mAdapterToRealPositions = new ArrayList<>();
        for (int i = 0; i < mSurveyQuestions.size(); i++) {
            Question question = mSurveyQuestions.getQuestionFor(i);
            if (isSkipped(question)) {
                QuestionAdapterPosition skippedInfo = new QuestionAdapterPosition(question.id, mAdapterToRealPositions.size());
                skippedQuestions.add(skippedInfo);
                if (!oldSkippedQuestions.contains(skippedInfo)) {
                    newlySkipped.add(skippedInfo);
                }
                continue;
            } else {
                QuestionAdapterPosition positionInfo = new QuestionAdapterPosition(question.id, mAdapterToRealPositions.size());
                if (oldSkippedQuestions.contains(positionInfo)) {
                    newlyShown.add(positionInfo);
                }
            }
            mAdapterToRealPositions.add(i);
        }

        if (mSkipStatusChangedListener == null) {
            return;
        }
        mSkippedQuestions = skippedQuestions;
        mSkipStatusChangedListener.skipStatusChanged(newlySkipped, newlyShown);
    }

    @Override
    public void questionStateChanged(QuestionState newQuestionState) {}

    @Override
    public void questionAnswered(QuestionState newQuestionState) {
        updateAdapterToRealPositions();
    }

    public class QuestionAdapterPosition {
        public String questionId;
        // If skipped, this will be the next adapter position
        public Integer adapterPosition;

        public QuestionAdapterPosition(String questionId, Integer adapterPosition) {
            this.questionId = questionId;
            this.adapterPosition = adapterPosition;
        }

        @Override
        public int hashCode() {
            return questionId.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof QuestionAdapterPosition)) {
                return false;
            }
            return questionId.equals(((QuestionAdapterPosition) obj).questionId);
        }
    }
}
