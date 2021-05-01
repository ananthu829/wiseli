package uk.ac.tees.mad.w9501736.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;
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
    View headerView;
    TextView navUsername;
    TextView navEmail;
    CircleImageView navProfile;

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
        headerView = navigationView.getHeaderView(0);
        navUsername = headerView.findViewById(R.id.navUsername);
        navEmail = headerView.findViewById(R.id.navEmail);
        navProfile = headerView.findViewById(R.id.navProfile);
        mAppPreferences = AppPreferences.getInstance(this);
        userDetails = mAppPreferences.getUserCashedInfo();
        navUsername.setText(userDetails.getUsername());
        navEmail.setText(userDetails.getEmail());
        Log.i("GlideImage", userDetails.toString());
        Glide.with(this)
                .load(userDetails.getProfilePic())
                .error(R.drawable.image1)
                .into(navProfile);
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

    public void updateNavHeader() {
        mAppPreferences = AppPreferences.getInstance(this);
        userDetails = mAppPreferences.getUserCashedInfo();
        navUsername.setText(userDetails.getUsername());
        navEmail.setText(userDetails.getEmail());
//        Log.i("GlideImage", userDetails.getProfilePic());
        Glide.with(this)
                .load(userDetails.getProfilePic())
                .error(R.drawable.image1)
                .into(navProfile);

    }
}
