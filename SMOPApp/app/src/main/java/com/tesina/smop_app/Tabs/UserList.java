package com.tesina.smop_app.Tabs;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.tesina.smop_app.Adapter.UserListAdapter;
import com.tesina.smop_app.Dialog.UserItemDialog;
import com.tesina.smop_app.Interfaces.MenuListener;
import com.tesina.smop_app.Product.UserProduct;
import com.tesina.smop_app.R;
import com.tesina.smop_app.Threads.UserProductThread;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static com.tesina.smop_app.MainActivity.USER_LIST_FILE_NAME;

public class UserList extends Fragment{

    View view;
    RecyclerView userList;
    Context context;
    UserListAdapter userListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_make_your_list, container, false);

        context = getActivity();

        userList = view.findViewById(R.id.user_list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        userList.setLayoutManager(layoutManager);


        UserProductThread productThread = new UserProductThread(context, USER_LIST_FILE_NAME, UserProductThread.MODE_LOAD);
        productThread.start();
        try {
            productThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<UserProduct> p = productThread.getItems();

        userListAdapter = new UserListAdapter(context, p, getLayoutInflater());
        userList.setAdapter(userListAdapter);

        if ( p != null){
            userListAdapter.setItemsList(p);
        } else {
            ConstraintLayout emptyListContainer = view.findViewById(R.id.container_empty_list);
            emptyListContainer.setVisibility(View.VISIBLE);
        }

        return view;
    }

    public RecyclerView getUserList(){
        return this.userList;
    }

    /*@Override
    public UserProduct applyDataOnTheScreen(String productToken) {
        StringTokenizer productFields = new StringTokenizer(productToken,",");
        String name = productFields.nextToken();
        int quantity = Integer.parseInt(productFields.nextToken());

        return new UserProduct(name,quantity);
    }*/
}
