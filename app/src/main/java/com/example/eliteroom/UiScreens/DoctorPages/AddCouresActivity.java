package com.example.eliteroom.UiScreens.DoctorPages;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eliteroom.Const;
import com.example.eliteroom.Models.ModelCourse;
import com.example.eliteroom.databinding.ActivityAddCouresBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class AddCouresActivity extends AppCompatActivity {
    ActivityAddCouresBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddCouresBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        onClick();

    }

    private void onClick() {
        binding.createCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseName = binding.editCourseName.getText().toString();
                String quizGrade = binding.editQuizGrade.getText().toString();
                String attendGrade = binding.editAttendGrade.getText().toString();
                String projectGrade = binding.editProjectGrade.getText().toString();
                validate(courseName, quizGrade, attendGrade, projectGrade);

            }
        });
    }

    private void validate(String courseName, String quizGrade, String attendGrade, String projectGrade) {
        if (courseName.isEmpty()) {
            binding.editCourseName.setError("This field can't be empty");
        } else if (quizGrade.isEmpty()) {
            binding.editQuizGrade.setError("This field can't be empty");
        } else if (attendGrade.isEmpty()) {
            binding.editAttendGrade.setError("This field can't be empty");
        } else if (projectGrade.isEmpty()) {
            binding.editProjectGrade.setError("This field can't be empty");
        } else {
            binding.progressBar.setVisibility(View.VISIBLE);
            sendToFirebase(courseName, quizGrade, attendGrade, projectGrade);
        }
    }

    private void sendToFirebase(String courseName, String quizGrade, String attendGrade, String projectGrade) {
        String id = Const.ref.push().getKey();
        Const.ref.child(Const.COURSES).child(id)
                .setValue(new ModelCourse(quizGrade, attendGrade, id, courseName, Const.auth.getUid(), projectGrade))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        binding.progressBar.setVisibility(View.GONE);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        binding.progressBar.setVisibility(View.GONE);
                        Toast.makeText(AddCouresActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}