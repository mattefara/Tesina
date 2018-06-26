package com.tesina.smop_app.Threads;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tesina.smop_app.Product.DatabaseProduct;

import java.util.StringTokenizer;

public class DatabaseProductThread extends OperationOnFile<DatabaseProduct> {

    public static final int MODE_READ = 0;
    public static final int MODE_WRITE = 1;
    public static final int MODE_LOAD = 2;

    int mode;
    public String read = null;

    public DatabaseProductThread(Context context, String FILE_NAME, int mode) {
        super(context, FILE_NAME);
        this.mode = mode;
    }

    public DatabaseProductThread(Context context, String FILE_NAME, int mode, String text) {
        super(context, FILE_NAME, text);
        this.mode = mode;
    }


    @Override
    public void run() {
        Log.i("DatabaseProduct Thread", "start");
        switch (mode){
            case MODE_READ:{
                read = read();
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
    public DatabaseProduct applyDataOnTheScreen(String productTocken) {
        Log.i("applyData Database", "executed " + productTocken);
        StringTokenizer tokenizer = new StringTokenizer(productTocken,",");
        String name = tokenizer.nextToken();
        int quantity = Integer.parseInt(tokenizer.nextToken());
        float price = Float.parseFloat(tokenizer.nextToken());
        float discount = Float.parseFloat(tokenizer.nextToken());
        int img = Integer.parseInt(tokenizer.nextToken());
        String description = tokenizer.nextToken();
        String ingredients = tokenizer.nextToken();
        String brand = tokenizer.nextToken();
        String barcode = tokenizer.nextToken();

        return new DatabaseProduct(name, quantity, price, discount, img, description, ingredients, brand, barcode);
    }
}
