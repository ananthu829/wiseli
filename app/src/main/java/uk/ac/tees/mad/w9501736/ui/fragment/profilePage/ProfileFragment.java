package uk.ac.tees.mad.w9501736.ui.fragment.profilePage;


import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.kroegerama.imgpicker.BottomSheetImagePicker;
import com.kroegerama.imgpicker.ButtonType;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import github.nisrulz.easydeviceinfo.base.EasyDeviceMod;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import uk.ac.tees.mad.w9501736.Database.DatabaseFactory;
import uk.ac.tees.mad.w9501736.R;
import uk.ac.tees.mad.w9501736.data.WiseLiRepository;
import uk.ac.tees.mad.w9501736.data.model.Resource;
import uk.ac.tees.mad.w9501736.data.model.WiseLiUser;
import uk.ac.tees.mad.w9501736.data.remote.WiseLiApiClient;
import uk.ac.tees.mad.w9501736.data.remote.WiseLiApiService;
import uk.ac.tees.mad.w9501736.models.LoginModel;
import uk.ac.tees.mad.w9501736.ui.BaseFragment;
import uk.ac.tees.mad.w9501736.ui.activity.LandingActivity;
import uk.ac.tees.mad.w9501736.ui.viewModel.RegisterPage.RegisterFragmentViewModel;
import uk.ac.tees.mad.w9501736.utils.AppPreferences;
import uk.ac.tees.mad.w9501736.utils.UtilHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment implements BottomSheetImagePicker.OnImagesSelectedListener {

    private static final String TAG = "RegisterFragment";
    public MultipartBody.Part image;
    @BindView(R.id.btnSignUp)
    AppCompatButton btnSignUp;
    @BindView(R.id.imgProfileImage)
    AppCompatImageView imgProfileImage;
    @BindView(R.id.edtFirstName)
    TextInputLayout edtFirstName;
    @BindView(R.id.edtLastName)
    TextInputLayout edtLastName;
    @BindView(R.id.etUserName)
    TextInputLayout etUserName;
    @BindView(R.id.edtPassword)
    TextInputLayout edtPassword;
    @BindView(R.id.rlUploadImage)
    RelativeLayout rlUploadImage;
    @BindView(R.id.etPhoneNumber)
    TextInputLayout etPhoneNumber;
    @BindView(R.id.btnTG)
    MaterialButtonToggleGroup btnTG;
    WiseLiUser wiseLiUser;
    @BindView(R.id.edtEmailID)
    TextInputLayout edtEmailID;
    Disposable dMainListObservable;
    AppPreferences mAppPreferences;
    WiseLiUser userDetails;
    private WiseLiRepository apiRepo;
    private RegisterFragmentViewModel registerFragmentViewModel;
    private FusedLocationProviderClient mFusedLocationClient;


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //WiseLiComponent wiseLiComponent = ((WiseLiComponentProvider) getActivity().getApplication()).getWiseLiComponent();
        //wiseLiComponent.inject(this);
        registerFragmentViewModel = new ViewModelProvider(this).get(RegisterFragmentViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_profile;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        getTextChangeListener();
        btnClicks();
    }

    private void init(View view) {


        wiseLiUser = new WiseLiUser();

        DatabaseFactory.setupObject(getContext());

        mAppPreferences = AppPreferences.getInstance(getContext());

        userDetails = mAppPreferences.getUserCashedInfo();

        wiseLiUser.setPassword(userDetails.getPassword());

        wiseLiUser.setProfilePic(userDetails.getProfilePic());

        Glide.with(getActivity()).load(userDetails.getProfilePic()).into(imgProfileImage);

        edtFirstName.getEditText().setText(userDetails.getFirstName());
        wiseLiUser.setFirstName(userDetails.getFirstName());
        edtLastName.getEditText().setText(userDetails.getLastName());
        wiseLiUser.setLastName(userDetails.getLastName());
        etUserName.getEditText().setText(userDetails.getUsername());
        wiseLiUser.setUsername(userDetails.getUsername());
        edtEmailID.getEditText().setText(userDetails.getEmail());
        wiseLiUser.setEmail(userDetails.getEmail());
        edtPassword.getEditText().setText(userDetails.getPassword());
        wiseLiUser.setPhoneNumber(userDetails.getPhoneNumber());
        etPhoneNumber.getEditText().setText(userDetails.getPhoneNumber());
        wiseLiUser.setPhoneNumber(userDetails.getPhoneNumber());
        //   Log.i("ENTER",userDetails.getPassword().toString());
        Glide.with(getContext())
                .load(mAppPreferences.getUserCashedInfo().getProfilePic() + "")
                .into(imgProfileImage);
        if (userDetails.getGender().equals("Male")) {
            btnTG.check(R.id.btnMale);
        }
        if (userDetails.getGender().equals("Female")) {
            btnTG.check(R.id.btnFemale);
        }
        MaterialButton materialButton = view.findViewById(btnTG.getCheckedButtonId());
        wiseLiUser.setGender(materialButton.getText().toString());

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        getCurrentLoc();

        getDeviceDetails();

        wiseLiUser.setToken(userDetails.getToken());


        btnTG.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    if (checkedId == R.id.btnMale) {
                        wiseLiUser.setGender("Male");
                    } else {
                        wiseLiUser.setGender("Female");
                    }
                }
            }
        });
    }

    private void btnClicks() {
        getImageBtnClick();
        getGenderOnBtnClick();
        registerBtnClick();
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

    private void getGenderOnBtnClick() {
        btnTG.setOnClickListener(v -> {
            MaterialButton materialButton = v.findViewById(btnTG.getCheckedButtonId());
            wiseLiUser.setGender(materialButton.getText().toString());
        });
    }

    private void getImageBtnClick() {
        rlUploadImage.setOnClickListener(v -> {
            Dexter.withContext(getActivity())
                    .withPermissions(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ).withListener(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport report) {
                    if (report.areAllPermissionsGranted()) {
                        new BottomSheetImagePicker.Builder(getString(R.string.file_provider))
                                .cameraButton(ButtonType.Button)
                                .galleryButton(ButtonType.Button)
                                .singleSelectTitle(R.string.pick_single)
                                .peekHeight(R.dimen._260)
                                .columnSize(R.dimen._90)
                                .requestTag("single")
                                .show(getChildFragmentManager(), null);
                    }
                }

                @Override
                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                    token.continuePermissionRequest();
                }
            }).check();
        });
    }

    private void registerBtnClick() {
        btnSignUp.setOnClickListener(v -> {
            getRegisterData();
        });

    }

    private void getTextChangeListener() {
        validateFirstName();
        validateLastName();
        validateUserName();
        validatePassword();
        validatePhoneNumber();
        validateEmailId();
    }

    private void validateFirstName() {
        edtFirstName.setHelperText(getString(R.string.empty_string));
        edtFirstName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    edtFirstName.setHelperText(getString(R.string.empty_string));
                    wiseLiUser.setFirstName(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 1) {
                    edtFirstName.setHelperText(getString(R.string.field_empty_error));
                }
            }
        });
    }

    private void validateLastName() {
        edtLastName.setHelperText(getString(R.string.empty_string));
        edtLastName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    edtLastName.setHelperText(getString(R.string.empty_string));
                    wiseLiUser.setLastName(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 1) {
                    edtLastName.setHelperText(getString(R.string.field_empty_error));
                }
            }
        });
    }

    private void validateUserName() {
        etUserName.setHelperText(getString(R.string.empty_string));
        etUserName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    etUserName.setHelperText(getString(R.string.empty_string));
                    wiseLiUser.setUsername(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 1) {
                    etUserName.setHelperText(getString(R.string.field_empty_error));
                }
            }
        });
    }

    private void validatePassword() {
        edtPassword.setHelperText(getString(R.string.empty_string));
        edtPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    edtPassword.setHelperText(getString(R.string.empty_string));
                    wiseLiUser.setPassword(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 1) {
                    edtPassword.setHelperText(getString(R.string.field_empty_error));
                }
            }
        });
    }

    private void validatePhoneNumber() {
        etPhoneNumber.setHelperText(getString(R.string.empty_string));
        etPhoneNumber.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    etPhoneNumber.setHelperText(getString(R.string.empty_string));
                    wiseLiUser.setPhoneNumber(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 1) {
                    etPhoneNumber.setHelperText(getString(R.string.field_empty_error));
                }
            }
        });
    }

    private void validateEmailId() {
        edtEmailID.setHelperText(getString(R.string.empty_string));
        edtEmailID.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    edtEmailID.setHelperText(getString(R.string.empty_string));
                    wiseLiUser.setEmail(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 1) {
                    edtEmailID.setHelperText(getString(R.string.field_empty_error));
                }
            }
        });
    }

    private void getRegisterData() {
        if (!edtPassword.getEditText().getText().equals("") &&
                !edtEmailID.getEditText().getText().equals("") &&
                !etUserName.getEditText().getText().equals("") &&
                !etPhoneNumber.getEditText().getText().equals("") &&
                !edtFirstName.getEditText().getText().equals("") &&
                !edtLastName.getEditText().getText().equals("")
        ) {
            //subscribeObservers(wiseLiUser);
            getRegisterApiCall();
        }

        //navigateToLanding();
    }

    private void navigateToLanding() {
        showProgressBar(false);
        startActivity(new Intent(getActivity(), LandingActivity.class));
        getActivity().finish();
    }

    private void subscribeObservers(final WiseLiUser wiseLiUser) {
        registerFragmentViewModel.getRegisterUserData(wiseLiUser).observe(this, userResponse -> {
            if (userResponse != null) {
                if (userResponse.data != null) {
                    switch (userResponse.status) {

                        case LOADING: {
                            showProgressBar(true);
                            break;
                        }

                        case ERROR: {
                            showProgressBar(false);
                            Toast.makeText(getContext(), "UnScussess Error : " + userResponse.message, Toast.LENGTH_SHORT).show();
                            break;
                        }

                        case SUCCESS: {
                            showProgressBar(false);
                            Toast.makeText(getContext(), "Scussess device ID : " + userResponse.data.getDeviceId(), Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
            }
        });
    }

    private MultipartBody.Part getImageFile(Uri uri) {
        MultipartBody.Part body = MultipartBody.Part.createFormData("", "");
        try {


          File file = new File(UtilHelper.getRealPathFromURI_API19(getContext(), uri));
            wiseLiUser.setProfilePic(file.getPath());
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"),file);

            body = MultipartBody.Part.createFormData("profile_pic", "abc.jpeg", requestFile);
            System.out.println("getImageFile: file.getName()" + file);


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception getImageFile: Error" + e.getMessage());
        }
        System.out.println("getImageFile: Error");
        return body;

    }

    @Override
    public void onImagesSelected(@NotNull List<? extends Uri> list, @org.jetbrains.annotations.Nullable String s) {
        for (Uri uri : list) {
            Glide.with(getActivity()).load(uri).into(imgProfileImage);
            image = getImageFile(uri);
        }
    }

    public void getRegisterApiCall() {
        showProgressBar(true);
        Retrofit retrofit = new WiseLiApiClient().getRetrofitClient();
        final WiseLiApiService webServices = retrofit.create(WiseLiApiService.class);
        Log.d(TAG, "getRegisterApiCall: wiseLiUser.getLatitude()" + wiseLiUser.getLatitude());
        Log.d(TAG, "getRegisterApiCall: wiseLiUser.getLongitude()" + wiseLiUser.getLongitude());
        Log.d(TAG, "getRegisterApiCall: wiseLiUser.getFirstName()" + wiseLiUser.getFirstName());
        Log.d(TAG, "getRegisterApiCall: wiseLiUser.getLastName()" + wiseLiUser.getLastName());
        Log.d(TAG, "getRegisterApiCall: wiseLiUser.getEmail()" + wiseLiUser.getEmail());
        Log.d(TAG, "getRegisterApiCall: wiseLiUser.getPhoneNumber()" + wiseLiUser.getDeviceType());
        Log.d(TAG, "getRegisterApiCall: wiseLiUser.getPhoneNumber()" + wiseLiUser.getDeviceId());

        RequestBody token = createPartFromString(wiseLiUser.getToken());
        RequestBody first_name = createPartFromString(wiseLiUser.getFirstName());
        RequestBody last_name = createPartFromString(wiseLiUser.getLastName());
        RequestBody username = createPartFromString(wiseLiUser.getUsername());
        RequestBody email = createPartFromString(wiseLiUser.getEmail());
        RequestBody phone_number = createPartFromString(wiseLiUser.getPhoneNumber());
        RequestBody password = createPartFromString(wiseLiUser.getPassword());
        RequestBody device_id = createPartFromString(wiseLiUser.getDeviceId());
        RequestBody device_type = createPartFromString(wiseLiUser.getDeviceType());
        RequestBody latitude = createPartFromString(wiseLiUser.getLatitude());
        RequestBody longitude = createPartFromString(wiseLiUser.getLongitude());
        RequestBody gender = createPartFromString(wiseLiUser.getGender());

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("token", token);
        map.put("first_name", first_name);
        map.put("last_name", last_name);
        map.put("email", email);
        map.put("gender", gender);
        map.put("phone_number", phone_number);
        map.put("username", username);
        map.put("password", password);
        map.put("device_id", device_id);
        map.put("device_type", device_type);
        map.put("latitude", latitude);
        map.put("longitude", longitude);

        Observable<Resource<WiseLiUser>> likedObservable = webServices.editUser(map,
                image
        );
        likedObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(registerUser());
    }

    private RequestBody createPartFromString(String partString) {
        return RequestBody.create(MultipartBody.FORM, partString);
    }

    private Observer<Resource<WiseLiUser>> registerUser() {
        return new Observer<Resource<WiseLiUser>>() {

            @Override
            public void onSubscribe(Disposable d) {
                dMainListObservable = d;
                Log.d("registerUser", " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onNext(Resource<WiseLiUser> value) {
                Log.d("registerUser", " onNext : value : " + value.getMessage());
                if (value.result) {
                    LoginModel.LoginModelDB loginModel = new LoginModel().new LoginModelDB();
                    loginModel.setUser_id(String.valueOf(wiseLiUser.getUserId()));
                    loginModel.setFirst_name(wiseLiUser.getFirstName());
                    loginModel.setLast_name(wiseLiUser.getLastName());
                    loginModel.setToken(userDetails.getToken());
                    loginModel.setEmail(wiseLiUser.getEmail());
                    loginModel.setUsername(wiseLiUser.getUsername());
                    loginModel.setGender(wiseLiUser.getGender());
                    if (wiseLiUser.getProfilePic() != null) {
                        loginModel.setProfile_pic(wiseLiUser.getProfilePic());
                    }
                    mAppPreferences.setToken(wiseLiUser.getToken());
                    mAppPreferences.setUserDetails(loginModel);
                    mAppPreferences.setUserCashedInfo(wiseLiUser);
                    userDetails.setPassword(wiseLiUser.getPassword());
                    ( (LandingActivity) getActivity()).updateNavHeader();

                    Log.d("registerUser", " onNext : value : " + value);
                } else {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), value.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(Throwable e) {
                showProgressBar(false);
                Snackbar.make(getActivity().findViewById(android.R.id.content), e.getMessage(), Snackbar.LENGTH_LONG).show();
                Log.d("registerUser", " onError : value : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                showProgressBar(false);
                Log.d("registerUser", " onComplete");
            }
        };
    }

    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        //iLandingPresenter.getWeatherForecastWebService(String.valueOf(latitude), String.valueOf(longitude));
        System.out.println("LandingActivity getLastLocation");
        wiseLiUser.setLongitude("0.000");
        wiseLiUser.setLatitude("0.000");
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        Location mLastLocation = task.getResult();
                        System.out.println("LandingActivity getLatitude : " + mLastLocation.getLatitude() + ", getLongitude : " + mLastLocation.getLongitude());
                        wiseLiUser.setLatitude(Double.toString(mLastLocation.getLatitude()));
                        wiseLiUser.setLongitude(Double.toString(mLastLocation.getLongitude()));
                    } else {
                        Snackbar.make(getActivity().findViewById(android.R.id.content), getResources().getString(R.string.snack_error_location_null), Snackbar.LENGTH_LONG).show();

                    }
                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (dMainListObservable != null)
            dMainListObservable.dispose();
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
                    wiseLiUser.setDeviceType(deviceName);
                    wiseLiUser.setDeviceId(easyDeviceMod.getBuildID());
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }
}
