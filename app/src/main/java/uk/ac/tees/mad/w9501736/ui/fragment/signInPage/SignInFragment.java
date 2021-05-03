package uk.ac.tees.mad.w9501736.ui.fragment.signInPage;


import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import github.nisrulz.easydeviceinfo.base.EasyDeviceMod;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.ac.tees.mad.w9501736.Database.DatabaseFactory;
import uk.ac.tees.mad.w9501736.R;
import uk.ac.tees.mad.w9501736.data.model.WiseLiUser;
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
    private TextInputLayout username, password;
    private AppCompatButton btnLogin;
    AppPreferences mAppPreferences;
    private FusedLocationProviderClient mFusedLocationClient;
    protected Location mLastLocation;
    public String lat = "0.000";
    public String log = "0.000";
    private  static  String USERNAME  ="username";
    private  static  String PASSWORD  ="password";

    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
            outState.putString(USERNAME,username.getEditText().getText().toString());
            outState.putString(PASSWORD,password.getEditText().getText().toString());

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null) {
            username.getEditText().setText(savedInstanceState.getString(USERNAME));
            password.getEditText().setText(savedInstanceState.getString(PASSWORD));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sign_in, container, false);
        username = v.findViewById(R.id.edtUsername);
        password = v.findViewById(R.id.edtPassword);
        DatabaseFactory.setupObject(getContext());

        mAppPreferences = AppPreferences.getInstance(getContext());
        validatePassword();
        validateUserName();
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
        getDeviceDetails();
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

    public void getDeviceDetails() {
        Dexter.withContext(getActivity())
                .withPermissions(
                        Manifest.permission.READ_PHONE_STATE
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.areAllPermissionsGranted()) {
                    EasyDeviceMod easyDeviceMod = new EasyDeviceMod(getActivity());
                    String deviceName = easyDeviceMod.getManufacturer() + " " + easyDeviceMod.getDevice() + " " + easyDeviceMod.getModel();
                    deviceId = easyDeviceMod.getBuildID();
                    deviceType = deviceName;
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
        showProgressBar(true);
        if (NetworkDetector.haveNetworkConnection(getActivity())) {

            mFusedLocationClient.getLastLocation()
                    .addOnCompleteListener(getActivity(), task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();
                            getLoginApi();
                            lat = String.valueOf(mLastLocation.getLatitude());
                            log = String.valueOf(mLastLocation.getLongitude());
                            System.out.println("LandingActivity getLatitude : " + mLastLocation.getLatitude() + ", getLongitude : " + mLastLocation.getLongitude());
                        } else {
                            Snackbar.make(getActivity().findViewById(android.R.id.content), getResources().getString(R.string.snack_error_location_null), Snackbar.LENGTH_LONG).show();

                        }
                    });
            getLoginApi();
        } else {
            showProgressBar(false);
            Snackbar.make(getActivity().findViewById(android.R.id.content), getResources().getString(R.string.snack_error_network), Snackbar.LENGTH_LONG).show();

        }
    }

    private void validateUserName() {
        username.setHelperText(getString(R.string.empty_string));
        username.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    username.setHelperText(getString(R.string.empty_string));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 1) {
                    username.setHelperText(getString(R.string.field_empty_error));
                }
            }
        });
    }

    private void validatePassword() {
        password.setHelperText(getString(R.string.empty_string));
        password.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    password.setHelperText(getString(R.string.empty_string));

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 1) {
                    password.setHelperText(getString(R.string.field_empty_error));
                }
            }
        });
    }
    private void getLoginApi() {

        Call<LoginModel> api = mRetrofitService.login(username.getEditText().getText().toString(), password.getEditText().getText().toString(), deviceId, deviceType, lat, log);


        api.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> responseCall, Response<LoginModel> response) {
                if (response.body().getResult()) {
                    LoginModel loginModel = response.body();
                    mAppPreferences.setToken(loginModel.getLoginDetails().getToken());
                    mAppPreferences.setUserDetails(loginModel.getLoginDetails());
                    WiseLiUser user = new WiseLiUser();
                    user.setUserId(Integer.parseInt(response.body().getLoginDetails().getUser_id()));
                    user.setFirstName(response.body().getLoginDetails().getFirst_name());
                    user.setLastName(response.body().getLoginDetails().getLast_name());
                    user.setEmail(response.body().getLoginDetails().getEmail());
                    user.setUsername(response.body().getLoginDetails().getUsername());
                    user.setGender(response.body().getLoginDetails().getGender());
                    user.setPhoneNumber(response.body().getLoginDetails().getPhone_number());
                    user.setProfilePic(response.body().getLoginDetails().getProfile_pic());
                    user.setToken(response.body().getLoginDetails().getToken());
                    user.setPassword(password.getEditText().getText().toString());
                    user.setLatitude(lat);
                    user.setLongitude(log);
                    mAppPreferences.setUserCashedInfo(user);
                    startActivity(new Intent(getContext(), LandingActivity.class));
                    getActivity().finish();
                } else {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), response.body().getMsg(), Snackbar.LENGTH_LONG).show();
                    Log.d("tag1", "Failed---");

                }
                showProgressBar(false);
            }

            @Override
            public void onFailure(Call<LoginModel> responseCall, Throwable t) {
                t.printStackTrace();
                showProgressBar(false);
            }

        });
    }
}
