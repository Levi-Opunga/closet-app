package com.moringaschool.closetapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.moringaschool.closetapp.R;
import com.moringaschool.closetapp.Validation;
import com.moringaschool.closetapp.ui.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SignInFragment extends Fragment {
    FirebaseAuth auth;
    @BindView(R.id.signInBtn)
    Button button;
    @BindView(R.id.editEmailSignIN)
    EditText email;
    @BindView(R.id.editPassSignIn)
    EditText password;
    @BindView(R.id.signUpText)
    TextView text;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance();
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        text.setOnClickListener(v -> {
            TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tabLayout2);
            tabLayout.selectTab(tabLayout.getTabAt(1));
        });
        button.setOnClickListener(v -> {
            String email = this.email.getText().toString().trim();
            String password = this.password.getText().toString().trim();
            if (!Validation.ValidEmail(email) && !Validation.ValidPassword(password)) {
                this.email.setError("Invalid Email");
                this.email.findFocus();
                this.password.setError("Invalid Password");
                this.password.findFocus();
                return;
            }
            if (!Validation.ValidEmail(email)) {
                this.email.setError("Invalid Email");
                this.email.findFocus();
                return;
            }
            if (!Validation.ValidPassword(password)) {
                this.password.setError("Invalid Password");
                this.password.findFocus();
                return;
            }
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        setErrors();
                        Toast.makeText(getContext(), "Incorrect Email or password", Toast.LENGTH_LONG).show();


                    }
                }
            });


        });

    }

    void setErrors() {
        this.email.setError("Incorrect Email");
        this.email.findFocus();
        this.password.setError("Incorrect Password");
        this.password.findFocus();
    }
}