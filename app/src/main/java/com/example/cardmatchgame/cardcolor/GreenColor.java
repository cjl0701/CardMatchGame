package com.example.cardmatchgame.cardcolor;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.cardmatchgame.cardcolor.CardColor;

public class GreenColor implements CardColor {
    private Bitmap card_Green;

    public GreenColor(Bitmap card_green) {
        this.card_Green=card_green;
    }

    @Override
    public void drawCard(Canvas canvas, int x, int y) {
        canvas.drawBitmap(card_Green, 100 + x * 230, 600 + y * 320, null);
    }
}
