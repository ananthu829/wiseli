package uk.ac.tees.mad.w9501736.ui;

import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import butterknife.ButterKnife;
import uk.ac.tees.mad.w9501736.R;
import uk.ac.tees.mad.w9501736.network.RestService;
import uk.ac.tees.mad.w9501736.utils.AppPreferences;


public abstract class BaseActivity extends AppCompatActivity {
    public FrameLayout mProgressBar;
    protected RestService mRetrofitService;
    protected AppPreferences mAppPreferences;
    @Override
    public void setContentView(int layoutResID) {

        ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout frameLayout = constraintLayout.findViewById(R.id.activity_content);
        mProgressBar = constraintLayout.findViewById(R.id.progress_bar);
        mAppPreferences = AppPreferences.getInstance(this);

        getLayoutInflater().inflate(layoutResID, frameLayout, true);
        ButterKnife.bind(this);
        super.setContentView(constraintLayout);
    }

    public void showProgressBar(boolean visibility) {
        mProgressBar.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
    }

}
