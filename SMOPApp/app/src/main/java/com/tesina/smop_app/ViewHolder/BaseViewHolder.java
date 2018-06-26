package com.tesina.smop_app.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tesina.smop_app.Interfaces.RecyclerItemClick;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder{


    public BaseViewHolder(View itemView) {
        super(itemView);
    }



    /*
        ABSTRACT METHODS
     */

    public abstract void bind(T item);
}
