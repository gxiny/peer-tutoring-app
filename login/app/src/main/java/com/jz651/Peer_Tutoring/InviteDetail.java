package com.jz651.Peer_Tutoring;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class InviteDetail extends AppCompatActivity {
    private ArrayList<String> list;
    private TextView subject;
    private TextView time;
    private TextView location;
    private TextView contact;
    private TextView user;
    private TextView Duration;
    private TextView Is_voluntary;
    private Button btnAccept;
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
        setContentView(R.layout.activity_invite_detail);

        subject = (TextView) findViewById(R.id.subject);
        time = (TextView) findViewById(R.id.time);
        location = (TextView) findViewById(R.id.location);
        contact = (TextView) findViewById(R.id.contact);
        user = (TextView) findViewById(R.id.user);
        btnAccept = (Button) findViewById(R.id.btnAccept);

        Duration = (TextView)findViewById(R.id.duration);
        Is_voluntary = (TextView)findViewById(R.id.is_voluntary);

        editInvite = (EditText) findViewById(R.id.editInvite);
        final Intent i = getIntent();
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
        progressDialog = new ProgressDialog(this);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> acceptlistener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent intent = new Intent(InviteDetail.this,UserSpace.class);
                        intent.putExtra("UserName",UserName);
                        intent.putExtra("user_id",temp);
                        InviteDetail.this.startActivity(intent);
                    }
                };
                AcceptRequest acceptRequest = new AcceptRequest(U_id,session_id,acceptlistener);
                RequestQueue requestQueue = Volley.newRequestQueue(InviteDetail.this);
                requestQueue.add(acceptRequest);
            }
        }) ;

        Duration.setText(f);
        Is_voluntary.setText(g);
    }
}



