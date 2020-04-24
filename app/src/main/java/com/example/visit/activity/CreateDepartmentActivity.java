package com.example.visit.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.visit.R;
import com.example.visit.model.DepartmentModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateDepartmentActivity extends AppCompatActivity {


    @BindView(R.id.etDepartmentName)
    TextInputEditText etDepartmentName;
    @BindView(R.id.etDepartmentEmail)
    TextInputEditText etDepartmentEmail;
    @BindView(R.id.etDepartmentPhone)
    TextInputEditText etDepartmentPhone;
    @BindView(R.id.etPassword)
    TextInputEditText etDepartmentPassword;
    @BindView(R.id.etHod)
    TextInputEditText etHod;
    @BindView(R.id.etHodEmail)
    TextInputEditText etHodEmail;
    @BindView(R.id.etHodPhone)
    TextInputEditText etHodPhone;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    String deptId,deptName,deptEmail,deptPhone,deptCode,deptPassword,deptHeadName,deptHeadEmail,deptHeadPhone;

    DatabaseReference databaseReference;



    String subjectID;
    private FirebaseAuth mAuth;

    String TAG = "FIREBASE_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_department);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Create Department");

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("DepartmentDetails");

    }

    @OnClick(R.id.btnSubmit)
    public void onViewClicked() {
        next();

    }

    public void next(){


        deptName= Objects.requireNonNull(etDepartmentName.getText()).toString().trim();
        deptEmail= Objects.requireNonNull(etDepartmentEmail.getText()).toString().trim();
        deptPhone= Objects.requireNonNull(etDepartmentPhone.getText()).toString().trim();
        deptEmail= Objects.requireNonNull(etDepartmentEmail.getText()).toString().trim();
        deptPassword= Objects.requireNonNull(etDepartmentPassword.getText()).toString().trim();
        deptHeadName= Objects.requireNonNull(etHod.getText()).toString().trim();
        deptHeadEmail= Objects.requireNonNull(etHodEmail.getText()).toString().trim();
        deptHeadPhone= Objects.requireNonNull(etHodPhone.getText()).toString().trim();

        String firstThreeChars = "";     //substring containing first 3 characters

        if (deptName.length() > 3)
        {
            firstThreeChars = deptName.substring(0, 3);
        }
        else
        {
            firstThreeChars = deptName;
        }

        String lastThreeDigits = "";     //substring containing last 3 characters

        if (deptPhone.length() > 3)
        {
            lastThreeDigits = deptPhone.substring(deptPhone.length() - 3);
        }
        else
        {
            lastThreeDigits = deptPhone;
        }
        deptCode= (firstThreeChars+"_"+lastThreeDigits).toUpperCase();

        mAuth.createUserWithEmailAndPassword(deptEmail, deptPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d(TAG, "createUserWithEmail:success" + user);
                            DepartmentModel departmentModel = new DepartmentModel(deptName,deptEmail,deptPassword,deptPhone,deptCode,deptHeadName,deptHeadEmail,deptHeadPhone);
                            databaseReference.child(deptName).setValue(departmentModel);
                            Toast.makeText(CreateDepartmentActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(CreateDepartmentActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }


                    }
                });








    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
