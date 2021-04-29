package uk.ac.tees.mad.w9501736.ui.fragment.splashScreen;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import uk.ac.tees.mad.w9501736.R;
import uk.ac.tees.mad.w9501736.data.model.WiseLiUser;
import uk.ac.tees.mad.w9501736.ui.BaseFragment;
import uk.ac.tees.mad.w9501736.ui.activity.LandingActivity;
import uk.ac.tees.mad.w9501736.ui.viewModel.splashScreen.SplashScreenFragmentViewModel;
import uk.ac.tees.mad.w9501736.utils.AppPreferences;

import static uk.ac.tees.mad.w9501736.utils.AppConstants.SPLASH_SCREEN_TIME_OUT;

public class SplashScreenFragment extends BaseFragment {

    AppPreferences mAppPreferences;
    WiseLiUser userDetails;

    private SplashScreenFragmentViewModel splashScreenFragmentViewModel;

    public SplashScreenFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false);
    }


    @Override
    protected int layoutRes() {
        return R.layout.fragment_splash_screen;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppPreferences = AppPreferences.getInstance(getContext());
        userDetails = mAppPreferences.getUserCashedInfo();
        //splashScreenFragmentViewModel = new ViewModelProvider(requireActivity()).get(SplashScreenFragmentViewModel.class);
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Dexter.withContext(getActivity())
                    .withPermissions(
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.INTERNET
                    ).withListener(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport report) {
                    if (report.areAllPermissionsGranted()) {
                        checkIfUserLoggedIn(view);
                    } else {
                        checkIfUserLoggedIn(view);
                    }
                }

                @Override
                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                    token.continuePermissionRequest();
                }
            }).check();
        }, SPLASH_SCREEN_TIME_OUT);
    }

    private void checkIfUserLoggedIn(View view) {
        if (userDetails != null) {
            startActivity(new Intent(getActivity(), LandingActivity.class));
            getActivity().finish();
        } else {
            Navigation.findNavController(view).navigate(R.id.action_splashScreenFragment_to_signInFragment);
        }
    }
}
