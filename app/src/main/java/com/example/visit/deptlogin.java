package com.example.visit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class deptlogin extends AppCompatActivity implements View.OnClickListener {
    Button deptbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.department);
      deptbutton=findViewById(R.id.deptbutton);
      deptbutton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent myintent=new Intent(v.getContext(),hampageuser.class);
              startActivity(myintent);
          }
      });
    }

    @Override
    public void onClick(View v) {

    }
}
