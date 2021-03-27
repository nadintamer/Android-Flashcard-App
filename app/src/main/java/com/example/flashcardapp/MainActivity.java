package com.example.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    boolean answerChoicesVisible = true;
    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 0;

    int ADD_CARD_REQUEST_CODE = 100;
    int EDIT_CARD_REQUEST_CODE = 200;

    Flashcard cardToEdit;

    public int getRandomNumber(int minNumber, int maxNumber) {
        Random rand = new Random();
        return rand.nextInt((maxNumber - minNumber) + 1) + minNumber;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView flashcardQuestion = ((TextView) findViewById(R.id.flashcard_question));
        TextView flashcardHint = ((TextView) findViewById(R.id.flashcard_hint));
        TextView incorrectAnswer1 = ((TextView) findViewById(R.id.flashcard_answer1));
        TextView incorrectAnswer2 = ((TextView) findViewById(R.id.flashcard_answer3));
        TextView correctAnswer = ((TextView) findViewById(R.id.flashcard_answer2));

        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();

        if (allFlashcards != null && !allFlashcards.isEmpty()) {
            Flashcard flashcard = allFlashcards.get(0);
            flashcardQuestion.setText(flashcard.getQuestion());
            flashcardHint.setText("Hint: " + flashcard.getHint());
            correctAnswer.setText(flashcard.getAnswer());
            incorrectAnswer1.setText(flashcard.getWrongAnswer1());
            incorrectAnswer2.setText(flashcard.getWrongAnswer2());
        }

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
                startActivityForResult(i, ADD_CARD_REQUEST_CODE);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        ImageView editButton = ((ImageView) findViewById(R.id.edit_card));

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Flashcard card : allFlashcards) {
                    if (card.getQuestion() == flashcardQuestion.getText().toString()) {
                        cardToEdit = card;
                    }
                }

                Intent i = new Intent(MainActivity.this, AddCardActivity.class);
                i.putExtra("question", flashcardQuestion.getText().toString());
                i.putExtra("answer", correctAnswer.getText().toString());
                i.putExtra("incorrect1", incorrectAnswer1.getText().toString());
                i.putExtra("incorrect2", incorrectAnswer2.getText().toString());
                i.putExtra("hint", flashcardHint.getText().toString().substring(6));
                startActivityForResult(i, EDIT_CARD_REQUEST_CODE);
            }
        });

        ImageView nextButton = ((ImageView) findViewById(R.id.next_card));

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allFlashcards.size() == 0) {
                    return;
                }

                int cardToDisplayIndex = getRandomNumber(0, allFlashcards.size() - 1);

                while (cardToDisplayIndex == currentCardDisplayedIndex) {
                    cardToDisplayIndex = getRandomNumber(0, allFlashcards.size() - 1);
                }

                currentCardDisplayedIndex = cardToDisplayIndex;

                Flashcard flashcard = allFlashcards.get(currentCardDisplayedIndex);
                flashcardQuestion.setText(flashcard.getQuestion());
                flashcardHint.setText("Hint: " + flashcard.getHint());
                correctAnswer.setText(flashcard.getAnswer());
                incorrectAnswer1.setText(flashcard.getWrongAnswer1());
                incorrectAnswer2.setText(flashcard.getWrongAnswer2());
            }
        });

        ImageView prevButton = ((ImageView) findViewById(R.id.prev_card));

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allFlashcards.size() == 0) {
                    return;
                }

                int cardToDisplayIndex = getRandomNumber(0, allFlashcards.size() - 1);

                while (cardToDisplayIndex == currentCardDisplayedIndex) {
                    cardToDisplayIndex = getRandomNumber(0, allFlashcards.size() - 1);
                }

                currentCardDisplayedIndex = cardToDisplayIndex;

                Flashcard flashcard = allFlashcards.get(currentCardDisplayedIndex);
                flashcardQuestion.setText(flashcard.getQuestion());
                flashcardHint.setText("Hint: " + flashcard.getHint());
                correctAnswer.setText(flashcard.getAnswer());
                incorrectAnswer1.setText(flashcard.getWrongAnswer1());
                incorrectAnswer2.setText(flashcard.getWrongAnswer2());
            }
        });

        ImageView deleteButton = ((ImageView) findViewById(R.id.delete_card));

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardDatabase.deleteCard(flashcardQuestion.getText().toString());
                allFlashcards = flashcardDatabase.getAllCards();

                if (allFlashcards.isEmpty()) {
                    flashcardQuestion.setText("Add a new card!");
                    flashcardHint.setText("Use the + button to add a new card!");
                    correctAnswer.setText("");
                    incorrectAnswer1.setText("");
                    incorrectAnswer2.setText("");

                    correctAnswer.setVisibility(View.INVISIBLE);
                    incorrectAnswer1.setVisibility(View.INVISIBLE);
                    incorrectAnswer2.setVisibility(View.INVISIBLE);
                    toggleButton.setImageResource(R.drawable.eye_visible);
                    answerChoicesVisible = false;
                } else {
                    currentCardDisplayedIndex--;

                    if (currentCardDisplayedIndex == -1) {
                        currentCardDisplayedIndex = allFlashcards.size() - 1;
                    }

                    Flashcard flashcard = allFlashcards.get(currentCardDisplayedIndex);
                    flashcardQuestion.setText(flashcard.getQuestion());
                    flashcardHint.setText("Hint: " + flashcard.getHint());
                    correctAnswer.setText(flashcard.getAnswer());
                    incorrectAnswer1.setText(flashcard.getWrongAnswer1());
                    incorrectAnswer2.setText(flashcard.getWrongAnswer2());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CANCELED) {
            return;
        }

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

        if (hint.isEmpty()) {
            hint = "You haven't provided a hint for this question!";
        }
        TextView hintView = ((TextView) findViewById(R.id.flashcard_hint));
        hintView.setText("Hint: " + hint);

        if (requestCode == ADD_CARD_REQUEST_CODE && resultCode == RESULT_OK) {
            flashcardDatabase.insertCard(new Flashcard(question, answer, hint, incorrect1, incorrect2));
            allFlashcards = flashcardDatabase.getAllCards();

            correctAnswer.setVisibility(View.VISIBLE);
            incorrectAnswer1.setVisibility(View.VISIBLE);
            incorrectAnswer2.setVisibility(View.VISIBLE);

            Snackbar.make(flashcardQuestion,
                    "Card created successfully!",
                    Snackbar.LENGTH_SHORT)
                    .show();
        } else if (requestCode == EDIT_CARD_REQUEST_CODE && resultCode == RESULT_OK) {
            cardToEdit.setQuestion(question);
            cardToEdit.setAnswer(answer);
            cardToEdit.setWrongAnswer1(incorrect1);
            cardToEdit.setWrongAnswer2(incorrect2);
            cardToEdit.setHint(hint);

            flashcardDatabase.updateCard(cardToEdit);
            allFlashcards = flashcardDatabase.getAllCards();

            Snackbar.make(flashcardQuestion,
                    "Card edited successfully!",
                    Snackbar.LENGTH_SHORT)
                    .show();
        }
    }
}