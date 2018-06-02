package com.tesina.smop_app.Shopping;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tesina.smop_app.Adapter.ScannedListAdapter;
import com.tesina.smop_app.Adapter.TabAdapter;
import com.tesina.smop_app.Adapter.UserListAdapter;
import com.tesina.smop_app.Camera.CameraScanner;
import com.tesina.smop_app.Dialog.UserItemDialog;
import com.tesina.smop_app.Product.DatabaseProduct;
import com.tesina.smop_app.Product.UserProduct;
import com.tesina.smop_app.R;
import com.tesina.smop_app.Tabs.BaseFragment;
import com.tesina.smop_app.Tabs.ScannedList;
import com.tesina.smop_app.Tabs.UserList;
import com.tesina.smop_app.Threads.UserProductThread;

import java.util.List;
import java.util.StringTokenizer;

import static android.app.Activity.RESULT_OK;

public class ShoppingList extends BaseFragment<DatabaseProduct> {

    //TODO: ADD DATABASEPRODUCT TO RECYCLERVIEW

    View view;
    Context context;
    FloatingActionButton actionListButton;
    ViewPager viewPager;
    View rootGroup;
    LayoutInflater fragmentInflater;
    UserList userList;
    ScannedList scannedList;
    UserListAdapter userListAdapter;
    ScannedListAdapter scannedListAdapter;
    TabLayout tabLayout;
    DatabaseReference productReference = FirebaseDatabase.getInstance().getReference("products");
    private MenuItem deleteSelectedItems;
    private final String USER_LIST_FILE_NAME = "user_list.csv";
    private final String SCANNED_LIST_FILE_NAME = "scanned_list.csv";
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

        tabLayout = view.findViewById(R.id.shop_tab);
        tabLayout.setupWithViewPager(viewPager);

        actionListButton = view.findViewById(R.id.list_action);
        actionListButton.setOnClickListener(actionListButtonClickListener());
        //Change image between add and camera
        setTabIconChange(tabLayout);
        //get the latest tab

        SharedPreferences preferences = context.getSharedPreferences("isSelectedScannedTab",Context.MODE_PRIVATE);
        if (preferences.getBoolean("selected", false)){
            tabLayout.getTabAt(1).select();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("selected",false);
            editor.apply();
        }

        //List<DatabaseProduct> data = loadData(context, SCANNED_LIST_FILE_NAME);

        return view;
    }


    private void setupViewPager(ViewPager viewPager){
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

                        userListAdapter = (UserListAdapter) userList.getUserList().getAdapter();

                        UserProduct userProduct = new UserProduct(
                                dialog.productName.getText().toString(),
                                Integer.parseInt(dialog.productQuantity.getText().toString())
                        );

                        List<UserProduct> userProducts = userListAdapter.getList();
                        int index ;
                        if ((index = userListAdapter.indexOf(userProduct)) != -1){
                            userProducts.get(index).setQuantity(userProducts.get(index).getQuantity() + userProduct.getQuantity() );
                        } else {
                            userListAdapter.setItemList(userProduct);
                        }
                        userListAdapter.sort();
                        UserProductThread productThread = new UserProductThread(context,USER_LIST_FILE_NAME, UserProductThread.MODE_WRITE , userListAdapter.toString(), Context.MODE_PRIVATE );
                        productThread.start();
                        //writeOnFile(userListAdapter.toString(),context,Context.MODE_PRIVATE, USER_LIST_FILE_NAME);
                        userListAdapter.notifyDataSetChanged();


                        if (userListAdapter.getList().size() == 1){
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
                checkForPermissions();
                scannedListAdapter = (ScannedListAdapter) scannedList.getScannedList().getAdapter();
            }
        };
    }

    public void checkForPermissions(){
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
        switch (requestCode){
            case CAMERA_REQUEST_CODE: {
                SharedPreferences preferences = context.getSharedPreferences("isSelectedScannedTab",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("selected",true);
                editor.apply();
                if (resultCode == RESULT_OK){
                    String barcode = data.getExtras().getString("Barcode");
                    String fakeBarcode = "725272730706";
                    makeQueryForProduct(fakeBarcode);
                    Toast.makeText(context, "Barcode: " + barcode, Toast.LENGTH_SHORT ).show();
                }
            }
        }

    }

    private void makeQueryForProduct(final String barcode) {
        productReference.orderByChild("barcode").equalTo(barcode).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    DatabaseProduct product = dataSnapshot.getValue(DatabaseProduct.class);
                } else {
                    Toast.makeText(context,getString(R.string.no_product_found), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public DatabaseProduct applyDataOnTheScreen(String productToken) {
        StringTokenizer productFields = new StringTokenizer(productToken,",");
        String name = productFields.nextToken();
        int quantity = Integer.parseInt(productFields.nextToken());
        double price = Double.parseDouble(productFields.nextToken());
        double discount = Double.parseDouble(productFields.nextToken());
        int img = Integer.parseInt(productFields.nextToken());
        String description = productFields.nextToken();
        String ingredient = productFields.nextToken();
        String brand = productFields.nextToken();
        String barcode = productFields.nextToken();


        return new DatabaseProduct(name,quantity,price,discount,img,description,ingredient,brand,barcode);
    }
}
