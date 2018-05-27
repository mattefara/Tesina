package com.tesina.smop_app.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tesina.smop_app.Interfaces.MenuListener;
import com.tesina.smop_app.Interfaces.RecyclerItemClick;
import com.tesina.smop_app.R;
import com.tesina.smop_app.ViewHolder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseListAdapter<T,V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> implements MenuListener{

    private List<T> itemsList = new ArrayList<>();
    private List<Integer> selectedItems = new ArrayList<>();
    private boolean selectionMode;
    private Context context;
    private MenuListener menuListener;
    public final int ITEM_SELECTED_COLOR;
    public final int ITEM_UNSELECTED_COLOR;

    public BaseListAdapter(Context context) {
        this.context = context;
        ITEM_SELECTED_COLOR = Color.parseColor('#' + Integer.toHexString(ContextCompat.getColor(context, R.color.colorPrimary)));
        ITEM_UNSELECTED_COLOR = Color.parseColor('#' + Integer.toHexString(ContextCompat.getColor(context,R.color.white)));
    }

    public List<T> getList(){
        return itemsList;
    }

    public void setItemsList(List<T> items){
        itemsList = items;
    }

    public void setItemList(T item){
        itemsList.add(item);
    }

    public void deleteItem(int position){
        itemsList.remove(position);
        this.notifyDataSetChanged();
    }

    public void deleteItems(){
        for (int i : selectedItems){
            deleteItem(i);
            notifyItemRemoved(i);
        }
        selectedItems.clear();
        selectionMode = false;
        menuListener.setMenuItemVisible(false);
    }

    public void deleteAll(){
        itemsList.clear();
    }

    public abstract void sort();

    public abstract String toString();

    public int indexOf(T comparator){
        int index = 0;
        while (index < itemsList.size()){
            if (itemsList.get(index).equals(comparator)){
                return index;
            }
            index ++;
        }
        return -1;
    }

    public void setMenuListener(MenuListener menuListener) {
        this.menuListener = menuListener;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Integer> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<Integer> selectedItems) {
        this.selectedItems = selectedItems;
    }

    public boolean isSelectionMode() {
        return selectionMode;
    }

    public void setSelectionMode(boolean selectionMode) {
        this.selectionMode = selectionMode;
    }

    public MenuListener getMenuListener() {
        return menuListener;
    }
}
