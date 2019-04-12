package com.recoveryrecord.surveyandroid;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.recoveryrecord.surveyandroid.question.AddTextFieldQuestion;
import com.recoveryrecord.surveyandroid.question.DatePickerQuestion;
import com.recoveryrecord.surveyandroid.question.DynamicLabelTextFieldQuestion;
import com.recoveryrecord.surveyandroid.question.MultiSelectQuestion;
import com.recoveryrecord.surveyandroid.question.MultiTextFieldQuestion;
import com.recoveryrecord.surveyandroid.question.Question;
import com.recoveryrecord.surveyandroid.question.SegmentSelectQuestion;
import com.recoveryrecord.surveyandroid.question.SingleSelectQuestion;
import com.recoveryrecord.surveyandroid.question.SingleTextFieldQuestion;
import com.recoveryrecord.surveyandroid.question.TableSelectQuestion;
import com.recoveryrecord.surveyandroid.question.YearPickerQuestion;
import com.recoveryrecord.surveyandroid.viewholder.AddTextFieldQuestionViewHolder;
import com.recoveryrecord.surveyandroid.viewholder.DatePickerQuestionViewHolder;
import com.recoveryrecord.surveyandroid.viewholder.DynamicLabelTextFieldQuestionViewHolder;
import com.recoveryrecord.surveyandroid.viewholder.MultiSelectQuestionViewHolder;
import com.recoveryrecord.surveyandroid.viewholder.MultiTextFieldQuestionViewHolder;
import com.recoveryrecord.surveyandroid.viewholder.QuestionViewHolder;
import com.recoveryrecord.surveyandroid.viewholder.SegmentSelectQuestionViewHolder;
import com.recoveryrecord.surveyandroid.viewholder.SingleSelectQuestionViewHolder;
import com.recoveryrecord.surveyandroid.viewholder.SingleTextFieldQuestionViewHolder;
import com.recoveryrecord.surveyandroid.viewholder.TableSelectQuestionViewHolder;
import com.recoveryrecord.surveyandroid.viewholder.YearPickerQuestionViewHolder;

public class SurveyQuestionAdapter extends RecyclerView.Adapter<QuestionViewHolder> {

    private Context mContext;
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
        @NonNull
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

    SurveyQuestionAdapter(Context context, SurveyQuestionsState state) {
        mContext = context;
        mState = state;
    }

