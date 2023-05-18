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

public class LoginActivity extends AppCompatActivity {

    private String TAG = "VMJ";
    private EditText emailET, passwordET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);


    }

    public void toMainMenu(View view) {
        Intent intent = new Intent(LoginActivity.this, MainMenu.class);
        startActivity(intent);
    }

    public void signIn(View v) {
        // Note we don't care what they entered for name here
        // it could be blank

        // Get user data
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();


        // verify all user data is entered
        if (email.length() == 0 || password.length() == 0) {
            Toast.makeText(getApplicationContext(), "Enter all fields", Toast.LENGTH_SHORT).show();
        }

        // verify password is at least 6 char long (otherwise firebase will deny)
        else if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password must be at least 6 char long", Toast.LENGTH_SHORT).show();
        }
        else {
            // code to sign in user

            MainActivity.firebaseHelper.getmAuth().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                // sign in succeeded, update UI with users info

                                // Could also make a user var like in signUp
                                MainActivity.firebaseHelper.updateUid(MainActivity.firebaseHelper.getmAuth().getCurrentUser().getUid());
                                // updateIfLoggedIn(); //update UI
                                Log.i(TAG, email + " logged in");

                                // This is needed to help with asych method calls in firebase
                                MainActivity.firebaseHelper.attachReadDataToUser();
                                Log.i(TAG, email + " read data attached");

                                // Use an intent to switch screens
                                // You can either say getApplicationContext() or the name of the
                                // current intent, see signUp method
                                Intent intent = new Intent(LoginActivity.this, MainMenu.class);
                                startActivity(intent);
                            }
                            else{
                                Log.d(TAG, "Sign in failed for " + email + ", " + password);
                            }
                        }
                    });
        }
    }

}