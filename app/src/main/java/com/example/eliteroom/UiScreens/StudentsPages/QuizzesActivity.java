package com.example.eliteroom.UiScreens.StudentsPages;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eliteroom.Adabters.QuizzesAdabter;
import com.example.eliteroom.Const;
import com.example.eliteroom.Models.ModelCourse;
import com.example.eliteroom.databinding.ActivityQuizzesBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuizzesActivity extends AppCompatActivity {
    ActivityQuizzesBinding binding;
    private ModelCourse modelCourse;
    ArrayList<String> list = new ArrayList<>();
    QuizzesAdabter adabter = new QuizzesAdabter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizzesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        getQuizzes();
        onClick();
    }


    private void init() {
        modelCourse = (ModelCourse) getIntent().getSerializableExtra("modelCourse");
    }

    private void onClick() {
        adabter.setOnItemClicked(new QuizzesAdabter.OnItemClicked() {
            @Override
            public void onClickRoot(String id) {
                Intent intent = new Intent(QuizzesActivity.this, SolveQuizActivity.class);
                intent.putExtra("courseId", modelCourse.getCourseId());
                intent.putExtra("quizId", id);
                startActivity(intent);
            }
        });

    }

    private void getQuizzes() {
        Const.ref.child(Const.COURSES_QUIZ).child(modelCourse.getCourseId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if (snapshot.getValue() != null) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren())
                        list.add(snapshot1.getKey());
                    adabter.setList(list);
                    binding.recyclerViewQuizzes.setAdapter(adabter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}