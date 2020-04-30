package com.example.visit.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.visit.R;
import com.example.visit.model.UsersModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateUser extends AppCompatActivity {

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
        setContentView(R.layout.activity_update_user);

        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Update User");

        databaseReference = FirebaseDatabase.getInstance().getReference("UserDetails");

        Bundle bundle = getIntent().getExtras();
        assert bundle!=null;
        String uName = bundle.getString("userName");
        String uEmail = bundle.getString("userEmail");
        String uContact = bundle.getString("userContact");
        String uWhomToMeet = bundle.getString("userWhomToMeet");
        String uPurposeToMeet = bundle.getString("userPurposeToMeet");
        String uAddress = bundle.getString("userAddress");

        etName.setText(uName);
        etEmail.setText(uEmail);
        etPhone.setText(uContact);
        etWhomtomeet.setText(uWhomToMeet);
        etPurposetomeet.setText(uPurposeToMeet);
        etAddress.setText(uAddress);


    }

    @OnClick(R.id.btnSubmit)
    public void onViewClicked() {
        next();
    }


    public void next() {


        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

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

        if(name.isEmpty()) {
            Toast.makeText(this,"Please enter Name",Toast.LENGTH_SHORT).show();

        } else if(email.isEmpty()) {
            Toast.makeText(this,"Please enter Email ID",Toast.LENGTH_SHORT).show();

        } else if(!emailPattern.matches(email)) {
            Toast.makeText(this,"Please enter Valid Email ID",Toast.LENGTH_SHORT).show();

        }else if(phone.length()!=10) {
            Toast.makeText(this,"Please enter Phone",Toast.LENGTH_SHORT).show();

        } else if(whomToMeet.isEmpty()) {
            Toast.makeText(this,"Please enter Whom To Meet",Toast.LENGTH_SHORT).show();

        } else if(purposeToMeet.isEmpty()) {
            Toast.makeText(this,"Please enter Purpose to Meet",Toast.LENGTH_SHORT).show();

        } else if(address.isEmpty()){
            Toast.makeText(this,"Please enter Address",Toast.LENGTH_SHORT).show();

        } else {

            /*UsersModel usersModel = new UsersModel(name, email, phone, whomToMeet, purposeToMeet, address, state, city, district, imagePath);
            databaseReference.child(name).setValue(usersModel);
            Toast.makeText(UpdateUser.this, "Updated Successfully", Toast.LENGTH_SHORT).show();*/
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
