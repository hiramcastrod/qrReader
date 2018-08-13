package qr.hiram.qreader.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.ResultPoint;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.BarcodeView;
import com.journeyapps.barcodescanner.CompoundBarcodeView;

import java.util.List;

import qr.hiram.qreader.R;

public class ScannerFragment extends Fragment {

    CompoundBarcodeView barcodeView;

    public ScannerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scanner, container, false);
        permissionCamera();
        barcodeView = view.findViewById(R.id.scanner_barcode);
        barcodeView.decodeContinuous(callback);
        return view;
    }

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if(result.getText() != null){
                barcodeView.setStatusText(result.getText());
            }
            

        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {

        }
    };

    @Override
    public void onResume() {
        barcodeView.resume();
        super.onResume();
    }

    @Override
    public void onPause() {
        barcodeView.pause();
        super.onPause();
    }

    public void permissionCamera(){
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {


            } else {
                int MY_PERMISSIONS_REQUEST_CAMERA = 1;
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);
            }

        }
    }
}
