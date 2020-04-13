package com.example.visit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class adminpage extends AppCompatActivity implements View.OnClickListener {
    EditText auser,apass;
    Button adminl;
    String au,ap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);
        auser=findViewById(R.id.adminuser);
        apass=findViewById(R.id.adminpass);
        adminl=findViewById(R.id.adminbutton);
        adminl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                au=auser.getText().toString();
                ap=apass.getText().toString();
                if(au.equals("Syntizen")&&ap.equals("VMS@123"))
                {
                    Intent myintent=new Intent(v.getContext(),hampage.class);
                    startActivity(myintent);
                    auser.setText("");
                    apass.setText("");
                }
                else
                {
                    Toast.makeText(adminpage.this, "unauthorised admin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
