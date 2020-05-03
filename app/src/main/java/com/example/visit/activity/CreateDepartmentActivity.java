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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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




    String TAG = "FIREBASE_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_department);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Create Department");
        databaseReference = FirebaseDatabase.getInstance().getReference("DepartmentDetails");

    }

    @OnClick(R.id.btnSubmit)
    public void onViewClicked() {

       try {
            next();
       } catch (IllegalArgumentException e) {
           Toast.makeText(this, "Added Successfully !", Toast.LENGTH_SHORT).show();
       }



    }

    public void next() throws IllegalArgumentException {


        deptName = Objects.requireNonNull(etDepartmentName.getText()).toString().trim();
        deptEmail = Objects.requireNonNull(etDepartmentEmail.getText()).toString().trim();
        deptPhone = Objects.requireNonNull(etDepartmentPhone.getText()).toString().trim();
        deptEmail = Objects.requireNonNull(etDepartmentEmail.getText()).toString().trim();
        deptPassword = Objects.requireNonNull(etDepartmentPassword.getText()).toString().trim();
        deptHeadName = Objects.requireNonNull(etHod.getText()).toString().trim();
        deptHeadEmail = Objects.requireNonNull(etHodEmail.getText()).toString().trim();
        deptHeadPhone = Objects.requireNonNull(etHodPhone.getText()).toString().trim();

        if (deptName.isEmpty()) {
            Toast.makeText(this, "Please enter Department Name", Toast.LENGTH_SHORT).show();
        } else if (deptEmail.isEmpty()) {
            Toast.makeText(this,"Please enter Department Email ID",Toast.LENGTH_SHORT).show();
        } else if (isValidEmail(deptEmail)) {
            Toast.makeText(this,"Please enter Valid Department Email",Toast.LENGTH_SHORT).show();
        } else if (deptPhone.isEmpty()) {
            Toast.makeText(this,"Please enter Department Phone",Toast.LENGTH_SHORT).show();
        } else if (isValidMoblie(deptPhone)) {
            Toast.makeText(this,"Please enter Valid Phone",Toast.LENGTH_SHORT).show();
        }else if (deptPassword.isEmpty()) {
            Toast.makeText(this,"Please enter Password",Toast.LENGTH_SHORT).show();
        } else if (deptHeadName.isEmpty()) {
            Toast.makeText(this,"Please enter HOD Name",Toast.LENGTH_SHORT).show();
        } else if (deptHeadEmail.isEmpty()) {
            Toast.makeText(this,"Please enter HOD Email",Toast.LENGTH_SHORT).show();
        } else if (isValidEmail(deptHeadEmail)) {
            Toast.makeText(this,"Please enter HOD Email",Toast.LENGTH_SHORT).show();
        } else if (deptHeadPhone.isEmpty()) {
            Toast.makeText(this,"Please enter HOD Phone",Toast.LENGTH_SHORT).show();
        }else if (isValidMoblie(deptHeadPhone)) {
            Toast.makeText(this,"Please enter Valid HOD Phone",Toast.LENGTH_SHORT).show();
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
                        DepartmentModel departmentModel = new DepartmentModel(deptName, deptEmail, deptPassword, deptPhone, deptCode, deptHeadName, deptHeadEmail, deptHeadPhone,"0");
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

    /*   Validating Fields */
    // Validating email id
    public static boolean isValidEmail(String email1) {

        String EMAIL_PATTERN = "^([_A-Za-z0-9-+].{2,})+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";



        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email1);
        return !matcher.matches();

    }

    //Validating Mobile
    public  static boolean isValidMoblie(String pass1) {

        return pass1 == null || pass1.length() != 10;

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
