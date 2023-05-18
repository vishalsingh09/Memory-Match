package com.example.memorymatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class levelselector extends AppCompatActivity {

    public static final String ROW = "com.example.matchingalgorithmmanu.ROW";
    public static final String COL = "com.example.matchingalgorithmmanu.COL";
    public static final String LEV = "com.example.matchingalgorithmmanu.LEV";

    private Button button4x4;
    private int row;
    private int col;
    private int lev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levelselector);
    }

    public void toMainMenu(View view) {
        Intent intent = new Intent(levelselector.this, MainMenu.class);
        startActivity(intent);
    }

    public void buttonPress(View v) {
        switch (v.getId()) {
            case R.id.button_2x4:
                row = 2;
                col = 4;
                lev = 1;
                Intent intent4 = new Intent(levelselector.this, GameScreen.class);
                intent4.putExtra(ROW, row);
                intent4.putExtra(COL, col);
                intent4.putExtra(LEV, lev);
                startActivity(intent4);
                break;
            case R.id.button_3x4:
                row = 3;
                col = 4;
                lev = 2;
                Intent intent1 = new Intent(levelselector.this, GameScreen.class);
                intent1.putExtra(ROW, row);
                intent1.putExtra(COL, col);
                intent1.putExtra(LEV, lev);
                startActivity(intent1);
                break;
            case R.id.button_4x4:
                row = 4;
                col = 4;
                lev = 3;
                Intent intent6 = new Intent(levelselector.this, GameScreen.class);
                intent6.putExtra(ROW, row);
                intent6.putExtra(COL, col);
                intent6.putExtra(LEV, lev);
                startActivity(intent6);
                break;
            case R.id.button_3x6:
                row = 3;
                col = 6;
                lev = 4;
                Intent intent2 = new Intent(levelselector.this, GameScreen.class);
                intent2.putExtra(ROW, row);
                intent2.putExtra(COL, col);
                intent2.putExtra(LEV, lev);
                startActivity(intent2);
                break;
            case R.id.button_6x4:
                row = 6;
                col = 4;
                lev = 5;
                Intent intent3 = new Intent(levelselector.this, GameScreen.class);
                intent3.putExtra(ROW, row);
                intent3.putExtra(COL, col);
                intent3.putExtra(LEV, lev);
                startActivity(intent3);
                break;
            case R.id.button_6x6:
                row = 6;
                col = 6;
                lev = 6;
                Intent intent5 = new Intent(levelselector.this, GameScreen.class);
                intent5.putExtra(ROW, row);
                intent5.putExtra(COL, col);
                intent5.putExtra(LEV, lev);
                startActivity(intent5);
                break;
            case R.id.button_2x4t:
                row = 2;
                col = 4;
                lev = 7;
                Intent intent7 = new Intent(levelselector.this, GameScreen.class);
                intent7.putExtra(ROW, row);
                intent7.putExtra(COL, col);
                intent7.putExtra(LEV, lev);
                startActivity(intent7);
                break;
            case R.id.button_3x4t:
                row = 3;
                col = 4;
                lev = 8;
                Intent intent8 = new Intent(levelselector.this, GameScreen.class);
                intent8.putExtra(ROW, row);
                intent8.putExtra(COL, col);
                intent8.putExtra(LEV, lev);
                startActivity(intent8);
                break;
            case R.id.button_4x4t:
                row = 4;
                col = 4;
                lev = 9;
                Intent intent9 = new Intent(levelselector.this, GameScreen.class);
                intent9.putExtra(ROW, row);
                intent9.putExtra(COL, col);
                intent9.putExtra(LEV, lev);
                startActivity(intent9);
                break;
            case R.id.button_3x6t:
                row = 3;
                col = 6;
                lev = 10;
                Intent intent10 = new Intent(levelselector.this, GameScreen.class);
                intent10.putExtra(ROW, row);
                intent10.putExtra(COL, col);
                intent10.putExtra(LEV, lev);
                startActivity(intent10);
                break;
            case R.id.button_6x4t:
                row = 6;
                col = 4;
                lev = 11;
                Intent intent11 = new Intent(levelselector.this, GameScreen.class);
                intent11.putExtra(ROW, row);
                intent11.putExtra(COL, col);
                intent11.putExtra(LEV, lev);
                startActivity(intent11);
                break;
            case R.id.button_6x6t:
                row = 6;
                col = 6;
                lev = 12;
                Intent intent12 = new Intent(levelselector.this, GameScreen.class);
                intent12.putExtra(ROW, row);
                intent12.putExtra(COL, col);
                intent12.putExtra(LEV, lev);
                startActivity(intent12);
                break;
        }
    }

}