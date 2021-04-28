package uk.ac.tees.mad.w9501736.ui.fragment.signInPage;


import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.ac.tees.mad.w9501736.Database.DatabaseFactory;
import uk.ac.tees.mad.w9501736.R;
import uk.ac.tees.mad.w9501736.models.LoginModel;
import uk.ac.tees.mad.w9501736.network.RestClient;
import uk.ac.tees.mad.w9501736.network.RestService;
import uk.ac.tees.mad.w9501736.ui.BaseFragment;
import uk.ac.tees.mad.w9501736.ui.activity.LandingActivity;
import uk.ac.tees.mad.w9501736.utils.AppPreferences;
import uk.ac.tees.mad.w9501736.utils.NetworkDetector;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends BaseFragment {
    protected RestService mRetrofitService;
    private EditText username, password;
    private AppCompatButton btnLogin;
    AppPreferences mAppPreferences;
    private FusedLocationProviderClient mFusedLocationClient;
    protected Location mLastLocation;
    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sign_in, container, false);
        username = v.findViewById(R.id.emailEdt);
        password = v.findViewById(R.id.pswEdt);
        DatabaseFactory.setupObject(getContext());

        mAppPreferences = AppPreferences.getInstance(getContext());
        return v;
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_sign_in;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnLogin = view.findViewById(R.id.btnLogin);
        mRetrofitService = RestClient.getInstance();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        btnLogin.setOnClickListener(v -> {
//            startActivity(new Intent(getActivity(), LandingActivity.class));
//            getActivity().finish();
            getCurrentLoc();
        });
    }
    public void getCurrentLoc() {
        Dexter.withContext(getActivity())
                .withPermissions(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.areAllPermissionsGranted()) {
                    getLastLocation();
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }
    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        //iLandingPresenter.getWeatherForecastWebService(String.valueOf(latitude), String.valueOf(longitude));
        System.out.println("LandingActivity getLastLocation");

        if (NetworkDetector.haveNetworkConnection(getActivity())) {

            mFusedLocationClient.getLastLocation()
                    .addOnCompleteListener(getActivity(), task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();
                            getLoginApi();
                            System.out.println("LandingActivity getLatitude : " + mLastLocation.getLatitude() + ", getLongitude : " + mLastLocation.getLongitude());
                        } else {
                            Snackbar.make(getActivity().findViewById(android.R.id.content), getResources().getString(R.string.snack_error_location_null), Snackbar.LENGTH_LONG).show();

                        }
                    });
        } else {
            Snackbar.make(getActivity().findViewById(android.R.id.content), getResources().getString(R.string.snack_error_network_available), Snackbar.LENGTH_LONG).show();

        }
    }
    private void getLoginApi() {

        Call<LoginModel> api = mRetrofitService.login(username.getText().toString(), password.getText().toString(), "device1", "android", String.valueOf(mLastLocation.getLatitude()), String.valueOf(mLastLocation.getLongitude()));


        api.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> responseCall, Response<LoginModel> response) {

                if (response.body() != null) {
                    LoginModel loginModel = response.body();
                    mAppPreferences.setToken(loginModel.getEmail().getToken());
                    DatabaseFactory.getInstance().insertUserData(loginModel.getEmail());
                    startActivity(new Intent(getActivity(), LandingActivity.class));
                    getActivity().finish();
                } else {
                    Log.d("tag1", "Failed---");

                }


            }

            @Override
            public void onFailure(Call<LoginModel> responseCall, Throwable t) {
                t.printStackTrace();
            }


        });
    }
}
