package com.example.visit.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visit.R;
import com.example.visit.adapters.DepartmentListAdapter;
import com.example.visit.model.DepartmentModel;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeaprtmentListActivity extends AppCompatActivity {

    @BindView(R.id.recyclerDepartment)
    RecyclerView recyclerDepartment;
    private SearchView searchView;
    DepartmentListAdapter adapter ;
    private ArrayList<DepartmentModel> departmentModels = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deaprtment_list);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Department List");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerDepartment.setLayoutManager(linearLayoutManager);
        recyclerDepartment.setHasFixedSize(true);
        adapter = new DepartmentListAdapter(departmentModels,this);
        recyclerDepartment.setAdapter(adapter);

        data();

    }

    private void data(){
        departmentModels.add(new DepartmentModel("DEP123","Suresh","suresh@gmail.com","8985410235","DEPECE","ECE","ece@gmail.com","0440223456"));
        departmentModels.add(new DepartmentModel("DEP456","Anil","suresh@gmail.com","8985410235","DEPECE","ECE","ece@gmail.com","0440223456"));
        departmentModels.add(new DepartmentModel("DEP789","Mahesh","suresh@gmail.com","8985410235","DEPECE","ECE","ece@gmail.com","0440223456"));
        departmentModels.add(new DepartmentModel("DEP123","Suresh","suresh@gmail.com","8985410235","DEPECE","ECE","ece@gmail.com","0440223456"));
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
