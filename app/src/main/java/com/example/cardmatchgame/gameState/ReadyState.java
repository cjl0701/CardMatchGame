package com.example.cardmatchgame.gameState;

import android.view.MotionEvent;

import com.example.cardmatchgame.CardGameView;
import com.example.cardmatchgame.gameState.IState;

public class ReadyState implements IState {
    private CardGameView cardGameView;

    public ReadyState(CardGameView cardGameView) {
        this.cardGameView = cardGameView;
    }

    @Override
    public void playGame(MotionEvent event){
        cardGameView.startGame();
        cardGameView.changeState(cardGameView.get_gameState());
        /* before
        if (_state == STATE_READY) {
            startGame();
            _state = STATE_GAME;
        */
    }
}
