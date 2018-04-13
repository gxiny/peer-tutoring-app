package com.jz651.Peer_Tutoring;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 24565 on 2018/3/27.
 */

public class AppointmentRequest extends StringRequest{
    private static final String Make_Appointment_URL = "http://54.245.53.222/temp/v1/appointment.php";
    private Map<String,String> params;
    public AppointmentRequest(String session_id, String tutor_id, Response.Listener<String> listener){
        super(Method.POST,Make_Appointment_URL,listener,null);
        params = new HashMap<>();
        params.put("session_id",session_id);
        params.put("tutor_id",tutor_id);
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

