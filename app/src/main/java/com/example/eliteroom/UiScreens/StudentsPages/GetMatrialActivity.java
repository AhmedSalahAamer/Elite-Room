package com.example.eliteroom.UiScreens.StudentsPages;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eliteroom.Adabters.MaterialsAdabter;
import com.example.eliteroom.Const;
import com.example.eliteroom.Models.ModelCourse;
import com.example.eliteroom.databinding.ActivityGetMatrialsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GetMatrialActivity extends AppCompatActivity {
    ActivityGetMatrialsBinding binding;
    private MaterialsAdabter adabter = new MaterialsAdabter();
    private ArrayList<String> list = new ArrayList<>();
    ModelCourse modelCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGetMatrialsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();

        onClick();
    }

    private void init() {
        modelCourse = (ModelCourse) getIntent().getSerializableExtra("modelCourse");
        getMatrialData();
    }

    private void onClick() {
        adabter.setOnItemClicked(new MaterialsAdabter.OnItemClicked() {
            @Override
            public void onClickRoot(String fileUri) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fileUri));
                startActivity(browserIntent);
            }
        });

    }

    private void getMatrialData() {
        Const.ref.child(Const.FILE_STORAGE).child(modelCourse.getCourseId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    list.add(snapshot1.getValue(String.class));
                }
                adabter.setList(list);
                binding.recyclerViewMatrials.setAdapter(adabter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAG", "onCancelled: ");
            }
        });
    }

}