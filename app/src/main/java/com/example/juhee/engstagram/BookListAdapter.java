package com.example.juhee.engstagram;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BookListAdapter extends BaseAdapter {


    private Context context;

    private String userID = realMainActivity.userID;

    private List<BookList> mbookList;
   // private ArrayList<BookList> mbookList;
    private BookListAdapter badapter;
    private Fragment parent;

    public BookListAdapter(Context context, List<BookList> mbookList, Fragment parent){
        this.context = context;
        this.mbookList = mbookList;
        this.parent = parent;
       // new BackgroundTaskBook().execute();
    }


    @Override
    public int getCount() {
        return mbookList.size();
    }

    @Override
    public Object getItem(int position) {
        return mbookList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(context, R.layout.book_list, null);

        TextView bookWordText = (TextView) v.findViewById(R.id.bookWordText);
        TextView bookMeanText = (TextView) v.findViewById(R.id.bookMeanText);

        bookWordText.setText(mbookList.get(position).getBookWord());
        bookMeanText.setText(mbookList.get(position).getBookMean());

        v.setTag(mbookList.get(position).getBookWord());
        return v;
    }

//    class BackgroundTaskBook extends AsyncTask<Void, Void, String>
//    {
//
//        String target;
//        //String userID = realMainActivity.userID;
//
//
//        @Override
//        protected void onPreExecute() {
//            try{
//                System.out.println(userID);
//                //target = "http://wngml815.cafe24.com/test/bookList.php?userID=" + URLEncoder.encode(userID, "UTF-8");
//                target = "http://wngml815.cafe24.com/test/bookList.php?userID=wngml815";
//
//            }
//            catch(Exception e){e.printStackTrace();}
//
//
//
//        }
//
//        @Override
//        protected String doInBackground(Void... voids) {
//            try{
//                URL url = new URL(target);
//                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                InputStream inputStream = httpURLConnection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((inputStream)));
//                String temp;
//                StringBuilder stringBuilder = new StringBuilder();
//                while((temp = bufferedReader.readLine()) != null)
//                {
//                    stringBuilder.append(temp+"\n");
//                    //System.out.println(temp);
//                }
//
//                bufferedReader.close();
//                inputStream.close();
//                httpURLConnection.disconnect();
//                return stringBuilder.toString().trim();
//            }
//            catch(Exception e) {e.printStackTrace();}
//            return null;
//        }
//
//        @Override
//        public void onProgressUpdate(Void... values){
//            super.onProgressUpdate();
//        }
//
//        @Override
//        public void onPostExecute(String result) {
//            //ListView homeListView;
//            try{
//                JSONObject jsonObject = new JSONObject(result);
//                JSONArray jsonArray = jsonObject.getJSONArray("response");
//                int count = 0;
//                String bookWord, bookMean;
//
//                while(count < jsonArray.length())
//                {
//                    JSONObject object = jsonArray.getJSONObject(count);
//                    //homeList = object.getString("homeList");
//                    bookWord = object.getString("boardWord");
//                    bookMean = object.getString("boardMean");
//
//
//                    BookList bookList = new BookList(bookWord, bookMean);
//                    //mhomeList = new ArrayList<>();
//                    mbookList.add(bookList);
//                    // System.out.println(mhomeList.toArray());
//                    badapter.notifyDataSetChanged();
//                    count++;
//
//                }
//            }catch(Exception e){
//
//            }
//        }
//    }
}
