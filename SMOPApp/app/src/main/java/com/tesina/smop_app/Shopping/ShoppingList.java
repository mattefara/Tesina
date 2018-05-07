package com.tesina.smop_app.Shopping;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tesina.smop_app.Adapter.TabAdapter;
import com.tesina.smop_app.R;
import com.tesina.smop_app.Tabs.ScannedList;
import com.tesina.smop_app.Tabs.UserList;

public class ShoppingList extends Fragment {

    View view;
    Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_shopping_list,container,false);
        final ViewPager viewPager = view.findViewById(R.id.fragment_tab);
        setupViewPager(viewPager);

        TabLayout tabLayout = view.findViewById(R.id.shop_tab);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    private void setupViewPager( ViewPager viewPager){
        TabAdapter tabAdapter = new TabAdapter(getChildFragmentManager());
        tabAdapter.addFragment(new UserList(), "List");
        tabAdapter.addFragment(new ScannedList(), "Scan list");
        viewPager.setAdapter(tabAdapter);
    }
}
