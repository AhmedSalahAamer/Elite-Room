package com.example.eliteroom.UiScreens;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eliteroom.Adabters.ChatMessagesAdabter;
import com.example.eliteroom.Const;
import com.example.eliteroom.Models.ModelCourse;
import com.example.eliteroom.Models.ModelMessage;
import com.example.eliteroom.databinding.ActivityChatBinding;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class ChatActivity extends AppCompatActivity {
    private ActivityChatBinding binding;

    private ModelCourse modelCourse = new ModelCourse();
    private ChatMessagesAdabter adabter = new ChatMessagesAdabter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        onClick();
    }

    private void init() {

        modelCourse = (ModelCourse) getIntent().getSerializableExtra("modelCourse");
        binding.toolBar.setTitle(modelCourse.getCourseName());
        binding.recyclerViewChats.setAdapter(adabter);
        getMessage();
    }

    private void onClick() {
        binding.btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = binding.editSendMessage.getText().toString();
                validate(message);
            }
        });
    }

    private void validate(String message) {
        if (!message.isEmpty()) {
            sendMessageToFirebase(message);
            binding.editSendMessage.setText(null);
        }
    }

    private void sendMessageToFirebase(String message) {
        Const.ref.child(Const.CHATS).child(modelCourse.getCourseId()).push()
                .setValue(new ModelMessage(Const.mUser.getUserName(), Const.mUser.getUserId(), message));
    }

    private void getMessage() {
        Const.ref.child(Const.CHATS).child(modelCourse.getCourseId()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                adabter.addMessage(snapshot.getValue(ModelMessage.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}