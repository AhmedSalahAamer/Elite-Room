package com.example.eliteroom;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.eliteroom.Models.ModelUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Const {
    public static String userType = "";
    public static final String USERS = "users";
    public static final String FILE_STORAGE = "files";
    public static final String USER_ID = "userId";
    public static final String REF_ATTENDACE = "attendace";
    public static final String USER_STUDENT = "student";
    public static final String COURSE_ID = "courseId";
    public static final String CHATS = "chats";
    public static final String MEMBERS = "members";
    public static ModelUser mUser = new ModelUser();
    public static final String USER_DOCTOR = "doctor";
    public static final String COURSES = "courses";
    public static final String DOCTOR_ID = "doctorId";
    public static final String COURSES_QUIZ = "Quizzes";


    public static final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    public static final FirebaseAuth auth = FirebaseAuth.getInstance();
    public static final StorageReference refStorage = FirebaseStorage.getInstance().getReference();


    public static void getUserDetails(FireBaseCallBack fireBaseCallBack) {

        if (Const.auth.getUid() != null) {
            Const.ref.child(Const.USERS).child(Const.auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    // Get the user data from the snapshot.

                    fireBaseCallBack.onCallBack(snapshot.getValue(ModelUser.class));

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("TAG", "onCancelled: ");
                }
            });

        } else {
            fireBaseCallBack.onCallBack(null);
        }
    }


    public interface FireBaseCallBack {
        void onCallBack(ModelUser modelUser1);
    }
}
