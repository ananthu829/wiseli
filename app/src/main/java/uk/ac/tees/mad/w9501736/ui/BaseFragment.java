package uk.ac.tees.mad.w9501736.ui;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import github.nisrulz.easydeviceinfo.base.EasyDeviceMod;
import uk.ac.tees.mad.w9501736.Database.DatabaseFactory;
import uk.ac.tees.mad.w9501736.data.model.WiseLiUser;
import uk.ac.tees.mad.w9501736.network.RestClient;
import uk.ac.tees.mad.w9501736.network.RestService;
import uk.ac.tees.mad.w9501736.ui.activity.LandingActivity;
import uk.ac.tees.mad.w9501736.ui.activity.LoginSignUpActivity;
import uk.ac.tees.mad.w9501736.utils.AppPreferences;


public abstract class BaseFragment extends Fragment {

    protected RestService mRetrofitService;
    protected AppPreferences mAppPreferences;
    protected WiseLiUser wiseLiUser;
    protected String deviceId = "";
    protected String deviceType = "";
    private Unbinder unbinder;
    private AppCompatActivity activity;

    @LayoutRes
    protected abstract int layoutRes();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        DatabaseFactory.setupObject(getContext());

        mAppPreferences = AppPreferences.getInstance(getContext());
        mRetrofitService = RestClient.getInstance();
        wiseLiUser = mAppPreferences.getUserCashedInfo();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (AppCompatActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    public void showProgressBar(boolean visibility) {
        if (activity instanceof LoginSignUpActivity) {
            ((LoginSignUpActivity) getBaseActivity()).showProgressBar(visibility);
        } else if (activity instanceof LandingActivity) {
            ((LandingActivity) getBaseActivity()).showProgressBar(visibility);
        }
    }

    public WiseLiUser getWiseLiUser() {
        return wiseLiUser;
    }

    public AppCompatActivity getBaseActivity() {
        return activity;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getDeviceType() {
        return deviceType;
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

}
