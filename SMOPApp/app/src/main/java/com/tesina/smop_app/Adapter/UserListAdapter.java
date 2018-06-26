package com.tesina.smop_app.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.expansionpanel.ExpansionLayout;
import com.google.android.gms.common.api.Api;
import com.tesina.smop_app.Dialog.UserItemDialog;
import com.tesina.smop_app.Product.UserProduct;
import com.tesina.smop_app.R;
import com.tesina.smop_app.Threads.UserProductThread;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UserListAdapter extends BaseListAdapter<UserProduct> {

    LayoutInflater inflater;

    public UserListAdapter(Context context, List<UserProduct> itemsList, LayoutInflater inflater) {
        super(context, itemsList);
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public BaseViewHolder<UserProduct> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_product,parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<UserProduct> holder, int position) {
        holder.bind(getItemsList().get(position));
        holder.update(position);
    }

    @Override
    protected void saveChanges(List<UserProduct> itemsList) {
        //TODO: WRITE ON FILE CHANGES
    }

    @Override
    public void sort() {
        //TODO: SORT PRODUCT BY NAME
    }

    @Override
    public String toString() {
        String toString = "";
        for (UserProduct p: getItemsList()) {
            toString += p.toString();
        }
        return toString;
    }



    public class UserViewHolder extends BaseListAdapter<UserProduct>.BaseViewHolder<UserProduct>{

        private TextView name, quantity;


        public UserViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.prod_name);
            quantity = itemView.findViewById(R.id.prod_quantity);

        }

        @Override
        public void bind(UserProduct userProduct){
            name.setText(userProduct.getName());
            String q = "x" + userProduct.getQuantity();
            quantity.setText(q);
        }

        @Override
        public void addClickListener(final Integer value) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isMultiSelect()){
                        selectItem(value);
                    } else {
                        final UserItemDialog dialog = new UserItemDialog(context, inflater);
                        dialog.buildDialog(context.getString(R.string.edit_product_dialog), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String newName = dialog.productName.getText().toString();
                                int newQuantity = Integer.parseInt(dialog.productQuantity.getText().toString());
                                checkProduct(newName, newQuantity, getAdapterPosition());

                                sort();
                                notifyDataSetChanged();
                            }
                        });
                    }
                }
            });
        }

        public void checkProduct(String newName, int newQuantity, int position){
            UserProduct newProduct = new UserProduct(newName,newQuantity);
            int index = findName(newName);
            if (index != -1){
                UserProduct currentProduct = getItemsList().get(index);
                //product found, quantity could be edited
                int previousQuantity = currentProduct.getQuantity();
                if ( previousQuantity !=  newQuantity) {
                    //Quantity edited
                    currentProduct.setQuantity(newQuantity);
                }
                if (position != index){
                    getItemsList().remove(position);
                }
            } else {
                //product name edited and it is new
                getItemsList().get(position).setName(newName);
                getItemsList().get(position).setQuantity(newQuantity);
            }
            //UserProductThread productThread = new UserProductThread(context,FILE_NAME, UserProductThread.MODE_WRITE , this.toString(), Context.MODE_PRIVATE );
            //productThread.start();
        }

        public int findName(String name){
            for (int i = 0; i< getItemsList().size(); i++){
                if (getItemsList().get(i).getName().equals(name)){
                    return i;
                }
            }
            return -1;
        }

        public TextView getName() {
            return name;
        }

        public void setName(TextView name) {
            this.name = name;
        }

        public TextView getQuantity() {
            return quantity;
        }

        public void setQuantity(TextView quantity) {
            this.quantity = quantity;
        }


    }
}

















    /*LayoutInflater inflater;
    MenuItem menuItem;
    private final int LAYOUT = R.layout.user_product;

    public UserListAdapter(Context context, String FILE_NAME, LayoutInflater inflater) {
        super(context, FILE_NAME);
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
        UserProductThread productThread = new UserProductThread(getContext(),FILE_NAME, UserProductThread.MODE_WRITE , this.toString(), Context.MODE_PRIVATE );
        productThread.start();
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
*/

