package uk.ac.tees.mad.w9501736.ui.fragment.registerPage;


import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.io.File;
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
import retrofit2.Retrofit;
import uk.ac.tees.mad.w9501736.R;
import uk.ac.tees.mad.w9501736.data.WiseLiRepository;
import uk.ac.tees.mad.w9501736.data.model.Resource;
import uk.ac.tees.mad.w9501736.data.model.WiseLiUser;
import uk.ac.tees.mad.w9501736.data.remote.WiseLiApiClient;
import uk.ac.tees.mad.w9501736.data.remote.WiseLiApiService;
import uk.ac.tees.mad.w9501736.ui.BaseFragment;
import uk.ac.tees.mad.w9501736.ui.activity.LandingActivity;
import uk.ac.tees.mad.w9501736.ui.viewModel.RegisterPage.RegisterFragmentViewModel;
import uk.ac.tees.mad.w9501736.utils.NetworkDetector;
import uk.ac.tees.mad.w9501736.utils.UtilHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends BaseFragment implements BottomSheetImagePicker.OnImagesSelectedListener {

    public MultipartBody.Part image;
    @BindView(R.id.btnSignUp)
    AppCompatButton btnSignUp;
    @BindView(R.id.imgProfileImage)
    AppCompatImageView imgProfileImage;
    protected Location mLastLocation;
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
    private WiseLiRepository apiRepo;
    private RegisterFragmentViewModel registerFragmentViewModel;
    Disposable dMainListObservable;
    private FusedLocationProviderClient mFusedLocationClient;

    public RegisterFragment() {
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
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_register;
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

        Glide.with(getActivity()).load(R.drawable.image1).into(imgProfileImage);

        MaterialButton materialButton = view.findViewById(btnTG.getCheckedButtonId());
        wiseLiUser.setGender(materialButton.getText().toString());

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        getCurrentLoc();

        getDeviceDetails();

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
        if (edtPassword.getEditText() != null &&
                edtEmailID.getEditText() != null &&
                etUserName.getEditText() != null &&
                etPhoneNumber.getEditText() != null &&
                edtFirstName.getEditText() != null &&
                edtLastName.getEditText() != null
        ) {
            //subscribeObservers(wiseLiUser);
            getRegisterApiCall();
        }

        //navigateToLanding();
    }

    private void navigateToLanding() {
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
            //pass it like this
            File file = new File(UtilHelper.getRealPathFromURI_API19(getContext(), uri));
            // create upload service client
            // create RequestBody instance from file
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(file));

            // MultipartBody.Part is used to send also the actual file name
            body = MultipartBody.Part.createFormData("profile_pic", file.getName(), requestFile);
            System.out.println("getImageFile: file.getName()" + file.getName());


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
            wiseLiUser.setImgBody(getImageFile(uri));
            image = getImageFile(uri);
        }
    }

    public void getRegisterApiCall() {
        showProgressBar(true);
        Retrofit retrofit = new WiseLiApiClient().getRetrofitClient();
        final WiseLiApiService webServices = retrofit.create(WiseLiApiService.class);
        Observable<Resource<WiseLiUser>> likedObservable = webServices.registerUser(wiseLiUser.getFirstName(),
                wiseLiUser.getLastName(),
                wiseLiUser.getEmail(),
                wiseLiUser.getGender(),
                image,
                wiseLiUser.getPhoneNumber(),
                wiseLiUser.getUsername(),
                wiseLiUser.getPassword(),
                wiseLiUser.getDeviceId(),
                wiseLiUser.getDeviceType(),
                wiseLiUser.getLatitude(),
                wiseLiUser.getLongitude());
        likedObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(registerUser());
    }

    private Observer<Resource<WiseLiUser>> registerUser() {
        return new Observer<Resource<WiseLiUser>>() {

            @Override
            public void onSubscribe(Disposable d) {
                dMainListObservable = d;
                Log.d("registerUser", " onSubscribe : " + d.isDisposed());
                showProgressBar(false);
            }

            @Override
            public void onNext(Resource<WiseLiUser> value) {

                Log.d("registerUser", " onNext : value : " + value);
                showProgressBar(false);
                navigateToLanding();
            }

            @Override
            public void onError(Throwable e) {
                Log.d("registerUser", " onError : value : " + e.getMessage());
                showProgressBar(false);
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

        if (NetworkDetector.haveNetworkConnection(getActivity())) {

            mFusedLocationClient.getLastLocation()
                    .addOnCompleteListener(getActivity(), task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();
                            System.out.println("LandingActivity getLatitude : " + mLastLocation.getLatitude() + ", getLongitude : " + mLastLocation.getLongitude());
                            wiseLiUser.setLatitude(Double.toString(mLastLocation.getLatitude()));
                            wiseLiUser.setLongitude(Double.toString(mLastLocation.getLongitude()));
                        } else {
                            Snackbar.make(getActivity().findViewById(android.R.id.content), getResources().getString(R.string.snack_error_location_null), Snackbar.LENGTH_LONG).show();

                        }
                    });
        } else {
            Snackbar.make(getActivity().findViewById(android.R.id.content), getResources().getString(R.string.snack_error_network_available), Snackbar.LENGTH_LONG).show();

        }
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
