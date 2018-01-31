package com.example.matte.swipe_sidebar;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    List<SidebarAction> actions;
    private DrawerLayout drawerLayout;
    private RecyclerView listView;
    final private String[] ACTION_MESSAGES = new String[] {"Home", "Info", "Utente", "Sedi"};
    final private int[] ICONS = new int[]{R.drawable.ic_home,R.drawable.ic_info,R.drawable.ic_account_circle,R.drawable.ic_place};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //products = getResources().getStringArray(R.array.lista_elementi);
        //drawerLayout = findViewById(R.id.drawer_layout);
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
}
