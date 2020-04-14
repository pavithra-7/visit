package com.example.visit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class createdept extends AppCompatActivity {

    public EditText departtid, departname, departmailid, departphone, departcode, departpwd, departconpwd, departhod, departhodmail, departhodphone;
    public Button submit;
    FirebaseDatabase root;
    DatabaseReference ref;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_dept);

        departtid = (EditText) findViewById(R.id.deptid);
        departname = (EditText) findViewById(R.id.deptname);
        departmailid = (EditText) findViewById(R.id.mailid);
        departphone = (EditText) findViewById(R.id.phno);
        departcode = (EditText) findViewById(R.id.code);
        departpwd = (EditText) findViewById(R.id.pass);
        departconpwd = (EditText) findViewById(R.id.pass1);
        departhod = (EditText) findViewById(R.id.head1);
        departhodmail = (EditText) findViewById(R.id.mail1);
        departhodphone = (EditText) findViewById(R.id.phno1);
        submit = (Button) findViewById(R.id.button1);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root = FirebaseDatabase.getInstance();
                ref = root.getReference().child("Department");
                String deptid = departtid.getText().toString();
                String deptname = departname.getText().toString();
                String deptmailid = departmailid.getText().toString();
                String deptphone = departphone.getText().toString();
                String deptcode = departcode.getText().toString();
                String deptpwd = departpwd.getText().toString();
                String deptconpwd = departconpwd.getText().toString();
                String depthod = departhod.getText().toString();
                String depthodmail = departhodmail.getText().toString();
                String depthodphone = departhodphone.getText().toString();
                if (deptname.isEmpty()) {
                    Toast.makeText(createdept.this, "Please enter Department Name", Toast.LENGTH_SHORT).show();
                } else if (deptmailid.isEmpty()) {
                    Toast.makeText(createdept.this, "Please enter Department Email Id", Toast.LENGTH_SHORT).show();
                } else if (deptphone.isEmpty()) {
                    Toast.makeText(createdept.this, "Please enter Department Phone No.", Toast.LENGTH_SHORT).show();
                } else if (deptcode.isEmpty()) {
                    Toast.makeText(createdept.this, "Please enter Department Code", Toast.LENGTH_SHORT).show();
                } else if (deptpwd.isEmpty()) {
                    Toast.makeText(createdept.this, "Please enter Password", Toast.LENGTH_SHORT).show();
                } else if (deptpwd.length() < 6) {
                    Toast.makeText(createdept.this, "Password too short", Toast.LENGTH_SHORT).show();
                } else if (!deptpwd.equals(deptconpwd)) {
                    Toast.makeText(createdept.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else if (depthod.isEmpty()) {
                    Toast.makeText(createdept.this, "Please enter HoD's name", Toast.LENGTH_SHORT).show();
                } else if (depthodmail.isEmpty()) {
                    Toast.makeText(createdept.this, "Please enter HoD's Email Id", Toast.LENGTH_SHORT).show();
                } else if (depthodphone.isEmpty()) {
                    Toast.makeText(createdept.this, "Please enter HoD's Phone No.", Toast.LENGTH_SHORT).show();
                } else {
                    Department department = new Department(deptid, deptname, deptmailid, deptphone,
                            deptcode, deptpwd, deptconpwd, depthod, depthodmail, depthodphone);
                    ref.push().setValue(department).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(createdept.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(createdept.this, "Failed to register", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }

}
