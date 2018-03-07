package com.tesina.smop_smartshop;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    int previousActivity = R.id.shop;

    private FirebaseAuth authentication;
    FirebaseUser currentUser;

    DrawerLayout drawerLayout;
    NavigationView sidebar;
    FrameLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        authentication = FirebaseAuth.getInstance();
        linearLayout = (FrameLayout) findViewById(R.id.content_frame);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        sidebar = (NavigationView) findViewById(R.id.sidebar);
        if (sidebar != null){ sidebar.setNavigationItemSelectedListener(this); }


    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = authentication.getCurrentUser();
        if (currentUser == null){
            startActivity(new Intent(MainActivity.this, Login.class));
        } else {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame,new ShopList()).commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (previousActivity == id){
            drawerLayout.closeDrawers();
            return true;
        }
        previousActivity = id;
        FragmentManager fragmentManager = getFragmentManager();

        switch (id){
            case R.id.shop:{
                fragmentManager.beginTransaction().replace(R.id.content_frame,new ShopList()).commit();
                break;
            }
            case R.id.settings:{
                fragmentManager.beginTransaction().replace(R.id.content_frame,new Prova()).commit();
                break;
            }
            case R.id.logout:{
                authentication.signOut();
                startActivity(new Intent(MainActivity.this,Login.class));
                finish();
                break;
            }

        }
        drawerLayout.closeDrawers();
        Toast.makeText(getApplicationContext(),item.getTitle(),Toast.LENGTH_SHORT).show();
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
