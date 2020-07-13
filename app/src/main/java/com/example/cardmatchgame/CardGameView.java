package com.example.cardmatchgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.cardmatchgame.cardcolor.CardColor;
import com.example.cardmatchgame.gameState.EndState;
import com.example.cardmatchgame.gameState.GameState;
import com.example.cardmatchgame.gameState.IState;
import com.example.cardmatchgame.gameState.ReadyState;

import java.io.IOException;

import static android.graphics.Bitmap.createScaledBitmap;

public class CardGameView extends View {
    Bitmap _backGroundImage;
    Bitmap _cardBackSide;
    Bitmap _card_Red;
    Bitmap _card_Green;
    Bitmap _card_Blue;

    IState _state;
    IState _readyState;
    IState _gameState;
    IState _endState;
    //요걸 상태 클래스화
    /*public static final int STATE_READY = 0;
    public static final int STATE_GAME = 1;
    public static final int STATE_END = 2;
    private int _state = STATE_READY;
    */

    public Card[][] get_shuffle() {
        return _shuffle;
    }

    public Rect[][] get_box_card() {
        return _box_card;
    }

    //화면에 표시할 카드
    Card _shuffle[][];

    //각 카드의 박스 영역 정보
    Rect _box_card[][];

    //짝 맞추기 비교를 위한 변수
    private Card _selectedCard1 = null;
    private Card _selectedCard2 = null;

    MediaPlayer _sound_Background;
    MediaPlayer _sound_1; //효과음

    public MediaPlayer get_sound_1() {
        return _sound_1;
    }

    Handler handler = new Handler();

    public CardGameView(Context context) {
        super(context);
        _backGroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.background, null);
        _cardBackSide = BitmapFactory.decodeResource(getResources(), R.drawable.backside, null);
        _card_Red = BitmapFactory.decodeResource(getResources(), R.drawable.front_red, null);
        _card_Green = BitmapFactory.decodeResource(getResources(), R.drawable.front_green, null);
        _card_Blue = BitmapFactory.decodeResource(getResources(), R.drawable.front_blue, null);

        _sound_Background = MediaPlayer.create(context, R.raw.background);
        _sound_1 = MediaPlayer.create(context, R.raw.effect3);

        _readyState = new ReadyState(this);
        _gameState = new GameState(this);
        _endState = new EndState(this);
        _state = _readyState;

        //화면에 표시할 카드만큼 할당
        _shuffle = new Card[3][2];
        //카드마다 할당
        _box_card = new Rect[3][2];

        //카드를 생성하고 섞음
        setCardShuffle();
        //각 카드의 박스 영역 설정
        setCardBox();

        _sound_Background.start();
        Toast.makeText(getContext(), "터치하면 시작합니다.", Toast.LENGTH_LONG).show();
        //짝맞추기를 검사하는 스레드 실행
        CardGameThread thread = new CardGameThread(this);
        thread.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        //배경 이미지 그리기
        int width = getWidth();
        int height = getHeight();
        _backGroundImage = createScaledBitmap(_backGroundImage, width, height, true);
        canvas.drawBitmap(_backGroundImage, 0, 0, null);

