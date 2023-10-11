package com.example.eliteroom.UiScreens.StudentsPages;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eliteroom.Const;
import com.example.eliteroom.Models.ModelMember;
import com.example.eliteroom.Models.ModelQuiz;
import com.example.eliteroom.R;
import com.example.eliteroom.databinding.ActivitySolveQuizBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SolveQuizActivity extends AppCompatActivity {

    private ActivitySolveQuizBinding binding;
    private String courseId;
    private String quizId;
    private ArrayList<ModelQuiz> questionsList = new ArrayList<>();
    private int postion = 0;
    private int choosedAnswer = 0;
    private int quizGrade = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySolveQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        onClick();

    }

    private void init() {
        courseId = getIntent().getStringExtra("courseId");
        quizId = getIntent().getStringExtra("quizId");
        getQuestionsFromFirebase();
        onClick();
    }


    private void getQuestionsFromFirebase() {
        Const.ref.child(Const.COURSES_QUIZ).child(courseId).child(quizId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                questionsList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren())
                    questionsList.add(snapshot1.getValue(ModelQuiz.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void onClick() {
        binding.startQuizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.cardView.setVisibility(View.GONE);
                setQuestion();
            }
        });
        binding.btnNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (choosedAnswer == questionsList.get(postion).getRightAnswer()) {
                    quizGrade++;
                }
                postion++;
                if (postion == questionsList.size()) {
                    setGradaes();

                } else if (postion == questionsList.size() - 1) {
                    binding.btnNextQuestion.setText("Submit");
                    setQuestion();
                } else {
                    setQuestion();
                }
                binding.tvAnswer1.setBackgroundResource(R.drawable.rounded);
                binding.tvAnswer2.setBackgroundResource(R.drawable.rounded);
                binding.tvAnswer3.setBackgroundResource(R.drawable.rounded);
                binding.tvAnswer4.setBackgroundResource(R.drawable.rounded);
            }
        });

        binding.tvAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvAnswer1.setBackgroundResource(R.drawable.choosed_answer);
                binding.tvAnswer2.setBackgroundResource(R.drawable.rounded);
                binding.tvAnswer3.setBackgroundResource(R.drawable.rounded);
                binding.tvAnswer4.setBackgroundResource(R.drawable.rounded);
                choosedAnswer = 1;

            }
        });

        binding.tvAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvAnswer1.setBackgroundResource(R.drawable.rounded);
                binding.tvAnswer2.setBackgroundResource(R.drawable.choosed_answer);
                binding.tvAnswer3.setBackgroundResource(R.drawable.rounded);
                binding.tvAnswer4.setBackgroundResource(R.drawable.rounded);
                choosedAnswer = 2;

            }
        });

        binding.tvAnswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvAnswer1.setBackgroundResource(R.drawable.rounded);
                binding.tvAnswer2.setBackgroundResource(R.drawable.rounded);
                binding.tvAnswer3.setBackgroundResource(R.drawable.choosed_answer);
                binding.tvAnswer4.setBackgroundResource(R.drawable.rounded);
                choosedAnswer = 3;

            }
        });

        binding.tvAnswer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvAnswer1.setBackgroundResource(R.drawable.rounded);
                binding.tvAnswer2.setBackgroundResource(R.drawable.rounded);
                binding.tvAnswer3.setBackgroundResource(R.drawable.rounded);
                binding.tvAnswer4.setBackgroundResource(R.drawable.choosed_answer);
                choosedAnswer = 4;

            }
        });
    }


    private void setQuestion() {
        binding.tvQuestion.setText(questionsList.get(postion).getQuestion());
        binding.tvAnswer1.setText(questionsList.get(postion).getAnswer1());
        binding.tvAnswer2.setText(questionsList.get(postion).getAnswer2());
        binding.tvAnswer3.setText(questionsList.get(postion).getAnswer3());
        binding.tvAnswer4.setText(questionsList.get(postion).getAnswer4());
    }


    private void setGradaes() {
        Const.ref.child(Const.COURSES).child(courseId).child(Const.MEMBERS).child(Const.mUser.getUserId())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ModelMember m = snapshot.getValue(ModelMember.class);
                        int i = m.getQuizGrade();
                        i = i + quizGrade;
                        m.setAttendGrade(i);
                        Const.ref.child(Const.COURSES).child(courseId).child(Const.MEMBERS).child(Const.mUser.getUserId())
                                .setValue(m);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}