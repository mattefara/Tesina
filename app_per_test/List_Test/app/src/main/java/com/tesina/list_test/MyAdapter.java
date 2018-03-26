package com.tesina.list_test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Oggetto> list;
    private Context context;

    public MyAdapter(List<Oggetto> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.product_layout, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            Oggetto o = list.get(position);
            holder.name.setText(o.getNome());
            holder.imageView.setImageResource(o.getImg());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

            TextView name, lastName, textViewRating, textViewPrice;
            ImageView imageView;

            public ViewHolder(View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.name);
                imageView = itemView.findViewById(R.id.last_name);
                }
        }


    // Provide a suitable constructor (depends on the kind of dataset)

    // Create new views (invoked by the layout manager)




}
