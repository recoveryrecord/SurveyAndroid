package com.recoveryrecord.surveyandroid;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.recoveryrecord.surveyandroid.question.Question;
import com.recoveryrecord.surveyandroid.viewholder.QuestionViewHolder;

public class SurveyQuestionAdapter extends RecyclerView.Adapter<QuestionViewHolder> {

    private SurveyQuestionsState mState;

    public enum QuestionType {
        SINGLE_SELECT("single_select"),
        MULTI_SELECT("multi_select"),
        YEAR_PICKER("year_picker"),
        DATE_PICKER("date_picker"),
        SINGLE_TEXT_FIELD("single_text_field"),
        MULTI_TEXT_FIELD("multi_text_field"),
        DYNAMIC_LABEL_TEXT_FIELD("dynamic_label_text_field"),
        ADD_TEXT_FIELD("add_text_field"),
        SEGMENT_SELECT("segment_select"),
        TABLE_SELECT("table_select");

        private String mValue;

        QuestionType(String value) {
            mValue = value;
        }

        @Override
        public String toString() {
            return mValue;
        }

        public static QuestionType fromString(String value) {
            for (QuestionType questionType : QuestionType.values()) {
                if (questionType.mValue.equals(value)) {
                    return questionType;
                }
            }
            return null;
        }
    }

    public SurveyQuestionAdapter(SurveyQuestionsState state) {
        mState = state;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        QuestionType questionType = QuestionType.values()[viewType];
        switch (questionType) {
            case SINGLE_SELECT:
                break;
            case MULTI_SELECT:
                break;
            case YEAR_PICKER:
                break;
            case DATE_PICKER:
                break;
            case SINGLE_TEXT_FIELD:
                break;
            case MULTI_TEXT_FIELD:
                break;
            case DYNAMIC_LABEL_TEXT_FIELD:
                break;
            case ADD_TEXT_FIELD:
                break;
            case SEGMENT_SELECT:
                break;
            case TABLE_SELECT:
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder questionViewHolder, int position) {
        Question question = getItem(position);
        questionViewHolder.bind(question);
    }

    @Override
    public int getItemViewType(int position) {
        return getQuestionType(position).ordinal();
    }

    public Question getItem(int position) {
        return mState.getQuestionFor(position);
    }

    public QuestionType getQuestionType(int position) {
        Question question = getItem(position);
        return QuestionType.fromString(question.questionType);
    }

    @Override
    public int getItemCount() {
        return mState.getVisibleQuestionCount();
    }
}
