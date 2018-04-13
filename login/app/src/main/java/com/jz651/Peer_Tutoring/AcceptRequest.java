package com.jz651.Peer_Tutoring;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 24565 on 2018/4/13.
 */

public class AcceptRequest extends StringRequest {

    private static final String ACCEPT_URL = "http://54.245.53.222/temp/v1/acceptInvitation.php ";
    private Map<String,String> params;

    public AcceptRequest(String User_id, String Session_id, Response.Listener<String>listener){
        super(Method.POST,ACCEPT_URL,listener,null);
        params = new HashMap<>();
        params.put("user_id",User_id);
        params.put("session_id",Session_id);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
