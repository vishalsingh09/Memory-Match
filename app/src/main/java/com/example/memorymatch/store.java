package com.example.memorymatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class store extends AppCompatActivity {

    private TextView points;
    private TextView hints;
    private TextView cards;
    private String TAG = "Jackson";
    private static ArrayList<String> cardPacks =new ArrayList<String>();
    private int counter = 0;
    private int cardCounter = 0;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        user  = MainActivity.firebaseHelper.getUser();

        points = findViewById(R.id.pointsTextView);
        points.setText("points: " + MainActivity.firebaseHelper.getUser().getCurrency());

        hints = findViewById(R.id.textView8);
        hints.setText("hints: " + MainActivity.firebaseHelper.getUser().getHints() + "x");

        cards = findViewById(R.id.textViewCards);
        cards.setText("card pack: " + GameScreen.currCards);

        Log.i(TAG, "Current points: " + MainActivity.firebaseHelper.getUser().getCurrency());

        cardPacks.add("default");

        if(cardPacks.size() == 3)
        {
            GameScreen.currCards = "flags";
        }
        else if(cardPacks.size() == 2)
        {
            GameScreen.currCards = "animals";
        }
        if(cardPacks.size() == 1)
        {
            GameScreen.currCards = "default";
        }

    }

    public void toMainMenu(View view) {
        Intent intent = new Intent(store.this, MainMenu.class);
        startActivity(intent);
    }

    public void buyHint(View view){
        if(user.getCurrency() >= 100) {
            user.setCurrency(user.getCurrency() - 100);
            user.setHints(user.getHints() + 1);
            MainActivity.firebaseHelper.editData(user);
            points.setText("points: " + MainActivity.firebaseHelper.getUser().getCurrency());
            hints.setText("hints: " + MainActivity.firebaseHelper.getUser().getHints() + "x");
        }
        else
        {
            Toast.makeText(store.this, "Get ur money up", Toast.LENGTH_SHORT).show();
        }
    }

    public void buyCards(View view){
        if(user.getCurrency() >= 200 && !cardPacks.contains("animals"))
        {
            GameScreen.currCards = "animals";
            counter = 1;
            cardPacks.add("animals");
            user.setCurrency(user.getCurrency() - 200);
            points.setText("points: " + MainActivity.firebaseHelper.getUser().getCurrency());
            cards.setText("card pack: " + GameScreen.currCards);
        }
        else if(user.getCurrency() >= 200 &&  !cardPacks.contains("flags"))
        {
            GameScreen.currCards = "flags";
            counter = 2;
            cardPacks.add("flags");
            user.setCurrency(user.getCurrency() - 200);
            points.setText("points: " + MainActivity.firebaseHelper.getUser().getCurrency());
            cards.setText("card pack: " + GameScreen.currCards);
        }
        else{
            Toast.makeText(store.this, "No more card packs or lack of funds", Toast.LENGTH_SHORT).show();
        }
    }

    public void switchCards(View view){
        if(GameScreen.currCards.equals("default"))
        {
            if(cardPacks.contains("animals"))
            {
                GameScreen.currCards = "animals";
                cards.setText("card pack: " + GameScreen.currCards);
            }
            else{
                Toast.makeText(store.this, "No more card packs", Toast.LENGTH_SHORT).show();
            }
        }
        else if(GameScreen.currCards.equals("animals"))
        {
            if(cardPacks.contains("flags"))
            {
                GameScreen.currCards = "flags";
                cards.setText("card pack: " + GameScreen.currCards);
            }
            else
            {
                GameScreen.currCards = "default";
                cards.setText("card pack: " + GameScreen.currCards);
            }
        }
        else
        {
            GameScreen.currCards = "default";
            cards.setText("card pack: " + GameScreen.currCards);
        }

    }
}