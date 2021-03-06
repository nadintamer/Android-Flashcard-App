package com.example.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    boolean answerChoicesVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView flashcardQuestion = ((TextView) findViewById(R.id.flashcard_question));
        TextView flashcardHint = ((TextView) findViewById(R.id.flashcard_hint));

        // User can tap on question to see answer
        flashcardQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardHint.setVisibility(View.VISIBLE);
                flashcardQuestion.setVisibility(View.INVISIBLE);
            }
        });

        // User can tap on answer to toggle back to question
        flashcardHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardHint.setVisibility(View.INVISIBLE);
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

        // User can tap on background view to reset back to default settings
        findViewById(R.id.parent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset background colors for answer choices
                incorrectAnswer1.setBackgroundColor(getResources().getColor(R.color.orange, null));
                incorrectAnswer2.setBackgroundColor(getResources().getColor(R.color.orange, null));
                correctAnswer.setBackgroundColor(getResources().getColor(R.color.orange, null));

                // Set answer choices to be visible
                correctAnswer.setVisibility(View.VISIBLE);
                incorrectAnswer1.setVisibility(View.VISIBLE);
                incorrectAnswer2.setVisibility(View.VISIBLE);
                toggleButton.setImageResource(R.drawable.eye_hidden);
                answerChoicesVisible = true;

                // Switch back to showing question
                flashcardHint.setVisibility(View.INVISIBLE);
                flashcardQuestion.setVisibility(View.VISIBLE);
            }
        });

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

        ImageView addNewCardButton = ((ImageView) findViewById(R.id.add_new_card));

        addNewCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddCardActivity.class);
                startActivityForResult(i, 100);
            }
        });

        ImageView editButton = ((ImageView) findViewById(R.id.edit_card));

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddCardActivity.class);
                i.putExtra("question", flashcardQuestion.getText().toString());
                i.putExtra("answer", correctAnswer.getText().toString());
                i.putExtra("incorrect1", incorrectAnswer1.getText().toString());
                i.putExtra("incorrect2", incorrectAnswer2.getText().toString());
                i.putExtra("hint", flashcardHint.getText().toString().substring(6));
                startActivityForResult(i, 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {
            String question = data.getExtras().getString("question");
            String answer = data.getExtras().getString("answer");
            String incorrect1 = data.getExtras().getString("incorrect1");
            String incorrect2 = data.getExtras().getString("incorrect2");
            String hint = data.getExtras().getString("hint");

            TextView flashcardQuestion = ((TextView) findViewById(R.id.flashcard_question));
            flashcardQuestion.setText(question);

            TextView correctAnswer = ((TextView) findViewById(R.id.flashcard_answer2));
            correctAnswer.setText(answer);

            TextView incorrectAnswer1 = ((TextView) findViewById(R.id.flashcard_answer1));
            incorrectAnswer1.setText(incorrect1);

            TextView incorrectAnswer2 = ((TextView) findViewById(R.id.flashcard_answer3));
            incorrectAnswer2.setText(incorrect2);

            TextView hintView = ((TextView) findViewById(R.id.flashcard_hint));
            if (hint.isEmpty()) {
                hintView.setText("Hint: You haven't provided a hint for this question!");
            } else {
                hintView.setText("Hint: " + hint);
            }

            Snackbar.make(flashcardQuestion,
                    "Card created successfully!",
                    Snackbar.LENGTH_SHORT)
                    .show();
        }
    }
}