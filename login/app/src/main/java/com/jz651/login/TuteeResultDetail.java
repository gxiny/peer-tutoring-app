package com.jz651.login;
/**
 * Created by 24565 on 2018/3/24.
 */
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TuteeResultDetail extends AppCompatActivity {
    private ArrayList<String> list;
    private TextView subject;
    private TextView time;
    private TextView location;
    private TextView contact;
    private TextView user;
    private TextView Duration;
    private TextView Is_voluntary;
    private TextView rate;
    private Button btnAppointment;

    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;
    private String session_id;
    private Integer temp;
    private String U_id;
    private String UserName;
    private String is_voluntary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutee_result_detail);

        subject = (TextView) findViewById(R.id.subject);
        time = (TextView) findViewById(R.id.time);
        location = (TextView) findViewById(R.id.location);
        contact = (TextView) findViewById(R.id.contact);
        user = (TextView) findViewById(R.id.user);
        Duration = (TextView)findViewById(R.id.duration);
        Is_voluntary = (TextView)findViewById(R.id.is_voluntary);
        rate = (TextView)findViewById(R.id.rate) ;
        btnAppointment = (Button) findViewById(R.id.btnAppointment);

        Intent i = getIntent();
        temp = i.getIntExtra("U_id",0);
        U_id = Integer.toString(temp);
        UserName = i.getStringExtra("UserName");
        System.out.println("Uid : "+U_id);
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

        h = "Rate : ";
        h += list.get(8).toString();

        subject.setText(a);
        time.setText(b);
        location.setText(c);
        contact.setText(d);
        user.setText(e);
        Duration.setText(f);
        Is_voluntary.setText(g);
        rate.setText(h);

        btnAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responselistener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("error");

                            if (!success) {
                                Intent intent = new Intent(TuteeResultDetail.this, UserSpace.class); //if make an appointment successful, jump back to userspace
                                intent.putExtra("user_id",temp);
                                intent.putExtra("UserName",UserName);
                                TuteeResultDetail.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(TuteeResultDetail.this);
                                builder.setMessage("Failed to make an appointment");
                                builder.setNegativeButton("Retry", null);
                                builder.create();
                                builder.show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                TuteeAppoint appointmentRequest = new TuteeAppoint(session_id,U_id,responselistener);
                RequestQueue queue = Volley.newRequestQueue(TuteeResultDetail.this);
                System.out.println("UserName in detail page = "+UserName);
                queue.add(appointmentRequest);
                //System.out.println("reach here!");
            }
        });


    }
}

