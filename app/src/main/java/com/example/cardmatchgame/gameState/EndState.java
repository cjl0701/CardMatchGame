package com.example.cardmatchgame.gameState;

import android.view.MotionEvent;

import com.example.cardmatchgame.CardGameView;
import com.example.cardmatchgame.gameState.IState;

public class EndState implements IState {
    private CardGameView cardGameView;

    public EndState(CardGameView cardGameView) {
        this.cardGameView = cardGameView;
    }

    @Override
    public void playGame(MotionEvent event){
        cardGameView.changeState(cardGameView.get_readyState());
        cardGameView.restart();
        /*
        else if (_state == STATE_END) {
            restart();
         */
    }
}
