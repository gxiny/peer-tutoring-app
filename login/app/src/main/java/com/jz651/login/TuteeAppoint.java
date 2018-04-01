package com.jz651.login;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by delta on 3/31/2018.
 */

public class TuteeAppoint extends StringRequest {
    private static final String Make_Appointment_URL = "http://54.245.53.222/temp/v1/tuteeAppoint.php";
    private Map<String,String> params;
    public TuteeAppoint(String session_id, String tutee_id, Response.Listener<String> listener){
        super(Method.POST,Make_Appointment_URL,listener,null);
        params = new HashMap<>();
        params.put("session_id",session_id);
        params.put("tutee_id",tutee_id);
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
