package com.jz651.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener{


    private EditText editSub,editTime,editLoc,editContact;
    private Button btnAdd;
    private ProgressDialog progressDialog;
    private int U_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Intent intent = getIntent();
        U_id = intent.getIntExtra("user_id",0);

        editSub = (EditText)findViewById(R.id.editSubject);
        editTime= (EditText)findViewById(R.id.editTime);
        editLoc = (EditText)findViewById(R.id.editLocation);
        editContact = (EditText)findViewById(R.id.editContact);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        progressDialog = new ProgressDialog(this);
        btnAdd.setOnClickListener(this);
    }

    private void Add(){
        final String subject = editSub.getText().toString().trim();
        final String time = editTime.getText().toString().trim();
        final String location = editLoc.getText().toString().trim();
        final String contact = editContact.getText().toString().trim();
        progressDialog.setMessage("Creating ... ");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constant.URL_CREATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_id", String.valueOf(U_id));
                params.put("subject",subject);
                params.put("time",time);
                params.put("location",location);
                params.put("contact_info",contact);
                System.out.println("User id passed to session is : " + U_id);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View view) {
        if(view==btnAdd){
            Add();
        }
    }
}
