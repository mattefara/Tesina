package com.tesina.smop_app.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tesina.smop_app.R;

public class UserItemDialog extends AlertDialog.Builder{

    public Context context;
    public View dialogView;
    public TextView productName;
    public TextView productQuantity;
    public ImageView addOne;
    public ImageView removeOne;


    public UserItemDialog(@NonNull Context context, LayoutInflater inflater) {
        super(context);
        this.context = context;
        this.dialogView = inflater.inflate(R.layout.user_item_dialog, null);
    }


    public void bindDialog(){

        productName = dialogView.findViewById(R.id.new_user_product_name);
        productQuantity = dialogView.findViewById(R.id.new_user_product_quantity);
        addOne = dialogView.findViewById(R.id.new_user_product_add);

        addOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(productQuantity.getText().toString()) + 1;
                productQuantity.setText(String.valueOf(quantity));
            }
        });

        removeOne = dialogView.findViewById(R.id.new_user_product_remove);

        removeOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(productQuantity.getText().toString());
                if (quantity > 1){
                    productQuantity.setText(String.valueOf(quantity - 1 ));
                }
            }
        });

    }

    public void buildDialog( final String title, DialogInterface.OnClickListener positiveClick){

        this.setView(this.dialogView);
        this.setTitle(title);
        this.bindDialog();
        this.setPositiveButton(R.string.ok, positiveClick);
        this.setNegativeButton("UNDO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        final android.app.AlertDialog dialog = this.create();
        dialog.show();

        dialog.getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE).setEnabled(false);

        this.productName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)) {
                    // Disable ok button
                    dialog.getButton(
                            android.support.v7.app.AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                } else {
                    // Something into edit text. Enable the button.
                    dialog.getButton(
                            android.support.v7.app.AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                }
            }
        });
    }

    public void setInformation(String productName, int quantity){
        this.productName.setText(productName);
        this.productQuantity.setText(String.valueOf(quantity));
    }


}
