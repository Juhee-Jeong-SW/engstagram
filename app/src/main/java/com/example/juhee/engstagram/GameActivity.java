package com.example.juhee.engstagram;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int MILLISINFUTURE = 5*1000; //총 시간
    private static final int COUNT_DOWN_INTERVAL = 1000; // Tick에 대한 시간
    // onTick 메서드가 1초(1000)에 한번씩 10번(위에꺼) 실행되고 마지막으로 onFinish() 메서드 호출

    private int count; //10초 세기
    private TextView timeText; //layout에서 가져온 시간띄우는 변수
    private CountDownTimer countDownTimer; //timer 객체

    private static final int TOTAL_CARD_NUM = 6; // 총 카드 개수
private int milli;

    private int CLICK_CNT = 0; //클릭 시 카운트 증가

    String first, second;
    private int SUCCESS_CNT = 0; //짝을 다 맞춘 성공 카운트
    private boolean INPLAY = false; //카드 클릭할 수 있는지 여부
    private int index;
    private int index1;


    private int tempCount;

    private LinearLayout wordLayout;
    private LinearLayout meanLayout;

    private int temp;

    Button[] btnWord = new Button[100];
    Button[] btnMean = new Button[100];
    HashMap<String, String> wordMapper = new HashMap<String,String>();

    List<String> list;
    List<String> list1;


    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        //startDialog();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        new BackgroundTask().execute();


        timeText = (TextView)findViewById(R.id.timeText);
//       countDownTimer();
//        countDownTimer.start();


        //////////////////// 버튼 동적으로 추가하기  ////////////////////// 버튼에 단어 랜덤으로 뿌리기
        wordLayout = (LinearLayout)findViewById(R.id.wordLayout);
        meanLayout = (LinearLayout)findViewById(R.id.meanLayout);

        for(int i=0; i< wordMapper.size(); i++)
        {
            btnWord[i] = new Button(this);
            btnWord[i].setWidth(30);
            btnWord[i].setHeight(10);
            btnWord[i].setOnClickListener(this);
            btnWord[i].setBackgroundColor(Color.GRAY);
            String result = list.get(i);
            btnWord[i].setText(result);
            wordLayout.addView(btnWord[i]);
        }

        for(int i=0; i< wordMapper.size(); i++)
        {
            btnMean[i] = new Button(this);
            btnMean[i].setWidth(30);
            btnMean[i].setHeight(20);
            btnMean[i].setOnClickListener(this);
            btnMean[i].setBackgroundColor(Color.GRAY);
           String result = list1.get(i);
           btnMean[i].setText(result);
            meanLayout.addView(btnMean[i]);
        }


        //List<String> mapList =
