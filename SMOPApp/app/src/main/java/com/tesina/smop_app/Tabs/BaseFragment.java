package com.tesina.smop_app.Tabs;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tesina.smop_app.Dialog.UserItemDialog;
import com.tesina.smop_app.Product.DatabaseProduct;
import com.tesina.smop_app.Product.UserProduct;
import com.tesina.smop_app.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

public class BaseFragment extends Fragment {

    protected String titleDialog;
    protected String productName;
    protected int quantity;


    public void writeOnFile(String s,Context context, int contextMode, String FILE_NAME){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(FILE_NAME, contextMode));
            outputStreamWriter.write(s);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }

    }

    public String readFile(Context context, String FILE_NAME){
        String ret = "";
        try {
            InputStream inputStream = context.openFileInput(FILE_NAME);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
            writeOnFile("",context,Context.MODE_PRIVATE, FILE_NAME);
            Log.i("BaseFragment", "File created");
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return ret;
    }

    /*public DatabaseProduct loadData(Context context, String FILE_NAME){
        String data = readFile(context, FILE_NAME);
        if (!data.equals("")){
            StringTokenizer tokenizer = new StringTokenizer(data,";");
            while (tokenizer.hasMoreElements()){
                String productToken = tokenizer.nextToken();
                Log.i("TOKEN:",productToken);
                return applyDataOnTheScreen(productToken);
            }
        }
        return null;
    }*/

    /*private DatabaseProduct applyDataOnTheScreen(String productToken) {
        StringTokenizer productFields = new StringTokenizer(productToken,",");
        String name = productFields.nextToken();
        int quantity = Integer.parseInt(productFields.nextToken());
        double price = Double.parseDouble(productFields.nextToken());
        double disc = Double.parseDouble(productFields.nextToken());
        int img = Integer.parseInt(productFields.nextToken());
        String desc = productFields.nextToken();
        String ingr = productFields.nextToken();
        String brand = productFields.nextToken();
        String barcode = productFields.nextToken();

        return new DatabaseProduct(name,quantity,price,disc,img,desc,ingr,brand,barcode);

    }*/

    public List<UserProduct> loadData(Context context, String FILE_NAME){
        String data = readFile(context, FILE_NAME);
        List<UserProduct> list =new ArrayList<>();
        if (!data.equals("")){
            StringTokenizer tokenizer = new StringTokenizer(data,";");
            while (tokenizer.hasMoreElements()){
                String productToken = tokenizer.nextToken();
                Log.i("TOKEN:",productToken);
                list.add( applyDataOnTheScreen(productToken));
            }
        }
        return (list.size()>0) ? list : null;
    }

    private UserProduct applyDataOnTheScreen(String productToken) {
        StringTokenizer productFields = new StringTokenizer(productToken,",");
        String name = productFields.nextToken();
        int quantity = Integer.parseInt(productFields.nextToken());

        return new UserProduct(name,quantity);

    }

}
