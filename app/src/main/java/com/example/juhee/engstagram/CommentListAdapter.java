package com.example.juhee.engstagram;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CommentListAdapter extends BaseAdapter {


    private Context context;

    private List<CommentList> mcommentList;
    private CommentListAdapter cadapter;
    private Activity parent;

    public CommentListAdapter(Context context, List<CommentList> mcommentList, Activity parent){
        this.context = context;
        this.mcommentList = mcommentList;
        this.parent = parent;
        // new BackgroundTaskBook().execute();
    }


    @Override
    public int getCount() {
        return mcommentList.size();
    }

    @Override
    public Object getItem(int position) {
        return mcommentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(context, R.layout.comment_list, null);
        TextView dateText = (TextView) v.findViewById(R.id.dateText);
        TextView commentText = (TextView) v.findViewById(R.id.commentText);
        TextView commentNameText = (TextView) v.findViewById(R.id.commentNameText);


        dateText.setText(mcommentList.get(position).getCommentDate());
        commentText.setText(mcommentList.get(position).getCommentContext());
        commentNameText.setText(mcommentList.get(position).getCommentUser());

        return v;
    }

}
