package com.tesina.smop_smartshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by matte on 22/02/2018.
 */

public class ExpandableShopListAdapter extends BaseExpandableListAdapter {
    Context context;
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
    }
}
