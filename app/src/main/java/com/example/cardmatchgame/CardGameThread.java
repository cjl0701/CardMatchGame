package com.example.cardmatchgame;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

public class CardGameThread extends Thread {
    CardGameView _cardGameView;
    public CardGameThread(CardGameView _cardGameView) {
        this._cardGameView = _cardGameView;
    }

    @Override
    public void run() {
        while(true) {
            _cardGameView.checkMatch();
            if(_cardGameView.isMatchedAll()){
                System.out.println("스레드 종료");
                Message message = _cardGameView.handler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putInt("value",1);
                message.setData(bundle);
                _cardGameView.handler.sendMessage(message);
                break;
            }
        }
    }
}
