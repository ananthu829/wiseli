package uk.ac.tees.mad.w9501736.ui.fragment.signInRegisterPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import uk.ac.tees.mad.w9501736.R;

public class SignInRegisterFragment extends Fragment {

    private AppCompatButton btnSignUp;
    private AppCompatTextView tvSignedUser;

    public SignInRegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setEnterTransition(null);
        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                getActivity().finish();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSignUp = view.findViewById(R.id.btnSignUp);
        tvSignedUser = view.findViewById(R.id.tvSignedUser);
        btnSignUp.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_signInRegisterFragment_to_registerFragment);
        });
        tvSignedUser.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_signInRegisterFragment_to_signInFragment);
        });

    }
}
