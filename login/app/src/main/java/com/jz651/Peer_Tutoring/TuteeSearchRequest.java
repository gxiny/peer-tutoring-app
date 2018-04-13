package com.jz651.Peer_Tutoring;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by delta on 3/31/2018.
 */

public class TuteeSearchRequest extends StringRequest {
    private static final String Search_REQUEST_URL="http://54.245.53.222/temp/v1/tuteeSearch.php";
    private Map<String,String> params;
    public TuteeSearchRequest(String subject, Response.Listener<String> listener){
        super(Method.POST,Search_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("subject",subject);
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
