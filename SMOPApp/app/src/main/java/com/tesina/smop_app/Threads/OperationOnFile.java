package com.tesina.smop_app.Threads;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public abstract class OperationOnFile<T> extends Thread {

    private List<T> items;

    Context context;
    int mode;
    int contextMode;
    String text;
    public final String FILE_NAME;

    public OperationOnFile(Context context, String FILE_NAME){
        this.context = context;
        this.FILE_NAME = FILE_NAME;
        this.items = new ArrayList<>();
        this.text = "";
    }

    public OperationOnFile(Context context, String FILE_NAME, String text, int contextMode){
        this.context = context;
        this.FILE_NAME = FILE_NAME;
        this.text = text;
        this.contextMode = contextMode;
        this.items = new ArrayList<>();
    }



    public String read(){
        String data = "";
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
                data = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
            write();
            Log.i("BaseFragment", "File created");
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return data;
    }

    public void write(){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(FILE_NAME, contextMode));
            outputStreamWriter.write(text);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Error", e.toString());
        }
    }

    public void load(){
        String data = read();
        List<T> list =new ArrayList<>();
        if (!data.equals("")){
            StringTokenizer tokenizer = new StringTokenizer(data,";");
            while (tokenizer.hasMoreElements()){
                String productToken = tokenizer.nextToken();
                Log.i("TOKEN:",productToken);
                list.add( applyDataOnTheScreen(productToken));
            }
        }
        items = (list.size()>0) ? list : null;
    }

    public abstract T applyDataOnTheScreen(String productTocker);

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
