package com.tesina.smop_app.ViewHolder;

import android.view.View;
import android.widget.TextView;

import com.tesina.smop_app.Adapter.BaseListAdapter;
import com.tesina.smop_app.Product.UserProduct;
import com.tesina.smop_app.R;

public class UserViewHolder extends BaseViewHolder<UserProduct> {

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
