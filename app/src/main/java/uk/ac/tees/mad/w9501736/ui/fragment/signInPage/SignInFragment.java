package uk.ac.tees.mad.w9501736.ui.fragment.signInPage;


import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.ac.tees.mad.w9501736.Database.DatabaseFactory;
import uk.ac.tees.mad.w9501736.R;
import uk.ac.tees.mad.w9501736.models.LoginModel;
import uk.ac.tees.mad.w9501736.network.RestClient;
import uk.ac.tees.mad.w9501736.network.RestService;
import uk.ac.tees.mad.w9501736.ui.activity.LandingActivity;
import uk.ac.tees.mad.w9501736.utils.AppPreferences;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {
    protected RestService mRetrofitService;
    private EditText username, password;
    private AppCompatButton btnLogin;
    AppPreferences mAppPreferences;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnLogin = view.findViewById(R.id.btnLogin);
        mRetrofitService = RestClient.getInstance();
        getProfileImage();
        btnLogin.setOnClickListener(v -> {
//            startActivity(new Intent(getActivity(), LandingActivity.class));
//            getActivity().finish();
            getProfileImage();
        });
    }

    private void getProfileImage() {

        Call<LoginModel> api = mRetrofitService.login(username.getText().toString(), password.getText().toString(), "device1", "android", "54.5760419", "-1.2344047");


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
