package com.jz651.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TuteeActivity extends AppCompatActivity {
    private TextView txWelMsg;
    private ListView NewSS, AppointedSS, FinishedSS;
    private Button btnCreateRQ,btnSearchSS;
    private String UserName;
    private int U_id;
    private ArrayList<String> NEWLIST,TODOLIST,FINISHEDLIST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutee);

        btnCreateRQ = findViewById(R.id.btnCreateRQ);
        btnSearchSS = findViewById(R.id.btnSearchSS);
        txWelMsg = findViewById(R.id.txWelcomemsg);
        NewSS = findViewById(R.id.NewSS);
        AppointedSS = findViewById(R.id.AppointedSS);
        FinishedSS = findViewById(R.id.FinishedSS);

        final Intent intent = getIntent();
        U_id = intent.getIntExtra("user_id",0);
        final String User_id = String.valueOf(U_id);
        UserName = intent.getStringExtra("UserName");

        String welmsg = "Hi, No." + U_id + ","+UserName+" welcome to your tutee page";
        txWelMsg.setText(welmsg);

        NEWLIST = intent.getStringArrayListExtra("NEWSS");
        TODOLIST = intent.getStringArrayListExtra("TODOSS");
        FINISHEDLIST = intent.getStringArrayListExtra("FINISHEDSS");

        ListAdapter NewSS_adapt = new ArrayAdapter<>(TuteeActivity.this,android.R.layout.simple_list_item_1,NEWLIST);
        ListAdapter TodoSS_adapt = new ArrayAdapter<>(TuteeActivity.this,android.R.layout.simple_list_item_1,TODOLIST);
        ListAdapter FinishedSS_adapt = new ArrayAdapter<>(TuteeActivity.this,android.R.layout.simple_list_item_1,FINISHEDLIST);

        NewSS.setAdapter(NewSS_adapt);
        AppointedSS.setAdapter(TodoSS_adapt);
        FinishedSS.setAdapter(FinishedSS_adapt);

        NewSS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String id_sub = (String) parent.getItemAtPosition(position);
                int pos = id_sub.indexOf("_");
                final String ss_id = id_sub.substring(0,pos);
                final String subject = id_sub.substring(pos+1);

                Response.Listener<String> detaillistener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println(ss_id);
                            JSONObject jsonObject = new JSONObject(response);
                            String Location = jsonObject.getString("location");
                            String Time = jsonObject.getString("time");
                            String Contact = jsonObject.getString("contact_info");
                            String UserName = jsonObject.getString("username");

                            Intent intentDetail = new Intent(TuteeActivity.this,NotYetSSDetail.class);
                            ArrayList<String> list = new ArrayList<>();
                            list.add(ss_id);
                            list.add(subject);
                            list.add(Time);
                            list.add(Location);
                            list.add(Contact);
                            list.add(UserName);
                            intentDetail.putStringArrayListExtra("list",list);
                            intentDetail.putExtra("U_id",U_id);
                            TuteeActivity.this.startActivity(intentDetail);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                DetailReq detailReq = new DetailReq(ss_id,detaillistener);
                RequestQueue requestQueue = Volley.newRequestQueue(TuteeActivity.this);
                requestQueue.add(detailReq);
            }
        });

        AppointedSS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String id_sub = (String) parent.getItemAtPosition(position);
                int pos = id_sub.indexOf("_");
                final String ss_id = id_sub.substring(0,pos);
                final String subject = id_sub.substring(pos+1);

                Response.Listener<String> detaillistener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println(ss_id);
                            JSONObject jsonObject = new JSONObject(response);
                            String Location = jsonObject.getString("location");
                            String Time = jsonObject.getString("time");
                            String Contact = jsonObject.getString("contact_info");
                            String UserName = jsonObject.getString("username");

                            Intent intentDetail = new Intent(TuteeActivity.this,SessionDetail.class);
                            ArrayList<String> list = new ArrayList<>();
                            list.add(ss_id);
                            list.add(subject);
                            list.add(Time);
                            list.add(Location);
                            list.add(Contact);
                            list.add(UserName);
                            intentDetail.putStringArrayListExtra("list",list);
                            intentDetail.putExtra("U_id",U_id);
                            TuteeActivity.this.startActivity(intentDetail);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                DetailReq detailReq = new DetailReq(ss_id,detaillistener);
                RequestQueue requestQueue = Volley.newRequestQueue(TuteeActivity.this);
                requestQueue.add(detailReq);
            }
        });

        FinishedSS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String id_sub = (String) parent.getItemAtPosition(position);
                int pos = id_sub.indexOf("_");
                final String ss_id = id_sub.substring(0,pos);
                final String subject = id_sub.substring(pos+1);

                Response.Listener<String> detaillistener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println(ss_id);
                            JSONObject jsonObject = new JSONObject(response);
                            String Location = jsonObject.getString("location");
                            String Time = jsonObject.getString("time");
                            String Contact = jsonObject.getString("contact_info");
                            String UserName = jsonObject.getString("username");

                            Intent intentDetail = new Intent(TuteeActivity.this,FinishedSession.class);
                            ArrayList<String> list = new ArrayList<>();
                            list.add(ss_id);
                            list.add(subject);
                            list.add(Time);
                            list.add(Location);
                            list.add(Contact);
                            list.add(UserName);
                            intentDetail.putStringArrayListExtra("list",list);
                            intentDetail.putExtra("U_id",U_id);
                            TuteeActivity.this.startActivity(intentDetail);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                DetailReq detailReq = new DetailReq(ss_id,detaillistener);
                RequestQueue requestQueue = Volley.newRequestQueue(TuteeActivity.this);
                requestQueue.add(detailReq);
            }
        });


        btnCreateRQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCreate = new Intent(TuteeActivity.this,CreateActivity.class);
                intentCreate.putExtra("user_id",U_id);
                intentCreate.putExtra("UserName",UserName);
                TuteeActivity.this.startActivity(intentCreate);
            }
        });

        btnSearchSS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSearch = new Intent(TuteeActivity.this,TuteeSearch.class);
                intentSearch.putExtra("user_id",U_id);
                intentSearch.putExtra("UserName",UserName);
                TuteeActivity.this.startActivity(intentSearch);
            }
        });
    }
}
