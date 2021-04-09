package com.promatas.wiseli.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.fragment.NavHostFragment;

import com.promatas.wiseli.R;

public class LoginSignUpActivity extends AppCompatActivity {

    public NavGraph navGraph;
    public NavController navController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);
        getWindow().setEnterTransition(null);
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavInflater navInflater = navHostFragment.getNavController().getNavInflater();
        navGraph = navInflater.inflate(R.navigation.sign_in_register_navigation);
        navController = navHostFragment.getNavController();
        navGraph.setStartDestination(R.id.splashScreenFragment);
    }
}
