package com.example.matte.database_connection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String res = "a";
        GetExample g = new GetExample();
        try {
            res = g.run("http://smopapp.altervista.org/helloworld.php");
        } catch (IOException e) {
            e.printStackTrace();
        }

        textView = (TextView) findViewById(R.id.text);
        textView.setText(res);
    }
}
