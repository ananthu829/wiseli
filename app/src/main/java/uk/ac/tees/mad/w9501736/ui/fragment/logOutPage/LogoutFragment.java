package uk.ac.tees.mad.w9501736.ui.fragment.logOutPage;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import uk.ac.tees.mad.w9501736.R;
import uk.ac.tees.mad.w9501736.ui.BaseFragment;
import uk.ac.tees.mad.w9501736.ui.activity.LoginSignUpActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogoutFragment extends BaseFragment {


    public LogoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logout, container, false);
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_logout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAppPreferences.clear();
        startActivity(new Intent(getBaseActivity(), LoginSignUpActivity.class));
        getBaseActivity().finish();
    }
}
