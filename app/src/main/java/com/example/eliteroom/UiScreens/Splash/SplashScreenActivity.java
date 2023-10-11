package com.example.eliteroom.UiScreens.Splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eliteroom.Const;
import com.example.eliteroom.Models.ModelUser;
import com.example.eliteroom.StudentOrDoctor;
import com.example.eliteroom.UiScreens.DoctorPages.DoctorHomeActivity;
import com.example.eliteroom.UiScreens.LoginPages.SignIn;
import com.example.eliteroom.UiScreens.LoginPages.SignUp;
import com.example.eliteroom.UiScreens.StudentsPages.StudentHomeActivity;
import com.example.eliteroom.databinding.ActivitySplashScreenBinding;

public class SplashScreenActivity extends AppCompatActivity {

    ActivitySplashScreenBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Const.getUserDetails(new Const.FireBaseCallBack() {
                    @Override
                    public void onCallBack(ModelUser modelUser1) {
                        Const.mUser = modelUser1;
                        if (Const.mUser != null) {
                            if (Const.mUser.getUserType().equals(Const.USER_STUDENT)) {
                                startActivity(new Intent(SplashScreenActivity.this, StudentHomeActivity.class));
                            } else if (Const.mUser.getUserType().equals(Const.USER_DOCTOR)) {
                                startActivity(new Intent(SplashScreenActivity.this, DoctorHomeActivity.class));
                            }
                        } else {
                            startActivity(new Intent(SplashScreenActivity.this, SignIn.class));
                        }
                    }
                });


            }

        }, 1000);
    }


}


