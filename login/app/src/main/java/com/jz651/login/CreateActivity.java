package com.jz651.login;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editSub,editTime,editLoc,editContact,editInvite,editDuration;
    private Button btnAdd,btnDate,btnTime,btnInvite;
    private RadioGroup rgVoluntary;
    private RadioButton rbChoice;
    private ProgressDialog progressDialog;
    int hour_x,minute_x;
    int year_x,month_x,day_x;
    StringBuffer dateBuffer=new StringBuffer();
    StringBuffer timeBuffer=new StringBuffer();
    private int U_id;
    private StringBuffer session_id=new StringBuffer();
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
        editInvite = (EditText)findViewById(R.id.editInvite);
        editDuration=(EditText)findViewById(R.id.editDuration);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnDate = (Button)findViewById(R.id.btnDate);
        btnTime = (Button)findViewById(R.id.btnTime);
        btnInvite = (Button)findViewById(R.id.btnInvite);
        rgVoluntary=(RadioGroup)findViewById(R.id.rgVoluntary);
        progressDialog = new ProgressDialog(this);
        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePic();
            }
        });
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePic();
            }
        });
        //editTime.setText(dateBuffer.toString()+" "+timeBuffer.toString());
        btnAdd.setOnClickListener(this);
        btnInvite.setOnClickListener(this);
    }

    private void Add(){
        final String subject = editSub.getText().toString().trim();
        final String time = editTime.getText().toString().trim();
        final String location = editLoc.getText().toString().trim();
        final String contact = editContact.getText().toString().trim();
        final String duration = editDuration.getText().toString().trim();
        rbChoice=(RadioButton) findViewById(rgVoluntary.getCheckedRadioButtonId());
        final String voluntary = rbChoice.getText().toString().trim();
        progressDialog.setMessage("Creating ... ");
        progressDialog.show();
        final StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constant.URL_CREATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                            session_id.append(jsonObject.getString("session_id"));
                            System.out.println(session_id.toString());
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
                params.put("duration",duration);
                params.put("voluntary",voluntary);
                System.out.println("User id passed to session is : " + U_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void Invite(){
        final String guest = editInvite.getText().toString().trim();
        progressDialog.setMessage("Invite ... ");
        progressDialog.show();
        final StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constant.URL_INVITE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                            System.out.println(jsonObject.getString("message"));
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
                params.put("invitor_id",String.valueOf(U_id));
                params.put("guest",guest);
                params.put("session_id",session_id.toString());
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
        else if(view==btnInvite){
            Invite();
        }
    }
    public void datePic(){
        final Calendar c = Calendar.getInstance();
        year_x=c.get(Calendar.YEAR);
        month_x=c.get(Calendar.MONTH);
        day_x=c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog=new DatePickerDialog(CreateActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateBuffer.setLength(0);
                dateBuffer.append(year+"-"+(month+1)+"-"+dayOfMonth);
                editTime.setText(dateBuffer.toString()+" "+timeBuffer.toString());
            }
        },day_x,month_x,year_x);
        datePickerDialog.show();
    }
    public void timePic(){
        final Calendar c = Calendar.getInstance();
        hour_x=c.get(Calendar.HOUR_OF_DAY);
        minute_x=c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog=new TimePickerDialog(CreateActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timeBuffer.setLength(0);
                timeBuffer.append(hourOfDay+":"+minute);
                editTime.setText(dateBuffer.toString()+" "+timeBuffer.toString());
            }
        },hour_x,minute_x,false);
        timePickerDialog.show();
    }
}