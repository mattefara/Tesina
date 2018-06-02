package com.tesina.smop_app.Threads;

import android.content.Context;
import android.util.Log;

import com.tesina.smop_app.Product.UserProduct;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class UserProductThread extends OperationOnFile<UserProduct>{

    public static final int MODE_READ = 0;
    public static final int MODE_WRITE = 1;
    public static final int MODE_LOAD = 2;

    int mode;

    public UserProductThread(Context context, String FILE_NAME, int mode) {
        super(context, FILE_NAME);
        this.mode = mode;
    }

    public UserProductThread(Context context, String FILE_NAME, int mode, String text, int contextMode) {
        super(context, FILE_NAME, text, contextMode);
        this.mode = mode;
    }


    @Override
    public void run() {
        Log.i("Thread manager", "start");
        switch (mode){
            case MODE_READ:{
                read();
                break;
            }
            case MODE_WRITE:{
                write();
                break;
            }
            case MODE_LOAD:{
                load();
                break;
            }
        }
    }


    @Override
    public UserProduct applyDataOnTheScreen(String productToken) {

        StringTokenizer productFields = new StringTokenizer(productToken,",");
        String name = productFields.nextToken();
        int quantity = Integer.parseInt(productFields.nextToken());

        return new UserProduct(name,quantity);
    }
}
