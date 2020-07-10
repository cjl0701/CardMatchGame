package com.example.cardmatchgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

public class CardGameView extends View {
    Bitmap _backGroundImage;
    Bitmap _cardBackSide;
    Bitmap _card_Red;
    Bitmap _card_Green;
    Bitmap _card_Blue;

    public static final int STATE_READY = 0;
    public static final int STATE_GAME = 1;
    public static final int STATE_END = 2;
    private int _state = STATE_READY;

    //화면에 표시할 카드
    Card _shuffle[][];

    //각 카드의 박스 영역 정보
    Rect _box_card[][];

    public CardGameView(Context context) {
        super(context);
        _backGroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.background, null);
        _cardBackSide = BitmapFactory.decodeResource(getResources(), R.drawable.backside, null);
        _card_Red = BitmapFactory.decodeResource(getResources(), R.drawable.front_red, null);
        _card_Green = BitmapFactory.decodeResource(getResources(), R.drawable.front_green, null);
        _card_Blue = BitmapFactory.decodeResource(getResources(), R.drawable.front_blue, null);

        //화면에 표시할 카드만큼 할당
        _shuffle = new Card[3][2];
        //카드마다 할당
        _box_card = new Rect[3][2];

        //카드를 생성하고 섞음
        setCardShuffle();
        //각 카드의 박스 영역 설정
        setCardBox();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        //배경 이미지 그리기
        canvas.drawBitmap(_backGroundImage, 0, 0, null);

        //카드 그려주기
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 3; x++)
                //카드 앞면을 그려야 하는 경우
                if (_shuffle[x][y]._state == Card.CARD_SHOW ||
                        _shuffle[x][y]._state == Card.CARD_PLAYEROPEN ||
                        _shuffle[x][y]._state == Card.CARD_MATCHED) {
                    //색상에 따라 카드 앞면 그리기
                    if (_shuffle[x][y]._color == Card.IMG_RED)
                        canvas.drawBitmap(_card_Red, 100 + x * 230, 600 + y * 320, null);
                    else if (_shuffle[x][y]._color == Card.IMG_GREEN)
                        canvas.drawBitmap(_card_Green, 100 + x * 230, 600 + y * 320, null);
                    else if (_shuffle[x][y]._color == Card.IMG_BLUE)
                        canvas.drawBitmap(_card_Blue, 100 + x * 230, 600 + y * 320, null);
                }
                //카드 뒷면을 그려야 하는 경우
                else
                    canvas.drawBitmap(_cardBackSide, 100 + x * 230, 600 + y * 320, null);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (_state == STATE_READY) {
            startGame();
            _state = STATE_GAME;
        } else if (_state == STATE_GAME) {
            //카드 뒤집는 처리
            int px = (int) event.getX();
            int py = (int) event.getY();
            for (int y = 0; y < 2; y++)
                for (int x = 0; x < 3; x++)
                    //선택된 카드 뒤집기
                    if (_box_card[x][y].contains(px, py))
                        _shuffle[x][y]._state = Card.CARD_PLAYEROPEN;
        } else if (_state == STATE_END) {
            _state = STATE_READY;
        }
        //화면 갱신
        invalidate(); //draw 이벤트 발생하여 onDraw() 호출

        //ACTION_MOVE나 ACTION_UP의 액션 이벤트 처리를 위해서는 TRUE를 반환해야 함
        return true;
    }

    public void startGame() {
        //모든 카드를 뒷면 상태로 만든다
        _shuffle[0][0]._state = Card.CARD_CLOSE;
        _shuffle[0][1]._state = Card.CARD_CLOSE;
        _shuffle[1][0]._state = Card.CARD_CLOSE;
        _shuffle[1][1]._state = Card.CARD_CLOSE;
        _shuffle[2][0]._state = Card.CARD_CLOSE;
        _shuffle[2][1]._state = Card.CARD_CLOSE;

        //화면 갱신
        invalidate();
    }

    //각각의 색을 가진 카드들을 생성
    public void setCardShuffle() {
        //랜덤으로 해야 하지만, 일단 단순히 고정된 값으로
        _shuffle[0][0] = new Card(Card.IMG_RED);
        _shuffle[0][1] = new Card(Card.IMG_BLUE);
        _shuffle[1][0] = new Card(Card.IMG_GREEN);
        _shuffle[1][1] = new Card(Card.IMG_GREEN);
        _shuffle[2][0] = new Card(Card.IMG_BLUE);
        _shuffle[2][1] = new Card(Card.IMG_RED);
    }

    private void setCardBox() {
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 3; x++)
                //각 카드의 박스 값을 생성
                _box_card[x][y] = new Rect(100 + x * 230, 600 + y * 320, 100 + x * 230 + 200, 600 + y * 320 + 300);
        }
    }
}