package com.tesina.smop_smartshop;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
public class DatabaseConnection {
    final String SERVER_URL = "http://smopapp.altervista.org/login.php";
    private String[] params, values;
    private Context context;

    public DatabaseConnection(String[] params, String[] values, Context context) {
        this.params = params;
        this.values = values;
        this.context = context;
    }

    public void startConnection(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVER_URL,
               responseListener(),errorResponseListener()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                if (params.length>0 && values.length>0){
                    Map<String, String> data = new HashMap<>();
                    for (int i = 0; i < params.length; i++) {
                        data.put(params[i],values[i]);
                    }
                    return data;
                }
                return new HashMap<>();
            }
        };
        LoginSingleton.getMySingletonInstance(context).addToRequestQue(stringRequest);
    }

    public Response.Listener responseListener(){
       return new Response.Listener() {
           @Override
           public void onResponse(Object response) {
               Toast.makeText(context,"Connected!!",Toast.LENGTH_SHORT);
           }
       };
    }

    public Response.ErrorListener errorResponseListener(){
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(context,"An error occurred!!",Toast.LENGTH_SHORT);
            }
        };
    }
}
