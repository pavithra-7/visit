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

public class AdminHomeActivity extends AppCompatActivity {

    @BindView(R.id.btnCreateDepartment)
    Button btnCreateDepartment;
    @BindView(R.id.btnDepartmentList)
    Button btnDepartmentList;
    @BindView(R.id.btnUsersList)
    Button btnUsersList;
    @BindView(R.id.btnUsersGraph)
    Button btnUsersGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Admin Home Page");
    }

    @OnClick({R.id.btnCreateDepartment, R.id.btnDepartmentList, R.id.btnUsersList, R.id.btnUsersGraph})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCreateDepartment:
                startActivity(new Intent(AdminHomeActivity.this,CreateDepartmentActivity.class));

                break;
            case R.id.btnDepartmentList:
                startActivity(new Intent(AdminHomeActivity.this,DeaprtmentListActivity.class));
                break;
            case R.id.btnUsersList:
                startActivity(new Intent(AdminHomeActivity.this,UserListActivity.class));
                break;
            case R.id.btnUsersGraph:
                break;
        }
    }
}
