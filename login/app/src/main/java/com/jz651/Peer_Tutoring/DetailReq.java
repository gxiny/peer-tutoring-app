package com.jz651.Peer_Tutoring;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by delta on 3/28/2018.
 */

public class DetailReq extends StringRequest {
    private static final String DETAIL_URL = "http://54.245.53.222/temp/v1/get_session_detail.php ";
    private Map<String,String> params;

    DetailReq(String ss_id, Response.Listener<String> listener){
        super(Method.POST,DETAIL_URL,listener,null);
        params = new HashMap<>();
        params.put("session_id",ss_id);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
