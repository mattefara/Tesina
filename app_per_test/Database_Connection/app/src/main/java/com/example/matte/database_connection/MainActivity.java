package com.example.matte.database_connection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button button;
    final String SERVER_URL = "http://smopapp.altervista.org/helloworld.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVER_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                /*try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    String value = "";
                                    for (int i=0; i<jsonArray.length(); i++){
                                        value += jsonArray.get(i);
                                    }
                                    textView.setText(value);
                                } catch (JSONException e) {
                                    textView.setText("JSON format is incorrect");
                                }*/
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response);
                                    textView.setText(jsonObject.getString("username"));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                //textView.setText(response);

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                textView.setText("Error");
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map <String, String> params = new HashMap<>();
                        params.put("username","Matteo");
                        params.put("password","123456");
                        return params;
                    }
                };
                MySingleton.getMySingletonInstance(MainActivity.this).addToRequestQue(stringRequest);
            }
        });
    }
}
