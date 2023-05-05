package com.hamza.printingpress.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.hamza.printingpress.Api.UserAuth;
import com.hamza.printingpress.Dashboard.HomeActivity;
import com.hamza.printingpress.R;

public class LoginFragment extends Fragment {

    Context context;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        EditText emailField = v.findViewById(R.id.login_email);
        EditText passwordField = v.findViewById(R.id.login_pass);
        AppCompatButton loginBtn = v.findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(view -> {
            if(emailField.getText().toString().isEmpty()) {
                emailField.setError("Email not be empty");
                return;
            }
            if(passwordField.getText().toString().isEmpty()) {
                passwordField.setError("Password not be empty");
                return;
            }

            UserAuth.loginUser(context, emailField.getText().toString(), passwordField.getText().toString(), r -> {
                //Goto shop_admin dashboard activity
                if(r) {
                    Intent intent = new Intent(requireActivity(), HomeActivity.class);
                    startActivity(intent);
                    requireActivity().finish();
                }
                return null;
            });

        });

        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }
}