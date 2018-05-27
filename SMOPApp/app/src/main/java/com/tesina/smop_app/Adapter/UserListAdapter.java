package com.tesina.smop_app.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.tesina.smop_app.Dialog.UserItemDialog;
import com.tesina.smop_app.Interfaces.MenuListener;
import com.tesina.smop_app.Interfaces.RecyclerItemClick;
import com.tesina.smop_app.Product.UserProduct;
import com.tesina.smop_app.R;
import com.tesina.smop_app.ViewHolder.UserViewHolder;

import java.util.Collections;
import java.util.Comparator;

public class UserListAdapter extends BaseListAdapter<UserProduct,UserViewHolder> {

    LayoutInflater inflater;
    MenuItem menuItem;
    private final int LAYOUT = R.layout.user_product;

    public UserListAdapter(Context context, LayoutInflater inflater) {
        super(context);
        this.inflater = inflater;
    }

    @Override
    public void sort() {
        Collections.sort(getList(), new Comparator<UserProduct>() {
            @Override
            public int compare(UserProduct userProduct, UserProduct t1) {
                return userProduct.getName().compareTo(t1.getName());
            }
        });
    }

    @Override
    public String toString() {
        String toString = "";
        for (UserProduct p: getList()) {
            toString += p.toString();
        }
        return toString;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(parent.getContext()).inflate(LAYOUT, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final UserViewHolder holder, int position) {
        holder.bind(getList().get(position));
        holder.setItemClick(new RecyclerItemClick() {
            @Override
            public void Onclick(View view, final int position, boolean isLongClicked) {
                if (!isLongClicked && !isSelectionMode()){
                    final UserItemDialog dialog = new UserItemDialog(getContext(), inflater);
                    dialog.buildDialog("Edit your product:", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String newName = dialog.productName.getText().toString();
                            int newQuantity = Integer.parseInt(dialog.productQuantity.getText().toString());
                            editOrAddProducts(newName,newQuantity,position);
                            }
                        });
                    int pQuantity = Integer.parseInt(holder.getQuantity().getText().toString().replace("x",""));
                    dialog.setInformation( holder.getName().getText().toString(), pQuantity);
                } else {
                    setSelectionMode(true);
                    UserProduct currentProduct = getList().get(position);
                    currentProduct.setSelected(!currentProduct.isSelected());
                    ColorDrawable[] colors = new ColorDrawable[2];
                    if (getList().get(position).isSelected()) {
                        colors[0] = new ColorDrawable(ITEM_UNSELECTED_COLOR);
                        colors[1] = new ColorDrawable(ITEM_SELECTED_COLOR);
                        getSelectedItems().add(position);
                    } else {
                        colors[0] = new ColorDrawable(ITEM_SELECTED_COLOR);
                        colors[1] = new ColorDrawable(ITEM_UNSELECTED_COLOR);
                        getSelectedItems().remove(getSelectedItems().indexOf(position));
                        if (getSelectedItems().size() == 0){
                            setSelectionMode(false);
                        }
                    }

                    TransitionDrawable transitionDrawable = new TransitionDrawable(colors);
                    Log.i("background isSelected", "" + currentProduct.isSelected() );
                    view.setBackground(transitionDrawable);
                    transitionDrawable.startTransition(150);
                    getMenuListener().setMenuItemVisible(isSelectionMode());
                }
            }
        });
    }

    private void editOrAddProducts(String newName,int newQuantity, int position){
        //TODO: CHECK FOR ENY BUG
        UserProduct newProduct = new UserProduct(newName,newQuantity);
        int index = indexOf(newProduct);
        if (index != -1){
            UserProduct currentProduct = getList().get(index);
            //product found, quantity could be edited
            int previousQuantity = currentProduct.getQuantity();
            if ( previousQuantity !=  newQuantity) {
                //Quantity edited
                currentProduct.setQuantity(newQuantity);
            }
            if (position != index){
                getList().remove(position);
            }
        } else {
            //product name edited and it is new
            getList().get(position).setName(newName);
            getList().get(position).setQuantity(newQuantity);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return getList().size();
    }

    @Override
    public void setMenuItemVisible(boolean isVisible) {
        menuItem.setVisible(isVisible);
    }

}
