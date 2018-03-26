package com.example.matte.database_connection;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class MySingleton {

    private static MySingleton mySingletonInstance;
    private RequestQueue requestQueue;
    private static Context context;

    public MySingleton(Context context){
        this.context = context;
        requestQueue = getRequestQueue();

    }

    public RequestQueue getRequestQueue(){
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized MySingleton getMySingletonInstance(Context context){
        if (mySingletonInstance == null){
            mySingletonInstance = new MySingleton(context);
        }
        return mySingletonInstance;
    }

    public<T> void addToRequestQue(Request<T> request){
        requestQueue.add(request);
    }

}
