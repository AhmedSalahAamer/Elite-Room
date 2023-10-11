package com.example.eliteroom.UiScreens.LoginPages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eliteroom.Const;
import com.example.eliteroom.Models.ModelUser;
import com.example.eliteroom.UiScreens.DoctorPages.DoctorHomeActivity;
import com.example.eliteroom.UiScreens.StudentsPages.StudentHomeActivity;
import com.example.eliteroom.databinding.ActivitySignInBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {
    ActivitySignInBinding binding;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        onClick();


    }

    private void onClick() {

        binding.studentRadioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.doctorRadioBtn.setChecked(false);
                    Const.userType = Const.USER_STUDENT;
                } else {
                    Const.userType = "";
                }
            }
        });


        binding.doctorRadioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.studentRadioBtn.setChecked(false);
                    Const.userType = Const.USER_DOCTOR;
                } else {
                    Const.userType = "";
                }
            }
        });

        binding.signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.editTextName.getText().toString();
                String email = binding.editTextEmail.getText().toString().trim();
                String password = binding.editTextPassword.getText().toString();
                validate(name, email, password);

            }
        });


        binding.tvNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignUp();
            }
        });

        binding.addUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignUp();
            }
        });
    }

    private void validate(String name, String email, String password) {

        if (name.isEmpty()) {
            binding.editTextName.setError("This field can't be empty");
        } else if (email.isEmpty()) {
            binding.editTextEmail.setError("This field can't be empty");
        } else if (password.isEmpty() || password.length() < 8) {
            binding.editTextPassword.setError("This field can't be empty");
        } else if (Const.userType == "") {
            Toast.makeText(SignIn.this, "Kindly enter your type", Toast.LENGTH_SHORT).show();
        } else {
            signUpToFirebase(name, email, password);

            binding.editTextName.setText(null);
            binding.editTextEmail.setText(null);
            binding.editTextPassword.setText(null);
        }
    }


    private void signUpToFirebase(String name, String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Const.ref.child(Const.USERS).child(auth.getUid()).setValue(new ModelUser(email, Const.userType, auth.getUid(), name));

                Const.getUserDetails(new Const.FireBaseCallBack() {
                    @Override
                    public void onCallBack(ModelUser modelUser1) {
                        Const.mUser = modelUser1;
                        if (Const.mUser.getUserType().equals(Const.USER_DOCTOR)) {
                            Intent intent = new Intent(SignIn.this, DoctorHomeActivity.class);
                            startActivity(intent);
                        } else if (Const.mUser.getUserType().equals(Const.USER_STUDENT)) {
                            Intent intent = new Intent(SignIn.this, StudentHomeActivity.class);
                            startActivity(intent);
                        }
                    }
                });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(SignIn.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void signInFirebase(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                if (Const.userType.equals(Const.USER_DOCTOR)) {
                    Intent intent = new Intent(SignIn.this, DoctorHomeActivity.class);
                    startActivity(intent);
                } else if (Const.userType.equals(Const.USER_STUDENT)) {
                    Intent intent = new Intent(SignIn.this, StudentHomeActivity.class);
                    startActivity(intent);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignIn.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void startSignUp() {
        Intent intent = new Intent(SignIn.this, SignUp.class);
        startActivity(intent);
    }
}