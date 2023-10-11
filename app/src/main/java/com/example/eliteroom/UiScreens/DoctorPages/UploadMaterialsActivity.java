package com.example.eliteroom.UiScreens.DoctorPages;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eliteroom.Const;
import com.example.eliteroom.databinding.ActivityUploadMatrialsBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.UploadTask;

public class UploadMaterialsActivity extends AppCompatActivity {
    ActivityUploadMatrialsBinding binding;


    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        Intent data = result.getData();
                        if (data != null && data.getData() != null)
                            fileMaterial = data.getData();

                    }
                }
            });
    Uri fileMaterial;
    private String courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUploadMatrialsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        onClick();
    }

    private void init() {
        courseId = getIntent().getStringExtra(Const.COURSE_ID);
    }

    private void onClick() {
        binding.btnGetFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                someActivityResultLauncher.launch(intent);
            }
        });
        binding.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFileToStorage();
            }
        });
    }

    private void sendFileToStorage() {
        if (fileMaterial != null) {
            binding.progressBar.setVisibility(View.VISIBLE);
            String id = Const.ref.push().getKey();
            Const.refStorage.child(Const.FILE_STORAGE).child(courseId).child(id).putFile(fileMaterial).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                   Const.ref.child(Const.FILE_STORAGE).child(courseId).child(id).setValue(fileMaterial.toString());
                    binding.progressBar.setVisibility(View.GONE);
                   finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(UploadMaterialsActivity.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}