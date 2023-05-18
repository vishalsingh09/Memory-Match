package com.example.memorymatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GameScreen extends AppCompatActivity implements View.OnClickListener {

    private int numberOfElements;

    private MemoryButton[] buttons;

    private int[] buttonGraphicLocations;
    private int[] buttonGraphics;
    private int[] pictures;

    private int[] threestarlist;
    private int[] twostarlist;
    private int[] onestarlist;

    private MemoryButton selectedButton1;
    private MemoryButton selectedButton2;

    private boolean isBusy = false;

    private int counter;
    private int moves;
    private boolean allMatched = false;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private TextView statspopup_msg, statspopup_points;
    private TextView points;
    private TextView movesTextView;
    private Button statspopup_repeat, statspopup_next;
    private ImageView statspopup_xicon;
    private Chronometer chronometer;
    private int lastPause;
    private boolean running;
    private Drawable drawable1;
    private Bitmap bitmap;
    public static String currCards = "default";
    private ImageView ImageView4;
    private StorageReference mStorageReference;

    public static final String ROW = "com.example.matchingalgorithmmanu.ROW";
    public static final String COL = "com.example.matchingalgorithmmanu.COL";
    public static final String LEV = "com.example.matchingalgorithmmanu.LEV";

    private int rowsRepeat;
    private int columnsRepeat;
    private int numRows;
    private int numColumns;
    private int currLev;
    private int pointCount;
    private TextView numHints;
    private int indexOfHint;

    private TextView countdown;
    User user;
    private boolean cdmode;
    private boolean cdmodedone;
    private int pairsLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        user  = MainActivity.firebaseHelper.getUser();

        mStorageReference = FirebaseStorage.getInstance().getReference().child("animals/animals1.png");

        try {
            final File localFile = File.createTempFile("animals1", "png");
            mStorageReference.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(GameScreen.this, "Picture Retrieved", Toast.LENGTH_SHORT).show();
                            bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            drawable1 = new BitmapDrawable(getResources(), bitmap);
                            //((ImageView)findViewById(R.id.imageView4)).setImageBitmap(bitmap);
                            //((ImageView)findViewById(R.id.imageView4)).setImageDrawable(drawable1);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(GameScreen.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        countdown = findViewById(R.id.countdown);
        chronometer = findViewById(R.id.chronometer);
        cdmode = false;

        points = findViewById(R.id.textView6);
        points.setText(MainActivity.firebaseHelper.getUser().getCurrency() + " points");

        movesTextView = findViewById(R.id.textView7);
        movesTextView.setText("Moves: " + moves);

        Intent intent = getIntent();

        numRows = intent.getIntExtra(levelselector.ROW, 0);
        numColumns = intent.getIntExtra(levelselector.COL, 0);
        currLev = intent.getIntExtra(levelselector.LEV, 0);

        time();

        rowsRepeat = numRows;
        columnsRepeat = numColumns;

        Log.i("Denna", "row: " + numRows);
        Log.i("Denna", "col: " + numColumns);

        pictures = new int[18];

        fillPictures();

        random();

        GridLayout gridLayout = (GridLayout) findViewById(R.id.grid_layout_4x4);

//        gridLayout.setColumnCount(3);
//        gridLayout.setRowCount(4);
//
//        int numColumns = gridLayout.getColumnCount();
//        int numRows = gridLayout.getRowCount();

        numberOfElements = numRows * numColumns;

        counter = numberOfElements/2;

        buttons = new MemoryButton[numberOfElements];

        buttonGraphics = new int[numberOfElements/2];

        for(int i = 0; i < numberOfElements/2; i++) {
            buttonGraphics[i] = pictures[i];
        }

//        buttonGraphics[0] = R.drawable.button_1;
//        buttonGraphics[1] = R.drawable.button_2;
//        buttonGraphics[2] = R.drawable.button_3;
//        buttonGraphics[3] = R.drawable.button_4;
//        buttonGraphics[4] = R.drawable.button_5;
//        buttonGraphics[5] = R.drawable.button_6;
        //buttonGraphics[6] = R.drawable.button_7;
        //buttonGraphics[7] = R.drawable.button_8;

        buttonGraphicLocations = new int[numberOfElements];

        shuffleButtonGraphics();

        for(int r = 0; r < numRows; r++) {
            for(int c = 0; c < numColumns; c++) {
                MemoryButton tempButton = new MemoryButton(this, r, c, buttonGraphics[buttonGraphicLocations[r * numColumns + c]], currLev);
                //tempButton.tempParams

                //tempButton.setPadding(500, 500, 0, 0);
                tempButton.setId(View.generateViewId());
                tempButton.setOnClickListener(this);
                buttons[r * numColumns + c] = tempButton;
                gridLayout.addView(tempButton);
            }
        }
        numHints = findViewById(R.id.textViewHints);
        numHints.setText("x" + user.getHints());

    }

    public void time() {
        if(currLev == 1 || currLev == 2 || currLev == 3 || currLev == 4 || currLev == 5 || currLev == 6) {
            if (!running) {
                Log.i("MANU", "in time method");
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                running = true;
            }
        }
        else {
            if(currLev == 7) {
                countdown(10000);
            }
            else if(currLev == 8) {
                countdown(15000);
            }
            else if(currLev == 9) {
                countdown(28000);
            }
            else if(currLev == 10) {
                countdown(30000);
            }
            else if(currLev == 11) {
                countdown(42000);
            }
            else {
                countdown(60000);
            }
        }
    }

    public void countdown(long time) {
        chronometer.setVisibility(View.INVISIBLE);
        countdown.setVisibility(View.VISIBLE);

        new CountDownTimer(time, 1000) {

            public void onTick(long millisUntilFinished) {
                countdown.setText("Seconds Remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                countdown.setText("DONE");
                chronometer.setVisibility(View.VISIBLE);
                countdown.setVisibility(View.INVISIBLE);
                cdmode = true;
                incdmode();
                //check();
            }
        }.start();
    }

    public void check() {

    }

    protected void shuffleButtonGraphics() {
        Random rand = new Random();

        for(int i = 0; i < numberOfElements; i++) {
            buttonGraphicLocations[i] = i % numberOfElements / 2;
        }
        for(int i = 0; i <numberOfElements; i++) {
            int temp = buttonGraphicLocations[i];

            int swapIndex = rand.nextInt(numberOfElements);

            buttonGraphicLocations[i] = buttonGraphicLocations[swapIndex];

            buttonGraphicLocations[swapIndex] = temp;
        }
        //Log.d("Denna", "stuff: " + buttonGraphicLocations);
    }

    public void random() {
        Random rnd = new Random();
        for(int i = pictures.length-1; i > 0; i--) {
            int j = rnd.nextInt(i + 1);

            int temp = pictures[i];
            pictures[i] = pictures[j];
            pictures[j] = temp;
        }
    }

    public void fillPictures() {

        if(currCards.equals("default"))
        {
            for(int i=1;i<=18;i++)
            {
                pictures[i-1] = getResources().getIdentifier("button_" + i,"drawable",getPackageName());
            }
        }
        if(currCards.equals("animals"))
        {
            for(int i=1;i<=18;i++)
            {
                pictures[i-1] = getResources().getIdentifier("animals" + i,"drawable",getPackageName());
            }
        }
        if(currCards.equals("flags"))
        {
            for(int i=1;i<=18;i++)
            {
                pictures[i-1] = getResources().getIdentifier("flag" + i,"drawable",getPackageName());
            }
        }
    }

    public void useHint(View view) {
       Log.d("Denna", "inside hints");
       Log.d("Denna", "id: " + buttons[1].getFrontDrawbleId());

       int r = (int) (Math.random() * (numberOfElements- 1 - 0)) + 0;

       if(user.getHints() > 0)
       {
           for (int i = numberOfElements - 2; i > 0; i--) {
               if (buttons[numberOfElements - 1].getFrontDrawbleId() == buttons[i].getFrontDrawbleId()) {
                   Log.d("Denna", "MATCH");
                   buttons[numberOfElements - 1].flip();
                   buttons[i].flip();
                   //buttons[numberOfElements - i].setMatched(true);
                   user.setHints(user.getHints() - 1);
                   counter--;
               }
           }

       }
       else
       {
           Toast.makeText(GameScreen.this, "No more hints", Toast.LENGTH_SHORT).show();
       }
       numHints = findViewById(R.id.textViewHints);
       numHints.setText("x" + user.getHints());
//        buttons[0].flip();
//        buttons[indexOfHint].flip();

   }

    public void points() {

        Log.i("MANU", "in point method");

        threestarlist = new int[] {10, 15, 28, 30, 42, 60, 10, 10, 10, 10, 10, 10};
        twostarlist = new int[] {12, 20, 33, 33, 47, 65, 15, 15, 15, 15, 15, 15};
        onestarlist = new int[] {15, 25, 38, 40, 55, 73, 20, 20, 20, 20, 20, 20};

        if(lastPause < threestarlist[currLev - 1]) {
            pointCount = 500;
        }
        else if(lastPause < twostarlist[currLev - 1]) {
            pointCount = 35;
        }
        else if(lastPause < onestarlist[currLev - 1]) {
            pointCount = 20;
        }
        else {
            pointCount = 10;
        }

        user.setCurrency(user.getCurrency() + pointCount);
        user.setCurrentLevel(currLev + 1);

        Log.i("MANU", "user points" + user.getCurrency());

        MainActivity.firebaseHelper.editData(user);

    }

    public void stats() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View statsPopUpView = getLayoutInflater().inflate(R.layout.popup, null);
        statspopup_msg = (TextView) statsPopUpView.findViewById(R.id.message);
        statspopup_points = (TextView) statsPopUpView.findViewById(R.id.points);
        Log.i("MANU", "pointcountinstats: " + pointCount);
        statspopup_points.setText("+ " + pointCount + " Points!");
        statspopup_repeat = (Button) statsPopUpView.findViewById(R.id.repeatButton);
        statspopup_next = (Button) statsPopUpView.findViewById(R.id.nextButton);

        statspopup_xicon = (ImageView) statsPopUpView.findViewById(R.id.xicon);

        dialogBuilder.setView(statsPopUpView);
        dialog = dialogBuilder.create();
        dialog.show();
        dialog.getWindow().setLayout(1000, 1800); //Controlling width and height.

        statspopup_xicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        statspopup_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameScreen.this, levelselector.class);
                startActivity(intent);
            }
        });

        statspopup_repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameScreen.this, GameScreen.class);
                intent.putExtra(ROW, rowsRepeat);
                intent.putExtra(COL, columnsRepeat);
                intent.putExtra(LEV, currLev);
                startActivity(intent);
            }
        });
    }

    public void incdmode() {
        if(cdmode) {
            pairsLeft = counter;
            if(pairsLeft <= 0.25 * (numberOfElements/2)) {
                lastPause = 5;
            }
            else if(pairsLeft <= 0.50 * (numberOfElements/2)) {
                lastPause = 12;
            }
            else {
                lastPause = 17;
            }
            cdmodedone = true;
            points();
            stats();
        }
    }
    @Override
    public void onClick(View view) {
        if(isBusy) {
            moves++;
            movesTextView.setText("Moves: " + moves);
            return;
        }

        MemoryButton button = (MemoryButton) view;

        if(button.isMatched) {
            //return;
        }

        else if(selectedButton1 == null) {
            selectedButton1 = button;
            selectedButton1.flip();
            moves++;
            movesTextView.setText("Moves: " + moves);
            //return;
        }
        else if(selectedButton1.getId() == button.getId()) {
            //return;
            moves++;
            movesTextView.setText("Moves: " + moves);
        }

        else if(selectedButton1.getFrontDrawbleId() == button.getFrontDrawbleId()) {
            button.flip();

            button.setMatched(true);
            selectedButton1.setMatched((true));

            selectedButton1.setEnabled(false);
            button.setEnabled(false);

            selectedButton1 = null;

            counter--;
            //return;
        }
        else {
            selectedButton2 = button;
            selectedButton2.flip();
            isBusy = true;

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    selectedButton2.flip();
                    selectedButton1.flip();
                    selectedButton2 = null;
                    selectedButton1 = null;
                    isBusy = false;
                }
            }, 500);
        }

        if(counter == 0) {

            if(running)
            {
                if(currLev == 1 || currLev == 2 || currLev == 3 || currLev == 4 || currLev == 5 || currLev == 6) {
                    lastPause = (int) ((SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000);
                    chronometer.stop();
                    running = false;
                }
            }

            Log.i("MANU", "last pause val: " + lastPause);

            points();

            Log.d("HELLO", "it worked");

            stats();

        }
    }
}