        //카드 그려주기
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 3; x++)
                _shuffle[x][y].draw(canvas, x, y);

                /* before
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
                else {
                    canvas.drawBitmap(_cardBackSide, 100 + x * 230, 600 + y * 320, null);
                }
                 */
        }
    }

    public void playGame(MotionEvent event) {
        _state.playGame(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        playGame(event);
        //gameState.do();
        /* before
        if (_state == STATE_READY) {
            startGame();
            _state = STATE_GAME;
        } else if (_state == STATE_GAME) {
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
                            if (_selectedCard1 == null) { //첫 카드를 뒤집는 경우
                                _selectedCard1 = _shuffle[x][y];
                                _selectedCard1.playerOpen();
                            } else { //두 번째 카드 뒤집는 경우
                                if (_selectedCard1 != _shuffle[x][y]) {//중복 뒤집기 방지
                                    _selectedCard2 = _shuffle[x][y];
                                    _selectedCard2.playerOpen();
                                }
                            }
                        }
                    }
            }
        } else if (_state == STATE_END) {
            restart();
        }
         */

        //화면 갱신
        invalidate(); //draw 이벤트 발생하여 onDraw() 호출

        //ACTION_MOVE나 ACTION_UP의 액션 이벤트 처리를 위해서는 TRUE를 반환해야 함
        return true;
    }

    public void restart() {
        //카드를 생성하고 섞음
        setCardShuffle();

        try {
            _sound_Background.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        _sound_Background.start();

        CardGameThread thread = new CardGameThread(this);
        thread.start();
    }

    public void checkMatch() {
        //두 카드 중 하나라도 선택이 안 되었다면 비교할 필요 없음
        if (_selectedCard1 == null || _selectedCard2 == null)
            return;

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //두 카드의 색상 비교
        if (_selectedCard1.get_color().getClass().isInstance(_selectedCard2.get_color())) {
            System.out.println("색상이 같아 매치 상태로 바꿈\n");
            //두 카드의 색상이 같으면 두 카드를 맞춘 상태로 바꿈
            _selectedCard1.changeState(_selectedCard1.get_matchedState());
            _selectedCard2.changeState(_selectedCard2.get_matchedState());
        } else {//두 카드의 색상이 다른 경우 두 카드를 이전처럼 뒷면으로 돌려줌
            System.out.println("색상이 달라 close 상태로 바꿈\n");
            _selectedCard1.close();
            _selectedCard2.close();
        }
        //다시 선택할 수 있도록 null로 설정
        _selectedCard1 = null;
        _selectedCard2 = null;

        //invalidate();
        postInvalidate();//스레드에서 사용하므로
    }

    public void startGame() {
        //모든 카드를 뒷면 상태로 만든다.
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 3; x++)
                _shuffle[x][y].close();
        }

        //화면 갱신
        invalidate();
    }

    //모든 카드가 매치되었는지 확인
    public boolean isMatchedAll() {
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 3; x++)
                if (_shuffle[x][y]._state != _shuffle[x][y].get_matchedState()) { //매치되지 않은 카드가 있다면
                    return false;
                }
        }
        changeState((_endState));
        _sound_Background.stop();
        return true;
    }

    //각각의 색을 가진 카드들을 생성
    public void setCardShuffle() {
        //초기값
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 3; x++)
                _shuffle[x][y] = new Card(_card_Red, _card_Green, _card_Blue, _cardBackSide);
        }
        _shuffle[0][0].changeColor(_shuffle[0][0]._red);
        _shuffle[0][1].changeColor(_shuffle[0][1]._red);
        _shuffle[1][0].changeColor(_shuffle[1][0]._blue);
        _shuffle[1][1].changeColor(_shuffle[1][1]._blue);
        _shuffle[2][0].changeColor(_shuffle[2][0]._green);
        _shuffle[2][1].changeColor(_shuffle[2][1]._green);

        //랜덤으로 섞기
        int x1, y1, x2, y2;
        CardColor temp;
        for (int i = 0; i < 6 * 2; i++) {
            x1 = (int) (Math.random() * 3);//0~2
            y1 = (int) (Math.random() * 2);//0~1
            x2 = (int) (Math.random() * 3);//0~2
            y2 = (int) (Math.random() * 2);//0~1
            temp = _shuffle[x1][y1].get_color();
            _shuffle[x1][y1].changeColor(_shuffle[x2][y2].get_color());
            _shuffle[x2][y2].changeColor(temp);
        }
    }

    private void setCardBox() {
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 3; x++)
                //각 카드의 박스 값을 생성
                _box_card[x][y] = new Rect(100 + x * 230, 600 + y * 320, 100 + x * 230 + 200, 600 + y * 320 + 300);
        }
    }

    public void changeState(IState state) {
        _state = state;
    }

    public IState get_readyState() {
        return _readyState;
    }

    public IState get_gameState() {
        return _gameState;
    }

    public IState get_endState() {
        return _endState;
    }
    public Card get_selectedCard1() {
        return _selectedCard1;
    }

    public Card get_selectedCard2() {
        return _selectedCard2;
    }
    public void set_selectedCard1(Card _selectedCard1) {
        this._selectedCard1 = _selectedCard1;
    }

    public void set_selectedCard2(Card _selectedCard2) {
        this._selectedCard2 = _selectedCard2;
    }
}