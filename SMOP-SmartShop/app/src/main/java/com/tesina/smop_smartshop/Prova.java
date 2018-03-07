package com.tesina.smop_smartshop;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by matte on 07/02/2018.
 */

public class Prova extends Fragment {
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.prova,container,false);
        final TextView x = view.findViewById(R.id.textView);
        Button y = view.findViewById(R.id.button);
        y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x.setText("afaaddasd");
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TextView x = view.findViewById(R.id.textView);
        //x.setText("afaaddasd");
        Log.i("!!!!!!!!!!!!!!!!!!!!","????");
    }
}
