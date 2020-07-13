package com.example.cardmatchgame.cardstate;

import android.graphics.Canvas;

import com.example.cardmatchgame.Card;
import com.example.cardmatchgame.cardstate.CardState;

public class ShowState implements CardState {
    private Card card;

    public ShowState(Card card){
        this.card=card;
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        //색상에 따라 카드 앞면 그리기
        card.drawCard(canvas, x, y);

        /* before
        if (card._color == Card.IMG_RED)
            canvas.drawBitmap(_card_Red, 100 + x * 230, 600 + y * 320, null);
        else if (card._color == Card.IMG_GREEN)
            canvas.drawBitmap(_card_Green, 100 + x * 230, 600 + y * 320, null);
        else if (card._color == Card.IMG_BLUE)
            canvas.drawBitmap(_card_Blue, 100 + x * 230, 600 + y * 320, null);
        */
    }
}
