package uk.ac.tees.mad.w9501736.ui.fragment.splashScreen;


import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import uk.ac.tees.mad.w9501736.R;
import uk.ac.tees.mad.w9501736.ui.BaseFragment;
import uk.ac.tees.mad.w9501736.ui.viewModel.splashScreen.SplashScreenFragmentViewModel;

import static uk.ac.tees.mad.w9501736.utils.AppConstants.SPLASH_SCREEN_TIME_OUT;

public class SplashScreenFragment extends BaseFragment {


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
        splashScreenFragmentViewModel = new ViewModelProvider(requireActivity()).get(SplashScreenFragmentViewModel.class);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Navigation.findNavController(view).navigate(R.id.action_splashScreenFragment_to_signInFragment);
        }, SPLASH_SCREEN_TIME_OUT);
    }
}
