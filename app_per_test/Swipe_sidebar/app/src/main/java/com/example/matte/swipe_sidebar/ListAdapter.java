package com.example.matte.swipe_sidebar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    List<SidebarAction> product;
    Context context;

    public ListAdapter(List<SidebarAction> productList, Context context) {
        this.product = productList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.sidebar_list_item, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SidebarAction action = product.get(position);
        holder.actionName.setText(action.getActionName());
        holder.icon.setImageResource(action.getIcon());
    }

    @Override
    public int getItemCount() {
        return product.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView actionName;
        ImageView icon;
        //ImageView immagine;


        public ViewHolder(View itemView) {
                super(itemView);
                //immagine = itemView.findViewById(R.id.immagine);
                actionName = itemView.findViewById(R.id.action_name);
                icon = itemView.findViewById(R.id.icon);

        }

    }
}
