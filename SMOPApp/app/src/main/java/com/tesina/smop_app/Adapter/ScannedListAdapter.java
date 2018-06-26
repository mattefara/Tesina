package com.tesina.smop_app.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.icu.util.ValueIterator;
import android.opengl.Visibility;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.florent37.expansionpanel.ExpansionLayout;
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection;
import com.tesina.smop_app.Product.DatabaseProduct;
import com.tesina.smop_app.Product.UserProduct;
import com.tesina.smop_app.R;
import com.tesina.smop_app.Tabs.ScannedList;
import com.tesina.smop_app.ViewHolder.ScannedViewHolder;

import java.util.ArrayList;
import java.util.List;

/*
* TODO: CLASS EXTENSION BECAUSE UserListAdapter AND ScannedListAdapter ARE REALLY SIMILAR
*/

public class ScannedListAdapter extends BaseListAdapter<DatabaseProduct>{

    private int expandedPosition = -1;
    private RecyclerView recyclerView;

    public ScannedListAdapter(Context context, List<DatabaseProduct> itemsList, RecyclerView recyclerView) {
        super(context, itemsList);
        this.recyclerView = recyclerView;
    }

    @Override
    protected void saveChanges(List<DatabaseProduct> itemsList) {
        //TODO: WRITE ON FILE
}

    @Override
    public void sort() {
        //TODO: SORT BY NAME
    }

    @NonNull
    @Override
    public BaseViewHolder<DatabaseProduct> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scanned_product,parent, false);
        return new ScannedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<DatabaseProduct> holder, int position) {
        holder.bind(getItemsList().get(position));
        holder.update(position);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public String toString() {
        String toString = "";
        for (DatabaseProduct p: getItemsList()) {
            toString += p.toString();
        }
        return toString;
    }

    public int indexOf(String name){
        for (int i=0; i<getItemsList().size(); i++){
            if (getItemsList().get(i).getName().equals(name)){
                return i;
            }
        }
        return -1;
    }


    public class ScannedViewHolder extends BaseListAdapter<DatabaseProduct>.BaseViewHolder<DatabaseProduct>{

        private TextView name, quantity,brand,barcode,ingredients,description,discount,price;
        private ImageView image;
        private RelativeLayout expandedLayout;
        boolean isExpanded = false;

        Transition transition;


        public ScannedViewHolder (View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.prod_name);
            quantity = itemView.findViewById(R.id.product_quantity);
            brand = itemView.findViewById(R.id.product_branding);
            barcode = itemView.findViewById(R.id.product_barcode);
            price = itemView.findViewById(R.id.product_price);
            ingredients = itemView.findViewById(R.id.product_ingredients);
            description = itemView.findViewById(R.id.product_description);
            discount = itemView.findViewById(R.id.product_discount);
            image = itemView.findViewById(R.id.product_picture);

            transition = TransitionInflater.from(context).inflateTransition(R.transition.expand);

            expandedLayout = itemView.findViewById(R.id.expansion_layout);
        }

        @Override
        public void addClickListener(final Integer value) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isMultiSelect()) {
                        if (expandedPosition != -1) {
                            expandedLayout.setVisibility((isExpanded) ? View.GONE : View.VISIBLE);
                        } else {
                            expandedLayout.setVisibility(View.VISIBLE);
                        }
                        TransitionManager.beginDelayedTransition(recyclerView,transition);
                        isExpanded = !isExpanded;
                        expandedPosition = getAdapterPosition();
                    } else {
                        selectItem(value);
                    }
                }
            });


        }

        @SuppressLint("SetTextI18n")
        public void bind(DatabaseProduct databaseProduct){

            name.setText(databaseProduct.getName());
            quantity.setText(databaseProduct.getQuantity() +"x");
            brand.setText(context.getResources().getString(R.string.brand) + " " + databaseProduct.getBrand());
            barcode.setText(context.getResources().getString(R.string.barcode) + " " + databaseProduct.getBarcode());
            ingredients.setText(context.getResources().getString(R.string.ingredient) + " " + databaseProduct.getIngredients());
            discount.setText(databaseProduct.getDiscount() + " " + context.getResources().getString(R.string.discount));
            description.setText(context.getResources().getString(R.string.description) + " " + databaseProduct.getDescription());
            image.setImageResource(databaseProduct.getImg());
            price.setText("" + databaseProduct.getPrice());
        }
    }


}
