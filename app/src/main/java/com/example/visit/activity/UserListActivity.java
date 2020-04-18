package com.example.visit.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visit.R;
import com.example.visit.adapters.UsersListAdapter;
import com.example.visit.model.UsersModel;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserListActivity extends AppCompatActivity {

    @BindView(R.id.recyclerUsers)
    RecyclerView recyclerUsers;
    private SearchView searchView;
    UsersListAdapter adapter;
    private ArrayList<UsersModel> usersModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Users List");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerUsers.setLayoutManager(linearLayoutManager);
        recyclerUsers.setHasFixedSize(true);
        adapter = new UsersListAdapter(usersModels, this);
        recyclerUsers.setAdapter(adapter);

        data();

    }

    private void data() {

        usersModels.add(new UsersModel("DEP@#$", "swaroop", "telangana", "hyd", "hyd", "kukatpally", "anil", "work", "9854543243", "anil@gmail.com", ""));
        usersModels.add(new UsersModel("DEP@#$", "Anil", "telangana", "hyd", "hyd", "kukatpally", "anil", "work", "9854543243", "anil@gmail.com", ""));
        usersModels.add(new UsersModel("DEP@#$", "mahesh", "telangana", "hyd", "hyd", "kukatpally", "anil", "work", "9854543243", "anil@gmail.com", ""));
        usersModels.add(new UsersModel("DEP@#$", "swaroop", "telangana", "hyd", "hyd", "kukatpally", "anil", "work", "9854543243", "anil@gmail.com", ""));
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
