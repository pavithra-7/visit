package com.example.visit.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.visit.R;
import com.example.visit.model.UsersModel;
import com.example.visit.utils.MyAppPrefsManager;
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

public class UsersGraphActivity extends AppCompatActivity {

    BarChart barChart;
    ProgressDialog progressDialog;

    MyAppPrefsManager myAppPrefsManager;

    String departmentName;

    int usersCount;

    DatabaseReference myRef;
    List<UsersModel> usersModelList;

    final ArrayList<BarEntry> barEntries = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_graph);

        Objects.requireNonNull(getSupportActionBar()).setTitle("List of Users");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        barChart = (BarChart) findViewById(R.id.bargraph);

        usersModelList = new ArrayList<>();

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
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        UsersModel usersModel = dataSnapshot1.getValue(UsersModel.class);
                        usersModelList.add(usersModel);
                    }

                    progressDialog.dismiss();


                    barEntries.add(new BarEntry(usersModelList.size(), 0));


                    XAxis xAxis = barChart.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setTextSize(15f);
                    xAxis.setDrawAxisLine(true);


                    BarDataSet barDataSet = new BarDataSet(barEntries, "List of Users");

                    ArrayList<String> type = new ArrayList<>();
                    type.add(departmentName);

                    barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                    BarData theData = new BarData(type, barDataSet);
                    barChart.setData(theData);
                    barChart.animateXY(3000, 3000);
                    barChart.setDescription("");

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
