package com.example.visit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.visit.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DepartmentHomeActivity extends AppCompatActivity {

    @BindView(R.id.btnCreateUser)
    Button btnCreateUser;
    @BindView(R.id.btnUserList)
    Button btnUserList;
    @BindView(R.id.btnGraph)
    Button btnGraph;
    @BindView(R.id.btnCheckout)
    Button btnCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_home);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Department Home Page");
    }

    @OnClick({R.id.btnCreateUser, R.id.btnUserList, R.id.btnGraph, R.id.btnCheckout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCreateUser:
                startActivity(new Intent(DepartmentHomeActivity.this,CreateUserActivity.class));
                break;
            case R.id.btnUserList:
                startActivity(new Intent(DepartmentHomeActivity.this,UserListActivity.class));
                break;
            case R.id.btnGraph:
                break;
            case R.id.btnCheckout:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.actionLogout:
                Intent intent = new Intent(this, AdminLoginActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
