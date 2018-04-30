package tesina.smop_smart_shop;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import tesina.smop_smart_shop.Camera.CameraScanner;
import tesina.smop_smart_shop.List.ShopList;
import tesina.smop_smart_shop.SignIn.Login;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    int previousActivity = R.id.shop;
    private final int CAMERA_REQUEST = 1;
    String scannedBarcode;

    TextView navUserEmail;

    private FirebaseAuth authentication;
    FirebaseUser currentUser;

    DrawerLayout drawerLayout;
    NavigationView sidebar;
    FrameLayout linearLayout;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        authentication = FirebaseAuth.getInstance();
        linearLayout = (FrameLayout) findViewById(R.id.content_frame);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        sidebar = (NavigationView) findViewById(R.id.sidebar);
        if (sidebar != null){ sidebar.setNavigationItemSelectedListener(this); }

        View sidebarView =  sidebar.getHeaderView(0);
        navUserEmail = (TextView) sidebarView.findViewById(R.id.nav_user_email);

        getFragmentManager().beginTransaction().replace(R.id.content_frame, new ShopList()).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        context = getApplicationContext();
        currentUser = authentication.getCurrentUser();
        if (currentUser == null){
            startActivity(new Intent(MainActivity.this,Login.class));
        } else {
            navUserEmail.setText(currentUser.getEmail());
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (previousActivity == id &&  previousActivity != R.id.barcode_scan){
            drawerLayout.closeDrawers();
            return true;
        }
        previousActivity = id;
        FragmentManager fragmentManager = getFragmentManager();

        switch (id){
            case R.id.shop:{
                fragmentManager.beginTransaction().replace(R.id.content_frame,new ShopList()).commit();
                break;
            }
            case R.id.settings:{
                fragmentManager.beginTransaction().replace(R.id.content_frame,new Prova()).commit();
                break;
            }
            case R.id.logout:{
                authentication.signOut();
                startActivity(new Intent(MainActivity.this,Login.class));
                finish();
                break;
            }
            case R.id.barcode_scan:{
                if (isPermissionGranted(Manifest.permission.CAMERA)){
                    /*
                    fragmentManager.beginTransaction().replace(R.id.content_frame, new BarcodeScanner()).commit();
                    */
                    startActivityForResult(new Intent(MainActivity.this,CameraScanner.class),CAMERA_REQUEST);
                } else {
                    ActivityCompat.requestPermissions( MainActivity.this ,new String[]{Manifest.permission.CAMERA},CAMERA_REQUEST);
                }
                break;
            }

        }
        drawerLayout.closeDrawers();
        Toast.makeText(getApplicationContext(),item.getTitle(),Toast.LENGTH_SHORT).show();
        return true;
    }

    public boolean isPermissionGranted(String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST){
            if (data != null){
                Barcode barcode = data.getParcelableExtra("Barcode");
                scannedBarcode = barcode.displayValue;
                startFragmentWithStringBundle(new ShopList(),"ScannedBarcode",scannedBarcode);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case CAMERA_REQUEST : {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED){
                    //modify activity if permission is not granted
                    Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Permission granted", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    private void startFragmentWithStringBundle(Fragment fragment, String key, String message){
        Bundle bundle = new Bundle();
        bundle.putString(key,message);
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

}
