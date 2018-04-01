package com.jz651.login;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by delta on 3/31/2018.
 */

public class FinalRequest extends StringRequest {
    private final static  String FINAL_URL = "http://54.245.53.222/temp/v1/finalize.php ";
    private Map<String,String> params;

    public FinalRequest(String session_id, Response.Listener<String> listener){
        super(Method.POST,FINAL_URL,listener,null);

        params = new HashMap<>();
        params.put("session_id",session_id);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
