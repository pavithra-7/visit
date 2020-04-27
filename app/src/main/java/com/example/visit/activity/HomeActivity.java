package com.example.visit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.visit.R;
import com.example.visit.utils.ConstantValues;
import com.example.visit.utils.MyAppPrefsManager;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.btnAdminLogin)
    Button btnAdminLogin;
    @BindView(R.id.btnDepartmentLogin)
    Button btnDepartmentLogin;

    MyAppPrefsManager myAppPrefsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Home Page");
        myAppPrefsManager = new MyAppPrefsManager(HomeActivity.this);
    }

    @OnClick({R.id.btnAdminLogin, R.id.btnDepartmentLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAdminLogin:

                if (ConstantValues.IS_USER_LOGGED_IN_ADMIN = myAppPrefsManager.isAdminLoggedIn()) {
                    Intent intent = new Intent(HomeActivity.this, AdminHomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(HomeActivity.this, AdminLoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }


                break;
            case R.id.btnDepartmentLogin:

                if (ConstantValues.IS_USER_LOGGED_IN_DEPARTMENT = myAppPrefsManager.isDepartmentLoggedIn()) {
                    Intent intent = new Intent(HomeActivity.this, DepartmentHomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Intent intent1 = new Intent(HomeActivity.this, DepartmentLoginActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                }

                break;
        }
    }
}
