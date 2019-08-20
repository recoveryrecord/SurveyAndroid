package com.recoveryrecord.surveyandroid;

public interface OnSurveyStateChangedListener {
    void questionInserted(int adapterPosition);
    void questionRemoved(int adapterPosition);
    void questionsRemoved(int adapterPosition, int itemCount);
    void questionChanged(int adapterPosition);
    void submitButtonInserted(int adapterPosition);
}
