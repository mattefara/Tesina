package com.tesina.list_test;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    List<Oggetto> users;

    //the recyclerview
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getting the recyclerview from xml
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        users = new ArrayList<>();

        for (int i=0; i<20; i++){
            Oggetto o = new Oggetto("Matteo","Faraci");
            users.add(o);
        }

        MyAdapter adapter = new MyAdapter(users,this);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);



    }

}
