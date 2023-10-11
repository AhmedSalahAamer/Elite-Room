package com.example.eliteroom.UiScreens.DoctorPages;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eliteroom.Adabters.StudenGradesAdabter;
import com.example.eliteroom.Const;
import com.example.eliteroom.Models.ModelMember;
import com.example.eliteroom.databinding.ActivityDoctorShowGradesBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DoctorShowGradesActivity extends AppCompatActivity {
    private ActivityDoctorShowGradesBinding binding;
    String courseId;
    private ArrayList<ModelMember> list = new ArrayList<>();
    StudenGradesAdabter adabter = new StudenGradesAdabter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorShowGradesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        courseId = getIntent().getStringExtra(Const.COURSE_ID);
        getGrades();

    }


    private void getGrades() {

        Const.ref.child(Const.COURSES).child(courseId).child(Const.MEMBERS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    list.add(snapshot1.getValue(ModelMember.class));
                }
                adabter.setMemberList(list);
                binding.recyclerViewGrades.setAdapter(adabter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}