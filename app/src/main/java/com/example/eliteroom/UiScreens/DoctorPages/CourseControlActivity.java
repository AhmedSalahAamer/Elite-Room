package com.example.eliteroom.UiScreens.DoctorPages;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eliteroom.Const;
import com.example.eliteroom.Models.ModelCourse;
import com.example.eliteroom.UiScreens.ChatActivity;
import com.example.eliteroom.databinding.ActivityCourseControlBinding;

public class CourseControlActivity extends AppCompatActivity {
    private ActivityCourseControlBinding binding;
    private ModelCourse modelCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCourseControlBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        modelCourse = (ModelCourse) getIntent().getSerializableExtra("modelCourse");

        onClick();

    }

    private void onClick() {
        binding.btnOpenQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseControlActivity.this, AddQuizActivity.class);
                intent.putExtra("modelCourse", modelCourse);
                startActivity(intent);
            }
        });
        binding.chatLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseControlActivity.this, ChatActivity.class);
                intent.putExtra("modelCourse", modelCourse);
                startActivity(intent);
            }
        });
        binding.generateCourseIdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", modelCourse.getCourseId());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(CourseControlActivity.this, "Id Copied To Clip Board", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnOpenTakeAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseControlActivity.this, GenrateAttendaceActivity.class);
                intent.putExtra(Const.COURSE_ID, modelCourse.getCourseId());
                startActivity(intent);
            }
        });
        binding.btnOpenGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseControlActivity.this, DoctorShowGradesActivity.class);
                intent.putExtra(Const.COURSE_ID, modelCourse.getCourseId());
                startActivity(intent);
            }
        });

        binding.btnOpenMatrials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseControlActivity.this, UploadMaterialsActivity.class);
                intent.putExtra(Const.COURSE_ID, modelCourse.getCourseId());
                startActivity(intent);
            }
        });
    }
}