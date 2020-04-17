package com.example.visit.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.visit.R;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends Activity {

    @BindView(R.id.radioAdmin)
    RadioButton radioAdmin;
    @BindView(R.id.radioDepartment)
    RadioButton radioDepartment;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.etEmail)
    TextInputEditText etEmail;
    @BindView(R.id.etPassword)
    TextInputEditText etPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    String radioGroupSelected ="Admin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        radioAdmin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b){
                radioGroupSelected = "Admin";
            }
            }
        });

        radioDepartment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    radioGroupSelected = "Department";
                }
            }
        });
    }

    @OnClick(R.id.btnLogin)
    public void onViewClicked() {
        if (radioGroupSelected.equalsIgnoreCase("Admin")) {
            startActivity(new Intent(this, AdminHomeActivity.class));
        }else {
            startActivity(new Intent(this, DepartmentHomeActivity.class));

        }
    }
}
