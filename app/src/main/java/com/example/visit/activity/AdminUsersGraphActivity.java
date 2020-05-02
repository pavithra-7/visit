package com.example.visit.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
    List<Integer> deptCount;

    final ArrayList<BarEntry> barEntries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_users_graph);

        Objects.requireNonNull(getSupportActionBar()).setTitle("List of Users");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        barChart = (BarChart) findViewById(R.id.bargraph);

        deptUsersModelList = new ArrayList<>();
        deptNamesList = new ArrayList<>();
        deptCount = new ArrayList<>();

        myRef = FirebaseDatabase.getInstance().getReference("DepartmentDetails");

        progressDialog = new ProgressDialog(AdminUsersGraphActivity.this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        getData();



    }

    public void getData() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    progressDialog.dismiss();

                    for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                        DepartmentModel departmentModel = dataSnapshot1.getValue(DepartmentModel.class);
                        deptUsersModelList.add(departmentModel);
                    }

                    for(DepartmentModel departmentModel : deptUsersModelList) {
                        String deptName = departmentModel.getDepName();
                        String usersCount = departmentModel.getUserCount();

                        deptNamesList.add(deptName);
                        deptCount.add(Integer.parseInt(usersCount));
                    }


                    for(int i=0;i<deptCount.size();i++) {
                        barEntries.add(new BarEntry(deptCount.get(i),i));
                    }

                    XAxis xAxis = barChart.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setTextSize(15f);
                    xAxis.setDrawAxisLine(true);

                    BarDataSet barDataSet = new BarDataSet(barEntries,"List of Users");

                    barChart.animateY(5000);
                    barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                    BarData theData = new BarData(deptNamesList,barDataSet);
                    barChart.setDragEnabled(false);
                    barChart.setScaleEnabled(false);
                    barChart.setDescription("");
                    barChart.setData(theData);

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(AdminUsersGraphActivity.this, "No Data Found !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(AdminUsersGraphActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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
