package com.tesina.smop_app.ViewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.expansionpanel.ExpansionLayout;
import com.tesina.smop_app.Adapter.ScannedListAdapter;
import com.tesina.smop_app.Product.DatabaseProduct;
import com.tesina.smop_app.R;

public class ScannedViewHolder extends BaseViewHolder<DatabaseProduct> {

    private TextView name, quantity,brand,barcode,ingredients,description;
    private ImageView image;
    private ExpansionLayout expansionLayout;


    public ScannedViewHolder (View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.prod_name);
        quantity = itemView.findViewById(R.id.product_quantity);
        brand = itemView.findViewById(R.id.product_branding);
        barcode = itemView.findViewById(R.id.product_barcode);
        ingredients = itemView.findViewById(R.id.product_ingredients);
        description = itemView.findViewById(R.id.product_description);
        image = itemView.findViewById(R.id.product_picture);

        expansionLayout = itemView.findViewById(R.id.expansion_layout);
    }

    public void bind(DatabaseProduct databaseProduct){

        name.setText(databaseProduct.getName());
        quantity.setText("1x");
        brand.setText(databaseProduct.getBrand());
        barcode.setText(databaseProduct.getBarcode());
        ingredients.setText(databaseProduct.getIngredients());
        description.setText(databaseProduct.getDescription());
        image.setImageResource(databaseProduct.getImg());

        expansionLayout.collapse(false);

    }

    public ExpansionLayout getExpansionLayout() {
        return expansionLayout;
    }


}
