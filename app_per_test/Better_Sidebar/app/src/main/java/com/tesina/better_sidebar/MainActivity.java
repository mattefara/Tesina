package com.tesina.better_sidebar;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView sideBar;
    DrawerLayout drawerLayout;
    LinearLayout sidebarLayout;
    android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sidebarLayout = findViewById(R.id.sidebarLayout);
        int height = getStatusBarHeight();
        sidebarLayout.setPadding(0,height,0,0);
        setStatusBarTransparent();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

    private void setStatusBarTransparent(){
        if (Build.VERSION.SDK_INT >= 21){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private int getStatusBarHeight(){
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        Log.i("Status bar height",statusBarHeight + "");
        return statusBarHeight;
    }
}
