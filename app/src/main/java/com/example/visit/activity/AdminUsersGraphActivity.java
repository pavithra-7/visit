package com.example.visit.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.visit.R;
import com.example.visit.model.DepartmentModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdminUsersGraphActivity extends AppCompatActivity {

    BarChart barChart;
    ProgressDialog progressDialog;
    DatabaseReference myRef;
    List<DepartmentModel> deptUsersModelList;
    List<String> deptNamesList;
    ArrayList<BarEntry> entries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_users_graph);

        Objects.requireNonNull(getSupportActionBar()).setTitle("List of Users");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        deptUsersModelList = new ArrayList<>();

        deptNamesList=new ArrayList<>();


        barChart = (BarChart)findViewById(R.id.bargraph);


        myRef = FirebaseDatabase.getInstance().getReference("DepartmentDetails");


        getData1();


    }


    public void getData1() {
        progressDialog = new ProgressDialog(AdminUsersGraphActivity.this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    progressDialog.dismiss();
                    deptUsersModelList.clear();
                    deptNamesList.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        DepartmentModel departmentModel = dataSnapshot1.getValue(DepartmentModel.class);
                        deptUsersModelList.add(departmentModel);

                    }

                    entries.clear();
                    for (DepartmentModel departmentModel1 : deptUsersModelList) {
                        deptNamesList.add(departmentModel1.getDepName());
                        int value=departmentModel1.getDepID()-1;
                        entries.add(new BarEntry(value, Float.parseFloat(departmentModel1.getUserCount())));
                    }

                    BarDataSet barDataSet = new BarDataSet(entries, "Visitors Data");
                    barDataSet.setBarBorderWidth(1f);
                    barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    BarData barData = new BarData(barDataSet);
                    XAxis xAxis = barChart.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(deptNamesList);
                    xAxis.setGranularity(1f);
                    xAxis.setValueFormatter(formatter);
                    barChart.setData(barData);
                    barChart.setFitBars(true);
                    barChart.animateXY(3000, 3000);
                    barChart.invalidate();

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(AdminUsersGraphActivity.this, "No Data Found !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(AdminUsersGraphActivity.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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
