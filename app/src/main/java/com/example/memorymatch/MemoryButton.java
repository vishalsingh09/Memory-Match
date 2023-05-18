package com.example.memorymatch;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Button;
import android.widget.GridLayout;

import androidx.appcompat.widget.AppCompatDrawableManager;

public class MemoryButton extends androidx.appcompat.widget.AppCompatButton {

    protected int row;
    protected int column;
    protected int frontDrawbleId;
    protected int currLev;

    protected boolean isFlipped = false;
    protected boolean isMatched = false;

    protected Drawable front;
    protected Drawable back;

    public MemoryButton(Context context, int r, int c, int frontImageDrawableID, int level) {
        super(context);

        row = r;
        column = c;
        frontDrawbleId = frontImageDrawableID;
        currLev = level;

        front = AppCompatDrawableManager.get().getDrawable(context, frontImageDrawableID);
        back = AppCompatDrawableManager.get().getDrawable(context, R.drawable.button_question);

        setBackground(back);

        GridLayout.LayoutParams tempParams = new GridLayout.LayoutParams(GridLayout.spec(r), GridLayout.spec(c));

        if(currLev == 6 || currLev == 5 || currLev == 11 || currLev == 4 || currLev == 10 || currLev == 12) {
            tempParams.width = (int) getResources().getDisplayMetrics().density * 75;
            tempParams.height = (int) getResources().getDisplayMetrics().density * 75;

            setLayoutParams(tempParams);
        }
        else {
            tempParams.width = (int) getResources().getDisplayMetrics().density * 100;
            tempParams.height = (int) getResources().getDisplayMetrics().density * 100;

            setLayoutParams(tempParams);
        }
    }

    public int getFrontDrawbleId() {
        return frontDrawbleId;
    }

    public void setFrontDrawbleId(int frontDrawbleId) {
        this.frontDrawbleId = frontDrawbleId;
    }

    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }

    public void flip() {
        if(isMatched) {
            return;
        }
        if(isFlipped) {
            setBackground(back);
            isFlipped = false;
        }
        else {
            setBackground(front);
            isFlipped = true;
        }
    }
}
