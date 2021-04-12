package com.promatas.wiseli.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.promatas.wiseli.R;

public class CommonEditableTextView extends CardView {


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
