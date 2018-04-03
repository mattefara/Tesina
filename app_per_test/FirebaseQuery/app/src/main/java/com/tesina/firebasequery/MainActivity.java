package com.tesina.firebasequery;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    EditText textToFind;
    Button search;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseFunctions functions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textToFind = findViewById(R.id.text);
        search = findViewById(R.id.button);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("query2");
        functions = FirebaseFunctions.getInstance();

        /*reference.push().setValue( new Product("Speghetti","AAA",3));
        reference.push().setValue( new Product("Speghetti","BBB",3));
        reference.push().setValue( new Product("Speghetti","CCC",3));
        reference.push().setValue( new Product("Speghetti","DDD",3));
        reference.push().setValue( new Product("Speghetti","EEE",3));*/
        //reference.setValue("Find me");

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                functions.getHttpsCallable(checkRightFunction(4)).call()
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.i("ERRORE!!!",e.getMessage());
                        }
                    })
                    .addOnCompleteListener(new OnCompleteListener<HttpsCallableResult>() {
                        @Override
                        public void onComplete(@NonNull Task<HttpsCallableResult> task) {

                            /* Funziona con la funzione1
                            String response = (String) task.getResult().getData();
                            Log.i("Messaggio", response);*/

                            /* Funziona con la funzione2
                            HashMap<String, Object> o = (HashMap<String, Object>) task.getResult().getData();
                            for (String key: o.keySet()){
                                String x = (String) o.get(key);
                                Log.i("SUCCESSO!!",key + ": " + x);
                            }*/

                            /*Funziona con la funzione3
                            HashMap<String, Object> objectHashMap = (HashMap<String, Object>) task.getResult().getData();
                            for (String key: objectHashMap.keySet()){
                                ArrayList x = (ArrayList) objectHashMap.get(key);
                                Log.i("SUCCESSO!!","Array " + key + ": " + x.toString());
                            }*/

                            /*Funziona con la funzione4
                            HashMap<String, Object> objectHashMap = (HashMap<String, Object>) task.getResult().getData();
                            for (String key: objectHashMap.keySet()) {
                                Object o = objectHashMap.get(key);
                                if (o instanceof ArrayList){
                                    Log.i("Risposta " + key, o.toString());
                                }else if (o instanceof HashMap){
                                    HashMap<String, Oggetto> x = (HashMap<String, Oggetto>) o;
                                    for (String keyX: x.keySet()){
                                        Object x1 = x.get(keyX);
                                        Log.i("Risposta " + key ,x1.toString());
                                    }
                                } else {
                                    Log.i("Risposta " + key, String.valueOf(o));
                                }
                            }*/
                        }
                    });
                /*Query resultQuery = reference.orderByChild("barcode").equalTo(textToFind.getText().toString());
                resultQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //toast(dataSnapshot.toString());
                        if (dataSnapshot.exists()){
                            List<Product> products = new ArrayList();
                            for (DataSnapshot productSnapshot : dataSnapshot.getChildren()){
                                Product p = productSnapshot.getValue(Product.class);
                                products.add(p);
                            }
                            for (Product p : products){
                                Log.i("barcode",p.toString());
                            }
                            dataSnapshot.getRef();
                        } else{
                            toast("Non esiste");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });*/

            }
        });

    }

    private String checkRightFunction(int c) {
        String function = "funzione";
        return function + c;
    }

    private void toast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }
}
