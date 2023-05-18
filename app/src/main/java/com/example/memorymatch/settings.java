package com.example.memorymatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class settings extends AppCompatActivity {

    User user;
    private String TAG = "JVM";
    private EditText firstNameET, lastNameET, usernameET, emailET, settingsPasswordET;
    private TextView topLeftTV, topRightTV;
    private static String uid = null;
    private boolean changeMethod = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Pass the user from SignUp and Login to MainMenu, and then to settings through intents
        Intent intent = getIntent();
        user = MainActivity.firebaseHelper.getUser();

        Log.i("TAG", "User output: " + user.toString());

        String firstname = user.getFirstName();
        String lastname = user.getLastName();
        String username = user.getUsername();
        String email = user.getEmail();
        String password = user.getPassword();

        Log.d(TAG, "password: " + password);

        firstNameET =findViewById(R.id.settingsFirstNameET);
        lastNameET = findViewById(R.id.settingsLastNameET);
        usernameET = findViewById(R.id.settingsUsernameET);
        emailET = findViewById(R.id.settingsEmailET);
        settingsPasswordET = findViewById(R.id.settingsPasswordET);

        firstNameET.setText(firstname);
        lastNameET.setText(lastname);
        usernameET.setText(username);
        emailET.setText(email);
        settingsPasswordET.setText(password);

        topLeftTV = findViewById(R.id.cancelTV);
        topRightTV = findViewById(R.id.doneTV);
        topLeftTV.setText("Back");
        topRightTV.setText("Edit Profile");
    }

    public void toMainMenu(View view){
        if(!changeMethod){
            Intent intent = new Intent(settings.this, MainMenu.class);
            startActivity(intent);
        }
        else if(changeMethod){
            // Making Edit texts unclickable
            firstNameET.setFocusable(false);
            firstNameET.setFocusableInTouchMode(false);
            lastNameET.setFocusable(false);
            lastNameET.setFocusableInTouchMode(false);
            usernameET.setFocusable(false);
            usernameET.setFocusableInTouchMode(false);
            emailET.setFocusable(false);
            emailET.setFocusableInTouchMode(false);
            settingsPasswordET.setFocusable(false);
            settingsPasswordET.setFocusableInTouchMode(false);

            // Change Edit Texts back to REAL value saved in user, not just whats written
            firstNameET.setText(user.getFirstName());
            lastNameET.setText(user.getLastName());
            usernameET.setText(user.getUsername());
            emailET.setText(user.getEmail());
            settingsPasswordET.setText(user.getPassword());

            // Changing Text View texts
            topLeftTV.setText("Back");
            topRightTV.setText("Edit Profile");

            // Change Boolean
            changeMethod = false;
        }
    }

    public void editProfile(View view){
        if(!changeMethod) {
            // Changing Text View texts
            topLeftTV.setText("Cancel");
            topRightTV.setText("Done");

            // Making Edit Texts Clickable
            firstNameET.setFocusable(true);
            firstNameET.setFocusableInTouchMode(true);
            lastNameET.setFocusable(true);
            lastNameET.setFocusableInTouchMode(true);
            usernameET.setFocusable(true);
            usernameET.setFocusableInTouchMode(true);
            emailET.setFocusable(true);
            emailET.setFocusableInTouchMode(true);
            settingsPasswordET.setFocusable(true);
            settingsPasswordET.setFocusableInTouchMode(true);

            Log.i(TAG, "Focusable is true. topLeftTV: " + topLeftTV.getText());

            // Change boolean
            changeMethod = true;
        }
        else if(changeMethod){
            // Making Edit Texts Unclickable
            firstNameET.setFocusable(false);
            firstNameET.setFocusableInTouchMode(false);
            lastNameET.setFocusable(false);
            lastNameET.setFocusableInTouchMode(false);
            usernameET.setFocusable(false);
            usernameET.setFocusableInTouchMode(false);
            emailET.setFocusable(false);
            emailET.setFocusableInTouchMode(false);
            settingsPasswordET.setFocusable(false);
            settingsPasswordET.setFocusableInTouchMode(false);

            // Save changed data to the user
            user.setFirstName(firstNameET.getText().toString());
            user.setLastName(lastNameET.getText().toString());
            user.setUsername(usernameET.getText().toString());
            user.setEmail(emailET.getText().toString());
            user.setPassword(settingsPasswordET.getText().toString());

            // Update the user in firestore
            MainActivity.firebaseHelper.editData(user);

            // Update Text View text
            topLeftTV.setText("Back");
            topRightTV.setText("Edit Profile");

            // Change boolean
            changeMethod = false;
        }


    }

    public void saveChanges(View view){
        user.setFirstName(firstNameET.getText().toString());
        user.setLastName(lastNameET.getText().toString());
        user.setUsername(usernameET.getText().toString());
        user.setEmail(emailET.getText().toString());
        user.setPassword(settingsPasswordET.getText().toString());

        MainActivity.firebaseHelper.editData(user);

        Intent intent = new Intent(settings.this, MainMenu.class);
        startActivity(intent);
    }

    public void signOut(View view) {
        MainActivity.firebaseHelper.getmAuth().signOut();
        MainActivity.firebaseHelper.updateUid(null);

        Intent intent = new Intent(settings.this, MainActivity.class);
        startActivity(intent);
    }

}
