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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider;
import com.moringaschool.closetapp.AppUser;
import com.moringaschool.closetapp.R;
import com.moringaschool.closetapp.ui.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpFragment extends Fragment {
    @BindView(R.id.editEmailSignUp)
    EditText email;
    @BindView(R.id.editNameSignUp)
    EditText name;
    @BindView(R.id.editPassSignUp)
    EditText password;
    @BindView(R.id.editNumberSignUp)
    EditText phone;
    @BindView(R.id.signUpBtn)
    Button button;
    @BindView(R.id.signInText)
    TextView text;
    FirebaseAuth auth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
text.setOnClickListener(v->{
    TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tabLayout2);
    tabLayout.selectTab(tabLayout.getTabAt(0));
});
        button.setOnClickListener(v -> {
            createUser();
        });
    }

    private void createUser() {
        String email = this.email.getText().toString().trim();
        String name = this.name.getText().toString().trim();
        String password = this.password.getText().toString().trim();
        String phone = this.phone.getText().toString().trim();


        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = user.getUid();

                    DatabaseReference restaurantRef = FirebaseDatabase
                            .getInstance()
                            .getReference("User").child(uid);
                    DatabaseReference pushRef = restaurantRef.push();
                    String pushId = pushRef.getKey();
                    AppUser newUser = new AppUser(email, name, phone);
                    newUser.setPushId(pushId);
                    pushRef.setValue(newUser).addOnCompleteListener(new OnCompleteListener() {

                        @Override
                        public void onComplete(@NonNull Task task) {
                            Toast.makeText(getContext(), "Successfully created Account", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    });

                } else {
                    Toast.makeText(getContext(), "Please Try Again", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}