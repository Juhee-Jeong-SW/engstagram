package com.example.juhee.engstagram;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.List;

public class InfoListAdapter extends BaseAdapter {

    private Context context;

    private List<InfoList> infoList;




    private Fragment parent;

    private AlertDialog dialog;

    Filter listFilter;


    public InfoListAdapter(Context context, List<InfoList> infoList, Fragment parent/*, List<HomeList> displayhomeList*/) {
        this.context = context;
        this.infoList = infoList;
        this.parent = parent;
        //this.displayhomeList = displayhomeList;
    }

    @Override
    public int getCount() {
        return infoList.size(); //*****여기
        //return displayhomeList.size();
    }

    @Override
    public Object getItem(int position) {
        return infoList.get(position); //*****여기
        //return displayhomeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {


        View v = View.inflate(context, R.layout.info_list, null);


        TextView boardNumText = (TextView) v.findViewById(R.id.boardNumText);
        TextView boardWordText = (TextView) v.findViewById(R.id.boardWordText);
        TextView boardMeanText = (TextView) v.findViewById(R.id.boardMeanText);
        TextView boardNameText = (TextView) v.findViewById(R.id.boardNameText);
        TextView boardDateText = (TextView) v.findViewById(R.id.boardDateText);
        TextView boardLikeText = (TextView) v.findViewById(R.id.boardLikeText);


        boardNumText.setText(infoList.get(position).getBoardNum());
        boardWordText.setText(infoList.get(position).getBoardWord());
        boardMeanText.setText(infoList.get(position).getBoardMean());
        boardNameText.setText(infoList.get(position).getBoardName());
        boardDateText.setText(infoList.get(position).getBoardDate());
        boardLikeText.setText(infoList.get(position).getBoardLike());

        v.setTag(infoList.get(position).getBoardNum());


        return v;
    }
}