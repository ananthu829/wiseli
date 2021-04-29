package uk.ac.tees.mad.w9501736.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import uk.ac.tees.mad.w9501736.R;
import uk.ac.tees.mad.w9501736.data.model.WiseLiUser;
import uk.ac.tees.mad.w9501736.ui.BaseActivity;
import uk.ac.tees.mad.w9501736.utils.AppPreferences;

public class LandingActivity extends BaseActivity {

    public NavGraph navGraph;
    public NavController navController;
    public DrawerLayout drawerLayout;
    public NavigationView navigationView;
    public Toolbar toolbar;
    AppPreferences mAppPreferences;
    WiseLiUser userDetails;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.landing_nav_host_fragment);
        NavInflater navInflater = navHostFragment.getNavController().getNavInflater();
        navGraph = navInflater.inflate(R.navigation.landing_navigation);
        navController = navHostFragment.getNavController();
        navGraph.setStartDestination(R.id.landingFragment);

        setupDrawerLayout();
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);


        mAppPreferences = AppPreferences.getInstance(this);
        userDetails = mAppPreferences.getUserCashedInfo();
    }

    private void setupDrawerLayout() {
        // Set up ActionBar
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, drawerLayout);
    }

    public void setToolbarTitle(String title) {
        toolbar.setTitle(title);
    }
}
