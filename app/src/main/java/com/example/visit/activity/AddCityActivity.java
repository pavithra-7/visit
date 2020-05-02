package com.example.visit.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.visit.R;
import com.example.visit.model.DistrictData;
import com.example.visit.model.CityData;
import com.example.visit.model.StateData;
import com.google.android.material.textfield.TextInputEditText;
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

public class AddCityActivity extends AppCompatActivity {

    @BindView(R.id.spinStateName)
    Spinner spinStateName;

    @BindView(R.id.spinDistName)
    Spinner spinDistName;

    @BindView(R.id.etCityName)
    TextInputEditText etCityName;

    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    DatabaseReference myref, databaseReference, databaseReference1;
    String cityName,  cityId, stateName, districtName;
    ArrayList<String> stateList, districtList;
    ProgressDialog progressDialog,progressDialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add City");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(AddCityActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        stateList = new ArrayList<String>();
        stateList.add("Select State Name");

        districtList = new ArrayList<>();
        districtList.add("Select District Name");

        myref = FirebaseDatabase.getInstance().getReference().child("City_Details");
        databaseReference = FirebaseDatabase.getInstance().getReference("State_Details");
        databaseReference1 = FirebaseDatabase.getInstance().getReference("District_Details");

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

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddCityActivity.this, R.layout.support_simple_spinner_dropdown_item, stateList);
                    arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinStateName.setAdapter(arrayAdapter);
                    progressDialog.dismiss();
                    //Retrieving State Code as per State Name
                    spinStateName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            String selectedState = spinStateName.getSelectedItem().toString();



                            //Retrieving District Names based on State Selected
                            Query query1 = databaseReference1.orderByChild("state").equalTo(selectedState);
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

                                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddCityActivity.this, R.layout.support_simple_spinner_dropdown_item, districtList);
                                        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                                        spinDistName.setAdapter(arrayAdapter);

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(AddCityActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityId = myref.push().getKey();

                cityName = Objects.requireNonNull(etCityName.getText()).toString().trim();
                stateName = spinStateName.getSelectedItem().toString();
                districtName = spinDistName.getSelectedItem().toString();

                if (stateName.equals("Select State Name")) {
                    Toast.makeText(AddCityActivity.this, "Please choose State Name", Toast.LENGTH_SHORT).show();
                }  else if (districtName.equals("Select District Name")) {
                    Toast.makeText(AddCityActivity.this, "Please choose District Name", Toast.LENGTH_SHORT).show();
                }  else if (cityName.isEmpty()) {

                    Toast.makeText(AddCityActivity.this, "Please enter City Name", Toast.LENGTH_SHORT).show();
                }  else {

                    myref.child(cityName).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.getValue() != null) {
                                //user exists, do something
                                Toast.makeText(AddCityActivity.this, "Already City Name Exists", Toast.LENGTH_SHORT).show();
                            } else {
                                CityData cityData = new CityData(stateName, districtName, cityId, cityName);
                                myref.child(cityName).setValue(cityData);

                                Toast.makeText(AddCityActivity.this, "Data inserted Successfully !", Toast.LENGTH_SHORT).show();

                                spinStateName.setSelection(0);

                                spinDistName.setSelection(0);

                                etCityName.setText("");


                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }

                    });





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
