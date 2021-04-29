package uk.ac.tees.mad.w9501736.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.fragment.NavHostFragment;

import uk.ac.tees.mad.w9501736.R;
import uk.ac.tees.mad.w9501736.ui.BaseActivity;

public class LoginSignUpActivity extends BaseActivity {

    public NavGraph navGraph;
    public NavController navController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.login_sign_up_nav_host_fragment);
        NavInflater navInflater = navHostFragment.getNavController().getNavInflater();
        navGraph = navInflater.inflate(R.navigation.sign_in_register_navigation);
        navController = navHostFragment.getNavController();
        if(mAppPreferences.getToken().equals("")) {
            navGraph.setStartDestination(R.id.splashScreenFragment);
        }else {
            startActivity(new Intent(this, LandingActivity.class));
            finish();
        }
    }
}
