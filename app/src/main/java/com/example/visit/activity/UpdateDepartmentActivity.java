package com.example.visit.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.visit.R;
import com.example.visit.model.DepartmentModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateDepartmentActivity extends AppCompatActivity {

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

    String deptId,deptName,deptEmail,deptPhone,deptCode,deptPassword,deptHeadName,deptHeadEmail,deptHeadPhone,usersCount;

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
        usersCount = bundle.getString("deptUsersCount");
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
            Toast.makeText(this,"Please enter Department Name",Toast.LENGTH_SHORT).show();

        } else if (deptEmail.isEmpty()) {
            Toast.makeText(this,"Please enter Department Email ID",Toast.LENGTH_SHORT).show();

        } else if (emailPattern.matches(deptEmail)) {
            Toast.makeText(this,"Invalid Mail ID",Toast.LENGTH_SHORT).show();

        } else if (deptPhone.length() != 10) {
            Toast.makeText(this,"Please enter Phone Number",Toast.LENGTH_SHORT).show();

        } else if (deptPassword.isEmpty()) {
            Toast.makeText(this,"Please enter Department Password",Toast.LENGTH_SHORT).show();

        } else if (deptHeadName.isEmpty()) {
            Toast.makeText(this,"Please enter HOD Name",Toast.LENGTH_SHORT).show();

        } else if (deptHeadEmail.isEmpty()) {
            Toast.makeText(this,"Please enter HOD Mail ID",Toast.LENGTH_SHORT).show();

        } else if (emailPattern.matches(deptHeadEmail)) {
            Toast.makeText(this,"Invalid Email ID",Toast.LENGTH_SHORT).show();

        } else if (deptHeadPhone.length() != 10) {
            Toast.makeText(this,"Please enter HOD Phone",Toast.LENGTH_SHORT).show();

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


            DepartmentModel departmentModel = new DepartmentModel(deptId,deptName, deptEmail, deptPassword, deptPhone, deptCode, deptHeadName, deptHeadEmail, deptHeadPhone,usersCount);
            databaseReference.child(deptName).setValue(departmentModel);

            Toast.makeText(this, "Details Updated Successfully !", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UpdateDepartmentActivity.this, AdminHomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

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
