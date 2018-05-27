package com.tesina.smop_app.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
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

public class ScannedListAdapter extends RecyclerView.Adapter<ScannedViewHolder>{

    List<DatabaseProduct> list = new ArrayList<>();
    ExpansionLayoutCollection collection = new ExpansionLayoutCollection();

    public ScannedListAdapter(){
        collection.openOnlyOne(true);
    }

    public ScannedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ScannedViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.scanned_product,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ScannedViewHolder holder, int position) {
        holder.bind(list.get(position));

        collection.add(holder.getExpansionLayout());
    }

    public int getItemCount() {
        return list.size();
    }

    public void setItemList(List<DatabaseProduct> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void setItemList(DatabaseProduct product){
        this.list.add(product);
    }


}
