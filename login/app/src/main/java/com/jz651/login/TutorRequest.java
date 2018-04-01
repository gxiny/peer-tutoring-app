package com.jz651.login;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by QYF on 2018/3/26.
 */

public class TutorRequest extends StringRequest {
    private static final String URL_TUTOR = "http://54.245.53.222/temp/v1/get_tutor_sessions.php" ;
    private Map<String,String> params;

    public TutorRequest(String U_id, Response.Listener<String> listener){
        super(Method.POST,URL_TUTOR, listener,null);
        params = new HashMap<>();
        System.out.println(U_id);
        params.put("user_id",U_id);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
