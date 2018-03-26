package com.example.matte.swipe_sidebar;

<<<<<<< HEAD
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
=======
>>>>>>> 2d749bf9af4d0388e3cfe1fdb8d345b51be308b4
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
<<<<<<< HEAD
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
=======
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
>>>>>>> 2d749bf9af4d0388e3cfe1fdb8d345b51be308b4

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<SidebarAction> actions;
    private DrawerLayout drawerLayout;
    private RecyclerView listView;
    final private String[] ACTION_MESSAGES = new String[] {"Home", "Info", "Utente", "Sedi", "Impostazioni"};
    final private int[] ICONS = new int[]{R.drawable.ic_home,R.drawable.ic_info,R.drawable.ic_account_circle,R.drawable.ic_place,R.drawable.ic_settings};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStatusBarTransparent();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        listView = findViewById(R.id.list);
        listView.setHasFixedSize(true);
        listView.setLayoutManager(new LinearLayoutManager(this));

        actions = new ArrayList<>();
        for (int i=0; i<ACTION_MESSAGES.length;i++){
            SidebarAction p = new SidebarAction(ACTION_MESSAGES[i],ICONS[i]);
            actions.add(p);
        }
        Log.i("Array",actions.toString());
        ListAdapter lista = new ListAdapter(actions,getApplicationContext());

        listView.setAdapter(lista);
    }

<<<<<<< HEAD
    private void setStatusBarTransparent(){
        if (Build.VERSION.SDK_INT >= 21){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.setStatusBarColor(Color.TRANSPARENT);
        }
    }
=======
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(getApplicationContext(),"x",Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.action_setting:
                Toast.makeText(getApplicationContext(),"x",Toast.LENGTH_SHORT).show();

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

>>>>>>> 2d749bf9af4d0388e3cfe1fdb8d345b51be308b4
}
