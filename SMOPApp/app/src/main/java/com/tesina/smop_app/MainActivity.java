package com.tesina.smop_app;

import android.content.Context;
import android.content.Intent;
import android.icu.util.Freezable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.signin.SignIn;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tesina.smop_app.Account.UserInformations;
import com.tesina.smop_app.Account.UserSingIn;
import com.tesina.smop_app.Shopping.ShoppingCart;
import com.tesina.smop_app.Shopping.ShoppingList;
import com.tesina.smop_app.Tabs.ScannedList;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String SCANNED_LIST_FILE_NAME = "scanned_list.csv";
    public static final String USER_LIST_FILE_NAME = "user_list.csv";

    private final int DEFAULT_ACTIVITY = R.layout.fragment_shopping_list;
    private int currentActivity;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference userInformarionReference;

    Context context;

    //Firebase


    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivity(new Intent(context, UserSingIn.class));
            finish();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,startCurrentActivity()).commit();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        if (auth.getCurrentUser() != null){
            TextView displayName = headerView.findViewById(R.id.display_name);
            TextView displayEmail = headerView.findViewById(R.id.display_email);
            setUserInfo(displayName,displayEmail);


        }

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        Log.i("OK!!", "onCreateOptionMenu1");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.make_your_list) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new ShoppingList()).commit();
        } else if (id == R.id.connect_to_shopping_cart) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new ShoppingCart()).commit();
        } else if (id == R.id.settings) {

        } else if (id == R.id.log_out) {
            auth.signOut();
            startActivity(new Intent(MainActivity.this,UserSingIn.class));
            finish();
        }
        currentActivity = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public Fragment startCurrentActivity(){
        switch (currentActivity){
            case R.id.make_your_list: {
                return new ShoppingList();
            }
            case R.id.connect_to_shopping_cart: {
                return new ShoppingCart();
            }
            default: {

                return new ShoppingList();
            }
        }
    }

    public void setUserInfo(final TextView displayName, final TextView displayEmail) {
        String path = "users/" + auth.getCurrentUser().getUid()+"/info";
        userInformarionReference = FirebaseDatabase.getInstance().getReference(path);
        userInformarionReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    displayEmail.setText(auth.getCurrentUser().getEmail());
                    UserInformations info = dataSnapshot.getValue(UserInformations.class);
                    displayName.setText(info.getDisplayName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
