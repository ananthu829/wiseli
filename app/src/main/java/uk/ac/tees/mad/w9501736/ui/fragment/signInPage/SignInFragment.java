package uk.ac.tees.mad.w9501736.ui.fragment.signInPage;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.promatas.wiseli.R;

import uk.ac.tees.mad.w9501736.ui.activity.LandingActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {

    private AppCompatButton btnLogin;
    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnLogin = view.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), LandingActivity.class));
            getActivity().finish();
        });
    }
}
