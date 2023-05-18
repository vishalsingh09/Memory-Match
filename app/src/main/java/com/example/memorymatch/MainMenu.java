package com.example.memorymatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainMenu extends AppCompatActivity {
    MediaPlayer song;
    User user;

    public static final String ROW = "com.example.matchingalgorithmmanu.ROW";
    public static final String COL = "com.example.matchingalgorithmmanu.COL";
    public static final String LEV = "com.example.matchingalgorithmmanu.LEV";
    private int row;
    private int col;
    private String TAG = "JACKSON";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Intent intent = getIntent();
        //user = intent.getParcelableExtra("USER");
        user = MainActivity.firebaseHelper.getUser();


        song = MediaPlayer.create(MainMenu.this,R.raw.backgroundmusic);
        song.start();
    }

    public void toGameScreen(View view) {
        Intent intent = new Intent(MainMenu.this, GameScreen.class);
        startActivity(intent);
    }

    public void toLevelSelector(View view) {
        Intent intent = new Intent(MainMenu.this, levelselector.class);
        startActivity(intent);
    }

    public void toSettings(View view) {
        Intent intent = new Intent(MainMenu.this, settings.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }

    public void toStore(View view) {
        Intent intent = new Intent(MainMenu.this, store.class);
        startActivity(intent);
    }

    public void buttonPress(View v) {
        user = MainActivity.firebaseHelper.getUser();
        int current = user.getCurrentLevel();
        Log.i(TAG, "Current level: " + current);
        if(current == 1){
            row = 2;
            col = 4;
            Intent intent4 = new Intent(MainMenu.this, GameScreen.class);
            intent4.putExtra(ROW, row);
            intent4.putExtra(COL, col);
            intent4.putExtra(LEV, current);
            startActivity(intent4);
        }
        else if (current == 2){
            row = 3;
            col = 4;
            Intent intent1 = new Intent(MainMenu.this, GameScreen.class);
            intent1.putExtra(ROW, row);
            intent1.putExtra(COL, col);
            intent1.putExtra(LEV, current);
            startActivity(intent1);
        }
        else if (current == 3){
            row = 4;
            col = 4;
            Intent intent6 = new Intent(MainMenu.this, GameScreen.class);
            intent6.putExtra(ROW, row);
            intent6.putExtra(COL, col);
            intent6.putExtra(LEV, current);
            startActivity(intent6);
        }
        else if (current == 4){
            row = 3;
            col = 6;
            Intent intent2 = new Intent(MainMenu.this, GameScreen.class);
            intent2.putExtra(ROW, row);
            intent2.putExtra(COL, col);
            intent2.putExtra(LEV, current);
            startActivity(intent2);
        }
        else if (current == 5){
            row = 6;
            col = 4;
            Intent intent3 = new Intent(MainMenu.this, GameScreen.class);
            intent3.putExtra(ROW, row);
            intent3.putExtra(COL, col);
            intent3.putExtra(LEV, current);
            startActivity(intent3);
        }
        else if (current == 6){
            row = 6;
            col = 6;
            Intent intent5 = new Intent(MainMenu.this, GameScreen.class);
            intent5.putExtra(ROW, row);
            intent5.putExtra(COL, col);
            intent5.putExtra(LEV, current);
            startActivity(intent5);
        }
        else if (current == 7){
            row = 2;
            col = 4;
            Intent intent7 = new Intent(MainMenu.this, GameScreen.class);
            intent7.putExtra(ROW, row);
            intent7.putExtra(COL, col);
            intent7.putExtra(LEV, current);
            startActivity(intent7);
        }
        else if (current == 8){
            row = 3;
            col = 4;
            Intent intent8 = new Intent(MainMenu.this, GameScreen.class);
            intent8.putExtra(ROW, row);
            intent8.putExtra(COL, col);
            intent8.putExtra(LEV, current);
            startActivity(intent8);
        }
        else if (current == 9){
            row = 4;
            col = 4;
            Intent intent9 = new Intent(MainMenu.this, GameScreen.class);
            intent9.putExtra(ROW, row);
            intent9.putExtra(COL, col);
            intent9.putExtra(LEV, current);
            startActivity(intent9);
        }
        else if (current == 10){
            row = 3;
            col = 6;
            Intent intent10 = new Intent(MainMenu.this, GameScreen.class);
            intent10.putExtra(ROW, row);
            intent10.putExtra(COL, col);
            intent10.putExtra(LEV, current);
            startActivity(intent10);
        }
        else if (current == 11){
            row = 6;
            col = 4;
            Intent intent11 = new Intent(MainMenu.this, GameScreen.class);
            intent11.putExtra(ROW, row);
            intent11.putExtra(COL, col);
            intent11.putExtra(LEV, current);
            startActivity(intent11);
        }
        else if (current == 12){
            row = 6;
            col = 6;
            Intent intent12 = new Intent(MainMenu.this, GameScreen.class);
            intent12.putExtra(ROW, row);
            intent12.putExtra(COL, col);
            intent12.putExtra(LEV, current);
            startActivity(intent12);
        }
        else {
            Intent intent = new Intent(MainMenu.this, levelselector.class);
            startActivity(intent);
        }
    }

}