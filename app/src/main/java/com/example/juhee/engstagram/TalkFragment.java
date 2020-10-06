package com.example.juhee.engstagram;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TalkFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TalkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TalkFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private String html;
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    private String ip = "127.0.0.0";
    private int port = 9999;

    public TalkFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TalkFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TalkFragment newInstance(String param1, String param2) {
        TalkFragment fragment = new TalkFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_talk, container, false);
        Button btnStartChat = view.findViewById(R.id.btnStartChat);

        btnStartChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TalkActivity.class);

                startActivity(intent);
            }
        });
//        ScrollView sv = (ScrollView) view.findViewById(R.id.scrollView01);
//        sv.fullScroll(View.FOCUS_DOWN); // 자동 스크롤 기능
//
//        final EditText et = (EditText) view.findViewById(R.id.EditText01);
//        Button btn = (Button) view.findViewById(R.id.Button01);
//
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                try{
//                    setSocket(ip, port);
//                }
//                catch(IOException e)
//                {
//                    e.printStackTrace();
//                }
//
//                final TextView tv = (TextView) view.findViewById(R.id.TextView01);
//                final ScrollView sv = (ScrollView) view.findViewById(R.id.scrollView01);
//
//                Log.w("ChattingStart", "Start Thread");
//                tv.append("==채팅시작==\n");
//                while(true)
//                {
//                    try{
//                        html = dis.readUTF();
//                        if(html!=null)
//                        {
//                            getActivity().runOnUiThread(new Runnable(){
//                                @Override
//                                public void run()
//                                {
//                                    tv.append(html+"\n");
//                                    sv.fullScroll(View.FOCUS_DOWN);
//                                }
//                            });
//                        }
//                    }
//                    catch(Exception e)
//                    {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//        }).start();
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(et.getText().toString() != null && !et.getText().toString().equals("")){
//                    String return_msg = et.getText().toString();
//                    et.setText("");
//                    try{
//                        dos.writeUTF(return_msg);
//                    }
//                    catch(Exception e){}
//                }
//            }
//        });


        return view;
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

//    public void setSocket(String ip, int port) throws IOException{
//        try{
//            socket = new Socket(ip, port);
//            dis = new DataInputStream(socket.getInputStream());
//            dos = new DataOutputStream(socket.getOutputStream());
//        }
//        catch (IOException e)
//        {
//            System.out.println(e);
//            e.printStackTrace();
//        }
//    }
}
