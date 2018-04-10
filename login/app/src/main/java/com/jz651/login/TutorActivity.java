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

public class TutorActivity extends AppCompatActivity {
    private TextView txWelMsg;
    private ListView NewSS, AppointedSS, FinishedSS;
    private Button btnCreateSS,btnSearchRQ;
    private String UserName;
    private int U_id;
    private ArrayList<String> NEWLIST,TODOLIST,FINISHEDLIST;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor);

        btnCreateSS = findViewById(R.id.btnCreateSS);
        btnSearchRQ = findViewById(R.id.btnSearchRQ);
        txWelMsg = findViewById(R.id.txWelcomemsg);
        NewSS = findViewById(R.id.NewSS);
        AppointedSS = findViewById(R.id.AppointedSS);
        FinishedSS = findViewById(R.id.FinishedSS);

        final Intent intent = getIntent();
        U_id = intent.getIntExtra("user_id",0);
        final String User_id = String.valueOf(U_id);
        UserName = intent.getStringExtra("UserName");

        String welmsg = "Hi " +UserName+" welcome to your tutor page";
        txWelMsg.setText(welmsg);

        NEWLIST = intent.getStringArrayListExtra("NEWSS");
        TODOLIST = intent.getStringArrayListExtra("TODOSS");
        FINISHEDLIST = intent.getStringArrayListExtra("FINISHEDSS");

        ListAdapter NewSS_adapt = new ArrayAdapter<>(TutorActivity.this,android.R.layout.simple_list_item_1,NEWLIST);
        ListAdapter TodoSS_adapt = new ArrayAdapter<>(TutorActivity.this,android.R.layout.simple_list_item_1,TODOLIST);
        ListAdapter FinishedSS_adapt = new ArrayAdapter<>(TutorActivity.this,android.R.layout.simple_list_item_1,FINISHEDLIST);

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
                            Double temp_duration = jsonObject.getDouble("duration");
                            Integer temp_is_volutary = jsonObject.getInt("IS_VOLUNTARY");
                            String duration = temp_duration.toString();
                            //System.out.println("duration");
                            //System.out.println(duration);
                            String is_voluntary = temp_is_volutary.toString();


                            Intent intentDetail = new Intent(TutorActivity.this,NotYetSSDetail.class);
                            ArrayList<String> list = new ArrayList<>();
                            list.add(ss_id);
                            list.add(subject);
                            list.add(Time);
                            list.add(Location);
                            list.add(Contact);
                            list.add(UserName);
                            list.add(duration);
                            list.add(is_voluntary);
                            intentDetail.putStringArrayListExtra("list",list);
                            intentDetail.putExtra("U_id",U_id);
                            TutorActivity.this.startActivity(intentDetail);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                DetailReq detailReq = new DetailReq(ss_id,detaillistener);
                RequestQueue requestQueue = Volley.newRequestQueue(TutorActivity.this);
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
                            Double temp_duration = jsonObject.getDouble("duration");
                            Integer temp_is_volutary = jsonObject.getInt("IS_VOLUNTARY");
                            String duration = temp_duration.toString();
                            String is_voluntary = temp_is_volutary.toString();


                            Intent intentDetail = new Intent(TutorActivity.this,SessionDetail.class);
                            ArrayList<String> list = new ArrayList<>();
                            list.add(ss_id);
                            list.add(subject);
                            list.add(Time);
                            list.add(Location);
                            list.add(Contact);
                            list.add(UserName);
                            list.add(duration);
                            list.add(is_voluntary);
                            intentDetail.putStringArrayListExtra("list",list);
                            intentDetail.putExtra("U_id",U_id);
                            TutorActivity.this.startActivity(intentDetail);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                DetailReq detailReq = new DetailReq(ss_id,detaillistener);
                RequestQueue requestQueue = Volley.newRequestQueue(TutorActivity.this);
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
                            Double temp_duration = jsonObject.getDouble("duration");
                            Integer temp_is_volutary = jsonObject.getInt("IS_VOLUNTARY");
                            String duration = temp_duration.toString();
                            String is_voluntary = temp_is_volutary.toString();

                            Intent intentDetail = new Intent(TutorActivity.this,FinishedSession.class);
                            ArrayList<String> list = new ArrayList<>();
                            list.add(ss_id);
                            list.add(subject);
                            list.add(Time);
                            list.add(Location);
                            list.add(Contact);
                            list.add(UserName);
                            list.add(duration);
                            list.add(is_voluntary);
                            intentDetail.putStringArrayListExtra("list",list);
                            intentDetail.putExtra("U_id",U_id);
                            TutorActivity.this.startActivity(intentDetail);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                DetailReq detailReq = new DetailReq(ss_id,detaillistener);
                RequestQueue requestQueue = Volley.newRequestQueue(TutorActivity.this);
                requestQueue.add(detailReq);
            }
        });


        btnCreateSS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCreate = new Intent(TutorActivity.this,CreateActivity.class);
                intentCreate.putExtra("user_id",U_id);
                intentCreate.putExtra("UserName",UserName);
                TutorActivity.this.startActivity(intentCreate);
            }
        });

        btnSearchRQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSearch = new Intent(TutorActivity.this,TutorSearch.class);
                intentSearch.putExtra("user_id",U_id);
                intentSearch.putExtra("UserName",UserName);
                TutorActivity.this.startActivity(intentSearch);
            }
        });
    }
}
