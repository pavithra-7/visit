package com.example.visit.activity;

import android.content.Intent;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    String deptId, deptName, deptEmail, deptPhone, deptCode, deptPassword, deptHeadName, deptHeadEmail, deptHeadPhone;

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

    public void next() {

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        deptName = Objects.requireNonNull(etDepartmentName.getText()).toString().trim();
        deptEmail = Objects.requireNonNull(etDepartmentEmail.getText()).toString().trim();
        deptPhone = Objects.requireNonNull(etDepartmentPhone.getText()).toString().trim();
        deptEmail = Objects.requireNonNull(etDepartmentEmail.getText()).toString().trim();
        deptPassword = Objects.requireNonNull(etDepartmentPassword.getText()).toString().trim();
        deptHeadName = Objects.requireNonNull(etHod.getText()).toString().trim();
        deptHeadEmail = Objects.requireNonNull(etHodEmail.getText()).toString().trim();
        deptHeadPhone = Objects.requireNonNull(etHodPhone.getText()).toString().trim();

        if (deptName.isEmpty()) {
            etDepartmentName.setError("Please enter Department Name");
        } else if (deptEmail.isEmpty()) {
            etDepartmentEmail.setError("Please enter Department Email ID");
        } else if (emailPattern.matches(deptEmail)) {
            etDepartmentEmail.setError("Please enter Valid Department Email");
        } else if (deptPhone.isEmpty()) {
            etDepartmentPhone.setError("Please enter Department Phone");
        } else if (deptPassword.isEmpty()) {
            etDepartmentPassword.setError("Please enter Password");
        } else if (deptHeadName.isEmpty()) {
            etHod.setError("Please enter HOD Name");
        } else if (deptHeadEmail.isEmpty()) {
            etHodEmail.setError("Please enter HOD Email");
        } else if (deptHeadPhone.isEmpty()) {
            etHodPhone.setError("Please enter HOD Phone");
        } else {

            String firstThreeChars = "";     //substring containing first 3 characters

            if (deptName.length() > 3) {
                firstThreeChars = deptName.substring(0, 3);
            } else {
                firstThreeChars = deptName;
            }

            String lastThreeDigits = "";     //substring containing last 3 characters

            if (deptPhone.length() > 3) {
                lastThreeDigits = deptPhone.substring(deptPhone.length() - 3);
            } else {
                lastThreeDigits = deptPhone;
            }
            deptCode = (firstThreeChars + "_" + lastThreeDigits).toUpperCase();



            databaseReference.child(deptName).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.getValue() != null) {
                        //user exists, do something
                        Toast.makeText(CreateDepartmentActivity.this, "Already Department Name Exists", Toast.LENGTH_SHORT).show();
                    } else {
                        DepartmentModel departmentModel = new DepartmentModel(deptName, deptEmail, deptPassword, deptPhone, deptCode, deptHeadName, deptHeadEmail, deptHeadPhone);
                        databaseReference.child(deptName).setValue(departmentModel);
                        Toast.makeText(CreateDepartmentActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CreateDepartmentActivity.this, AdminHomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });


        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
