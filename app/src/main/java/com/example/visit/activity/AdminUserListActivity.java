package com.example.visit.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visit.R;
import com.example.visit.adapters.AdminUsersListAdapter;
import com.example.visit.model.DepartmentModel;
import com.example.visit.model.StateData;
import com.example.visit.model.UsersModel;
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

public class AdminUserListActivity extends AppCompatActivity {
    @BindView(R.id.recyclerUsers)
    RecyclerView recyclerUsers;
    @BindView(R.id.spinDepartments)
    Spinner spinDepartments;
    @BindView(R.id.editEmpty)
    TextView editEmpty;
    private SearchView searchView;
    AdminUsersListAdapter adapter;
    private ArrayList<UsersModel> usersModels = new ArrayList<>();

    String TAG = "FIREBASE_DATA";
    DatabaseReference myRef, myRefDepartments;

    ArrayList<String> departmentList;
    String departmentName;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_list);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Users List");

        departmentList = new ArrayList<>();

        progressDialog = new ProgressDialog(AdminUserListActivity.this);

        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait....");
        editEmpty.setVisibility(View.VISIBLE);
        myRefDepartments = FirebaseDatabase.getInstance().getReference("DepartmentDetails");
        myRef = FirebaseDatabase.getInstance().getReference("UserDetails");
        getDepartments();

    }

    public void getDepartments() {
        myRefDepartments.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    departmentList.clear();
                    departmentList.add("Select Department");
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        String departmentName = Objects.requireNonNull(dataSnapshot1.getValue(DepartmentModel.class)).getDepName();
                        departmentList.add(departmentName);
                    }

                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    departmentList.clear();
                    departmentList.add("Select Department");
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AdminUserListActivity.this, R.layout.support_simple_spinner_dropdown_item, departmentList);
                arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spinDepartments.setAdapter(arrayAdapter);


                spinDepartments.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        departmentName = spinDepartments.getSelectedItem().toString();
                        getData(departmentName);


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                        getData("");
                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getData(String departmentName) {


        myRef.child(departmentName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                usersModels.clear();
                for (DataSnapshot issue : dataSnapshot.getChildren()) {
                    // do something with the individual "issues"
                    UsersModel details = issue.getValue(UsersModel.class);
                    usersModels.add(details);
                }

                if (usersModels.size() == 0) {
                    editEmpty.setVisibility(View.VISIBLE);
                }else {
                    editEmpty.setVisibility(View.GONE);
                }
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AdminUserListActivity.this, RecyclerView.VERTICAL, false);
                recyclerUsers.setLayoutManager(linearLayoutManager);
                recyclerUsers.setHasFixedSize(true);
                adapter = new AdminUsersListAdapter(usersModels, AdminUserListActivity.this);
                recyclerUsers.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
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
