package com.jz651.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FinishedSession extends AppCompatActivity {

    private ArrayList<String> list;
    private TextView subject;
    private TextView time;
    private TextView location;
    private TextView contact;
    private TextView user;
    private Button btnRate;
    private ProgressDialog progressDialog;
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String session_id;
    private Integer temp;
    private String U_id;
    private String UserName;
    private boolean is_rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished_session);

        is_rate = false;
        subject = (TextView) findViewById(R.id.subject);
        time = (TextView) findViewById(R.id.time);
        location = (TextView) findViewById(R.id.location);
        contact = (TextView) findViewById(R.id.contact);
        user = (TextView) findViewById(R.id.user);
        btnRate = (Button) findViewById(R.id.btnRate);
        Intent i = getIntent();
        temp = i.getIntExtra("U_id", 0);
        U_id = Integer.toString(temp);
        UserName = i.getStringExtra("UserName");
        System.out.println("Uid--->>>>>> : " + U_id);
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
        subject.setText(a);
        time.setText(b);
        location.setText(c);
        contact.setText(d);
        user.setText(e);
        progressDialog = new ProgressDialog(this);
        if (!is_rate) {
            btnRate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    is_rate = true;
                    Intent intentRate = new Intent(FinishedSession.this, RateActivity.class);
                    intentRate.putExtra("U_id", temp);
                    intentRate.putExtra("UserName", e);
                    FinishedSession.this.startActivity(intentRate);
                }
            });

        }

    }
}
