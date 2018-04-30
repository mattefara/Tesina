package tesina.smop_smart_shop.Camera;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import tesina.smop_smart_shop.R;

public class BarcodeScanner extends Fragment {
    Context context;
    View view;
    SurfaceView camera;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();

        view = inflater.inflate(R.layout.activity_camera, container, false);
        camera = view.findViewById(R.id.camera_preview);
        startCamera();
        return view;
    }


    private void startCamera() {
        BarcodeDetector scanner = new BarcodeDetector.Builder(context).build();
        final CameraSource cameraSource = new CameraSource.Builder(context, scanner)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1024, 768)
                .build();

        camera.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                try {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    cameraSource.start();

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
        scanner.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() > 0) {
                    Intent intent = new Intent();
                    intent.putExtra("Barcode", barcodes.valueAt(0));
                    /*
                    setResult(CommonStatusCodes.SUCCESS, intent);
                    finish();
                    */
                }
            }
        });
    }
}
