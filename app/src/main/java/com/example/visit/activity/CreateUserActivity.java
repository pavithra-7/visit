package com.example.visit.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.visit.R;
import com.example.visit.model.DepartmentModel;
import com.example.visit.model.UsersModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateUserActivity extends AppCompatActivity {

    @BindView(R.id.imgProfile)
    ImageView imgProfile;
    @BindView(R.id.etName)
    TextInputEditText etName;
    @BindView(R.id.etEmail)
    TextInputEditText etEmail;
    @BindView(R.id.etPhone)
    TextInputEditText etPhone;
    @BindView(R.id.etWhomtomeet)
    TextInputEditText etWhomtomeet;
    @BindView(R.id.etPurposetomeet)
    TextInputEditText etPurposetomeet;
    @BindView(R.id.etAddress)
    TextInputEditText etAddress;
    @BindView(R.id.spinstate)
    Spinner spinstate;
    @BindView(R.id.spinCity)
    Spinner spinCity;
    @BindView(R.id.spinDistrict)
    Spinner spinDistrict;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    DatabaseReference databaseReference;

    String name, email, phone, whomToMeet, purposeToMeet, address, state, city, district,imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Create User");

        databaseReference = FirebaseDatabase.getInstance().getReference("UserDetails");
    }

    @OnClick(R.id.btnSubmit)
    public void onViewClicked() {
        next();
    }


    public void next() {



        name = Objects.requireNonNull(etName.getText()).toString().trim();
        email = Objects.requireNonNull(etEmail.getText()).toString().trim();
        phone = Objects.requireNonNull(etPhone.getText()).toString().trim();
        whomToMeet = Objects.requireNonNull(etWhomtomeet.getText()).toString().trim();
        purposeToMeet = Objects.requireNonNull(etPurposetomeet.getText()).toString().trim();
        address = Objects.requireNonNull(etAddress.getText()).toString().trim();
        state = "";
        city = "";
        district = "";
        imagePath="";


        UsersModel usersModel = new UsersModel(name, email, phone, whomToMeet, purposeToMeet,address, state,city,district,imagePath);
        databaseReference.child(name).setValue(usersModel);
        Toast.makeText(CreateUserActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


}
