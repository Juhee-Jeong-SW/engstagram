package com.example.juhee.engstagram;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class BoardAddRequest extends StringRequest {

    final static private String URL = "http://wngml815.cafe24.com/test/commentAdd.php";

    private Map<String,String> parameters;

    public BoardAddRequest(/*String boardNum,*/ String boardWord, String boardMean, String boardName, /*String boardDate,*/ String boardLike, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        //parameters.put("boardNum", boardNum);
        parameters.put("boardWord", boardWord);
        parameters.put("boardMean", boardMean);
        parameters.put("boardName", boardName);
        parameters.put("boardLike", boardLike);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}

/*
*
*      $boardNum = $_POST["boardNum"];
     $boardWord = $_POST["boardWord"];
     $boardMean = $_POST["boardMean"];
     $boardName = $_POST["boardName"];
     $boardDate = $_POST["boardDate"];
     $boardLike = $_POST["boardLike"];*/