package com.example.juhee.engstagram;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class realMainActivity extends AppCompatActivity {

    public static String userID;
    private ListView homeListView;
    private ArrayList<HomeList> homeList;
    private HomeListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real);

        //UserDTO userDTO = new UserDTO(userID);



//        homeListView = (ListView) findViewById(R.id.homeListView);
//        homeList = new ArrayList<HomeList>();
//        homeList.add(new HomeList("공지사항", "stella" , "2017-11-08"));
//        homeList.add(new HomeList("공지사항", "kane" , "2017-11-08"));
//        homeList.add(new HomeList("공지사항", "son" , "2017-11-08"));
//        homeList.add(new HomeList("공지사항", "ale" , "2017-11-08"));
//
//        adapter = new HomeListAdapter(getApplicationContext(), homeList);
//        homeListView.setAdapter(adapter);

        final Button homeButton = (Button) findViewById(R.id.homeButton);
        final Button bookButton = (Button) findViewById(R.id.bookButton);
        final Button gameButton = (Button) findViewById(R.id.gameButton);
        final Button talkButton = (Button) findViewById(R.id.talkButton);
        final Button infoButton = (Button) findViewById(R.id.infoButton);
        final Button boardAddButton = (Button) findViewById(R.id.boardAddButton);

        //final Fragment homeFragment = (Fragment) findViewById(R.id.fragment_home);
       // final LinearLayout homeLinear = (LinearLayout) findViewById(R.id.homeLinear);
        //final FrameLayout mainFrame = (FrameLayout) findViewById(R.id.main_frame);
        final LinearLayout testLinear = (LinearLayout) findViewById(R.id.testLinear);
        Intent intent = new Intent(getApplicationContext(), BoardAddActivity.class);
        userID = getIntent().getStringExtra("user_id");

        boardAddButton.setOnClickListener(new View.OnClickListener() { //bookButton을 눌렀을 때 bookFragment로 이동하는 이벤트를 구현.
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BoardAddActivity.class);
                userID = getIntent().getStringExtra("user_id");
                intent.putExtra("user_id", userID);
                //System.out.println(userDTO.getUserID().toString());
                startActivity(intent);
            }
        });


        homeButton.setOnClickListener(new View.OnClickListener() { //bookButton을 눌렀을 때 bookFragment로 이동하는 이벤트를 구현.
            @Override
            public void onClick(View v) {
//                bookButton.setBackgroundColor(Color.GRAY);
//                gameButton.setBackgroundColor(Color.BLUE);
//                talkButton.setBackgroundColor(Color.BLUE);
//                infoButton.setBackgroundColor(Color.BLUE);
                testLinear.setVisibility(View.GONE);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, new HomeFragment());
                fragmentTransaction.commit();
            }
        });

        bookButton.setOnClickListener(new View.OnClickListener() { //bookButton을 눌렀을 때 bookFragment로 이동하는 이벤트를 구현.
            @Override
            public void onClick(View v) {
                System.out.println("home: "+ userID);
//                bookButton.setBackgroundColor(Color.GRAY);
//                gameButton.setBackgroundColor(Color.BLUE);
//                talkButton.setBackgroundColor(Color.BLUE);
//                infoButton.setBackgroundColor(Color.BLUE);

                testLinear.setVisibility(View.GONE);


                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, new BookFragment());
                fragmentTransaction.commit();
            }
        });

        gameButton.setOnClickListener(new View.OnClickListener() { //bookButton을 눌렀을 때 bookFragment로 이동하는 이벤트를 구현.
            @Override
            public void onClick(View v) {
//                gameButton.setBackgroundColor(Color.GRAY);
//                bookButton.setBackgroundColor(Color.BLUE);
//                talkButton.setBackgroundColor(Color.BLUE);
//                infoButton.setBackgroundColor(Color.BLUE);
               testLinear.setVisibility(View.GONE);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, new GameFragment());
                fragmentTransaction.commit();
            }
        });

        talkButton.setOnClickListener(new View.OnClickListener() { //bookButton을 눌렀을 때 bookFragment로 이동하는 이벤트를 구현.
            @Override
            public void onClick(View v) {
//                talkButton.setBackgroundColor(Color.GRAY);
//                bookButton.setBackgroundColor(Color.BLUE);
//                gameButton.setBackgroundColor(Color.BLUE);
//                infoButton.setBackgroundColor(Color.BLUE);
               testLinear.setVisibility(View.GONE);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, new TalkFragment());
                fragmentTransaction.commit();
            }
        });

        infoButton.setOnClickListener(new View.OnClickListener() { //bookButton을 눌렀을 때 bookFragment로 이동하는 이벤트를 구현.
            @Override
            public void onClick(View v) {
//                infoButton.setBackgroundColor(Color.GRAY);
//                bookButton.setBackgroundColor(Color.BLUE);
//                gameButton.setBackgroundColor(Color.BLUE);
//                talkButton.setBackgroundColor(Color.BLUE);
                testLinear.setVisibility(View.GONE);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, new InfoFragment());
                fragmentTransaction.commit();
            }
        });

    }
}
