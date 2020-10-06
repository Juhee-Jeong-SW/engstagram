package com.example.juhee.engstagram;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.EditText;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Comment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity {

    ListView commentListView;
    ArrayList<CommentList> mcommentList;
    CommentListAdapter cAdapter;
    //String boardNum = getIntent().getStringExtra("boardNum");
    String userID = realMainActivity.userID;

    Cursor cursor;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        commentListView = (ListView) findViewById(R.id.commentListView);
        mcommentList = new ArrayList<CommentList>();

        new BackgroundTaskComment().execute();

        cAdapter = new CommentListAdapter(getApplicationContext(), mcommentList, this);

        commentListView.setAdapter(cAdapter);
        final EditText commentEditText = (EditText) findViewById(R.id.commentEditText);
        Button commentButton = (Button) findViewById(R.id.commentButton);
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String context = commentEditText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                mcommentList.clear();
                                new BackgroundTaskComment().execute();
                                cAdapter.notifyDataSetChanged();
                                commentEditText.setText("");

                            } else {
                                //성공 못할 때
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                };

                CommentRequest commentRequest = new CommentRequest(getIntent().getStringExtra("boardNum"), userID, context, responseListener);
                RequestQueue queue = Volley.newRequestQueue(CommentActivity.this);
                queue.add(commentRequest);

            }
        });
    }


    class BackgroundTaskComment extends AsyncTask<Void, Void, String>
    {
        String boardNum = getIntent().getStringExtra("boardNum");

        String target;
        //String userID = realMainActivity.userID;


        @Override
        protected void onPreExecute() {
            try{
                target = "http://wngml815.cafe24.com/test/commentList.php?boardNum=" + URLEncoder.encode(boardNum, "UTF-8");
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

        @Override
        public void onPostExecute(String result) {
            //ListView homeListView;
            try{
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String commentUser, commentDate, commentContext;


                while(count < jsonArray.length())
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    commentUser = object.getString("commentUser");
                    commentDate = object.getString("commentDate");
                    commentContext = object.getString("commentContext");


                    CommentList commentList = new CommentList(commentUser, commentDate, commentContext);
                    //mhomeList = new ArrayList<>();
                    mcommentList.add(commentList);
                    // System.out.println(mhomeList.toArray());
                    cAdapter.notifyDataSetChanged();

                    count++;

                }
            }catch(Exception e){

            }
        }
    }

}
