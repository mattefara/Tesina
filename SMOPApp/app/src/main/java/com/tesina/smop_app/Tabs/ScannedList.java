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
import com.tesina.smop_app.Threads.DatabaseProductThread;

import java.util.ArrayList;
import java.util.List;

import static com.tesina.smop_app.MainActivity.SCANNED_LIST_FILE_NAME;

public class ScannedList extends BaseFragment<DatabaseProduct> {

    View view;
    private RecyclerView scannedList;
    Context context;
    List<DatabaseProduct> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_scan_your_list, container, false);

        context = getActivity();

        /*DatabaseProductThread databaseProductThread = new DatabaseProductThread(context,SCANNED_LIST_FILE_NAME,DatabaseProductThread.MODE_LOAD);
        //DatabaseProductThread databaseProductThread = new DatabaseProductThread(context,SCANNED_LIST_FILE_NAME,DatabaseProductThread.MODE_WRITE,"");
        databaseProductThread.start();
        try {
            databaseProductThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        list = databaseProductThread.getItems();*/
        //Log.i("DatabaseProduct Thread","finished " + databaseProductThread.read);
        list = new ArrayList<>();
            list.add( new DatabaseProduct("Maionese in vasetto",
                    4,
                    3.5,
                    15,
                    R.drawable.maionese,
                    "Questa è una breve descrizione per la maionese.",
                    "Gli ingredienti sono tanti e buoni.",
                    "Calvè",
                    "725272730706")
            );
            list.add( new DatabaseProduct("Manzo macinato",
                    1,
                    13,
                    3,
                    R.drawable.manzo_macinato,
                    "Questa è una breve descrizione per il manzo",
                    "Gli ingredienti sono pochi, per lo più conservanti",
                    "Coop",
                    "671860013624")
            );


        scannedList = view.findViewById(R.id.scanned_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        scannedList.setLayoutManager(layoutManager);
        ScannedListAdapter scannedListAdapter = new ScannedListAdapter(context, list,scannedList);
        scannedList.setAdapter(scannedListAdapter);

        View root = container.getRootView();



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
