package com.jz651.login;

import android.app.Activity;
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
import java.util.List;


public class UserSpace extends AppCompatActivity {
    private TextView txWelMsg;
    private Button btnTutor,btnTutee,btnProfile,btnLogout;
    private String UserName;
    private int U_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_space);


        txWelMsg = (TextView) findViewById(R.id.txWelcomemsg);
        btnTutor = (Button) findViewById(R.id.btnTutor);
        btnTutee = (Button) findViewById(R.id.btnTutee);
        btnProfile = (Button) findViewById(R.id.btnProfile);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        Intent intent = getIntent();
        UserName = intent.getStringExtra("UserName");
        System.out.println("Userspace : UserName :"+UserName);
        U_id = intent.getIntExtra("user_id",0);
        final String User_id = String.valueOf(U_id);
        String WelMsg = "Hi " + UserName + " , Welcome !";

        txWelMsg.setText(WelMsg);


        btnTutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> tutorlistener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            //boolean error = jsonObject.getBoolean("error");

                            JSONArray jsonArray_NY = jsonObject.getJSONArray("notyet");
                            JSONArray jsonArray_TD = jsonObject.getJSONArray("todo");
                            JSONArray jsonArray_FN = jsonObject.getJSONArray("finished");
//                                int Uid = jsonObject.getInt("user_id");
//                                String UserName = jsonObject.getString("UserName");


                            //get back all the session information here!
                            ArrayList<String> NEWSS = new ArrayList<String>();
                            ArrayList<String> TODOSS = new ArrayList<String>();
                            ArrayList<String> FNSS = new ArrayList<String>();

                            for(int i=0;i<jsonArray_NY.length()-1;++i){
                                if(i%2==0) {
                                    String ID_SUB = jsonArray_NY.getString(i+1) + "_" +jsonArray_NY.getString(i);
                                    //System.out.println(ID_SUB);
                                    NEWSS.add(ID_SUB);
                                }
                                else{}
                            }

                            for(int i=0;i<jsonArray_TD.length()-1;++i){
                                if(i%2==0) {
                                    String ID_SUB = jsonArray_TD.getString(i+1) + "_" +jsonArray_TD.getString(i);
                                    TODOSS.add(ID_SUB);                             }
                                else{
                                }
                            }

                            for(int i=0;i<jsonArray_FN.length()-1;++i){
                                if(i%2==0) {
                                    String ID_SUB = jsonArray_FN.getString(i+1) + "_" +jsonArray_FN.getString(i);
                                    FNSS.add(ID_SUB);
                                }
                                else{
                                }
                            }



                            Intent createIntent = new Intent(UserSpace.this,TutorActivity.class);
                            createIntent.putStringArrayListExtra("NEWSS",NEWSS);
                            createIntent.putStringArrayListExtra("TODOSS",TODOSS);
                            createIntent.putStringArrayListExtra("FINISHEDSS",FNSS);

                            createIntent.putExtra("user_id",U_id);
                            createIntent.putExtra("UserName",UserName);
                            UserSpace.this.startActivity(createIntent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                TutorRequest tutorRequest = new TutorRequest(User_id,tutorlistener);

                RequestQueue requestQueue = Volley.newRequestQueue(UserSpace.this);
                requestQueue.add(tutorRequest);

            }
        });

        btnTutee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> tuteelistener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            ArrayList<String> NEWSS = new ArrayList<String>();
                            ArrayList<String> TODOSS = new ArrayList<String>();
                            ArrayList<String> FNSS = new ArrayList<String>();

                            //boolean error = jsonObject.getBoolean("error");

                                // no_sess = "No Session";
                                JSONArray jsonArray_NY = jsonObject.getJSONArray("notyet");
                                String judge_null_ny = jsonArray_NY.getString(0);
//                                System.out.println(judge_null_ny);
//                                 if (judge_null_ny.equals("null")){
//                                     System.out.println("test");
//                                     System.out.println(no_sess);
//                                     NEWSS.add(no_sess);
//                                   }
                                 //  else {
                                     for (int i = 0; i < jsonArray_NY.length() - 1; ++i) {
                                         if (i % 2 == 0) {
                                             String ID_SUB = jsonArray_NY.getString(i + 1) + "_" + jsonArray_NY.getString(i);
                                             NEWSS.add(ID_SUB);
                                         }
                                         else {}
                                     }
                              //   }
                                System.out.println(NEWSS);

                                JSONArray jsonArray_TD = jsonObject.getJSONArray("todo");
                                String judge_null_td = jsonArray_TD.getString(0);
//                                if (judge_null_td.equals("null")) {
//
//                                     TODOSS.add(no_sess);
//                                }
                               // else {
                                     for (int i = 0; i < jsonArray_TD.length() - 1; ++i) {
                                         if (i % 2 == 0) {
                                             String judge_null = jsonArray_TD.getString(i);
                                             String ID_SUB = jsonArray_TD.getString(i + 1) + "_" + jsonArray_TD.getString(i);
                                             TODOSS.add(ID_SUB);
                                         }
                                         else {}
                                     }
                               //  }

                                JSONArray jsonArray_FN = jsonObject.getJSONArray("finished");
                                String judge_null_fn = jsonArray_FN.getString(0);
//                                if (judge_null_fn.equals("null")) {
//
//                                     FNSS.add(no_sess);
//                                }
                               // else {
                                    for (int i = 0; i < jsonArray_FN.length() - 1; ++i) {
                                        if (i % 2 == 0) {
                                            String judge_null = jsonArray_FN.getString(i);
                                            String ID_SUB = jsonArray_FN.getString(i + 1) + "_" + jsonArray_FN.getString(i);
                                            FNSS.add(ID_SUB);
                                        }
                                        else {}
                                    }
                               //}
//                                int Uid = jsonObject.getInt("user_id");
//                                String UserName = jsonObject.getString("UserName");


                            //get back all the session information here!
                            Intent createIntent = new Intent(UserSpace.this,TuteeActivity.class);
                            createIntent.putStringArrayListExtra("NEWSS",NEWSS);
                            createIntent.putStringArrayListExtra("TODOSS",TODOSS);
                            createIntent.putStringArrayListExtra("FINISHEDSS",FNSS);

                            createIntent.putExtra("user_id",U_id);
                            createIntent.putExtra("UserName",UserName);
                            UserSpace.this.startActivity(createIntent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                TuteeRequest tuteeRequest = new TuteeRequest(User_id,tuteelistener);

                RequestQueue requestQueue = Volley.newRequestQueue(UserSpace.this);
                requestQueue.add(tuteeRequest);
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createIntent = new Intent(UserSpace.this,ProfileActivity.class);
                createIntent.putExtra("user_id",U_id);
                UserSpace.this.startActivity(createIntent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logoutIntent = new Intent(UserSpace.this,MainActivity.class);
                UserSpace.this.startActivity(logoutIntent);
            }
        });
    }
}
