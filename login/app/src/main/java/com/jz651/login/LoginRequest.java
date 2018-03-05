package com.jz651.login;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by QYF on 2018/3/3.
 */

public class LoginRequest extends StringRequest {
    private static final String Login_REQUEST_URL = "http://54.245.53.222/temp/v1/Login.php";
    private Map<String,String> params;
    public LoginRequest(String UserName, String Password, Response.Listener<String> listener){
        super(Method.POST,Login_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("UserName",UserName);
        params.put("Password",Password);
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
