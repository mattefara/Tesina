package com.example.matte.sceneexample;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends Activity {
    Scene sceneA, sceneB;
    ViewGroup root;
    Button startButton;
    Context context;
    RecyclerView recyclerView;

    boolean selectedSceneA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //root = findViewById(R.id.scene_root);
        context = this;

        //sceneA = Scene.getSceneForLayout(root,R.layout.a_scene, context);
        //sceneB = Scene.getSceneForLayout(root,R.layout.b_scene, context);
        //startButton = findViewById(R.id.start_animation);
        selectedSceneA = true;
        recyclerView = findViewById(R.id.recycler_view);

        ArrayList<ExampleObject> objects = new ArrayList<>();
        for (int i = 0 ; i < 10; i++){
            objects.add(new ExampleObject("Riga " + i, "Riga 2 " + i, R.drawable.ic_launcher_background));
        }
        ItemListAdapter itemsAdapter = new ItemListAdapter(objects, context,recyclerView);
        recyclerView.setAdapter(itemsAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Transition fadeTransition = TransitionInflater.from(context).inflateTransition(R.transition.fade_transition);

        /*startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Transition fadeTransition = new Fade();
                if (selectedSceneA){
                    TransitionManager.go(sceneB,fadeTransition);
                } else {
                    TransitionManager.go(sceneA, fadeTransition);

                }
                selectedSceneA = !selectedSceneA;
            }
        });*/

        /*startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransitionSet transitionSet = (TransitionSet) TransitionInflater.from(context).inflateTransition(R.transition.adding_to_layout);
                if (selectedSceneA){
                    TransitionManager.go(sceneB,transitionSet);
                } else {
                    TransitionManager.go(sceneA, transitionSet);
                }
                selectedSceneA = !selectedSceneA;
            }
        });*/

    }

}
