package com.example.matte.expandcollapse;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ListViewHolder extends RecyclerView.Adapter<ListViewHolder.ViewHolder> {
    List<ObjectExample> objects;
    Context contex;

    public ListViewHolder(List<ObjectExample> objects, Context contex) {
        this.objects = objects;
        this.contex = contex;
    }


    @Override
    public ListViewHolder.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(contex);
        View view = inflater.inflate(R.layout.item_collapsed_listview,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder.ViewHolder holder, int position) {
        holder.text.setText(objects.get(position).getName());
        //holder.img.setImageResource(objects.get(position).getImg());

    }


    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            img = itemView.findViewById(R.id.image);
        }
    }
}
