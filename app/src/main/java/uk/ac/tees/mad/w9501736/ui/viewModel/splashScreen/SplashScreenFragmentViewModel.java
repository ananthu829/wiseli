package uk.ac.tees.mad.w9501736.ui.viewModel.splashScreen;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import javax.inject.Inject;

import uk.ac.tees.mad.w9501736.ui.viewModel.BaseViewModel;

public class SplashScreenFragmentViewModel extends BaseViewModel {
    private static final String TAG = "SplashScreenFragmentVie";

    @Inject
    public SplashScreenFragmentViewModel(@NonNull Application application) {
        super(application);
        Log.d(TAG, "SplashScreenFragmentViewModel: viewmodel is working...");
    }
}
