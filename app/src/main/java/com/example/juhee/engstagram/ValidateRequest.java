package com.example.juhee.engstagram;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ValidateRequest extends StringRequest {
    final static private String URL = "http://wngml815.cafe24.com/test/userValidate.php";

    private Map<String,String> parameters;

    public ValidateRequest(String userID,  Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("user_id", userID);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }

}
