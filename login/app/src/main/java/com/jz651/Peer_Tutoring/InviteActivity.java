package com.jz651.Peer_Tutoring;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InviteActivity extends AppCompatActivity {
    private ListView InviteSS;
    private TextView tvInvite;
    private int user_id;
    private String username;
    private ArrayList<String> InviteList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        InviteSS = findViewById(R.id.InviteSS);
        tvInvite = findViewById(R.id.tvInvite);
        tvInvite.setText("Invite Subject List");
        Intent i = getIntent();
        user_id = i.getIntExtra("user_id",0);
        username = i.getStringExtra("username");
        InviteList = i.getStringArrayListExtra("invite");
        System.out.println(username);

        ListAdapter InviteSS_adapt = new ArrayAdapter<>(InviteActivity.this,android.R.layout.simple_list_item_1,InviteList);
        InviteSS.setAdapter(InviteSS_adapt);

        InviteSS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String ID_SUB = (String) adapterView.getItemAtPosition(i);
                int pos = ID_SUB.indexOf("_");
                final String ss_id = ID_SUB.substring(0,pos);
                final String subject = ID_SUB.substring(pos+1);

                Response.Listener<String> Idetaillistener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String Location = jsonObject.getString("location");
                            String Time = jsonObject.getString("time");
                            String Contact = jsonObject.getString("contact_info");
                            String UserName = jsonObject.getString("username");
                            Double temp_duration = jsonObject.getDouble("duration");
                            Integer temp_is_volutary = jsonObject.getInt("IS_VOLUNTARY");
                            String duration = temp_duration.toString();
                            String is_voluntary = temp_is_volutary.toString();

                            Intent intentDetail = new Intent(InviteActivity.this,InviteDetail.class);
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
                            intentDetail.putExtra("U_id",user_id);
                            intentDetail.putExtra("UserName",username);
                            InviteActivity.this.startActivity(intentDetail);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                DetailReq detailReq = new DetailReq(ss_id,Idetaillistener);
                RequestQueue requestQueue = Volley.newRequestQueue(InviteActivity.this);
                requestQueue.add(detailReq);
            }
        });
    }
}
