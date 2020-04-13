package com.example.visit;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class home extends AppCompatActivity {
    Button ad,dep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        ad=findViewById(R.id.Adminlogin);
        dep=findViewById(R.id.deptlogin);
        LinearLayout myl = findViewById(R.id.mylayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) myl.getBackground();
        animationDrawable.setEnterFadeDuration(4500);
        animationDrawable.setExitFadeDuration(4500);
        animationDrawable.start();
        ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent=new Intent(v.getContext(),adminpage.class);
                startActivity(myintent);
            }
        });
        dep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent=new Intent(v.getContext(),deptlogin.class);
                startActivity(myintent);
            }
        });

    }
}
