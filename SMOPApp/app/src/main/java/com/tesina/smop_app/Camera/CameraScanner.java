package com.tesina.smop_app.Camera;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tesina.smop_app.Interfaces.BackListener;
import com.tesina.smop_app.Product.DatabaseProduct;
import com.tesina.smop_app.R;
import com.tesina.smop_app.Threads.DatabaseProductThread;

import java.io.IOException;

import static com.tesina.smop_app.MainActivity.SCANNED_LIST_FILE_NAME;

public class CameraScanner extends Activity {
    Context context;
    SurfaceView camera;
    private final int CAMERA_REQUEST_CODE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera);
        context = getApplicationContext();
        camera = findViewById(R.id.camera_preview);
        startCamera();

    }

    private void startCamera() {
        final BarcodeDetector barcode = new BarcodeDetector.Builder(this).build();

        if (!barcode.isOperational()){
            Toast.makeText(context, "Error",Toast.LENGTH_SHORT).show();
            return;
        }

        final CameraSource cameraSource = new CameraSource.Builder(this, barcode)
                .setAutoFocusEnabled(true)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1024, 768)
                .build();

        camera.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                try {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    cameraSource.start(camera.getHolder());

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("Errore", e.toString());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });
        barcode.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() > 0) {
                    Intent intent = new Intent();
                    String barcode = barcodes.valueAt(0).displayValue;
                    intent.putExtra("barcode",barcode);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

}
