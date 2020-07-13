package com.example.cardmatchgame.gameState;

import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.MotionEvent;

import com.example.cardmatchgame.Card;
import com.example.cardmatchgame.CardGameView;
import com.example.cardmatchgame.gameState.IState;

public class GameState implements IState {
    private CardGameView cardGameView;

    public GameState(CardGameView cardGameView) {
        this.cardGameView = cardGameView;
    }

    @Override
    public void playGame(MotionEvent event) {
        //화면에 표시할 카드
        Card _shuffle[][] = cardGameView.get_shuffle();
        //각 카드의 박스 영역 정보
        Rect _box_card[][] =cardGameView.get_box_card();
        MediaPlayer _sound_1=cardGameView.get_sound_1();

        //카드 뒤집는 처리
        int px = (int) event.getX();
        int py = (int) event.getY();
        //어떤 카드가 선택되었는지 확인
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 3; x++)
                if (_box_card[x][y].contains(px, py)) {
                    //선택된 카드 뒤집기
                    if (_shuffle[x][y].get_state() != _shuffle[x][y].get_matchedState()) { //맞춘 카드는 뒤집을 필요x
                        _sound_1.start();
                        if (cardGameView.get_selectedCard1() == null) { //첫 카드를 뒤집는 경우
                            cardGameView.set_selectedCard1(_shuffle[x][y]);
                            cardGameView.get_selectedCard1().playerOpen();
                        } else { //두 번째 카드 뒤집는 경우
                            if (cardGameView.get_selectedCard1() != _shuffle[x][y]) {//중복 뒤집기 방지
                                cardGameView.set_selectedCard2(_shuffle[x][y]);
                                cardGameView.get_selectedCard2().playerOpen();
                            }
                        }
                    }
                }
        }
    }
}
