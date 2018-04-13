package com.jz651.login;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 24565 on 2018/4/12.
 */

public class CancelRequest extends StringRequest {
    private static final String Cancel_Appointment_URL = "http://54.245.53.222/temp/v1/cancel.php";
    private Map<String,String> params;
    public CancelRequest(String user_id, String session_id, Response.Listener<String> listener){
        super(Method.POST,Cancel_Appointment_URL,listener,null);
        params = new HashMap<>();
        params.put("user_id",user_id);
        params.put("session_id",session_id);
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}



