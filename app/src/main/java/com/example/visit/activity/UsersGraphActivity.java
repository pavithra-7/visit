package com.example.visit.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.visit.R;
import com.example.visit.model.DepartmentModel;
import com.example.visit.model.UsersModel;
import com.example.visit.utils.MyAppPrefsManager;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class UsersGraphActivity extends AppCompatActivity {

    BarChart barChart;
    ProgressDialog progressDialog;

    MyAppPrefsManager myAppPrefsManager;

    String departmentName;

    int usersCount;

    DatabaseReference myRef;
    List<UsersModel> usersModelList;

    List<String> deptNamesList;
    List<String> deptMaleList;
    List<String> deptFemaleList;
    ArrayList<BarEntry> entries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_graph);

        Objects.requireNonNull(getSupportActionBar()).setTitle("List of Users");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        barChart = (BarChart) findViewById(R.id.bargraph);

        usersModelList = new ArrayList<>();
        deptNamesList=new ArrayList<>();
        deptMaleList=new ArrayList<>();
        deptFemaleList=new ArrayList<>();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        myRef = FirebaseDatabase.getInstance().getReference("UserDetails");

        myAppPrefsManager = new MyAppPrefsManager(this);

        departmentName = myAppPrefsManager.getDepartmentName();

        getData();

    }

    public void getData() {


        myRef.child(departmentName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    usersModelList.clear();
                    deptNamesList.clear();
                    deptMaleList.clear();
                    deptFemaleList.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        UsersModel usersModel = dataSnapshot1.getValue(UsersModel.class);
                        usersModelList.add(usersModel);
                    }

                    entries.clear();
                    for (UsersModel departmentModel1 : usersModelList) {
                        deptNamesList.add(departmentModel1.getDeptName());

                        String gender=(departmentModel1.getGender());

                        if (gender.equalsIgnoreCase("Male")){
                            deptMaleList.add(gender);
                        }
                        if (gender.equalsIgnoreCase("Female")){
                            deptFemaleList.add(gender);
                        }

                    }



                    String maleCount= String.valueOf(deptMaleList.size());
                    String femaleCount= String.valueOf(deptFemaleList.size());

                    entries.clear();
                    entries.add(new BarEntry(0,Float.parseFloat(maleCount)));
                    entries.add(new BarEntry(1,Float.parseFloat(femaleCount)));


                    BarDataSet barDataSet = new BarDataSet(entries, "Visitors Data");
                    barDataSet.setBarBorderWidth(1f);
                    barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    BarData barData = new BarData(barDataSet);
                    XAxis xAxis = barChart.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    String[] deptNamesList = new String[]{"Male Visitors","Female Visitors"};
                    IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(deptNamesList);
                    xAxis.setGranularity(1f);
                    xAxis.setValueFormatter(formatter);
                    barChart.setData(barData);
                    barChart.setFitBars(true);
                    barChart.animateXY(3000, 3000);
                    barChart.invalidate();

                    progressDialog.dismiss();




                } else {
                    progressDialog.dismiss();
                    Toast.makeText(UsersGraphActivity.this, "No Data Found !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(UsersGraphActivity.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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