//        for(int i=0; i<TOTAL_CARD_NUM; i++)
//        {
//            cardArray[i] = new Card(i/2); // 카드 생성
//            findViewById(cardId[i]).setOnClickListener(this); // 카드 클릭 리스너 설정
//            cardArray[i].card = (Button) findViewById(cardId[i]); // 카드 할당
//            cardArray[i].onBack(); // 카드 뒤집어 놓음
//        }

       // startGame();
    }



    // 카운트 다운해주는 함수
    public void countDownTimer()
    {

        System.out.println("여기는 timer 안 : " + getCount());
        //count = tempCount;
        int tmpFuture = (tempCount + 1 ) * 1000;
        countDownTimer = new CountDownTimer(getCount()*1000, COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeText.setText(String.valueOf(count));
                count--;
            }

            @Override
            public void onFinish() {
                timeText.setText(String.valueOf("finish"));
                timeOutAlert();

            }
        };
    }

    //timer의 thread의 상태를 체크하는 부분
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        try {
            countDownTimer.cancel();
        }
        catch (Exception e) {  e.printStackTrace(); }
        countDownTimer = null; //timer를 진행하고 있지 않을 때 cancel하면 오류가 나는데 이럴 때 catch문에서 timer를 초기화시켜줌
    }


    @Override
    public void onClick(View v) {

        /// 하나씩 지정해보기


        switch (CLICK_CNT) //click 숫자에 따라 하는 일이 달라지도록
        {
            case 0: //카드 1개만 클릭했을 때
                for(int i=0; i<5; i++)
                {
                    if(btnWord[i] == (Button) v) //먼저 단어 쪽 버튼을 클릭한다면
                    {
                        first = list.get(i); //리스트의 첫번째 스트링 넣어줌.
                        //System.out.println("이건 맵 테스트" + wordMapper.get(first));
                        btnWord[i].setBackgroundColor(Color.BLUE);
                        setCorrectIndex(i); //맞는 건 인덱스에 넣어주기 뒤에 사용하기 위하여
                        break;
                    }
                    else if(btnMean[i] == (Button) v) //먼저 단어 뜻 쪽 버튼을 클릭한다면
                    {
                        first = list1.get(i);
                        btnMean[i].setBackgroundColor(Color.BLUE);
                        setCorrectIndex1(i);
                        System.out.println(first);

                        break;
                    }
                }
                CLICK_CNT= 1;
                break;


            case 1: //카드 2개 클릭시
                for(int i=0; i<5; i++)
                {
                    if(btnMean[i] == (Button) v) // 앞에서 단어를 먼저 눌렀을 경우
                    {
                        second = list1.get(i);
                        System.out.println("카드 2개에 들어옴" + second);
                        if(second.equals(wordMapper.get(first))) //처음 누른 단어와 단어뜻이 일치하는 경우
                        {
                            System.out.println("맞아욤");
                            btnMean[i].setBackgroundColor(Color.BLUE);
                            //correctIndex2 = i;
                            //setCorrectIndex(i);
                            SUCCESS_CNT++;
                            if(SUCCESS_CNT == wordMapper.size())
                            {
                                finishAlert();
                            }
                            break;

                        }
                        else if(!second.equals(wordMapper.get(first))){ //일치하지 않는 경우
                            int temp = getCorrectIndex();
                            System.out.println(temp);

                            btnWord[temp].setBackgroundColor(Color.GRAY);
                            btnMean[i].setBackgroundColor(Color.GRAY);

                            Toast.makeText(getApplicationContext(), "It's wrong. Please try again", Toast.LENGTH_SHORT).show();
                            setCorrectIndex1(0);

                        }

                        break;
                    }
                    else if(btnWord[i] == (Button) v) // 앞에서 뜻을 먼저 눌렀을 경우
                    {
                        second = list.get(i);
                        int temp1 = getCorrectIndex1();
                        System.out.println("카드 2개에 들어옴" + second);
                        String fromValue = getWordfromMean(wordMapper, first);


                        if(second.equals(fromValue)){ //두개가 일치하는 경우
                            btnWord[i].setBackgroundColor(Color.BLUE);
                            SUCCESS_CNT++;
                            if(SUCCESS_CNT == wordMapper.size())
                            {
                                finishAlert();
                            }
                            break;
                        }

                        else if(!second.equals(fromValue)) //두개가 일치하지 않는 경우
                        {

                            btnMean[temp1].setBackgroundColor(Color.GRAY);
                            btnWord[i].setBackgroundColor(Color.GRAY);

                            Toast.makeText(getApplicationContext(), "It's wrong. Please try again", Toast.LENGTH_SHORT).show();

                            setCorrectIndex(0);

                        }

                    }

                }
                CLICK_CNT=0;
                break;



        }

//        if(SUCCESS_CNT==5){
//            finishAlert();
//        }

//        if(INPLAY) // 카드를 클릭할 수 있다면
//        {
//
//            for(int i =0 ; i<TOTAL_CARD_NUM; i++)
//            {
//                if(cardArray[i].card == (Button) v){
//                    //cardArray[i].card.setText();
//                }
//            }
//            switch (CLICK_CNT){
//                case 0: //카드 하나만 뒤집었을 때
//                    for(int i=0; i<TOTAL_CARD_NUM; i++)
//                    {
//                        if(cardArray[i].card == (Button) v){ //누른 버튼과
//                            first = cardArray[i];
//                            break;
//                        }
//
//                    }
//                    if(first.isBack) //이미 첫 카드가 뒤집혀 있으면
//                    {
//                        first.onFront(); //그건 그냥 앞으로
//                        System.out.print(first);
//                        CLICK_CNT = 1;
//                    }
//                    break;
//
//
//                case 1: //카드 두개 뒤집었을 때
//                    for(int i =0; i<TOTAL_CARD_NUM; i++)
//                    {
//                        if(cardArray[i].card == (Button) v) {
//                            second = cardArray[i];
//                            break;
//                        }
//                    }
//
//                    if(second.isBack){ //꼭 뒷면인 카드에만 해당하여(아직 뒤집힌 카드가 아닌 경우)
//                        second.onFront();
//
//                        if(first.value == second.value){
//                            SUCCESS_CNT++;
//                            if(SUCCESS_CNT == TOTAL_CARD_NUM/2){
//                                // Dialog띄우기
//                            }
//                        }
//                        else { //짝이 맞지 않을 경우
//                            System.out.print(second.toString());
//                        }
//                        CLICK_CNT = 0;
//                    }
//                    break;
//            }

    }


    //값(딴어뜻)으로부터 키(단어)를 얻기 위한 메소드
    private String getWordfromMean(HashMap<String,String> wordMapper, String value)
    {
        for (String v : wordMapper.keySet()) {
            if (wordMapper.get(v).equals(value)) {
                return v;
            }
        }
        return null;
    }


    /////첫번째 누른 버튼의 인덱스를 간직하기 위한 게터와 세터
    private void setCorrectIndex(int index)
    {
        this.index = index;

    }
    private int getCorrectIndex()
    {
        return index;
    }

    private void setCorrectIndex1(int index1)
    {
        this.index1 = index1;

    }
    private int getCorrectIndex1()
    {
        return index1;
    }

    private void setCount(int countTemp)
    {
        this.count = countTemp;
    }
    private int getCount(){return count;}


    public void finishAlert()
    {
        countDownTimer.cancel();
        AlertDialog.Builder alt1 = new AlertDialog.Builder(this);
        alt1.setMessage("Congratulations! You're done everything")
                .setCancelable(false)
                .setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        //finish();
        AlertDialog alt2 = alt1.create();
        alt2.setTitle("Game Over");
        alt2.show();

    }

    public void timeOutAlert()
    {
        AlertDialog.Builder alt1 = new AlertDialog.Builder(this);
        alt1.setMessage("Time's out.")
                .setCancelable(false)
                .setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        //finish();
        AlertDialog alt2 = alt1.create();
        alt2.setTitle("Game Over");
        alt2.show();

    }



        class BackgroundTask extends AsyncTask<Void, Void, String>
        {

            String target;
            String userID = realMainActivity.userID;


            @Override
            protected void onPreExecute() {
                try{
                    System.out.println(realMainActivity.userID);
                    target = "http://wngml815.cafe24.com/test/bookList.php?userID=" + URLEncoder.encode(userID, "UTF-8");

                }
                catch(Exception e){e.printStackTrace();}



            }

            @Override
            protected String doInBackground(Void... voids) {
                try{
                    URL url = new URL(target);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((inputStream)));
                    String temp;
                    StringBuilder stringBuilder = new StringBuilder();
                    while((temp = bufferedReader.readLine()) != null)
                    {
                        stringBuilder.append(temp+"\n");
                        //System.out.println(temp);
                    }

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return stringBuilder.toString().trim();
                }
                catch(Exception e) {e.printStackTrace();}
                return null;
            }

            @Override
            public void onProgressUpdate(Void... values){
                super.onProgressUpdate();
            }
            public void countDownTimer()
            {

                System.out.println("여기는 timer 안 : " + getCount());
                //count = tempCount;
                int tmpFuture = (tempCount + 1 ) * 1000;
                countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        timeText.setText(String.valueOf(getCount()));
                        count--;
                    }

                    @Override
                    public void onFinish() {
                        timeText.setText(String.valueOf("finish"));
                        timeOutAlert();

                    }
                };
            }
            @Override
            public void onPostExecute(String result) {
                //ListView homeListView;
                try{
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    int count = 0;
                    String bookWord, bookMean;

                    while(count < jsonArray.length())
                    {
                        JSONObject object = jsonArray.getJSONObject(count);
                        //homeList = object.getString("homeList");
                        bookWord = object.getString("boardWord");
                        bookMean = object.getString("boardMean");

                        wordMapper.put(bookWord,bookMean);

                        count++;

                    }

                    setCount(count);

                    list = new ArrayList<String>(wordMapper.keySet());
                    list1 = new ArrayList<String>(wordMapper.values());

                    Collections.shuffle(list1);
                    Collections.shuffle(list);


                    wordLayout = (LinearLayout)findViewById(R.id.wordLayout);
                    meanLayout = (LinearLayout)findViewById(R.id.meanLayout);

                    for(int i=0; i< wordMapper.size(); i++)
                    {
                        btnWord[i] = new Button(GameActivity.this);
                        btnWord[i].setWidth(30);
                        btnWord[i].setHeight(10);
                        btnWord[i].setOnClickListener(GameActivity.this);
                        btnWord[i].setBackgroundColor(Color.GRAY);
                        btnWord[i].setPadding(10,10,10,10);
                        String results = list.get(i);
                        btnWord[i].setText(results);
                        wordLayout.addView(btnWord[i]);
                    }

                    for(int i=0; i< wordMapper.size(); i++)
                    {
                        btnMean[i] = new Button(GameActivity.this);
                        btnMean[i].setWidth(30);
                        btnMean[i].setHeight(20);
                        btnMean[i].setOnClickListener(GameActivity.this);
                        btnMean[i].setBackgroundColor(Color.GRAY);
                        String results = list1.get(i);
                        btnMean[i].setText(results);
                        meanLayout.addView(btnMean[i]);
                    }

                    setCount(wordMapper.size());
                    System.out.println("여긴 백안 : " + getCount());
                    //timeText.setText(getCount()+"");
                    milli = getCount();

                    timeText = (TextView)findViewById(R.id.timeText);
   countDownTimer();
        countDownTimer.start();

                }catch(Exception e){

                }


            }

        }
}




