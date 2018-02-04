package com.tesina.smop_smartshop;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

class LoginSingleton {
    private static LoginSingleton mySingletonInstance;
    private RequestQueue requestQueue;
    private static Context context;

    public LoginSingleton(Context context){
        this.context = context;
        requestQueue = getRequestQueue();

    }

    public RequestQueue getRequestQueue(){
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized LoginSingleton getMySingletonInstance(Context context){
        if (mySingletonInstance == null){
            mySingletonInstance = new LoginSingleton(context);
        }
        return mySingletonInstance;
    }

    public<T> void addToRequestQue(Request<T> request){
        requestQueue.add(request);
    }
}
