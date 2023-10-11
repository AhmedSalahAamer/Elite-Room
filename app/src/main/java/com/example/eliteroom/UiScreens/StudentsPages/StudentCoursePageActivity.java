package com.example.eliteroom.UiScreens.StudentsPages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eliteroom.Const;
import com.example.eliteroom.Models.ModelCourse;
import com.example.eliteroom.Models.ModelMember;
import com.example.eliteroom.UiScreens.ChatActivity;
import com.example.eliteroom.databinding.ActivityStudentCoursePageBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class StudentCoursePageActivity extends AppCompatActivity {


    private ModelMember modelMember;
    private ModelCourse modelCourse;
    private ActivityStudentCoursePageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentCoursePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        onClick();

    }

    private void init() {
        modelMember = (ModelMember) getIntent().getSerializableExtra("modelMember");
        modelCourse = (ModelCourse) getIntent().getSerializableExtra("modelCourse");
        binding.studentName.setText(modelMember.getName());
        binding.courseName.setText(modelCourse.getCourseName());
    }

    private void onClick() {
        binding.activiateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = binding.editAttend.getText().toString().trim();
                valiDate(code);
            }
        });

        binding.cardChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentCoursePageActivity.this, ChatActivity.class);
                intent.putExtra("modelCourse", modelCourse);
                startActivity(intent);
            }
        });
        binding.cardMatrial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentCoursePageActivity.this, GetMatrialActivity.class);
                intent.putExtra("modelCourse", modelCourse);
                startActivity(intent);
            }
        });

        binding.cardQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentCoursePageActivity.this, QuizzesActivity.class);
                intent.putExtra("modelCourse", modelCourse);
                startActivity(intent);
            }
        });
    }

    private void valiDate(String code) {
        if (code.isEmpty()) {
            binding.editAttend.setError("Enter Code");
        } else {
            attendStudent(code);
        }
    }

    private void attendStudent(String code) {
        Const.ref.child(Const.REF_ATTENDACE).child(modelMember.getCourseId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(code)) {
                    if (snapshot.child(code).hasChild(Const.auth.getUid())) {
                        makeToast("You are already attended");
                    } else {
                        setMemberAttendace(code);
                    }
                } else {
                    makeToast("Code not vailde");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void makeToast(String s) {
        Toast.makeText(StudentCoursePageActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    private void setMemberAttendace(String code) {
        Const.ref.child(Const.REF_ATTENDACE).child(modelMember.getCourseId()).child(code).child(Const.auth.getUid())
                .setValue(Const.auth.getUid());

        Const.ref.child(Const.COURSES).child(modelMember.getCourseId()).child(Const.MEMBERS).child(modelMember.getMemberId())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ModelMember m = snapshot.getValue(ModelMember.class);
                        int i = m.getAttendGrade();
                        i = i + 1;
                        m.setAttendGrade(i);
                        Const.ref.child(Const.COURSES).child(modelMember.getCourseId()).child(Const.MEMBERS).child(modelMember.getMemberId())
                                .setValue(m);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}