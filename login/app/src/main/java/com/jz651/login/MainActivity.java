package com.jz651.login;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText etUserName,etPassword;
    private TextView txRegisterLink;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUserName = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText)findViewById(R.id.etPassword);

        btnLogin = (Button) findViewById(R.id.bLogin);

        txRegisterLink = (TextView) findViewById(R.id.tvRegister);

        txRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainActivity.this,RegisterActivity.class);
                MainActivity.this.startActivity(registerIntent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String UserName = etUserName.getText().toString();
                final String Password = etPassword.getText().toString();

                Response.Listener<String> responselistener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("error");



                            if(!success){

                                String UserName = jsonResponse.getString("UserName");
                                String Email = jsonResponse.getString("Email");
                                int U_id = jsonResponse.getInt("user_id");
                                //System.out.println("User id get: "+U_id);


                                Intent intent = new Intent(MainActivity.this,UserSpace.class);

                                intent.putExtra("Email",Email);
                                intent.putExtra("UserName",UserName);
                                intent.putExtra("user_id",U_id);
                                MainActivity.this.startActivity(intent);
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setMessage("Login Failed");
                                builder.setNegativeButton("Retry",null);
                                builder.create();
                                builder.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                LoginRequest loginRequest = new LoginRequest(UserName,Password,responselistener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(loginRequest);
            }
        });
    }



}
