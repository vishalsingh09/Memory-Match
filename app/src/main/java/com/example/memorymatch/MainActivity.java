package com.example.memorymatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    public static FirebaseHelper firebaseHelper;
    private Button play;
    MediaPlayer song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        song = MediaPlayer.create(MainActivity.this,R.raw.backgroundmusic);
        song.start();
        firebaseHelper = new FirebaseHelper();


    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check if user is signed in
        Log.i("JACKSON", "Inside onstart");
        updateIfLoggedIn();
    }
    public void updateIfLoggedIn(){
        FirebaseUser user = firebaseHelper.getmAuth().getCurrentUser();
        //Log.i("JACKSON", "CUrrent user is " + MainActivity.firebaseHelper.getUser().toString());
        if (user != null){
            firebaseHelper.attachReadDataToUser();

            Intent intent = new Intent(MainActivity.this, MainMenu.class);
            startActivity(intent);
        }
    }


    public void toMainMenu(View view) {
        Intent intent = new Intent(MainActivity.this, MainMenu.class);
        startActivity(intent);
    }
    public void toLoginActivity(View view) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
    public void toSignupActivity(View view) {
        Intent intent = new Intent(MainActivity.this, SignupActivity.class);
        startActivity(intent);
    }

}