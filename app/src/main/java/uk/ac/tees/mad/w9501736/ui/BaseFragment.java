package uk.ac.tees.mad.w9501736.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import uk.ac.tees.mad.w9501736.ui.activity.LandingActivity;
import uk.ac.tees.mad.w9501736.ui.activity.LoginSignUpActivity;


public abstract class BaseFragment extends Fragment {

    private Unbinder unbinder;
    private AppCompatActivity activity;

    @LayoutRes
    protected abstract int layoutRes();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (AppCompatActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    public void showProgressBar(boolean visibility) {
        if (activity instanceof LoginSignUpActivity) {
            ((LoginSignUpActivity) getBaseActivity()).showProgressBar(visibility);
        } else if (activity instanceof LandingActivity) {
            ((LandingActivity) getBaseActivity()).showProgressBar(visibility);
        }
    }

    public AppCompatActivity getBaseActivity() {
        return activity;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
    }

}