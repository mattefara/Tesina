package com.tesina.list_test;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    List<Oggetto> products;
    private DrawerLayout drawerLayout;
    private RecyclerView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //products = getResources().getStringArray(R.array.lista_elementi);
        drawerLayout = findViewById(R.id.drawer_layout);
        listView = findViewById(R.id.list);
        listView.setHasFixedSize(true);
        listView.setLayoutManager(new LinearLayoutManager(this));

        products = new ArrayList<>();
        for (int i=0; i<5;i++){
            Oggetto p = new Oggetto("Account",R.drawable.ic_launcher_background);
            products.add(p);
        }
        Log.i("Array",products.toString());
        MyAdapter lista = new MyAdapter(products,getApplicationContext());

        listView.setAdapter(lista);
    }

}
