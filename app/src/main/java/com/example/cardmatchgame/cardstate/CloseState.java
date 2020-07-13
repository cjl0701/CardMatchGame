package com.example.cardmatchgame.cardstate;

import android.graphics.Canvas;

import com.example.cardmatchgame.Card;
import com.example.cardmatchgame.cardstate.CardState;

public class CloseState implements CardState {
    Card card;

    public CloseState(Card card) {
        this.card = card;
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        //카드 뒷면을 그림
        canvas.drawBitmap(card.get_cardBackSide(), 100 + x * 230, 600 + y * 320, null);
    }
}
