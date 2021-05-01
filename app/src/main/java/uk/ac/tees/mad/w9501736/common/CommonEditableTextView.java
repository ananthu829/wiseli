package uk.ac.tees.mad.w9501736.common;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;

import uk.ac.tees.mad.w9501736.R;
import uk.ac.tees.mad.w9501736.ui.helper.AdapterInterface;

public class CommonEditableTextView extends CardView {

    AdapterInterface editClickListner;
    Integer id;
    private AppCompatTextView tv;
    private AppCompatEditText et;
    private AppCompatImageView ivEdit;
    private AppCompatImageView ivDelete;

    public CommonEditableTextView(@NonNull Context context) {
        super(context);
        init(context, null, 0);
    }

    public CommonEditableTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public CommonEditableTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public void init(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.view_cardview_editable_text_view, this, true);

        tv = findViewById(R.id.tvShopAddress);
        et = findViewById(R.id.et);
        ivEdit = findViewById(R.id.ivEdit);
        ivDelete = findViewById(R.id.ivLocChose);


        ivEdit.setOnClickListener(v -> {
            if (et.getVisibility() == VISIBLE) {
                String currentText = et.getText().toString();
                et.setVisibility(GONE);
                tv.setVisibility(VISIBLE);
                tv.setText(currentText);
                ivEdit.setImageResource(R.drawable.ic_mode_edit);
                if (editClickListner != null) {
                    editClickListner.setEditableText(id, et.getText().toString());
                }
            } else {
                et.setVisibility(VISIBLE);
                tv.setVisibility(GONE);
                et.setFocusable(true);
                ivEdit.setImageResource(R.drawable.ic_done);
            }
        });
    }

    public Boolean getEditTextVisibility() {
        return et.getVisibility() == VISIBLE;
    }

    public Boolean getTextTextVisibility() {
        return tv.getVisibility() == VISIBLE;
    }

    public String getEditableText() {
        return tv.getText().toString();
    }

    public void enableOrDisable(Boolean status) {
        ivEdit.setEnabled(status);
    }

    public void setEditableText(String text) {
        tv.setText(text);
        et.setText(text);
    }

    public void hideImageEditBtn(Boolean value) {
        if (value) {
            ivEdit.setVisibility(GONE);
        } else {
            ivEdit.setVisibility(VISIBLE);
        }
    }

    public void hideImageDeleteBtn(Boolean value) {
        if (value) {
            ivDelete.setVisibility(GONE);
        } else {
            ivDelete.setVisibility(VISIBLE);
        }
    }

    public void setEditableHintText(String text) {
        tv.setHint(text);
        et.setHint(text);
    }

    public void setEditableNumberInputType() {
        tv.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        et.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
    }

    public void setOnDeleteClickListener(AdapterInterface deleteClickListener, Integer id) {
        ivDelete.setOnClickListener(v -> {
            deleteClickListener.onDeleteCtaClicked(id);
        });
    }

    public void setOnEditClickListener(AdapterInterface editClickListner, Integer id) {
        this.editClickListner = editClickListner;
        this.id = id;
    }

}
