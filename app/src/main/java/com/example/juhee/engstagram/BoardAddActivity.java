package com.example.juhee.engstagram;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Date;

public class BoardAddActivity extends AppCompatActivity {

    public static String userID;

    private String boardWord;
    private String boardMean;
    private String boardDate;
    private String boardNum;
    private String boardLike;
    private boolean validate = false;


    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boardadd);
        userID = getIntent().getStringExtra("user_id");
        System.out.println(userID);

        final EditText englishText = (EditText) findViewById(R.id.englishText);
        final EditText koreanText = (EditText) findViewById(R.id.koreanText);

        final Button addButton = (Button) findViewById(R.id.addButton);
        final Button addCheckButton = (Button) findViewById(R.id.addCheckButton);

        addCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BoardAddActivity.this);
                dialog = builder.setMessage("This word can be added.")
                        .setPositiveButton("OK", null)
                        .create();
                dialog.show();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String boardNum = "9";
                String boardWord = englishText.getText().toString();
                String boardMean = koreanText.getText().toString();
                //String boardDate = "2018-11-18";
                String boardName = userID;
                String boardLike = "0";


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            System.out.println("logcheck");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(BoardAddActivity.this);
                                dialog = builder.setMessage("Succeed")
                                        .setPositiveButton("OK", null)
                                        .create();
                                dialog.show();

                                //finish();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(BoardAddActivity.this);
                                dialog = builder.setMessage("Fail")
                                        .setNegativeButton("OK", null)
                                        .create();
                                dialog.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }


                };
                BoardAddRequest boardAddRequest = new BoardAddRequest(/*boardNum, */boardWord, boardMean,boardName, /*boardDate,*/ boardLike, responseListener);
                RequestQueue queue = Volley.newRequestQueue(BoardAddActivity.this);
                queue.add(boardAddRequest);


            }
        });

        addCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String english = englishText.getText().toString();
                if (validate) {
                    return;
                }
                if (english.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(BoardAddActivity.this);
                    dialog = builder.setMessage("Word cannot be EMPTY!!")
                            .setPositiveButton("OK", null)
                            .create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(BoardAddActivity.this);
                                dialog = builder.setMessage("The word is available")
                                        .setPositiveButton("OK", null)
                                        .create();
                                dialog.show();
                                englishText.setEnabled(false);
                                validate = true;
                                englishText.setBackgroundColor(Color.GRAY);
                                addCheckButton.setBackgroundColor(Color.GRAY);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(BoardAddActivity.this);
                                dialog = builder.setMessage("The word cannot be used!!")
                                        .setNegativeButton("OK", null)
                                        .create();
                                dialog.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }


                };
                WordValidateRequest wordvalidateRequest = new WordValidateRequest(english, responseListener);
                //System.out.println(validateRequest);
                RequestQueue queue = Volley.newRequestQueue(BoardAddActivity.this);
                queue.add(wordvalidateRequest);
            }
        });


    }

    @Override
    protected void onStop(){
        super.onStop();
        if(dialog != null){
            dialog.dismiss();
            dialog = null;
        }
    }
}

/*
*      $boardNum = $_POST["boardNum"];
     $boardWord = $_POST["boardWord"];
     $boardMean = $_POST["boardMean"];
     $boardName = $_POST["boardName"];
     $boardDate = $_POST["boardDate"];
     $boardLike = $_POST["boardLike"];*/
