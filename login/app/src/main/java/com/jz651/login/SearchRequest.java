package com.jz651.login;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xinyuzhou on 3/24/18.
 */

public class SearchRequest extends StringRequest {
    private static final String Search_REQUEST_URL="http://54.245.53.222/temp/v1/searchSession.php";
    private Map<String,String> params;
    public SearchRequest(String subject, Response.Listener<String> listener){
        super(Method.POST,Search_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("subject",subject);
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
