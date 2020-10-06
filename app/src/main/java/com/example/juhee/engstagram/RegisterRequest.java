package com.example.juhee.engstagram;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    final static private String URL = "http://wngml815.cafe24.com/test/userRegister.php";

    private Map<String,String> parameters;

    public RegisterRequest(String userID, String userPassword, String userEmail, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("user_id", userID);
        parameters.put("user_password", userPassword);
        parameters.put("user_email", userEmail);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }

}
