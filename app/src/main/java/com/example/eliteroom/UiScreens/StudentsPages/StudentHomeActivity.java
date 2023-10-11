package com.example.eliteroom.UiScreens.StudentsPages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eliteroom.Adabters.StudentCourseAdabter;
import com.example.eliteroom.Const;
import com.example.eliteroom.Models.ModelCourse;
import com.example.eliteroom.Models.ModelMember;
import com.example.eliteroom.databinding.ActivityStudentHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentHomeActivity extends AppCompatActivity {
    private ActivityStudentHomeBinding binding;
    ArrayList<ModelMember> list = new ArrayList<>();
    private StudentCourseAdabter adabter = new StudentCourseAdabter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getCoursesData();
        onClick();

    }

    private void onClick() {

        binding.addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseCode = binding.editCourseId.getText().toString();
                validation(courseCode);
                getCoursesData();

            }
        });
        binding.signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Const.auth.signOut();
                finish();
            }
        });
        adabter.setOnItemClick(new StudentCourseAdabter.OnItemClick() {
            @Override
            public void onClickRoot(ModelMember modelMember) {
                Intent intent = new Intent(StudentHomeActivity.this, StudentCoursePageActivity.class);
                intent.putExtra("modelMember", modelMember);
                ModelCourse modelCourse = new ModelCourse(
                        String.valueOf(modelMember.getQuizGrade()),
                        String.valueOf(modelMember.getAttendGrade()),
                        modelMember.getCourseId(),
                        modelMember.getCourseName(),
                        "asdasd",
                        String.valueOf(modelMember.getProjectGrade())
                );

                intent.putExtra("modelCourse", modelCourse);
                startActivity(intent);
            }
        });
    }

    private void validation(String courseCode) {

        if (courseCode.isEmpty()) {
            binding.editCourseId.setError("Enter course code");
        } else {
            searchForCourse(courseCode);
        }

    }

    private void searchForCourse(String courseCode) {
        Const.ref.child(Const.COURSES).orderByChild(Const.COURSE_ID).equalTo(courseCode)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getValue() != null) {
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                ModelCourse m = snapshot1.getValue(ModelCourse.class);

                                Const.ref.child(Const.MEMBERS).child(Const.auth.getUid()).child(m.getCourseId())
                                        .setValue(
                                                new ModelMember(
                                                        Const.auth.getUid(),
                                                        m.getCourseName(),
                                                        m.getCourseId(),
                                                        Const.mUser.getUserEmail(),
                                                        Const.mUser.getUserName(),
                                                        0, 0, 0)
                                        );


                                Const.ref.child(Const.COURSES).child(m.getCourseId()).child(Const.MEMBERS).child(Const.auth.getUid())
                                        .setValue(new ModelMember(Const.auth.getUid(), m.getCourseName(), m.getCourseId(), Const.mUser.getUserEmail(), Const.mUser.getUserName(), 0, 0, 0));

                                binding.editCourseId.setText(null);
                            }

                        } else {
                            Toast.makeText(StudentHomeActivity.this, "ID not vailed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void getCoursesData() {
        Const.ref.child(Const.MEMBERS).child(Const.auth.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            list.add(snapshot1.getValue(ModelMember.class));
                        }
                        adabter.setCoursesList(list);
                        binding.recyclerViewCourses.setAdapter(adabter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}