    private Context getContext() {
        return mContext;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        QuestionType questionType = QuestionType.values()[viewType];
        View view = LayoutInflater.from(getContext()).inflate(getLayout(questionType), viewGroup, false);
        switch (questionType) {
            case SINGLE_SELECT:
                return new SingleSelectQuestionViewHolder(getContext(), view);
            case MULTI_SELECT:
                return new MultiSelectQuestionViewHolder(getContext(), view);
            case YEAR_PICKER:
                return new YearPickerQuestionViewHolder(getContext(), view);
            case DATE_PICKER:
                return new DatePickerQuestionViewHolder(getContext(), view);
            case SINGLE_TEXT_FIELD:
                return new SingleTextFieldQuestionViewHolder(getContext(), view);
            case MULTI_TEXT_FIELD:
                return new MultiTextFieldQuestionViewHolder(getContext(), view);
            case DYNAMIC_LABEL_TEXT_FIELD:
                return new DynamicLabelTextFieldQuestionViewHolder(getContext(), view);
            case ADD_TEXT_FIELD:
                return new AddTextFieldQuestionViewHolder(getContext(), view);
            case SEGMENT_SELECT:
                return new SegmentSelectQuestionViewHolder(getContext(), view);
            case TABLE_SELECT:
                return new TableSelectQuestionViewHolder(getContext(), view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder questionViewHolder, int position) {
        Question question = getItem(position);
        QuestionType questionType = getQuestionType(position);
        switch (questionType) {
            case SINGLE_SELECT:
                if (questionViewHolder instanceof SingleSelectQuestionViewHolder && question instanceof SingleSelectQuestion) {
                    ((SingleSelectQuestionViewHolder) questionViewHolder).bind((SingleSelectQuestion) question);
                }
                break;
            case MULTI_SELECT:
                if (questionViewHolder instanceof MultiSelectQuestionViewHolder && question instanceof MultiSelectQuestion) {
                    ((MultiSelectQuestionViewHolder) questionViewHolder).bind((MultiSelectQuestion) question);
                }
                break;
            case YEAR_PICKER:
                if (questionViewHolder instanceof YearPickerQuestionViewHolder && question instanceof YearPickerQuestion) {
                    ((YearPickerQuestionViewHolder) questionViewHolder).bind((YearPickerQuestion) question);
                }
                break;
            case DATE_PICKER:
                if (questionViewHolder instanceof DatePickerQuestionViewHolder && question instanceof DatePickerQuestion) {
                    ((DatePickerQuestionViewHolder) questionViewHolder).bind((DatePickerQuestion) question);
                }
                break;
            case SINGLE_TEXT_FIELD:
                if (questionViewHolder instanceof SingleTextFieldQuestionViewHolder && question instanceof SingleTextFieldQuestion) {
                    ((SingleTextFieldQuestionViewHolder) questionViewHolder).bind((SingleTextFieldQuestion) question);
                }
                break;
            case MULTI_TEXT_FIELD:
                if (questionViewHolder instanceof MultiTextFieldQuestionViewHolder && question instanceof MultiTextFieldQuestion) {
                    ((MultiTextFieldQuestionViewHolder) questionViewHolder).bind((MultiTextFieldQuestion) question);
                }
                break;
            case DYNAMIC_LABEL_TEXT_FIELD:
                if (questionViewHolder instanceof DynamicLabelTextFieldQuestionViewHolder && question instanceof DynamicLabelTextFieldQuestion) {
                    ((DynamicLabelTextFieldQuestionViewHolder) questionViewHolder).bind((DynamicLabelTextFieldQuestion) question);
                }                break;
            case ADD_TEXT_FIELD:
                if (questionViewHolder instanceof AddTextFieldQuestionViewHolder && question instanceof AddTextFieldQuestion) {
                    ((AddTextFieldQuestionViewHolder) questionViewHolder).bind((AddTextFieldQuestion) question);
                }
                break;
            case SEGMENT_SELECT:
                if (questionViewHolder instanceof SegmentSelectQuestionViewHolder && question instanceof SegmentSelectQuestion) {
                    ((SegmentSelectQuestionViewHolder) questionViewHolder).bind((SegmentSelectQuestion) question);
                }
                break;
            case TABLE_SELECT:
                if (questionViewHolder instanceof TableSelectQuestionViewHolder && question instanceof TableSelectQuestion) {
                    ((TableSelectQuestionViewHolder) questionViewHolder).bind((TableSelectQuestion) question);
                }
                break;
        }
    }

    private @LayoutRes int getLayout(QuestionType questionType) {
        switch (questionType) {
            case SINGLE_SELECT:
                return R.layout.view_single_select_question;
            case MULTI_SELECT:
                return R.layout.view_multi_select_question;
            case YEAR_PICKER:
                return R.layout.view_year_picker_question;
            case DATE_PICKER:
                return R.layout.view_date_picker_question;
            case SINGLE_TEXT_FIELD:
                return R.layout.view_single_text_field_question;
            case MULTI_TEXT_FIELD:
                return R.layout.view_multi_text_field_question;
            case DYNAMIC_LABEL_TEXT_FIELD:
                return R.layout.view_dynamic_label_text_field_question;
            case ADD_TEXT_FIELD:
                return R.layout.view_add_text_field_question;
            case SEGMENT_SELECT:
                return R.layout.view_segment_select_question;
            case TABLE_SELECT:
                return R.layout.view_table_select_question;
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return getQuestionType(position).ordinal();
    }

    private Question getItem(int position) {
        return mState.getQuestionFor(position);
    }

    private QuestionType getQuestionType(int position) {
        Question question = getItem(position);
        return QuestionType.fromString(question.questionType);
    }

    @Override
    public int getItemCount() {
        return mState.getVisibleQuestionCount();
    }
}
