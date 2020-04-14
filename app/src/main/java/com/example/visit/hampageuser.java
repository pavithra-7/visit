package com.example.visit;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class hampageuser extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout draweruser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ham_pageuser);

        Toolbar toolbar=findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        draweruser=findViewById(R.id.drawer1_layout);
        NavigationView navigationView=findViewById(R.id.nav_viewuser);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle =new ActionBarDrawerToggle(this,draweruser,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        draweruser.addDrawerListener(toggle);
        toggle.syncState();
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containeruser,
                    new hamfrontuser()).commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (draweruser.isDrawerOpen(GravityCompat.START)) {
            draweruser.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.createuser:
                Intent intent = new Intent(hampageuser.this,createuser.class);
                startActivity(intent);
                break;
            case R.id.listuser:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containeruser,
                        new listofuser()).commit();
                break;
            case R.id.edituser:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containeruser,
                        new edit_user()).commit();
                break;
        }
        draweruser.closeDrawer(GravityCompat.START);
        return true;
    }
}
