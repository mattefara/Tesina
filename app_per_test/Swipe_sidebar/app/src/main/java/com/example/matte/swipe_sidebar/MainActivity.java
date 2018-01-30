package com.example.matte.swipe_sidebar;

import android.app.Activity;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends Activity {
    private String[] lista;
    private DrawerLayout drawerLayout;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = getResources().getStringArray(R.array.lista_elementi);
        drawerLayout = findViewById(R.id.drawer_layout);
        listView = findViewById(R.id.list);

        listView.setAdapter(new ArrayAdapter<String>(this,R.layout.drawer_list_item,lista));
    }
}
