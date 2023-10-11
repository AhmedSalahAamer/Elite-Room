package com.example.eliteroom.UiScreens.LoginPages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eliteroom.Const;
import com.example.eliteroom.Models.ModelUser;
import com.example.eliteroom.UiScreens.DoctorPages.DoctorHomeActivity;
import com.example.eliteroom.UiScreens.StudentsPages.StudentHomeActivity;
import com.example.eliteroom.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    ActivitySignUpBinding binding;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        onClick();

    }

    private void onClick() {
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        binding.signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.editTextEmail.getText().toString().trim();
                String password = binding.editTextPassword.getText().toString().trim();
                validate(email, password);

            }
        });

    }

    private void validate(String email, String password) {
        if (email.isEmpty()) {
            binding.editTextEmail.setError("This field can't be empty");
        } else if (password.isEmpty() || password.length() < 5) {
            binding.editTextPassword.setError("This field can't be empty");
        } else {
            signInFirebase(email, password);
            binding.editTextPassword.setText(null);
            binding.editTextEmail.setText(null);
        }
    }


    private void signInFirebase(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Const.getUserDetails(new Const.FireBaseCallBack() {
                    @Override
                    public void onCallBack(ModelUser modelUser1) {
                        Const.mUser = modelUser1;
                        Const.mUser.getUserType();
                        if (Const.mUser.getUserType().equals(Const.USER_DOCTOR)) {
                            Intent intent = new Intent(SignUp.this, DoctorHomeActivity.class);
                            startActivity(intent);
                        } else if (Const.mUser.getUserType().equals(Const.USER_STUDENT)) {
                            Intent intent = new Intent(SignUp.this, StudentHomeActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUp.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void signUpToFirebase(String name, String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                ref.child(Const.USERS).child(auth.getUid()).setValue(new ModelUser(email, Const.userType, auth.getUid(), name));
                if (Const.userType == Const.USER_DOCTOR) {
                    Intent intent = new Intent(SignUp.this, DoctorHomeActivity.class);
                    startActivity(intent);
                } else if (Const.userType == Const.USER_STUDENT) {
                    Intent intent = new Intent(SignUp.this, StudentHomeActivity.class);
                    startActivity(intent);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(SignUp.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}