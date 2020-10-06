package com.example.juhee.engstagram;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.w3c.dom.Comment;
import org.w3c.dom.Text;


import java.util.ArrayList;
import java.util.List;

public class HomeListAdapter extends BaseAdapter {

    private Context context;

    private List<HomeList> homeList;

    private List<HomeList> displayhomeList;


    private Fragment parent;

    private AlertDialog dialog;

    Filter listFilter;


    public HomeListAdapter(Context context, List<HomeList> homeList, Fragment parent/*, List<HomeList> displayhomeList*/) {
        this.context = context;
        this.homeList = homeList;
        this.parent = parent;
        //this.displayhomeList = displayhomeList;
    }

    @Override
    public int getCount() {
        return homeList.size(); //*****여기
        //return displayhomeList.size();
    }

    @Override
    public Object getItem(int position) {
        return homeList.get(position); //*****여기
        //return displayhomeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {


        View v = View.inflate(context, R.layout.home_list, null);


        TextView boardNumText = (TextView) v.findViewById(R.id.boardNumText);
        TextView boardWordText = (TextView) v.findViewById(R.id.boardWordText);
        TextView boardMeanText = (TextView) v.findViewById(R.id.boardMeanText);
        TextView boardNameText = (TextView) v.findViewById(R.id.boardNameText);
        TextView boardDateText = (TextView) v.findViewById(R.id.boardDateText);
        TextView boardLikeText = (TextView) v.findViewById(R.id.boardLikeText);


        boardNumText.setText(homeList.get(position).getBoardNum());
        boardWordText.setText(homeList.get(position).getBoardWord());
        boardMeanText.setText(homeList.get(position).getBoardMean());
        boardNameText.setText(homeList.get(position).getBoardName());
        boardDateText.setText(homeList.get(position).getBoardDate());
        boardLikeText.setText(homeList.get(position).getBoardLike());

        v.setTag(homeList.get(position).getBoardNum());

        Button bookAddButton = (Button) v.findViewById(R.id.bookAddButton);
        bookAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = realMainActivity.userID;
                System.out.println(homeList.get(position).getBoardName());
                System.out.println(homeList.get(position).getBoardWord());


//                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
//                dialog = builder.setMessage("Succeed")
//                        .setPositiveButton("OK", null)
//                        .create();
//                dialog.show();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
                                dialog = builder.setMessage("This word is added to your wordbook.")
                                        .setPositiveButton("OK", null)
                                        .create();
                                dialog.show();

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
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

//                WordBookRequest wordBookRequest = new WordBookRequest(homeList.get(position).boardName, homeList.get(position).getBoardWord(), homeList.get(position).getBoardMean(), responseListener);
//                RequestQueue queue = Volley.newRequestQueue(parent.getContext());
//                queue.add(wordBookRequest);
                WordBookRequest wordBookRequest = new WordBookRequest(userID, homeList.get(position).getBoardNum(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(parent.getContext());
                queue.add(wordBookRequest);
            }
        });

        Button commentButton = (Button) v.findViewById(R.id.commentButton);
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = homeList.get(position).getBoardNum();
                //System.out.println(number);

                Intent intent = new Intent(context, CommentActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("boardNum", number);
                context.startActivity(intent);

            }
        });
        return v;
    }

//    @Override
//    public Filter getFilter(){
//        if(listFilter == null)
//        {
//            listFilter = new ListFilter();
//        }
//        return listFilter;
//    }

    //listviewitemList => homeList
    //filteredItemList => displayhomeList
    //ListViewItem => HomeList
//    private class ListFilter extends Filter {
//        FilterResults results = new FilterResults();
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint)
//        {
//            if(constraint == null || constraint.length() ==0)
//            {
//                results.values = homeList;
//                results.count = homeList.size();
//            }
//            else {
//                ArrayList<HomeList> itemList = new ArrayList<>();
//
//                for(HomeList item : homeList){
//                    if(item.getBoardWord().toUpperCase().contains(toString().toString().toUpperCase())){
//                        itemList.add(item);
//                    }
//                }
//
//                results.values = itemList;
//                results.count = itemList.size();
//            }
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results)
//        {
//            displayhomeList = (ArrayList<HomeList>) results.values;
//
//            if(results.count> 0){
//                notifyDataSetChanged();
//            }
//            else {
//                notifyDataSetInvalidated();
//            }
//        }
//
//
//    }


}

//<!--         this.homeList = homeList;
//        this.boardNum = boardNum;
//        this.boardWord = boardWord;
//        this.boardMean = boardMean;
//        this.boardName = boardName;
//        this.boardDate = boardDate;
//        this.boardLike = boardLike;->


//        if(convertView == null){
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.home_list, parent, false);
//        }
//
//        final TextView nameText = (TextView) convertView.findViewById(R.id.nameText);
//        final TextView dateText = (TextView) convertView.findViewById(R.id.dateText);
//
//        HomeList myhomeList = getItem(position);
//        nameText.setText(myhomeList.getName());
//        dateText.setText(homeList.getDate());
//        return convertView;