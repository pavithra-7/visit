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
import androidx.appcompat.app.AppCompatActivity;

import com.example.visit.R;
import com.example.visit.utils.ConstantValues;
import com.example.visit.utils.MyAppPrefsManager;
import com.google.firebase.auth.FirebaseAuth;

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
    @BindView(R.id.btnRegister)
    Button btnRegister;

    MyAppPrefsManager myAppPrefsManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        ButterKnife.bind(this);
        myAppPrefsManager=new MyAppPrefsManager(AdminHomeActivity.this);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Admin Home Page");
    }

    @OnClick({R.id.btnCreateDepartment, R.id.btnDepartmentList, R.id.btnUsersList, R.id.btnUsersGraph,R.id.btnRegister})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCreateDepartment:
                startActivity(new Intent(AdminHomeActivity.this, CreateDepartmentActivity.class));

                break;
            case R.id.btnDepartmentList:
                startActivity(new Intent(AdminHomeActivity.this, DeaprtmentListActivity.class));
                break;
            case R.id.btnUsersList:
                startActivity(new Intent(AdminHomeActivity.this, AdminUserListActivity.class));
                break;
            case R.id.btnUsersGraph:
                startActivity(new Intent(AdminHomeActivity.this,AdminUsersGraph.class));
                break;
            case R.id.btnRegister:
                Intent intent = new Intent(AdminHomeActivity.this, RegistrationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
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
        switch (item.getItemId()) {
            case R.id.actionLogout:
                Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                myAppPrefsManager.setAdminLoggedIn(false);
                ConstantValues.IS_USER_LOGGED_IN_ADMIN = myAppPrefsManager.isAdminLoggedIn();
                Intent intent = new Intent(AdminHomeActivity.this, AdminLoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
