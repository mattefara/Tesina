package com.example.matte.listwithsuggestedthings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    AutoCompleteTextView colorTextView;
    Button show;
    ImageView dropDown;

    private String[] colors = new String[]{"Green","Red","Blue"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colorTextView = findViewById(R.id.Colors);
        show = findViewById(R.id.showSelectedItem);
        dropDown = findViewById(R.id.dropdown);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,colors);
        colorTextView.setAdapter(adapter);

        dropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorTextView.showDropDown();
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),colorTextView.getText().toString(),Toast.LENGTH_LONG).show();
            }
        });

    }
}
