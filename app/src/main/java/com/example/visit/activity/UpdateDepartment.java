package com.example.visit.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

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
import com.google.firebase.database.Query;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateDepartment extends AppCompatActivity {

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
        setContentView(R.layout.activity_update_department);

        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Update Department");

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String dName = bundle.getString("deptName");
        String dEmail = bundle.getString("deptEmail");
        String dPhone = bundle.getString("deptPhone");
        String dPwd = bundle.getString("deptPwd");
        String dHod = bundle.getString("deptHod");
        String dHemail = bundle.getString("deptHeadEmail");
        String dHphone= bundle.getString("deptHeadPhone");
        deptCode = bundle.getString("deptCode");
        deptId=bundle.getString("deptID");

        etDepartmentName.setText(dName);
        etDepartmentEmail.setText(dEmail);
        etDepartmentPhone.setText(dPhone);
        etDepartmentPassword.setText(dPwd);
        etHod.setText(dHod);
        etHodEmail.setText(dHemail);
        etHodPhone.setText(dHphone);


        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("DepartmentDetails");
    }

    @OnClick(R.id.btnSubmit)
    public void onViewClicked() {
        next();
    }

    public void next() {


        deptName = Objects.requireNonNull(etDepartmentName.getText()).toString().trim();
        deptEmail = Objects.requireNonNull(etDepartmentEmail.getText()).toString().trim();
        deptPhone = Objects.requireNonNull(etDepartmentPhone.getText()).toString().trim();
        deptEmail = Objects.requireNonNull(etDepartmentEmail.getText()).toString().trim();
        deptPassword = Objects.requireNonNull(etDepartmentPassword.getText()).toString().trim();
        deptHeadName = Objects.requireNonNull(etHod.getText()).toString().trim();
        deptHeadEmail = Objects.requireNonNull(etHodEmail.getText()).toString().trim();
        deptHeadPhone = Objects.requireNonNull(etHodPhone.getText()).toString().trim();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


        if (deptName.isEmpty()) {
            etDepartmentName.setError("Please enter Department Name");
        } else if (deptEmail.isEmpty()) {
            etDepartmentEmail.setError("Please enter Department Email ID");
        } else if (!emailPattern.matches(deptEmail)) {
            etDepartmentEmail.setError("Invalid Mail ID");
        } else if (deptPhone.length() != 10) {
            etDepartmentPhone.setError("Please enter Phone Number");
        } else if (deptPassword.isEmpty()) {
            etDepartmentPassword.setError("Please enter Department Password");
        } else if (deptHeadName.isEmpty()) {
            etHod.setError("Please enter HOD Name");
        } else if (deptHeadEmail.isEmpty()) {
            etHodEmail.setError("Please enter HOD Mail ID");
        } else if (emailPattern.matches(deptHeadEmail)) {
            etHodEmail.setError("Invalid Email ID");
        } else if (deptHeadPhone.length() != 10) {
            etHodPhone.setError("Please enter HOD Phone");
        } else {

            //databaseReference.child(deptName).child("depName").setValue(deptName);
            databaseReference.child(deptName).child("depMail").setValue(deptEmail);
            databaseReference.child(deptName).child("depPhone").setValue(deptPhone);
            databaseReference.child(deptName).child("depPassword").setValue(deptPassword);
            databaseReference.child(deptName).child("headofdep").setValue(deptHeadName);
            databaseReference.child(deptName).child("headEmail").setValue(deptHeadEmail);
            databaseReference.child(deptName).child("headPhone").setValue(deptHeadPhone);

            Toast.makeText(this, "Details Updated Successfully !", Toast.LENGTH_SHORT).show();

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
