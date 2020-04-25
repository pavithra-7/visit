package com.example.visit.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.visit.R;
import com.example.visit.model.DepartmentModel;
import com.example.visit.utils.ConstantValues;
import com.example.visit.utils.MyAppPrefsManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DepartmentLoginActivity extends AppCompatActivity {


    @BindView(R.id.etUserId)
    EditText etUserId;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;


    String userId, password;

    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    MyAppPrefsManager myAppPrefsManager;
    DatabaseReference myref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_login);
        ButterKnife.bind(this);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Department Login");

        mAuth = FirebaseAuth.getInstance();
        myAppPrefsManager = new MyAppPrefsManager(DepartmentLoginActivity.this);

        btnLogin = (Button) findViewById(R.id.btnLogin);

        progressDialog = new ProgressDialog(DepartmentLoginActivity.this);

        myref = FirebaseDatabase.getInstance().getReference("DepartmentDetails");


    }

    @OnClick(R.id.btnLogin)
    public void onViewClicked() {


        userId = etUserId.getText().toString().trim();
        password = etPassword.getText().toString().trim();


        if (TextUtils.isEmpty(userId)) {
            if ((TextUtils.isEmpty(password)))
                Toast.makeText(this, "Please Enter Email and Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userId)) {
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        Query query = myref.orderByChild("depCode").equalTo(userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {
                        String dbPassword = dataSnapshot1.getValue(DepartmentModel.class).getDepPassword();
                        if(dbPassword.equals(password)) {
                            progressDialog.dismiss();
                            Toast.makeText(DepartmentLoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DepartmentLoginActivity.this,DepartmentHomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(DepartmentLoginActivity.this, "Invalid Password !!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(DepartmentLoginActivity.this, "No User Found !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DepartmentLoginActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

       /* mAuth.signInWithEmailAndPassword(, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {

                            progressDialog.dismiss();
                            Toast.makeText(DepartmentLoginActivity.this, "Please Enter Valid Email/Password", Toast.LENGTH_SHORT).show();


                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(DepartmentLoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                            myAppPrefsManager.setDepartmentLoggedIn(true);
                            ConstantValues.IS_USER_LOGGED_IN_DEPARTMENT = myAppPrefsManager.isDepartmentLoggedIn();
                            Intent intent = new Intent(DepartmentLoginActivity.this, DepartmentHomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("email", email);
                            startActivity(intent);

                        }


                    }
                });*/



    }


    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
