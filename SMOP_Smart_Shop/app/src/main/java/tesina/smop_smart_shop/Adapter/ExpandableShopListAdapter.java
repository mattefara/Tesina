package tesina.smop_smart_shop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;


import java.util.HashMap;
import java.util.List;

import tesina.smop_smart_shop.Products.ProductDetails;
import tesina.smop_smart_shop.Products.ProductGroup;
import tesina.smop_smart_shop.R;

/**
 * I use recyclerView instead
 */

public class ExpandableShopListAdapter extends BaseExpandableListAdapter {
    @Override
    public int getGroupCount() {
        return 0;
    }

    @Override
    public int getChildrenCount(int i) {
        return 0;
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
    /*Context context;
    List<ProductGroup> listHeader;
    HashMap<ProductGroup,List<ProductDetails>> listHashMap;

    public ExpandableShopListAdapter(Context context, List<ProductGroup> listHeader, HashMap<ProductGroup, List<ProductDetails>> listHashMap) {
        this.context = context;
        this.listHeader = listHeader;
        this.listHashMap = listHashMap;
    }

    @Override
    public int getGroupCount() {
        return listHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listHashMap.get(listHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listHashMap.get(listHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ProductGroup group = (ProductGroup) getGroup(groupPosition);
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.product_group,null);
        }
        TextView name = convertView.findViewById(R.id.product_group_name);
        TextView price = convertView.findViewById(R.id.group_price);
        TextView quantity = convertView.findViewById(R.id.group_quantity);
        TextView discount = convertView.findViewById(R.id.group_discount);
        name.setText(group.getProductName());
        price.setText(String.valueOf(group.getPrice()));
        quantity.setText(String.valueOf(group.getQuantity()));
        discount.setText(String.valueOf(group.getDiscount()));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ProductDetails child = (ProductDetails) getChild(groupPosition,childPosition);
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.product_detail,null);
        }
        TextView ingredients = convertView.findViewById(R.id.product_ingredients);
        TextView branding = convertView.findViewById(R.id.product_branding);
        TextView details = convertView.findViewById(R.id.product_details);
        TextView barCode = convertView.findViewById(R.id.product_barcode);

        ingredients.setText(child.getIngredients());
        branding.setText(child.getBranding());
        details.setText(child.getDescription());
        barCode.setText(child.getBarCode());
        return convertView;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }*/
}
