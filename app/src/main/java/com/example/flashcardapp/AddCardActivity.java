package com.example.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;

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
;
        ImageView cancelButton = ((ImageView) findViewById(R.id.cancel));
        ImageView saveButton = ((ImageView) findViewById(R.id.save));
        EditText questionField = ((EditText) findViewById(R.id.enterQuestion));
        EditText answerField = ((EditText) findViewById(R.id.enterAnswer));

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredQuestion = questionField.getText().toString();
                String enteredAnswer = answerField.getText().toString();
                
                if (enteredQuestion.isEmpty() || enteredAnswer.isEmpty()) {
                    Snackbar.make(saveButton,
                            "Please enter both the question and answer!",
                            Snackbar.LENGTH_SHORT)
                            .show();
                } else {
                    Intent data = new Intent();
                    data.putExtra("question", enteredQuestion);
                    data.putExtra("answer", enteredAnswer);
                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        });
    }
}
