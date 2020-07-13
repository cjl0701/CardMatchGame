package com.example.cardmatchgame.cardcolor;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.cardmatchgame.cardcolor.CardColor;

public class RedColor implements CardColor {
    private Bitmap card_Red;

    public RedColor(Bitmap card_Red){
        this.card_Red=card_Red;
    }
    
    @Override
    public void drawCard(Canvas canvas, int x, int y) {
        canvas.drawBitmap(card_Red, 100 + x * 230, 600 + y * 320, null);
    }
}
