package com.example.matte.expandcollapse;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListAdapter  {



    /*private String[] names;
    private Activity context;

    public ListAdapter(Activity context, String[] names){
        super(context, R.layout.item_collapsed_listview, names);

        this.context = context;
        this.names = names;

    }

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    @NonNull
    @Override
    public Activity getContext() {
        return context;
    }

    public void setContext(Activity context) {
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View collapsedItem = inflater.inflate(R.layout.item_collapsed_listview,null, true);

        TextView name = collapsedItem.findViewById(R.id.text);
        name.setText(names[position]);

        return collapsedItem;
    }*/
}
