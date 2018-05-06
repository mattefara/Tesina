package tesina.smop_smart_shop.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.expansionpanel.ExpansionLayout;
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection;

import java.util.ArrayList;
import java.util.List;

import tesina.smop_smart_shop.Products.ListProduct;
import tesina.smop_smart_shop.R;

public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ViewHolder>{

    List<ListProduct> list = new ArrayList<>();
    ExpansionLayoutCollection collection = new ExpansionLayoutCollection();

    public ShopListAdapter(){
        collection.openOnlyOne(true);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.bindFor(parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(list.get(position));

        collection.add(holder.getExpansionLayout());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItemList(List<ListProduct> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void setItemList(ListProduct product){
        this.list.add(product);
    }

    public final static class ViewHolder extends RecyclerView.ViewHolder {

        private final static int LAYOUT = R.layout.collapsed_product_list_item;
        private ImageView img;
        private TextView name, quantity, description, discount, price, brand, ingredients, barcode;
        private final TextView money;
        private ExpansionLayout expansionLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.product_picture);
            name = itemView.findViewById(R.id.product_name);
            brand = itemView.findViewById(R.id.product_branding);
            ingredients = itemView.findViewById(R.id.product_ingredients);
            barcode = itemView.findViewById(R.id.product_barcode);
            quantity = itemView.findViewById(R.id.quantity);
            description = itemView.findViewById(R.id.product_description);
            discount = itemView.findViewById(R.id.product_discount);
            price = itemView.findViewById(R.id.price);

            money = itemView.findViewById(R.id.money);

            expansionLayout = itemView.findViewById(R.id.expansion_layout);
        }

        public static ViewHolder bindFor(ViewGroup viewGroup){
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(LAYOUT, viewGroup, false));
        }

        public void bind(ListProduct p){
            img.setImageResource(p.getImg());
            name.setText(p.getProductName());

            checkExistingStringAttribute(p.getBrand(),brand,"Band");
            checkExistingStringAttribute(p.getIngredients(),ingredients,"Ingredients");
            checkExistingStringAttribute(p.getBarcode(),barcode,"Barcode");
            checkExistingStringAttribute(p.getDescription(),description,"Description");

            quantity.setText((p.getPrice() == 0) ? "x" + p.getQuantity() : (p.getQuantity() + "x"));
            if (p.getPrice() == 0){
                price.setVisibility(View.GONE);
                money.setVisibility(View.GONE);
                quantity.setPadding(0,0,40,0);
                quantity.setTextSize(20);
            } else {
                price.setText(String.valueOf(p.getPrice()));
            }



            expansionLayout.collapse(false);
        }

        public void checkExistingStringAttribute(String attr, TextView tw, String msg){
            if (attr.equals("")) {
                tw.setVisibility(View.GONE);
            } else {
                tw.setText(msg + ": " +attr);
            }
        }

        public ExpansionLayout getExpansionLayout() {
            return expansionLayout;
        }
    }
}
