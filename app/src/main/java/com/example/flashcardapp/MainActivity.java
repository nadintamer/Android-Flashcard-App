package com.example.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    boolean answerChoicesVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView flashcardQuestion = ((TextView) findViewById(R.id.flashcard_question));
        TextView flashcardAnswer = ((TextView) findViewById(R.id.flashcard_answer));

        // User can tap on question to see answer
        flashcardQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardAnswer.setVisibility(View.VISIBLE);
                flashcardQuestion.setVisibility(View.INVISIBLE);
            }
        });

        // User can tap on answer to toggle back to question
        flashcardAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardAnswer.setVisibility(View.INVISIBLE);
                flashcardQuestion.setVisibility(View.VISIBLE);
            }
        });

        // User can tap on multiple choice answers to see whether they're correct
        TextView incorrectAnswer1 = ((TextView) findViewById(R.id.flashcard_answer1));
        TextView incorrectAnswer2 = ((TextView) findViewById(R.id.flashcard_answer3));
        TextView correctAnswer = ((TextView) findViewById(R.id.flashcard_answer2));

        incorrectAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incorrectAnswer1.setBackgroundColor(getResources().getColor(R.color.tomato, null));
                incorrectAnswer2.setBackgroundColor(getResources().getColor(R.color.orange, null));
                correctAnswer.setBackgroundColor(getResources().getColor(R.color.orange, null));
            }
        });

        incorrectAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incorrectAnswer2.setBackgroundColor(getResources().getColor(R.color.tomato, null));
                incorrectAnswer1.setBackgroundColor(getResources().getColor(R.color.orange, null));
                correctAnswer.setBackgroundColor(getResources().getColor(R.color.orange, null));
            }
        });

        correctAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correctAnswer.setBackgroundColor(getResources().getColor(R.color.green, null));
                incorrectAnswer1.setBackgroundColor(getResources().getColor(R.color.orange, null));
                incorrectAnswer2.setBackgroundColor(getResources().getColor(R.color.orange, null));
            }
        });

        ImageView toggleButton = ((ImageView) findViewById(R.id.toggle_choices_visibility));

        // User can tap on toggle button to show/hide answer choices
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answerChoicesVisible) {
                    correctAnswer.setVisibility(View.INVISIBLE);
                    incorrectAnswer1.setVisibility(View.INVISIBLE);
                    incorrectAnswer2.setVisibility(View.INVISIBLE);
                    toggleButton.setImageResource(R.drawable.eye_visible);
                    answerChoicesVisible = false;
                } else {
                    correctAnswer.setVisibility(View.VISIBLE);
                    incorrectAnswer1.setVisibility(View.VISIBLE);
                    incorrectAnswer2.setVisibility(View.VISIBLE);
                    toggleButton.setImageResource(R.drawable.eye_hidden);
                    answerChoicesVisible = true;
                }
            }
        });

    }
}