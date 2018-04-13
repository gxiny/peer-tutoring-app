package com.jz651.Peer_Tutoring;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 24565 on 2018/4/13.
 */

public class InviteRequest extends StringRequest {
    private static final String INVITE_URL = "http://54.245.53.222/temp/v1/get_invitation.php";
    private Map<String, String> params;
    public  InviteRequest(String user_id, Response.Listener<String>listener){
        super(Method.POST,INVITE_URL,listener,null);
        params = new HashMap<String, String>();
        params.put("user_id",user_id);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
