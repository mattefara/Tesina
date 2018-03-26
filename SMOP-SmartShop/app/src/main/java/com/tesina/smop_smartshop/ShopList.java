package com.tesina.smop_smartshop;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.internal.li;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ShopList extends Fragment {
    View view;
    Context context;
    int count_item;

    DatabaseReference listReference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    ExpandableListView shopList;
    List<ProductGroup> groupProductInformation;
    HashMap<ProductGroup, List<ProductDetails>> listHashMap;

    FloatingActionButton addItem;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_make_list,container,false);
        context = getActivity();
        addItem = view.findViewById(R.id.add_one_item);
        shopList = view.findViewById(R.id.shop_list);

        listHashMap = new HashMap<>();
        groupProductInformation = new ArrayList<>();

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildDialog(inflater);
            }
        });

        listReference = database.getReference("users/"+userUid+"/lists/").push();


        return view;
    }


    private void buildDialog(LayoutInflater inflater) {
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
                addData(dialogView);
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

    private void addData(View view) {
        String name = "Spaghetti";
        double price = 1.32;
        String branding = "brand";
        String ingr = "ingr";
        String desc = "desc";
        String barcode = "AAA";
        double disc = 50.0;
        int quantity = 5;

        groupProductInformation.add(new ProductGroup(name,price));

        List<ProductDetails> productDetails = new ArrayList<>();
        productDetails.add(new ProductDetails(branding,ingr,desc,barcode,disc,quantity));

        listHashMap.put(groupProductInformation.get(groupProductInformation.size()-1),productDetails);
        DatabaseReference productsReference  = listReference.push();
        productsReference.setValue(new Product(name,branding,ingr,desc,barcode,quantity,price,disc));
    }
}