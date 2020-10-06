package com.example.juhee.engstagram;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ListView homeListView;
    private EditText searchText;

    private ArrayList<HomeList> displayhomeList; //회원검색 기능용 복사본
    private ArrayList<HomeList> mhomeList;
    private HomeListAdapter adapter;

    private AlertDialog dialog;
    private Button bookAddButton;

    public static String sortFlag ="";



    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private ArrayAdapter sortAdapter;
    private Spinner sortSpinner;
    //private EditText et_searchText;




    // 게시글 정렬을 위한 스피너 객체 및 어댑터 설정
    @Override
    public void onActivityCreated(Bundle b) {

        super.onActivityCreated(b);

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public void filter(String searchText) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        homeListView = (ListView) view.findViewById(R.id.homeListView);
        mhomeList = new ArrayList<HomeList>();
        //displayhomeList = mhomeList; // 회원검색 기능용
        searchText = (EditText) view.findViewById(R.id.searchText);

        sortSpinner = (Spinner) view.findViewById(R.id.spinner_sort);
        sortAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.sort, R.layout.support_simple_spinner_dropdown_item);
        sortSpinner.setAdapter(sortAdapter);

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(sortSpinner.getSelectedItem().equals("Latest"))
                {
                    mhomeList.clear();
                    new BackgroundTask().execute();
                    adapter = new HomeListAdapter(getActivity(), mhomeList, HomeFragment.this);
                    homeListView.setAdapter(adapter);

                    System.out.println(sortSpinner.getSelectedItem().toString());
                }
                else if(sortSpinner.getSelectedItem().equals("Alphabetically"))
                {

                    mhomeList.clear();
                    new BackgroundTask().execute();
                    adapter = new HomeListAdapter(getActivity(), mhomeList, HomeFragment.this);
                    homeListView.setAdapter(adapter);

                }
                else if(sortSpinner.getSelectedItem().equals("Like"))
                {
                    mhomeList.clear();
                    new BackgroundTask().execute();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        Button commentButton = (Button) view.findViewById(R.id.commentButton);
//        commentButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //String number = homeList.get(position).getBoardNum();
//                //System.out.println(number);
//
//                Intent intent = new Intent(getActivity(), CommentActivity.class);
//
//                startActivity(intent);
//            }
//        });

       //mhomeList.add(new HomeList("1", "stella", "스텔라", "정주희", "2017-19-11","1"));
       // mhomeList.add(new HomeList("1", "stella", "스텔라", "정주희", "2017-19-11","1"));
        //new BackgroundTask().execute(); //***********여기!!
        //adapter = new HomeListAdapter(getActivity(), mhomeList);

        ////있어야함////
//        adapter = new HomeListAdapter(getActivity(), mhomeList, this);
//
//        homeListView.setAdapter(adapter);

        //return inflater.inflate(R.layout.fragment_home, container, false);

        //et_searchText = (EditText) view.findViewById(R.id.searchText);

//        et_searchText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                String searchText = et_searchText.getText().toString();
//
//                //mhomeList.clear();
//                if(searchText.length() == 0)
//                {
//                    displayhomeList.addAll(mhomeList);
//                }
//                else
//                {
//                    for(HomeList item : mhomeList){
//                        if(item.getBoardWord().contains(searchText))
//                        {
//                            displayhomeList.add(item);
//                        }
//
//                    }
//                }
//                adapter.notifyDataSetChanged();
//                //adapter.filter(searchText);
//
//            }
//
//
//        });

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String filterText = s.toString();

                if(filterText.length() > 0){
                    homeListView.setFilterText(filterText);
                }else
                {
                    homeListView.clearTextFilter();
                }
            }
        });
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

    class BackgroundTask extends AsyncTask<Void, Void, String>
    {

        String target;
        //String sortFlag = HomeFragment.sortFlag;

        @Override
        protected void onPreExecute() {
            try{
                target = "http://wngml815.cafe24.com/test/boardList.php?sortFlag=" + URLEncoder.encode(sortSpinner.getSelectedItem().toString(), "UTF-8");
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
                String boardNum, boardWord, boardMean, boardName, boardDate, boardLike;

                while(count < jsonArray.length())
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    //homeList = object.getString("homeList");
                    boardNum = object.getString("boardNum");
                    boardWord = object.getString("boardWord");
                    boardMean = object.getString("boardMean");
                    boardName = object.getString("boardName");
                    boardDate = object.getString("boardDate");
                    boardLike = object.getString("boardLike");

                    HomeList homeList = new HomeList(boardNum, boardWord, boardMean, boardName, boardDate, boardLike);
                    //mhomeList = new ArrayList<>();
                    mhomeList.add(homeList);
                    //displayhomeList.add(homeList); //**수정**
                   // System.out.println(mhomeList.toArray());
                    adapter.notifyDataSetChanged();
                    count++;

                }
            }catch(Exception e){

            }
        }
    }


}
