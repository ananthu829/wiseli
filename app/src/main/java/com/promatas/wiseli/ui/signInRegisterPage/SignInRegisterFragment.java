package com.promatas.wiseli.ui.signInRegisterPage;

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

import com.promatas.wiseli.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignInRegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignInRegisterFragment extends Fragment {

    private AppCompatButton btnSignUp;
    private AppCompatTextView tvSignedUser;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignInRegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignInRegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignInRegisterFragment newInstance(String param1, String param2) {
        SignInRegisterFragment fragment = new SignInRegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setEnterTransition(null);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().getWindow().setExitTransition(null);
    }
}
