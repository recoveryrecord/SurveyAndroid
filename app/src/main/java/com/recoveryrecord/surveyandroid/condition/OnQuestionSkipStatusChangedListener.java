package com.recoveryrecord.surveyandroid.condition;

import com.recoveryrecord.surveyandroid.condition.FilteredQuestions.QuestionAdapterPosition;

import java.util.Set;

public interface OnQuestionSkipStatusChangedListener {
    void skipStatusChanged(Set<QuestionAdapterPosition> newlySkippedQuestionIds, Set<QuestionAdapterPosition> newlyShownQuestionIds);
}
