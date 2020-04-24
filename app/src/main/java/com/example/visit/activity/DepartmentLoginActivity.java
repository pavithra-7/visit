package com.example.visit.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.visit.R;
import com.example.visit.utils.ConstantValues;
import com.example.visit.utils.MyAppPrefsManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DepartmentLoginActivity extends Activity {


    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;


    String email, password;

    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    MyAppPrefsManager myAppPrefsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_login);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        myAppPrefsManager = new MyAppPrefsManager(DepartmentLoginActivity.this);

        btnLogin = (Button) findViewById(R.id.btnLogin);

        progressDialog = new ProgressDialog(DepartmentLoginActivity.this);


    }

    @OnClick(R.id.btnLogin)
    public void onViewClicked() {


        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        if (!isValidEmail(email)) {
            Toast.makeText(this, "Please Enter Valid Email", Toast.LENGTH_SHORT).show();

        }

        if (TextUtils.isEmpty(email)) {
            if ((TextUtils.isEmpty(password)))
                Toast.makeText(this, "Please Enter Email and Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
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
                });

    }


    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
