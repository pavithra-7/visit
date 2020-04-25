package com.example.visit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.visit.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.btnAdminLogin)
    Button btnAdminLogin;
    @BindView(R.id.btnDepartmentLogin)
    Button btnDepartmentLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Home Page");
    }

    @OnClick({R.id.btnAdminLogin, R.id.btnDepartmentLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAdminLogin:

                Intent intent=new Intent(HomeActivity.this,AdminLoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                break;
            case R.id.btnDepartmentLogin:

                Intent intent1=new Intent(HomeActivity.this,DepartmentLoginActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                break;
        }
    }
}
