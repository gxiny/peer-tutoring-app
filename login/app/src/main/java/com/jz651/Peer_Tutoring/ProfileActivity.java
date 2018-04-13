package com.jz651.Peer_Tutoring;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        final TextView username = findViewById(R.id.tvUserName);
        final TextView myname = findViewById(R.id.tvMyName);
        //final TextView email = findViewById(R.id.tvEmail);
        //final TextView myemail = findViewById(R.id.tvMyemail);
        final Button btnInvite = findViewById(R.id.btnInvite);
        final TextView balance = findViewById(R.id.tvbalance);
        final TextView mybalance = findViewById(R.id.tvMybalance);

        Intent i = getIntent();
        final String name = i.getStringExtra("username");
        final String e = i.getStringExtra("email");
        final int user_id = i.getIntExtra("user_id",0);
        final String ID = String.valueOf(user_id);
        final int b = i.getIntExtra("balance",0);
        final String str_b = String.valueOf(b);

        username.setText("UserName:");
        //myemail.setText(e);
        myname.setText(name);
        //email.setText("Email:");
        balance.setText("Balance:");
        mybalance.setText(str_b);

        btnInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> invitelistener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray jsonArray_invited = jsonObject.getJSONArray("result");

                            ArrayList<String> INVITEEDSS = new ArrayList<String>();
                            System.out.println(jsonArray_invited.length()-1);
                            for(int i=0;i<jsonArray_invited.length()-1;++i) {
                                if (i % 2 == 0) {
                                    String ID_SUB = jsonArray_invited.getString(i + 1) + "_" + jsonArray_invited.getString(i);
                                    //System.out.println(ID_SUB);
                                    INVITEEDSS.add(ID_SUB);
                                } else {
                                }
                            }

                            Intent invitedIntent = new Intent(ProfileActivity.this,InviteActivity.class);
                            invitedIntent.putStringArrayListExtra("invite",INVITEEDSS);
                            invitedIntent.putExtra("user_id",user_id);
                            invitedIntent.putExtra("username",name);
                            ProfileActivity.this.startActivity(invitedIntent);

                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                };

                InviteRequest inviteRequest = new InviteRequest(ID,invitelistener);
                RequestQueue requestQueue = Volley.newRequestQueue(ProfileActivity.this);
                requestQueue.add(inviteRequest);
            }
        });
    }
}

