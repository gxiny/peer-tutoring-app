package com.jz651.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        Button bSubmit = findViewById(R.id.bSubmit);
        final RatingBar rb = findViewById(R.id.ratingBar);
        final EditText etName = findViewById(R.id.Name);
        final EditText etMail = findViewById(R.id.Email);
        final EditText etComment = findViewById(R.id.Comment);

        Intent intent = getIntent();
        final int U_id = intent.getIntExtra("U_id",0);
        final String username = intent.getStringExtra("commentee");
        final String commentor = intent.getStringExtra("commentor");
        final String ID = String.valueOf(U_id);
        System.out.println("User id ----------------------------------------->: "+ID+" Name------------->:"+username);
        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //System.out.println("outside");
                final String Name = etName.getText().toString();
                final String Email = etMail.getText().toString();
                final String Comment = etComment.getText().toString();
                final String Rate = String.valueOf(rb.getRating());
//                final String[] Rate = new String[1];
//                rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//                    @Override
//                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
//                        System.out.println(ratingBar.getRating());
//                        Rate[0] = Float.toString(ratingBar.getRating());
//                    }
//                });

                Response.Listener<String> responselistener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean error = jsonResponse.getBoolean("error");
//                            System.out.println("outside");
//                            System.out.println(Rate);
//                            System.out.println(ID);
//                            System.out.println(Comment);
//                            System.out.println(Email);
//                            System.out.println(Name);

                            if(!error){
                                System.out.println("this is commentor name : "+commentor);

                                Intent intent = new Intent(RateActivity.this, UserSpace.class);
                                intent.putExtra("user_id",U_id);
                                intent.putExtra("UserName",commentor);
                                RateActivity.this.startActivity(intent);
                            }
                            else{
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                System.out.println("this is commentee name : "+username);

                Feedback feedbackRequest = new Feedback(ID,Name,username,Email,Comment, Rate,responselistener);
                RequestQueue queue = Volley.newRequestQueue(RateActivity.this);
                queue.add(feedbackRequest);
            }
        });

    }

}

