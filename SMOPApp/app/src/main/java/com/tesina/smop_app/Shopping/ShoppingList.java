package com.tesina.smop_app.Shopping;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tesina.smop_app.Adapter.TabAdapter;
import com.tesina.smop_app.Adapter.UserListAdapter;
import com.tesina.smop_app.Camera.CameraScanner;
import com.tesina.smop_app.Dialog.UserItemDialog;
import com.tesina.smop_app.Manifest;
import com.tesina.smop_app.Product.UserProduct;
import com.tesina.smop_app.R;
import com.tesina.smop_app.Tabs.BaseFragment;
import com.tesina.smop_app.Tabs.ScannedList;
import com.tesina.smop_app.Tabs.UserList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

public class ShoppingList extends BaseFragment {

    View view;
    Context context;
    FloatingActionButton actionListButton;
    ViewPager viewPager;
    View rootGroup;
    LayoutInflater fragmentInflater;
    UserList userList;
    ScannedList scannedList;
    UserListAdapter adapter;
    private MenuItem deleteSelectedItems;
    private final String LIST_FILE_NAME = "user_list.csv";
    private final int CAMERA_REQUEST_CODE = 1;

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
        //Add toolbar
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)context).setSupportActionBar(toolbar);

        rootGroup = container.getRootView();
        fragmentInflater = inflater;
        //Add toolbar
        DrawerLayout drawer = rootGroup.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                (AppCompatActivity)context, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        viewPager = view.findViewById(R.id.fragment_tab);

        setupViewPager(viewPager);

        TabLayout tabLayout = view.findViewById(R.id.shop_tab);
        tabLayout.setupWithViewPager(viewPager);

        actionListButton = view.findViewById(R.id.list_action);
        actionListButton.setOnClickListener(actionListButtonClickListener());
        //Change image between add and camera
        setTabIconChange(tabLayout);



        return view;
    }

    private void setupViewPager( ViewPager viewPager){
        TabAdapter tabAdapter = new TabAdapter(getChildFragmentManager());
        userList = new UserList();
        tabAdapter.addFragment(userList, "List");
        scannedList = new ScannedList();
        tabAdapter.addFragment(scannedList, "Scan list");
        viewPager.setAdapter(tabAdapter);
    }

    public void setTabIconChange(TabLayout tabLayout) {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(final TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0: {
                        actionListButton.setImageResource(R.drawable.ic_add);
                        actionListButton.setOnClickListener(actionListButtonClickListener());
                        break;
                    }
                    case 1: {
                        actionListButton.setImageResource(R.drawable.ic_menu_camera);
                        actionListButton.setOnClickListener(actionScannedListButtonClickListener());
                        break;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    public View.OnClickListener actionListButtonClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final UserItemDialog dialog = new UserItemDialog(context,getLayoutInflater());
                dialog.buildDialog("Enter a new product", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        adapter = (UserListAdapter) userList.getUserList().getAdapter();

                        UserProduct userProduct = new UserProduct(
                                dialog.productName.getText().toString(),
                                Integer.parseInt(dialog.productQuantity.getText().toString())
                        );

                        List<UserProduct> userProducts = adapter.getList();
                        int index ;
                        if ((index = adapter.indexOf(userProduct)) != -1){
                            userProducts.get(index).setQuantity(userProducts.get(index).getQuantity() + userProduct.getQuantity() );
                        } else {
                            adapter.setItemList(userProduct);
                        }
                        adapter.sort();
                        writeOnFile(adapter.toString(),context,Context.MODE_PRIVATE,LIST_FILE_NAME);
                        adapter.notifyDataSetChanged();


                        if (adapter.getList().size() == 1){
                            ConstraintLayout emptyListContainer = rootGroup.findViewById(R.id.container_empty_list);
                            emptyListContainer.setVisibility(View.GONE);
                        }



                    }
                });
            }
        };
    }



    public View.OnClickListener actionScannedListButtonClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checKForPermissions();
            }
        };
    }

    public void checKForPermissions(){
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){

            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, android.Manifest.permission.CAMERA)){

            } else {
                ActivityCompat.requestPermissions((Activity) context, new String[] {android.Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
            }
        } else {
            startActivityForResult(new Intent(context, CameraScanner.class),CAMERA_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0){
                    startActivityForResult(new Intent(context, CameraScanner.class),CAMERA_REQUEST_CODE);
                } else {
                    Toast.makeText(context, "Please turn allow the camera permission ", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle datas = data.getExtras();
        String barcode = datas.getString("Barcode");
        Toast.makeText(context, "Request code: " + requestCode + "\nResult code: " + resultCode, Toast.LENGTH_SHORT ).show();
    }
}
