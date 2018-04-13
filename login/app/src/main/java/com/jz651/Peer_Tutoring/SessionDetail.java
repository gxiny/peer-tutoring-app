package com.jz651.Peer_Tutoring;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SessionDetail extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<String> list;
    private TextView subject;
    private TextView time;
    private TextView location;
    private TextView contact;
    private TextView user;
    private TextView Duration;
    private TextView Is_voluntary;
    private Button btnInvite;
    private Button btnFinalize;
    private Button btnCancel;
    private EditText editInvite;
    private ProgressDialog progressDialog;
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;
    private String session_id;
    private Integer temp;
    private String U_id;
    private String UserName;
    private String is_voluntary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_detail);
        subject = (TextView) findViewById(R.id.subject);
        time = (TextView) findViewById(R.id.time);
        location = (TextView) findViewById(R.id.location);
        contact = (TextView) findViewById(R.id.contact);
        user = (TextView) findViewById(R.id.user);
        Duration = (TextView)findViewById(R.id.duration);
        Is_voluntary = (TextView)findViewById(R.id.is_voluntary);
        btnInvite = (Button) findViewById(R.id.btnInvite);
        btnFinalize = (Button)  findViewById(R.id.btnFinalize);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        editInvite = (EditText) findViewById(R.id.editInvite);

        Intent i = getIntent();
        temp = i.getIntExtra("U_id",0);
        U_id = Integer.toString(temp);
        UserName = i.getStringExtra("UserName");
        System.out.println("Uid--->>>>>> : "+U_id);
        list = i.getStringArrayListExtra("list");
        session_id = list.get(0).toString();
        a = "Subject : ";
        a += list.get(1).toString();
        b = "Time : ";
        b += list.get(2).toString();
        c = "Location : ";
        c += list.get(3).toString();
        d = "Contact Information : ";
        d += list.get(4).toString();
        e = "Name : ";
        e += list.get(5).toString();

        f = "Duration : ";
        f += list.get(6);
        g = "Voluntary or Paid : ";
        is_voluntary = list.get(7).toString();
        if(is_voluntary.equals("1")){
            g += "voluntary";
        }
        else if(is_voluntary.equals("0")){
            g += "paid";
        }

        subject.setText(a);
        time.setText(b);
        location.setText(c);
        contact.setText(d);
        user.setText(e);
        Duration.setText(f);
        Is_voluntary.setText(g);
        progressDialog = new ProgressDialog(this);
        btnInvite.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnFinalize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Finalize ... ");
                progressDialog.show();
                Response.Listener<String> finallistener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                };
                FinalRequest finalRequest = new FinalRequest(session_id,finallistener);
                RequestQueue requestQueue = Volley.newRequestQueue(SessionDetail.this);
                requestQueue.add(finalRequest);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responselistener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("error");

                            if(!success){
                                Intent intent = new Intent(SessionDetail.this, UserSpace.class);
                                intent.putExtra("user_id",temp);
                                intent.putExtra("UserName",UserName);
                                //System.out.println("not_yet_ssDetail : user_id :"+temp);
                                System.out.println("not_yet_ssDetail : UserName :"+UserName);
                                SessionDetail.this.startActivity(intent);
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(SessionDetail.this);
                                builder.setMessage("Failed to cancel");
                                builder.setNegativeButton("Retry",null);
                                builder.create();
                                builder.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                CancelRequest cancelRequest = new CancelRequest(U_id,session_id,responselistener);
                RequestQueue queue = Volley.newRequestQueue(SessionDetail.this);
                queue.add(cancelRequest);
            }
        });
    }
    public void Invite(){
        final String guest = editInvite.getText().toString().trim();
        progressDialog.setMessage("Invite ... ");
        progressDialog.show();
        final StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constant.URL_INVITE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                            System.out.println(jsonObject.getString("message"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("invitor_id",String.valueOf(U_id));
                params.put("guest",guest);
                params.put("session_id",session_id.toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    @Override
    public void onClick(View view) {
        if(view==btnInvite){
            Invite();
        }
    }
}
