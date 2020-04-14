package com.example.visit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class createuser extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public EditText username, useraddress, usertomeet, purposetovisit, usercontact, usermail;
    public Button submit1;
    FirebaseDatabase root;
    DatabaseReference ref;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user);
        username = (EditText)findViewById(R.id.name);
        final Spinner state = findViewById(R.id.spinstate);
        final Spinner city = findViewById(R.id.spin);
        final Spinner district = findViewById(R.id.spin1);
        useraddress = (EditText)findViewById(R.id.address);
        usertomeet = (EditText)findViewById(R.id.meet);
        purposetovisit = (EditText)findViewById(R.id.visit);
        usercontact = (EditText)findViewById(R.id.number);
        usermail = (EditText)findViewById(R.id.email);
        submit1 = (Button)findViewById(R.id.b1);

        ArrayAdapter<CharSequence> state1 = ArrayAdapter.createFromResource(this, R.array.states, android.R.layout.simple_spinner_dropdown_item);
        state1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(state1);
        state.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> city1 = ArrayAdapter.createFromResource(this, R.array.cities, android.R.layout.simple_spinner_dropdown_item);
        city1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city.setAdapter(city1);
        city.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> district1 = ArrayAdapter.createFromResource(this, R.array.districts  , android.R.layout.simple_spinner_dropdown_item);
        district1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        district.setAdapter(district1);
        district.setOnItemSelectedListener(this);

        submit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root = FirebaseDatabase.getInstance();
                ref = root.getReference().child("User");
                String usname = username.getText().toString();
                String state2 = state.getSelectedItem().toString();
                String city2 = city.getSelectedItem().toString();
                String district2 = district.getSelectedItem().toString();
                String usaddress = useraddress.getText().toString();
                String usmeet = usertomeet.getText().toString();
                String purposevisit = purposetovisit.getText().toString();
                String uscontact = usercontact.getText().toString();
                String usmail = usermail.getText().toString();
                if (usname.isEmpty()) {
                    Toast.makeText(createuser.this, "Please enter User Name", Toast.LENGTH_SHORT).show();
                } else if (state2.equals("Choose State")) {
                    Toast.makeText(createuser.this, "Please choose a state", Toast.LENGTH_SHORT).show();
                } else if (city2.equals("Choose City")) {
                    Toast.makeText(createuser.this, "Please choose a city", Toast.LENGTH_SHORT).show();
                } else if (district2.equals("Choose District")) {
                    Toast.makeText(createuser.this, "Please choose a district", Toast.LENGTH_SHORT).show();
                } else if (usaddress.isEmpty()) {
                    Toast.makeText(createuser.this, "Please enter address", Toast.LENGTH_SHORT).show();
                }else if (usmeet.isEmpty()) {
                    Toast.makeText(createuser.this, "Please enter the person to meet", Toast.LENGTH_SHORT).show();
                } else if (purposevisit.isEmpty()) {
                    Toast.makeText(createuser.this, "Please enter purpose of visit", Toast.LENGTH_SHORT).show();
                } else if (uscontact.isEmpty()) {
                    Toast.makeText(createuser.this, "Please enter Contact No.", Toast.LENGTH_SHORT).show();
                } else if (usmail.isEmpty()) {
                    Toast.makeText(createuser.this, "Please enter User Mail", Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User(usname, state2, city2, district2,
                            usaddress, usmeet, purposevisit, uscontact, usmail);
                    ref.push().setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(createuser.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(createuser.this, "Failed to register", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
