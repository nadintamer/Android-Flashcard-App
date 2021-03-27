package com.example.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class AddCardActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_card_activity);

        ImageView cancelButton = ((ImageView) findViewById(R.id.cancel));
        ImageView saveButton = ((ImageView) findViewById(R.id.save));
        EditText questionField = ((EditText) findViewById(R.id.enterQuestion));
        EditText answerField = ((EditText) findViewById(R.id.enterAnswer));
        EditText incorrectField1 = ((EditText) findViewById(R.id.enterIncorrectAnswer1));
        EditText incorrectField2 = ((EditText) findViewById(R.id.enterIncorrectAnswer2));
        EditText hintField = ((EditText) findViewById(R.id.enterHint));

        String question = getIntent().getStringExtra("question");
        String answer = getIntent().getStringExtra("answer");
        String incorrect1 = getIntent().getStringExtra("incorrect1");
        String incorrect2 = getIntent().getStringExtra("incorrect2");
        String hint = getIntent().getStringExtra("hint");

        questionField.setText(question);
        answerField.setText(answer);
        incorrectField1.setText(incorrect1);
        incorrectField2.setText(incorrect2);
        hintField.setText(hint);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredQuestion = questionField.getText().toString();
                String enteredAnswer = answerField.getText().toString();
                String enteredIncorrectAnswer1 = incorrectField1.getText().toString();
                String enteredIncorrectAnswer2 = incorrectField2.getText().toString();
                String enteredHint = hintField.getText().toString();
                
                if (enteredQuestion.isEmpty() || enteredAnswer.isEmpty() ||
                        enteredIncorrectAnswer1.isEmpty() || enteredIncorrectAnswer2.isEmpty()) {
                    Snackbar.make(saveButton,
                            "Please make sure you've filled out all required fields!",
                            Snackbar.LENGTH_SHORT)
                            .show();
                } else {
                    Intent data = new Intent();
                    data.putExtra("question", enteredQuestion);
                    data.putExtra("answer", enteredAnswer);
                    data.putExtra("incorrect1", enteredIncorrectAnswer1);
                    data.putExtra("incorrect2", enteredIncorrectAnswer2);
                    data.putExtra("hint", enteredHint);
                    setResult(RESULT_OK, data);
                    finish();
                    overridePendingTransition(R.anim.left_in, R.anim.right_out);
                }
            }
        });
    }
}
