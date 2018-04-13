package com.jz651.login;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.w3c.dom.Comment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by QYF on 2018/3/24.
 */

public class Feedback extends StringRequest{
    private static final String FEEDBACK_REQUEST_URL = "http://54.245.53.222/temp/v1/feedback.php ";
    private Map<String,String> params;
    public Feedback(String ID, String Name, String Commentee ,String Email, String Comment, String Rate, Response.Listener<String> listener){
        super(Method.POST,FEEDBACK_REQUEST_URL,listener,null);
        System.out.println(Rate);
        params = new HashMap<>();

        params.put("rating",Rate);
        params.put("feedback",Comment);
        params.put("user_id",ID);
        params.put("username",Name);
        params.put("commentee",Commentee);
        params.put("email",Email);
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
