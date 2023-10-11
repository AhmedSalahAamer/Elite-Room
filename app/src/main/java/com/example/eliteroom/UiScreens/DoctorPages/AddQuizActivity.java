package com.example.eliteroom.UiScreens.DoctorPages;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eliteroom.Const;
import com.example.eliteroom.Models.ModelCourse;
import com.example.eliteroom.Models.ModelQuiz;
import com.example.eliteroom.databinding.ActivityAddQuizBinding;

import java.util.ArrayList;

public class AddQuizActivity extends AppCompatActivity {
    ActivityAddQuizBinding binding;
    ArrayList<ModelQuiz> quizList = new ArrayList<>();
    private ModelCourse modelCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        onClick();
    }

    private void init() {
        modelCourse = (ModelCourse) getIntent().getSerializableExtra("modelCourse");
    }

    private void onClick() {
        binding.btnNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = binding.etYourQuestion.getText().toString();
                String answer1 = binding.etAnswer1.getText().toString();
                String answer2 = binding.etAnswer2.getText().toString();
                String answer3 = binding.etAnswer3.getText().toString();
                String answer4 = binding.etAnswer4.getText().toString();
                String correctAnswer = binding.etCorrectAnswer.getText().toString();
                validation(question, answer1, answer2, answer3, answer4, correctAnswer);
            }
        });

        binding.btnUploadQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendQuizToFireBase();
            }
        });
    }

    private void sendQuizToFireBase() {
        if (quizList.size() < 1) {
            Toast.makeText(AddQuizActivity.this, "Questions list is empty", Toast.LENGTH_SHORT).show();
        }
        else {
            finish();
            Const.ref.child(Const.COURSES_QUIZ).child(modelCourse.getCourseId()).push().setValue(quizList);
            quizList.clear();
        }
    }

    private void validation(String question, String answer1, String answer2, String answer3, String answer4, String correctAnswer) {
        if (question.isEmpty()) {
            binding.etYourQuestion.setError("This field can't be empty");
        } else if (answer1.isEmpty()) {
            binding.etAnswer1.setError("This field can't be empty");
        } else if (answer2.isEmpty()) {
            binding.etAnswer2.setError("This field can't be empty");
        } else if (answer3.isEmpty()) {
            binding.etAnswer3.setError("This field can't be empty");
        } else if (answer4.isEmpty()) {
            binding.etAnswer4.setError("This field can't be empty");
        } else if (correctAnswer.isEmpty() || !(correctAnswer.equals("1") || correctAnswer.equals("2") || correctAnswer.equals("3") || correctAnswer.equals("4")) ) {
            binding.etCorrectAnswer.setError("Enter 1 or 2 or 3 or 4");
        } else {
            String id = Const.ref.push().getKey();
            quizList.add(new ModelQuiz(question, answer1, answer2, answer3, answer4, Integer.parseInt(correctAnswer)));
            binding.etYourQuestion.setText(null);
            binding.etAnswer1.setText(null);
            binding.etAnswer2.setText(null);
            binding.etAnswer3.setText(null);
            binding.etAnswer4.setText(null);
            binding.etCorrectAnswer.setText(null);
            Toast.makeText(AddQuizActivity.this, "Question " + quizList.size() + " is Added", Toast.LENGTH_SHORT).show();
        }
    }
}