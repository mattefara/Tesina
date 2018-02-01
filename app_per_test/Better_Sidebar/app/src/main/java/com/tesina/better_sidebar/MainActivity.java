package com.tesina.better_sidebar;

import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView sideBar;
    DrawerLayout drawerLayout;
    android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        sideBar = findViewById(R.id.sidebar);
        if (sideBar != null){ sideBar.setNavigationItemSelectedListener(this); }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.ic_home:{
                Toast.makeText(getApplicationContext(),"Home",Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.ic_info:{
                Toast.makeText(getApplicationContext(),"Info",Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.ic_user:{
                Toast.makeText(getApplicationContext(),"Utente",Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.ic_places:{
                Toast.makeText(getApplicationContext(),"Sedi",Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.ic_settings:{
                Toast.makeText(getApplicationContext(),"Impostazioni",Toast.LENGTH_SHORT).show();
                break;
            }
        }
        drawerLayout.closeDrawers();
        return true;
    }
}
