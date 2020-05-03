package com.example.visit.activity;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visit.R;
import com.example.visit.adapters.DepartmentListAdapter;
import com.example.visit.model.DepartmentModel;
import com.example.visit.utils.MyAppPrefsManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeaprtmentListActivity extends AppCompatActivity {

    @BindView(R.id.recyclerDepartment)
    RecyclerView recyclerDepartment;
    private SearchView searchView;
    DepartmentListAdapter adapter;
    private ArrayList<DepartmentModel> departmentModels = new ArrayList<>();

    DatabaseReference myRef;
    ProgressDialog progressDialog;

    String TAG="FIREBASE_DATA";

    MyAppPrefsManager myAppPrefsManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deaprtment_list);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Department List");

        myAppPrefsManager = new MyAppPrefsManager(this);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

        myRef = FirebaseDatabase.getInstance().getReference("DepartmentDetails");

        data();

    }

    private void data() {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    departmentModels.clear();
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        // do something with the individual "issues"
                        DepartmentModel details = issue.getValue(DepartmentModel.class);
                        departmentModels.add(details);
                    }

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DeaprtmentListActivity.this, RecyclerView.VERTICAL, false);
                    recyclerDepartment.setLayoutManager(linearLayoutManager);
                    recyclerDepartment.setHasFixedSize(true);
                    adapter = new DepartmentListAdapter(departmentModels, DeaprtmentListActivity.this);
                    recyclerDepartment.setAdapter(adapter);
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                progressDialog.dismiss();
                Log.d(TAG, "onCancelled: " + error);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem search = menu.findItem(R.id.actionSearch);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }


}
