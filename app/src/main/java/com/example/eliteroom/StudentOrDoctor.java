package com.example.eliteroom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eliteroom.UiScreens.LoginPages.SignIn;
import com.example.eliteroom.UiScreens.LoginPages.SignUp;
import com.example.eliteroom.databinding.ActivityStudentOrDoctorBinding;
import com.google.android.material.snackbar.Snackbar;

public class StudentOrDoctor extends AppCompatActivity {

    ActivityStudentOrDoctorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentOrDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());Log.e("TAG", "onCreate: ",null );
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

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Const.userType == "") {
                    Snackbar.make(v, "Kindly select the type", Snackbar.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(StudentOrDoctor.this, SignIn.class);
                    startActivity(intent);
                }
            }
        });

    }
}