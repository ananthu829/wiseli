package uk.ac.tees.mad.w9501736.ui.fragment.registerPage;


import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.kroegerama.imgpicker.BottomSheetImagePicker;
import com.kroegerama.imgpicker.ButtonType;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import uk.ac.tees.mad.w9501736.R;
import uk.ac.tees.mad.w9501736.data.WiseLiRepository;
import uk.ac.tees.mad.w9501736.data.model.WiseLiUser;
import uk.ac.tees.mad.w9501736.ui.BaseFragment;
import uk.ac.tees.mad.w9501736.ui.activity.LandingActivity;
import uk.ac.tees.mad.w9501736.ui.viewModel.RegisterPage.RegisterFragmentViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends BaseFragment implements BottomSheetImagePicker.OnImagesSelectedListener {

    @BindView(R.id.btnSignUp)
    AppCompatButton btnSignUp;
    @BindView(R.id.imgProfileImage)
    AppCompatImageView imgProfileImage;
    @BindView(R.id.edtFirstName)
    AppCompatEditText edtFirstName;
    @BindView(R.id.edtLastName)
    AppCompatEditText edtLastName;
    @BindView(R.id.etPhoneNumber)
    TextInputEditText etPhoneNumber;
    @BindView(R.id.rlUploadImage)
    RelativeLayout rlUploadImage;
    @BindView(R.id.edtEmailID)
    AppCompatEditText edtEmailID;
    @BindView(R.id.btnTG)
    MaterialButtonToggleGroup btnTG;
    WiseLiUser wiseLiUser;
    private WiseLiRepository apiRepo;
    private RegisterFragmentViewModel registerFragmentViewModel;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //WiseLiComponent wiseLiComponent = ((WiseLiComponentProvider) getActivity().getApplication()).getWiseLiComponent();
        //wiseLiComponent.inject(this);
        registerFragmentViewModel = new ViewModelProvider(getActivity()).get(RegisterFragmentViewModel.class);
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
        Glide.with(getActivity()).load(R.drawable.image1).into(imgProfileImage);
        btnSignUp.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), LandingActivity.class));
            getActivity().finish();
        });
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
        btnTG.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            int buttonID = group.getCheckedButtonId();
            MaterialButton materialButton = view.findViewById(buttonID);
            materialButton.getText();
            Toast.makeText(getActivity(), "Text : " + materialButton.getText(), Toast.LENGTH_SHORT).show();
        });

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
                            break;
                        }

                        case SUCCESS: {
                            showProgressBar(false);
                            startActivity(new Intent(getActivity(), LandingActivity.class));
                            getActivity().finish();
                            break;
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onImagesSelected(@NotNull List<? extends Uri> list, @org.jetbrains.annotations.Nullable String s) {
        for (Uri uri : list) {
            Glide.with(getActivity()).load(uri).into(imgProfileImage);
        }
    }
}
