/**
 *  Purpose/HiLo Game!
 *  @author Ryan Radmore (radm0018@algoqnuinlive.com)
 */

package com.radm0018algonquincollege.hilo;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final String ABOUT_DIALOG_TAG = "About Dialog";
    public int theNumber = (int) (Math.random() * (1000 - 1)) + 1; //theNumber, generates a number between 1 and 1000
    public int attempts = 0; //attempts set to 0
    private EditText userGuessInput;

    //Dialog
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about) {
            DialogFragment newFragment = new AboutDialogFragment();
            newFragment.show(getFragmentManager(), ABOUT_DIALOG_TAG);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userGuessInput = (EditText) findViewById(R.id.GuessInput); //Get input from GuessInput field
        final TextView attemptsText = (TextView) findViewById(R.id.attemptsText); //Update triesText after every attempt

        //Creating the Guess and Reset buttons
        Button GuessButton = (Button) findViewById(R.id.GuessButton);
        Button ResetButton = (Button) findViewById(R.id.ResetButton);

        attemptsText.setText("Attempts: " + attempts + "\\10"); //Creates attemptsText when the app launches

        //Functionality of guess button
        GuessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userGuess = userGuessInput.getText().toString();

                //Checks to see if the user input is empty or equal to 0 or more than 1000
                if (userGuess.isEmpty() || Integer.parseInt(userGuess) > 1000 || Integer.parseInt(userGuess) == 0) {

                    userGuessInput.setError("Please enter a number between 1-1000");
                    userGuessInput.requestFocus();
                    return;
                }

                if (attempts < 10) { //Checks if the user has less than 10 attempts

                    if (theNumber == Integer.parseInt(userGuess)) { //Checks if the guess is equal to theNumber

                        if (attempts <= 5) { //if user had less than 5 attempts
                            Toast.makeText(getApplicationContext(), " Extraordinary Guessing! You must be a computer!", Toast.LENGTH_LONG).show();
                            return;

                        } else { //if User had more than 5 tries 6-10
                            Toast.makeText(getApplicationContext(), "Great Guessing! But you can do better...", Toast.LENGTH_LONG).show();
                            return;
                        }

                    } else {
                        if (theNumber > Integer.parseInt(userGuess)) { //Input number is less than theNumber
                            Toast.makeText(getApplicationContext(), "Too low! Guess Again!", Toast.LENGTH_SHORT).show();

                        } else { //Input number is higher than theNumber
                            Toast.makeText(getApplicationContext(), "Too high! Guess Again!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    attempts++; //Adds another attempt if user fails at guessing theNumber
                    attemptsText.setText("Tries: " + attempts + "\\10"); //Updates the triesText

                } else { //User runs out of attempts, must hit the Reset Button
                    Toast.makeText(getApplicationContext(), "Please Press Reset!", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Reset Button- when reset game randomly pick a new theNumber, resets the number of guesses back to 0
        ResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                theNumber = (int) (Math.random() * (1000 - 1)) + 1;
                attempts = 0;
                attemptsText.setText("Tries: " + attempts + "\\10");
                userGuessInput.setText("");
            }
        });

        //Reset Button when clicked longer by user (theNumber) will be displayed
        ResetButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Toast.makeText(getApplicationContext(), "The number was " + theNumber + "!", Toast.LENGTH_LONG).show();
                userGuessInput.setText("");
                return false;
            }
        });
    }
}