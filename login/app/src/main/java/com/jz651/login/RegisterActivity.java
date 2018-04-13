package com.jz651.login;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    private EditText etEmail,etUserName,etPassword;
    private Button btnRegister;
    private String error1="Password is too short, should be larger than 8 characters";
    private String error2="Password should contain at least one number";
    private String error3="Password should contain at least one upper letter";
    private String error4="Password should contain at least one symobl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etUserName= (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnRegister = (Button) findViewById(R.id.bRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String UserName = etUserName.getText().toString();
                final String Password = etPassword.getText().toString();
                final String Email = etEmail.getText().toString();
                if(Password.length()<8){
                    Toast.makeText(getApplicationContext(),error1,Toast.LENGTH_LONG).show();
                    return;
                }
                if(!check1(Password)){
                    Toast.makeText(getApplicationContext(),error2,Toast.LENGTH_LONG).show();
                    return;
                }
                if(!check2(Password)){
                    Toast.makeText(getApplicationContext(),error3,Toast.LENGTH_LONG).show();
                    return;
                }
                if(!check3(Password)){
                    Toast.makeText(getApplicationContext(),error4,Toast.LENGTH_LONG).show();
                    return;
                }
                Response.Listener<String> responselistener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("error");

                            if(!success){
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Register Failed");
                                builder.setNegativeButton("Retry",null);
                                builder.create();
                                builder.show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(UserName,Password,Email,responselistener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
    private boolean check1(String str){
        char[] chars=str.toCharArray();
        for(int i=0;i<chars.length;++i){
            if(isNum(chars[i])){
                return true;
            }
        }
        return false;
    }
    private boolean check2(String str){
        char[] chars=str.toCharArray();
        for(int i=0;i<chars.length;++i){
            if(isUpper(chars[i])){
                return true;
            }
        }
        return false;
    }
    private boolean check3(String str){
        char[] chars=str.toCharArray();
        for(int i=0;i<chars.length;++i){
            if(isSimp(chars[i])){
                return true;
            }
        }
        return false;
    }
    private boolean isNum(char c){
        if(c<'0'||c>'9'){
            return false;
        }
        return true;
    }
    private boolean isUpper(char c){
        if(c<'A'||c>'Z'){
            return false;
        }
        return true;
    }
    private boolean isSimp(char c){
        if((c>='!'&&c<='/')|| (c>=':'&&c<='@')||(c>='['&&c<='`')||(c>='{'&&c<='~') ){
            return true;
        }
        return false;
    }
}
