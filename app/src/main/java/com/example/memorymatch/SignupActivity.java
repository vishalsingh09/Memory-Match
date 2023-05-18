package com.example.memorymatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    // Global Variables
    private String TAG = "JVM";
    private EditText firstNameET, lastNameET, usernameET, emailET, passwordET, confirmPasswordET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // instantiate FirebaseHelper var

    }

    public void toMainMenu(View view) {
        Intent intent = new Intent(SignupActivity.this, MainMenu.class);
        startActivity(intent);
    }

    public void signUp(View v){
        // Make references to EditTexts
        firstNameET = findViewById(R.id.FirstNameET);
        lastNameET = findViewById(R.id.LastNameET);
        usernameET = findViewById(R.id.UsernameET);
        emailET = findViewById(R.id.EmailET);
        passwordET = findViewById(R.id.PasswordET);
        confirmPasswordET = findViewById(R.id.ConfirmPasswordET);

        // Get user data
        String firstName = firstNameET.getText().toString();
        String lastName = lastNameET.getText().toString();
        String username = usernameET.getText().toString();
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();
        String confirmPassword = confirmPasswordET.getText().toString();
        Log.i(TAG, firstName + " " + lastName + " " + email + " " + password);

        // verify all user data is entered
        if (firstName.length() == 0 || lastName.length() == 0 || username.length() == 0 ||
                email.length() == 0 || password.length() == 0 || confirmPassword.length() == 0){
            Toast.makeText(getApplicationContext(), "Enter all fields", Toast.LENGTH_SHORT).show();
        }
        else if (!password.equals(confirmPassword)) {
            Toast.makeText(getApplicationContext(), "Those passwords didn't match. Try again. ", Toast.LENGTH_SHORT).show();
        }
        else if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password must be at least 6 char long", Toast.LENGTH_SHORT).show();
        }
        else {
            // code to sign up user
            MainActivity.firebaseHelper.getmAuth().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                // Sign up worked and a user was created we want a reference to our user
                                FirebaseUser user = MainActivity.firebaseHelper.getmAuth().getCurrentUser();

                                // Adds a document to firestore with the users name and their unique
                                // UID from auth account
                                MainActivity.firebaseHelper.addUserToFirestore(firstName, lastName, username, email, password, user.getUid());

                                // Update the UID variable in firebaseHelper class so we know which
                                // user is logged in
                                MainActivity.firebaseHelper.updateUid(user.getUid());

                                // This is needed to help with asynch method calls in firebase
                                MainActivity.firebaseHelper.attachReadDataToUser();

                                // Update UI
                                // (old) updateIfLoggedIn();

                                // Include code to go to a new screen

                                User newUser = MainActivity.firebaseHelper.getUser();
                                //Log.i("TAG", newUser.toString());

                                Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                                intent.putExtra("USER", newUser);
                                startActivity(intent);
                            }
                            else{
                                // Sign up fails
                                Log.d(TAG, "Sign up failed" + task.getException());
                            }

                        }
                    });
        }
    }

}