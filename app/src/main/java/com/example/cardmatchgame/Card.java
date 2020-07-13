package com.example.cardmatchgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.cardmatchgame.cardcolor.BlueColor;
import com.example.cardmatchgame.cardcolor.CardColor;
import com.example.cardmatchgame.cardcolor.GreenColor;
import com.example.cardmatchgame.cardcolor.RedColor;
import com.example.cardmatchgame.cardstate.CardState;
import com.example.cardmatchgame.cardstate.CloseState;
import com.example.cardmatchgame.cardstate.MatchedState;
import com.example.cardmatchgame.cardstate.PlayerOpenState;
import com.example.cardmatchgame.cardstate.ShowState;

//카드 상태 정보
public class Card {
    //현재의 상태 -> 상태 별로 기능 동작
    CardState _state;
    CardColor _color;

    //상태별 인스턴스
    CardState _showState;
    CardState _closeState;
    CardState _playerOpenState;
    CardState _matchedState;

    //색상별 인스턴스
    CardColor _red;
    CardColor _green;
    CardColor _blue;

    Bitmap _cardBackSide; //카드 뒷면

    public Card(Bitmap card_Red,Bitmap card_Green,Bitmap card_Blue, Bitmap cardBackSide) {
        _showState = new ShowState(this);
        _closeState = new CloseState(this);
        _playerOpenState = new PlayerOpenState(this);
        _matchedState = new MatchedState(this);
        _state = _showState;

        _red = new RedColor(card_Red);
        _blue = new BlueColor(card_Blue);
        _green = new GreenColor(card_Green);

        _cardBackSide=cardBackSide;
    }

    public void changeState(CardState state){
        _state=state;
        System.out.println("상태가 "+_state+"로 바뀌었습니다\n");
    }

    public void changeColor(CardColor color){
        _color=color;
    }

    //상태에 따라 앞면 or 뒷면 그림
    public void draw(Canvas canvas, int x, int y){
        _state.draw(canvas, x, y);
    }

    //색상에 따라 카드 그림
    public void drawCard(Canvas canvas, int x, int y){
        _color.drawCard(canvas, x, y);
    }

    //카드 뒤집기
    public void close() {
        //_state.close();
        changeState(_closeState);
    }

    //선택한 카드 오픈
    public void playerOpen(){
        changeState(_playerOpenState);
    }

    public CardState get_showState() {
        return _showState;
    }

    public CardState get_closeState() {
        return _closeState;
    }

    public CardState get_playerOpenState() {
        return _playerOpenState;
    }

    public CardState get_matchedState() {
        return _matchedState;
    }

    public CardState get_state(){
        return _state;
    }
    public CardColor get_color() {
        return _color;
    }

    public Bitmap get_cardBackSide() {
        return _cardBackSide;
    }
}
