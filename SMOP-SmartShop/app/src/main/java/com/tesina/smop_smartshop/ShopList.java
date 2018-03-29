package com.tesina.smop_smartshop;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;


public class ShopList extends Fragment {
    final String LIST_FILE_NAME = "list.txt";

    View view;
    Context context;
    int count_item;

    DatabaseReference listReference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    ExpandableListView shopList;
    List<ProductGroup> groupProductInformation;
    HashMap<ProductGroup, List<ProductDetails>> listHashMap;

    FloatingActionButton addItem, deleteList;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //Init data
        view = inflater.inflate(R.layout.activity_make_list,container,false);
        addItem = view.findViewById(R.id.add_one_item);
        deleteList = view.findViewById(R.id.deleteList);
        shopList = view.findViewById(R.id.shop_list);

        listHashMap = new HashMap<>();
        groupProductInformation = new ArrayList<>();

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildAddItemDialog(inflater);
            }
        });

        deleteList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildDeleteDialog("Confirm","Cancel","Are you sure?");
            }
        });

        listReference = database.getReference("users/"+userUid+"/lists/").push();

        loadData();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        //Toast.makeText(context, readFile(), Toast.LENGTH_SHORT ).show();

    }

    private void buildAddItemDialog(LayoutInflater inflater) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View dialogView = inflater.inflate(R.layout.dialog_add_item, null);

        count_item = 0;

        //set buttons and other alert things
        ImageView add = dialogView.findViewById(R.id.add_one_item);
        ImageView remove = dialogView.findViewById(R.id.remove_one_item);
        final TextView countItem = dialogView.findViewById(R.id.number_of_item_to_add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count_item ++;
                countItem.setText(String.valueOf(count_item));
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count_item > 0){ count_item --; }
                countItem.setText(String.valueOf(count_item));
            }
        });

        builder.setPositiveButton("Conferma", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addData();
                shopList.setAdapter(new ExpandableShopListAdapter(context,groupProductInformation,listHashMap));

            }
        });

        builder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void buildDeleteDialog(String posiveAction, String negativeAction, String builderContent){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton(posiveAction, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                writeOnFile("", Context.MODE_PRIVATE);
                groupProductInformation = new ArrayList<>();
                listHashMap = new HashMap<>();
                shopList.setAdapter(new ExpandableShopListAdapter(context,groupProductInformation,listHashMap));
            }
        });
        builder.setNegativeButton(negativeAction, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setMessage(builderContent);
        builder.show();
    }

    private void addData() {
        String name = "Manzo";
        double price = 1.50;
        String branding = "Mucca";
        String ingr = "bella vez";
        String desc = "Molto Buono!";
        String barcode = "XYZ";
        double disc = 2.0;
        int quantity = 1;

        groupProductInformation.add(new ProductGroup(name,price,quantity,disc));

        List<ProductDetails> productDetails = new ArrayList<>();
        productDetails.add(new ProductDetails(branding,ingr,desc,barcode));

        listHashMap.put(groupProductInformation.get(groupProductInformation.size()-1),productDetails);

        DatabaseReference productsReference  = listReference.push();
        Product product = new Product(name,branding,ingr,desc,barcode,quantity,price,disc);
        productsReference.setValue(product);

        writeOnFile(product.toString(), Context.MODE_APPEND);

    }

    private void loadData(){
        String data = readFile();
        if (!data.equals("")){
            StringTokenizer tokenizer = new StringTokenizer(data,";");
            while (tokenizer.hasMoreElements()){
                String productToken = tokenizer.nextToken();
                Log.i("TOKEN:",productToken);
                applyDataOnTheScreen(productToken);
            }
        }
    }

    private void applyDataOnTheScreen(String productToken) {
        StringTokenizer productFields = new StringTokenizer(productToken,",");
        String name = productFields.nextToken();
        String branding = productFields.nextToken();
        String ingr = productFields.nextToken();
        String desc = productFields.nextToken();
        String barcode = productFields.nextToken();
        int quantity = Integer.parseInt(productFields.nextToken());
        double price = Double.parseDouble(productFields.nextToken());
        double disc = Double.parseDouble(productFields.nextToken());

        groupProductInformation.add(new ProductGroup(name,price,quantity,disc));

        List<ProductDetails> productDetails = new ArrayList<>();
        productDetails.add(new ProductDetails(branding,ingr,desc,barcode));

        listHashMap.put(groupProductInformation.get(groupProductInformation.size()-1),productDetails);
        shopList.setAdapter(new ExpandableShopListAdapter(context,groupProductInformation,listHashMap));
    }

    public void writeOnFile(String s, int contextMode){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(LIST_FILE_NAME, contextMode));
            outputStreamWriter.write(s);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }

    }

    public String readFile(){
        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(LIST_FILE_NAME);

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
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }
}