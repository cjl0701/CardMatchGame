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
                /*UI는 메인 스레드 담당이므로 동시 접근 문제 발생! -> 스레드에서 UI에 접글할 때는 Handler를 써야 한다!*/
                //Toast.makeText(getContext(), "clear! 터치하면 재시작합니다.", Toast.LENGTH_LONG).show();

                /*너무 길어!
                Message message = _cardGameView.handler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putInt("value",1);
                message.setData(bundle);
                _cardGameView.handler.sendMessage(message);*/

                _cardGameView.handler.post(new Runnable() {
                    @Override
                    public void run() { //메인스레드에서 run()이 실행됨!
                        Toast.makeText(_cardGameView.getContext(), "clear! 터치하면 재시작합니다.", Toast.LENGTH_LONG).show();
                    }
                });
                break;
            }
        }
    }
}
