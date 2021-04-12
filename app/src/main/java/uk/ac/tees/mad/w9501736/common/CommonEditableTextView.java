package uk.ac.tees.mad.w9501736.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;

import uk.ac.tees.mad.w9501736.R;

public class CommonEditableTextView extends CardView {

    private AppCompatTextView tv;
    private AppCompatEditText et;
    private AppCompatImageView ivEdit;
    private AppCompatImageView ivDelete;


    public CommonEditableTextView(@NonNull Context context) {
        super(context);
    }

    public CommonEditableTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CommonEditableTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

/*        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.CommonEditableTextView, 0, 0);

        String titleText = a.getString(R.styleable.ExpandableCardView_headerTitle);
        final Drawable drawable = a.getDrawable(R.styleable.ExpandableCardView_headerIcon);

        a.recycle();*/

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_cardview_editable_text_view, this, true);

        tv = findViewById(R.id.tv);
        et = findViewById(R.id.et);
        ivEdit = findViewById(R.id.ivEdit);
        ivDelete = findViewById(R.id.ivDelete);

        ivEdit.setOnClickListener(v -> {
        });

/*        final LinearLayout parent = (LinearLayout) getChildAt(0);

        final RelativeLayout header = (RelativeLayout) parent.getChildAt(0);

        final TextView titleTextView = (TextView) header.getChildAt(0);
        titleTextView.setText(titleText);

        final ImageView toggle = (ImageView) header.getChildAt(1);
        if(drawable != null) {
            toggle.setImageDrawable(drawable);
        }

        header.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setExpanded(toggle, !isExpanded);
                onExpansionToggled(toggle);
            }
        });*/
    }
}
