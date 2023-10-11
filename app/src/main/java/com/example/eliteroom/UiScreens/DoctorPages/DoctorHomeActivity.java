package com.example.eliteroom.UiScreens.DoctorPages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eliteroom.Adabters.CourseAdabter;
import com.example.eliteroom.Const;
import com.example.eliteroom.Models.ModelCourse;
import com.example.eliteroom.databinding.ActivityDoctorHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DoctorHomeActivity extends AppCompatActivity {
    ActivityDoctorHomeBinding binding;
    ArrayList<ModelCourse> list = new ArrayList<>();
    private CourseAdabter adabter = new CourseAdabter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getCoursesData();
        onClick();

    }

    private void onClick() {
        binding.addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorHomeActivity.this, AddCouresActivity.class);
                startActivity(intent);
            }
        });

        binding.signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Const.auth.signOut();
                finish();
            }
        });

        adabter.setOnItemClick(new CourseAdabter.OnItemClick() {
            @Override
            public void onClickRoot(ModelCourse modelCourse) {
                Intent intent = new Intent(DoctorHomeActivity.this,CourseControlActivity.class);
                intent.putExtra("modelCourse", modelCourse);
                startActivity(intent);
            }
        });


    }

    private void getCoursesData() {
        Const.ref.child(Const.COURSES).orderByChild(Const.DOCTOR_ID).equalTo(Const.auth.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            list.add(snapshot1.getValue(ModelCourse.class));
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