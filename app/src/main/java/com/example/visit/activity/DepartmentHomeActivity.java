package com.example.visit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.visit.R;
import com.example.visit.utils.ConstantValues;
import com.example.visit.utils.MyAppPrefsManager;
import com.google.firebase.auth.FirebaseAuth;

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
    MyAppPrefsManager myAppPrefsManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_home);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Department Home Page");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        myAppPrefsManager=new MyAppPrefsManager(DepartmentHomeActivity.this);


    }

    @OnClick({R.id.btnCreateUser, R.id.btnUserList, R.id.btnGraph})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCreateUser:
                Intent intent=new Intent(DepartmentHomeActivity.this,CreateUserActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.btnUserList:
                Intent intent1=new Intent(DepartmentHomeActivity.this,UserListActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                break;
            case R.id.btnGraph:
                Intent intent2=new Intent(DepartmentHomeActivity.this, UsersGraphActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
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
                Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                myAppPrefsManager.setDepartmentLoggedIn(false);
                ConstantValues.IS_USER_LOGGED_IN_DEPARTMENT = myAppPrefsManager.isDepartmentLoggedIn();
                Intent intent = new Intent(DepartmentHomeActivity.this, DepartmentLoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
