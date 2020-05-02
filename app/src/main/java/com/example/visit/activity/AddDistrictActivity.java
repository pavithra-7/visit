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
import com.example.visit.model.StateData;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddDistrictActivity extends AppCompatActivity {

    @BindView(R.id.spinStateName)
    Spinner spinStateName;
    @BindView(R.id.etDistName)
    TextInputEditText etDistName;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    DatabaseReference myref, databaseReference;
    String distId, distName, stateName;
    List<String> stateList;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_district);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add District");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

        myref = FirebaseDatabase.getInstance().getReference().child("District_Details");
        databaseReference = FirebaseDatabase.getInstance().getReference("State_Details");

        stateList = new ArrayList<String>();
        stateList.add("Select State Name");


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

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddDistrictActivity.this, R.layout.support_simple_spinner_dropdown_item, stateList);
                    arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinStateName.setAdapter(arrayAdapter);

                    progressDialog.dismiss();
                    //Retrieving State Code as per State Name


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distId = myref.push().getKey();
                distName = Objects.requireNonNull(etDistName.getText()).toString().trim();

                stateName = spinStateName.getSelectedItem().toString();

                if (stateName.equals("Select State Name")) {
                    Toast.makeText(AddDistrictActivity.this, "Please Select State Name", Toast.LENGTH_SHORT).show();
                } else if (distName.isEmpty()) {
                    Toast.makeText(AddDistrictActivity.this, "Please enter District Name", Toast.LENGTH_SHORT).show();
                } else {

                    myref.child(distName).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.getValue() != null) {
                                //user exists, do something
                                Toast.makeText(AddDistrictActivity.this, "Already District Name Exists", Toast.LENGTH_SHORT).show();
                            } else {
                                DistrictData districtData = new DistrictData(stateName, distId, distName);
                                myref.child(distName).setValue(districtData);
                                spinStateName.setSelection(0);

                                etDistName.setText("");

                                Toast.makeText(AddDistrictActivity.this, "Data inserted Successfully !", Toast.LENGTH_SHORT).show();

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
