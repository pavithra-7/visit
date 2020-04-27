package com.example.visit.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.visit.R;
import com.example.visit.model.CityData;
import com.example.visit.model.DepartmentModel;
import com.example.visit.model.DistrictData;
import com.example.visit.model.StateData;
import com.example.visit.model.UsersModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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
    DatabaseReference databaseReferenceState;
    DatabaseReference databaseReferenceDistrict;
    DatabaseReference databaseReferenceCity;

    String name, email, phone, whomToMeet, purposeToMeet, address, state, city, district, imagePath;
    ArrayList<String> stateList, districtList, cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Create User");

        stateList = new ArrayList<String>();
        stateList.add("Select State Name");

        districtList = new ArrayList<>();
        districtList.add("Select District Name");

        cityList = new ArrayList<>();
        cityList.add("Select City Name");

        databaseReference = FirebaseDatabase.getInstance().getReference("UserDetails");
        databaseReferenceState = FirebaseDatabase.getInstance().getReference("State_Details");
        databaseReferenceDistrict = FirebaseDatabase.getInstance().getReference("District_Details");
        databaseReferenceCity = FirebaseDatabase.getInstance().getReference().child("City_Details");

        // Retrieving State Name
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    stateList.clear();
                    stateList.add("Select State Name");
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        String stateName = Objects.requireNonNull(dataSnapshot1.getValue(StateData.class)).getState();
                        stateList.add(stateName);
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CreateUserActivity.this, R.layout.support_simple_spinner_dropdown_item, stateList);
                    arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinstate.setAdapter(arrayAdapter);

                    //Retrieving State Code as per State Name
                    spinstate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            String selectedState = spinstate.getSelectedItem().toString();


                            //Retrieving District Names based on State Selected
                            Query query1 = databaseReferenceDistrict.orderByChild("state").equalTo(selectedState);
                            query1.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        districtList.clear();
                                        districtList.add("Select District Name");
                                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                            String districtName = Objects.requireNonNull(dataSnapshot1.getValue(DistrictData.class)).getDistrictname();
                                            districtList.add(districtName);
                                        }

                                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CreateUserActivity.this, R.layout.support_simple_spinner_dropdown_item, districtList);
                                        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                                        spinDistrict.setAdapter(arrayAdapter);


                                        spinDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                String selectedDistrict = spinDistrict.getSelectedItem().toString();
                                                Query query=databaseReferenceCity.orderByChild("district").equalTo(selectedDistrict);
                                                query.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        if (dataSnapshot.exists()) {
                                                            cityList.clear();
                                                            cityList.add("Select City Name");
                                                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                                String cityName = Objects.requireNonNull(dataSnapshot1.getValue(CityData.class)).getCity();
                                                                cityList.add(cityName);
                                                            }

                                                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CreateUserActivity.this, R.layout.support_simple_spinner_dropdown_item, cityList);
                                                            arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                                                            spinCity.setAdapter(arrayAdapter);
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });

                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> parent) {

                                            }
                                        });

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


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
        state = spinstate.getSelectedItem().toString().trim();
        city = spinCity.getSelectedItem().toString().trim();
        district = spinDistrict.getSelectedItem().toString().trim();
        imagePath = "";

        if (name.isEmpty()) {
            etName.setError("Please enter Name");
        } else if (email.isEmpty()) {
            etEmail.setError("Please enter Email ID");
        } else if (!emailPattern.matches(email)) {
            etEmail.setError("Please enter Valid Email ID");
        } else if (phone.length() != 10) {
            etPhone.setError("Please enter Phone");
        } else if (whomToMeet.isEmpty()) {
            etWhomtomeet.setError("Please enter Whom To Meet");
        } else if (purposeToMeet.isEmpty()) {
            etPurposetomeet.setError("Please enter Purpose to Meet");
        } else if (address.isEmpty()) {
            etAddress.setError("Please enter Address");
        } else {

            UsersModel usersModel = new UsersModel(name, email, phone, whomToMeet, purposeToMeet, address, state, city, district, imagePath);
            databaseReference.child(name).setValue(usersModel);
            Toast.makeText(CreateUserActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();

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
