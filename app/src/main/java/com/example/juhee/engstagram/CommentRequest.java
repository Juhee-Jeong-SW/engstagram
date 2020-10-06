package com.example.juhee.engstagram;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class CommentRequest extends StringRequest {
    private Map<String, String> parameters;
    final static private String URL = "http://wngml815.cafe24.com/test/commentAdd.php";

    public CommentRequest(/*String bookUser*, String bookWord, String bookMean,*/String boardNum, String commentUser, String commentContext, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);
        parameters = new HashMap<>();

        //parameters.put("bookUser", bookUser);
        //parameters.put("bookWord", bookWord);
        //parameters.put("bookMean", bookMean);

        parameters.put("boardNum", boardNum);
        parameters.put("commentUser", commentUser);
        parameters.put("commentContext", commentContext);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
