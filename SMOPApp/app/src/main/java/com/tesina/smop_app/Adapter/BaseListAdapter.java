package com.tesina.smop_app.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.github.florent37.expansionpanel.ExpansionLayout;
import com.tesina.smop_app.Interfaces.MenuListener;
import com.tesina.smop_app.R;
import com.tesina.smop_app.ViewHolder.BaseViewHolder;

import java.net.IDN;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseListAdapter<T> extends RecyclerView.Adapter<BaseListAdapter<T>.BaseViewHolder<T>>{

    Context context;
    String FILE_NAME;

    private List<T> itemsList;
    private boolean multiSelect = false;
    private List<Integer> selectedItems = new ArrayList<>();
    public ActionMode actionMode;
    private ActionMode.Callback callback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.contextual_action_bar,menu);
            multiSelect = true;
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            actionMode = mode;
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()){
                case R.id.delete_selected: {
                    for (Integer intItem : selectedItems) {
                        deleteOne(intItem);
                        notifyItemRemoved(intItem);
                    }
                    break;
                }
            }
            saveChanges(itemsList);
            actionMode.finish();
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            multiSelect = false;
            selectedItems.clear();
            notifyDataSetChanged();
        }
    };


    public BaseListAdapter(Context context, List<T> itemsList){
        this.context = context;
        this.itemsList = (itemsList != null) ? itemsList : new ArrayList<T>();
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    protected abstract void saveChanges(List<T> itemsList);

    public void deleteOne(int position){
        itemsList.remove(position);
    }

    public abstract void sort();
    public abstract String toString();
    /*
        GETTER AND SETTER OF THE CLASS
    */

    public List<T> getItemsList() {
        return itemsList;
    }

    public void setItemsList(List<T> itemsList) {
        this.itemsList = itemsList;
    }

    public void addItem(T item){
        itemsList.add(item);
        notifyDataSetChanged();
    }

    public boolean isMultiSelect() {
        return multiSelect;
    }

    public void setMultiSelect(boolean multiSelect) {
        this.multiSelect = multiSelect;
    }

    public List<Integer> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<Integer> selectedItems) {
        this.selectedItems = selectedItems;
    }

    public ActionMode.Callback getCallback() {
        return callback;
    }

    public void setCallback(ActionMode.Callback callback) {
        this.callback = callback;
    }

    public ActionMode getActionMode() {
        return actionMode;
    }

    public void setActionMode(ActionMode actionMode) {
        this.actionMode = actionMode;
    }

    public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder{

        public BaseViewHolder(View itemView) {
            super(itemView);

        }

        //METHODS
        public void selectItem(Integer item){
            if (multiSelect){
                if (selectedItems.contains(item)){
                    selectedItems.remove(item);
                    itemView.setBackgroundColor(Color.WHITE);
                } else {
                    selectedItems.add(item);
                    itemView.setBackgroundColor(Color.LTGRAY);
                }
                if (selectedItems.size() == 0){
                    actionMode.finish();
                } else {
                    actionMode.setTitle(selectedItems.size() + " selected");
                }
            }
        }

        public void update(final Integer value){
            if (selectedItems.contains(value)) {
                itemView.setBackgroundColor(Color.LTGRAY);
            } else {
                itemView.setBackgroundColor(Color.WHITE);
            }
            addLongClickListener(value);
            addClickListener(value);

        }

        public void addLongClickListener(final Integer value){
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    ((AppCompatActivity)view.getContext()).startSupportActionMode(callback);
                    selectItem(value);
                    return true;
                }
            });
        }

        public BaseViewHolder<T> getHolder(){
            return this;
        }

        //ABSTRACT METHODS
        public abstract void addClickListener(Integer value);
        public abstract void bind(T item);
    }


}

    /*private List<Integer> selectedItems = new ArrayList<>();
    private boolean selectionMode;
    private Context context;
    private MenuListener menuListener;
    public final int ITEM_SELECTED_COLOR;
    public final int ITEM_UNSELECTED_COLOR;
    public final String FILE_NAME;

    public BaseListAdapter(Context context, String FILE_NAME) {
        this.context = context;
        this.FILE_NAME = FILE_NAME;
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
        Integer[] items = new Integer[selectedItems.size()];
        selectedItems.toArray( items );
        Arrays.sort(items);
        for (int i = items.length-1; i>=0; i--){
            deleteItem(items[i]);
            manager.removeViewAt(items[i]);
            notifyItemRemoved(items[i]);
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

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }*/

