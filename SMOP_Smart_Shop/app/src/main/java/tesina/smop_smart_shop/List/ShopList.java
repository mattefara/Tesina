package tesina.smop_smart_shop.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import tesina.smop_smart_shop.Adapter.ExpandableShopListAdapter;
import tesina.smop_smart_shop.Adapter.ShopListAdapter;
import tesina.smop_smart_shop.Products.DatabaseProduct;
import tesina.smop_smart_shop.Products.ListProduct;
import tesina.smop_smart_shop.Products.Product;
import tesina.smop_smart_shop.Products.ProductDetails;
import tesina.smop_smart_shop.Products.ProductGroup;
import tesina.smop_smart_shop.R;
import tesina.smop_smart_shop.SignIn.Login;


public class ShopList extends Fragment {
    final String LIST_FILE_NAME = "list.txt";
    final String IMG_FORMAT = "jpg";
    final String IMG_FILE_NAME = "barcode_d";
    private final boolean PROGETTO_PROMOZIONALE = false;

    private String[] products = new String[]{"Pasta", "Spaghetti"};

    View view;
    Context context;
    int count_item;
    String scannedBarcode;

    DatabaseReference listReference;
    DatabaseReference productReference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    StorageReference storage = FirebaseStorage.getInstance().getReference();
    String userUid;
    RecyclerView shopList;
    List<ListProduct> list;
    ShopListAdapter listAdapter;
    //List<ProductGroup> groupProductInformation;
    //HashMap<ProductGroup, List<ProductDetails>> listHashMap;

