package com.jz651.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class UserSpace extends AppCompatActivity {
    private TextView txWelMsg;
    private Button btnCreate;
    private String UserName;
    private int U_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_space);


        txWelMsg = (TextView) findViewById(R.id.txWelcomemsg);
        btnCreate = (Button) findViewById(R.id.btnCreate);

        Intent intent = getIntent();
        UserName = intent.getStringExtra("UserName");
        U_id = intent.getIntExtra("user_id",0);

        String WelMsg = "No." +U_id+ " , Welcome to your page!";

        txWelMsg.setText(WelMsg);


        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createIntent = new Intent(UserSpace.this,CreateActivity.class);
                createIntent.putExtra("user_id",U_id);
                UserSpace.this.startActivity(createIntent);
            }
        });
    }
}
