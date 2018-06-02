package com.tesina.smop_app.Tabs;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tesina.smop_app.Adapter.ScannedListAdapter;
import com.tesina.smop_app.Adapter.UserListAdapter;
import com.tesina.smop_app.Product.DatabaseProduct;
import com.tesina.smop_app.Product.UserProduct;
import com.tesina.smop_app.R;

import java.util.ArrayList;
import java.util.List;

public class ScannedList extends BaseFragment<DatabaseProduct> {

    View view;
    private RecyclerView scannedList;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_scan_your_list, container, false);

        context = getActivity();

        scannedList = view.findViewById(R.id.scanned_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        scannedList.setLayoutManager(layoutManager);
        ScannedListAdapter scannedListAdapter = new ScannedListAdapter();
        scannedList.setAdapter(scannedListAdapter);

        View root = container.getRootView();

        List<DatabaseProduct> list = new ArrayList<>();

        return view;
    }

    public RecyclerView getScannedList() {
        return scannedList;
    }

    public void setScannedList(RecyclerView scannedList) {
        this.scannedList = scannedList;
    }

    @Override
    public DatabaseProduct applyDataOnTheScreen(String productToken) {
        return null;
    }
}