//카드 담아두는 객체
//class Card {
//
//    int value; // 어떤 카드인지 파라미터로 넘어올 변수
//    boolean isBack; // 카드가 뒤집혀 있는지 여부
//    Button card; // 각 카드들(버튼)
//
//    Card(int value)
//    {
//        this.value = value;
//    }
//
//    public void onBack(){ //카드 뒷면이 보이도록 뒤집기
//        if(!isBack){ //뒤집혀 있지 않으면
//            card.setBackgroundColor(Color.GRAY);
//            card.setText(null);
//            isBack = true;
//        }
//    }
//
//    public void onFlip() { //카드를 뒤집음.
//        if(!isBack)
//        {
//            card.setBackgroundColor(Color.GRAY);
//
//            isBack = true;
//        }
//        else {
//            card.setBackgroundColor(Color.YELLOW);
//            System.out.println("뒤집힘");
//            isBack = false;
//        }
//    }
//
//    public void onFront(){ //카드 앞쪽 보여주기
//        if(isBack)
//        {
//            card.setBackgroundColor(Color.YELLOW);
//            card.setText("제발");
//            isBack = false;
//        }
//    }
//
//
//}


//    void startGame()
//    {
//        int[] random = new int [TOTAL_CARD_NUM]; // 랜덤 정수 담아두는 배열
//        int x; //임시로 숫자 생성하는 변수
//
////        for(int i =0; i<TOTAL_CARD_NUM; i++)
////        {
////
////            if(!cardArray[i].isBack)
////                cardArray[i].onBack();
////        }
//
//        boolean dup; //제대로 복사됐는지 여부
//        for(int i=0; i<TOTAL_CARD_NUM; i++) //0부터 6까지 랜덤으로 random배열에 저장하기
//        {
//                while(true) //중복확인
//                {
//                    dup = false;
//                    x = (int) (Math.random() * TOTAL_CARD_NUM); //x에 아무 숫자
//                    //System.out.println(x);
//                    for(int j =0; j<i; j++)
//                    {
//                        if(random[j] == x){ // 제대로 복사됐으면
//                            dup = true;
//                            break;
//                        }
//                    }
//                    if(!dup) break;
//                }
//                random[i] = x; // 랜덤으로 뽑은 정수를 random배열에 넣어준다
//            System.out.println(random[i]);
//        }
//
//
//
//        for(int i=0; i<TOTAL_CARD_NUM; i++)
//        {
//            cardArray[i].card = (Button) findViewById(cardId[random[i]]);
//
//            System.out.println(cardArray[i].card); //android.support.v7.widget.AppCompatButton{4a352a0 VFED..C.. ......ID 0,0-0,0 #7f080039 app:id/card03}
//            //cardArray[i].onFront();
//            //cardArray[i].card.setText(cardId[random[i]]);
//        }
//
//        SUCCESS_CNT = 0;
//        CLICK_CNT = 0;
//        INPLAY = true;
//
//    }