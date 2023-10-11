package com.example.eliteroom.UiScreens.DoctorPages;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eliteroom.Const;
import com.example.eliteroom.databinding.ActivityGenrateAttendaceBinding;

import java.util.Random;

public class GenrateAttendaceActivity extends AppCompatActivity {

    private ActivityGenrateAttendaceBinding binding;
    private String courseId = null;
    private String randS = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGenrateAttendaceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        courseId = getIntent().getStringExtra(Const.COURSE_ID);
        onClick();
    }

    private void onClick() {
        binding.btnGenerateCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int rand = random.nextInt(10000 - 999 + 1) + 999;
                randS = String.valueOf(rand);
                binding.tvGenrateCode.setText(randS);
            }
        });
        binding.btnConfirmCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.tvGenrateCode.getText() == "") {
                    Toast.makeText(GenrateAttendaceActivity.this, "Click Generate button plz", Toast.LENGTH_SHORT).show();
                } else {
                    addLeactureCode();
                }
            }
        });
    }

    private void addLeactureCode() {
        Const.ref.child(Const.REF_ATTENDACE).child(courseId).child(randS).setValue("");

    }
}