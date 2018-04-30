package com.example.matte.sceneexample;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.transition.Scene;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.zip.CheckedOutputStream;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ListViewHolder> {

    private ArrayList<ExampleObject> objects;
    Context context;
    private int expandedPosition = -1;
    RecyclerView recyclerView;


    public ItemListAdapter(ArrayList<ExampleObject> objects, Context context, RecyclerView recyclerView) {
        this.objects = objects;
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.scene_root,parent,false);
        ListViewHolder viewHolder = new ListViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ListViewHolder holder,final int position) {
        holder.bindObject(objects.get(position));
        final boolean isExpanded = position == expandedPosition;
        holder.imageView.setVisibility((isExpanded) ? View.VISIBLE : View.GONE);
        holder.itemView.setActivated(isExpanded);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandedPosition = isExpanded ? -1 : position;
                TransitionManager.beginDelayedTransition(recyclerView);
                //notifyDataSetChanged();
                //notifyItemChanged(position);
                notifyDataSetChanged();
            }
        });
        /*holder.itemClickListener = new ItemClickListener() {
            @Override
            public void onClick(View view, final int position, boolean isLongClick) {
               /* ViewGroup item = (ViewGroup) root.getChildAt(position);
                Scene sceneA = Scene.getSceneForLayout(item,R.layout.a_scene, context);
                Scene sceneB = Scene.getSceneForLayout(item,R.layout.b_scene, context);

                TransitionSet transitionSet = (TransitionSet) TransitionInflater.from(context).inflateTransition(R.transition.adding_to_layout);
                if (isAlreaySelected){
                    TransitionManager.go(sceneB, transitionSet);
                } else {
                    TransitionManager.go(sceneA, transitionSet);
                }
                isAlreaySelected = !isAlreaySelected;




                String option = (isLongClick) ? "lungo" : "corto";
                Toast.makeText(context,"premuto " + position + " " + option, Toast.LENGTH_SHORT).show();
            }
        };*/
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{
        //set attribute for the custom layout item
        TextView phrase1, phrase2;
        ImageView imageView;

        private ItemClickListener itemClickListener;

        public ListViewHolder(View itemView) {
            super(itemView);

            phrase1 = itemView.findViewById(R.id.text_1);
            phrase2 = itemView.findViewById(R.id.text_2);
            imageView = itemView.findViewById(R.id.image);

        }

        public void bindObject(ExampleObject object){
            phrase1.setText(object.getText1());
            phrase2.setText(object.getText2());
            imageView.setImageResource(object.getImage());
        }



    }

}