    FloatingActionButton addItem, deleteList;



    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //Init data
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivity(new Intent(context,Login.class));
        } else {
            userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        view = inflater.inflate(R.layout.activity_make_list,container,false);
        addItem = view.findViewById(R.id.add_one_item);
        deleteList = view.findViewById(R.id.deleteList);
        shopList = view.findViewById(R.id.shop_list);

        shopList.setLayoutManager(new LinearLayoutManager(getActivity()));
        listAdapter = new ShopListAdapter();
        shopList.setAdapter(listAdapter);

        list = new ArrayList<>();
        //loadData();
        promo();

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildAddItemDialog(inflater);
            }
        });

        deleteList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildDeleteDialog("Confirm","Cancel","Are you sure?");
            }
        });

        listReference = database.getReference("users/"+userUid+"/lists/").push();
        productReference = database.getReference("products/");


        if (getArguments() != null){
            scannedBarcode = getArguments().getString("ScannedBarcode");
            Toast.makeText(context, scannedBarcode, Toast.LENGTH_SHORT).show();
            Query result = productReference.child(scannedBarcode);
            result.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        DatabaseProduct databaseProduct = dataSnapshot.getValue(DatabaseProduct.class);

                        int index = searchForProduct(list,scannedBarcode);
                        if (index != -1){
                            Toast.makeText(context,"Bella!!!",Toast.LENGTH_SHORT).show();
                            writeAllListOnFile(list);
                        } else {
                            Toast.makeText(context,"Non era gia stato aggiunto !!!",Toast.LENGTH_SHORT).show();
                            ListProduct p = new ListProduct(databaseProduct, true);
                            /*try {
                                File localFile = File.createTempFile(IMG_FILE_NAME, IMG_FORMAT);
                                final StorageReference img = storage.child("products/" + IMG_FILE_NAME + "." + IMG_FORMAT);
                                img.getDownloadUrl()
                                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                Log.i("OK", "Ok");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.i("Errore", e.toString());
                                            }
                                        });
                            } catch (IOException e) {
                                e.printStackTrace();
                            }*/
                            p.setImg(R.drawable.ic_shopping_basket);
                            p.setQuantity(p.getQuantity() + 1);
                            p.setPrice(p.getPrice() * p.getQuantity());
                            p.setScanned(true);
                            p.setScannedQuantity(p.getScannedQuantity() + 1);
                            list.add(p);
                            writeOnFile(p.toString(),Context.MODE_APPEND);
                        }
                        listAdapter = new ShopListAdapter();
                        shopList.setAdapter(listAdapter);
                        listAdapter.setItemList(list);
                    } else {
                        Toast.makeText(context,"No product found",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        return view;
    }

    public void writeAllListOnFile(List<ListProduct> listProducts){
        for (ListProduct p : listProducts){
            writeOnFile(p.toString(),Context.MODE_PRIVATE);
        }
    }

    public int searchForProduct(List<ListProduct> products, String name){
        int i =-1; boolean find;
        if (products.size() > 0){
            do {
                i++;
                find = findScannedBarcode(products.get(i),name);
            } while (products.size() <= i && !find);
        } else {
            find = false;
        }
        return (find) ? i : -1;
    }

    public boolean findScannedBarcode(ListProduct product, String name){
        if (product.isScanned() && product.getBarcode().equals(name)){
            product.setScannedQuantity(product.getScannedQuantity() + 1);
            product.setQuantity(product.getQuantity() + 1);
            product.setPrice(product.getQuantity() * product.getPrice());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        //Toast.makeText(context, readFile(), Toast.LENGTH_SHORT ).show();

    }

    private void buildAddItemDialog(LayoutInflater inflater) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View dialogView = inflater.inflate(R.layout.dialog_add_item, null);

        count_item = 0;

        //set buttons and other alert things
        ImageView add = dialogView.findViewById(R.id.add_one_item);
        ImageView remove = dialogView.findViewById(R.id.remove_one_item);
        ImageView dropDownProductName = dialogView.findViewById(R.id.dropdown_add_product);
        final AutoCompleteTextView productToAdd = dialogView.findViewById(R.id.add_product);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_dropdown_item_1line,products);
        productToAdd.setAdapter(adapter);

        dropDownProductName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productToAdd.showDropDown();
            }
        });

        productToAdd.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                //Log.i("KeyListener:",view.toString());
                //Log.i("KeyListener:", String.valueOf(i));
                if (keyEvent.getAction() == 1){
                    Query query = productReference.equalTo(productToAdd.getText().toString());
                    Toast.makeText(context,query.toString(),Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        final TextView countItem = dialogView.findViewById(R.id.number_of_item_to_add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count_item ++;
                countItem.setText(String.valueOf(count_item));
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count_item > 0){ count_item --; }
                countItem.setText(String.valueOf(count_item));
            }
        });

        builder.setPositiveButton("Conferma", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addData();
                //shopList.setAdapter(new ExpandableShopListAdapter(context,groupProductInformation,listHashMap));

            }
        });

        builder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void buildDeleteDialog(String posiveAction, String negativeAction, String builderContent){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton(posiveAction, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                writeOnFile("", Context.MODE_PRIVATE);
                shopList.setAdapter(new ShopListAdapter());
                //groupProductInformation = new ArrayList<>();
                //listHashMap = new HashMap<>();
                //shopList.setAdapter(new ExpandableShopListAdapter(context,groupProductInformation,listHashMap));
            }
        });
        builder.setNegativeButton(negativeAction, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setMessage(builderContent);
        builder.show();
    }

    private void addData() {
        String name = "Manzo";
        double price = 1.50;
        String branding = "Mucca";
        String ingr = "bella vez";
        String desc = "Molto Buono!";
        String barcode = "XYZ";
        double disc = 2.0;
        int quantity = 1;

        //groupProductInformation.add(new ProductGroup(name,price,quantity,disc));

        List<ProductDetails> productDetails = new ArrayList<>();
        productDetails.add(new ProductDetails(branding,ingr,desc,barcode));

        //listHashMap.put(groupProductInformation.get(groupProductInformation.size()-1),productDetails);

        DatabaseReference productsReference  = listReference.push();
        Product product = new Product(name,branding,ingr,desc,barcode,quantity,price,disc);
        productsReference.setValue(product);

        writeOnFile(product.toString(), Context.MODE_APPEND);

    }

    private void loadData(){
        String data = readFile();
        if (!data.equals("")){
            StringTokenizer tokenizer = new StringTokenizer(data,";");
            while (tokenizer.hasMoreElements()){
                String productToken = tokenizer.nextToken();
                Log.i("TOKEN:",productToken);
                applyDataOnTheScreen(productToken);
            }
        }
    }

    private void applyDataOnTheScreen(String productToken) {
        StringTokenizer productFields = new StringTokenizer(productToken,",");
        String name = productFields.nextToken();
        String desc = productFields.nextToken();
        String brand = productFields.nextToken();
        String ingr = productFields.nextToken();
        String barcode = productFields.nextToken();
        int quantity = Integer.parseInt(productFields.nextToken());
        int img = Integer.parseInt(productFields.nextToken());
        double price = Double.parseDouble(productFields.nextToken());
        double disc = Double.parseDouble(productFields.nextToken());
        int scQuantity = Integer.parseInt(productFields.nextToken());
        boolean isScanned = Boolean.parseBoolean(productFields.nextToken());

        list.add(new ListProduct(name, desc, brand, ingr, barcode, quantity, img, price, disc,scQuantity, isScanned));
        listAdapter.setItemList(list);
        //groupProductInformation.add(new ProductGroup(name,price,quantity,disc));

        //List<ProductDetails> productDetails = new ArrayList<>();
        //productDetails.add(new ProductDetails(branding,ingr,desc,barcode));

        //listHashMap.put(groupProductInformation.get(groupProductInformation.size()-1),productDetails);
        //shopList.setAdapter(new ExpandableShopListAdapter(context,groupProductInformation,listHashMap));
    }

    public void writeOnFile(String s, int contextMode){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(LIST_FILE_NAME, contextMode));
            outputStreamWriter.write(s);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }

    }

    public String readFile(){
        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(LIST_FILE_NAME);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    public void promo(){
        list.add( new ListProduct("Maionese in vasetto.",
                "Maionese da utilizzare in cucina, buona con qualsiasi cosa.",
                "Calvè.",
                "Tuorlo, succo di limoni, olio, aceto di vino bianco, pepe e sale fino.",
                "725272730706",
                2,
                R.drawable.maionese,
                1.50,
                5,
                1,
                true));
        list.add( new ListProduct("Manzo.",
                "Manzo da 200g.",
                "Macellaio",
                "Pura carne di animale. 100% BIO",
                "9788679912077",
                1,
                R.drawable.manzo,
                13.4,
                0,
                0,
                true));
        listAdapter.setItemList(list);
    }
}