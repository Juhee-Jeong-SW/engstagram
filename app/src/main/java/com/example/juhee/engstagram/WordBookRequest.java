package com.example.juhee.engstagram;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class WordBookRequest extends StringRequest {

    final static private String URL = "http://wngml815.cafe24.com/test/wordBookAdd.php";

    private Map<String, String> parameters;

    public WordBookRequest(/*String bookUser*, String bookWord, String bookMean,*/String userID, String boardNum, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);
        parameters = new HashMap<>();

        //parameters.put("bookUser", bookUser);
        //parameters.put("bookWord", bookWord);
        //parameters.put("bookMean", bookMean);
        parameters.put("userID", userID);
        parameters.put("boardNum", boardNum);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
