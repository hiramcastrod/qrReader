package qr.hiram.qreader.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CompoundBarcodeView;

import java.util.List;

import qr.hiram.qreader.R;
import qr.hiram.qreader.activities.DetailsActivity;
import qr.hiram.qreader.activities.NewArticleActivity;
import qr.hiram.qreader.models.Article;

public class ScannerFragment extends Fragment {

    CompoundBarcodeView barcodeView;
    DatabaseReference QRS;

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
        QRS = FirebaseDatabase.getInstance().getReference("Qrs");
        return view;
    }

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(final BarcodeResult result) {
            if(result.getText() != null) {
                barcodeView.setStatusText(result.getText());
                Query query = QRS.child("Articles").orderByChild("id").equalTo(result.getText()).limitToFirst(1);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Article article = new Article();
                        if (dataSnapshot.exists()) {
                            for(DataSnapshot post: dataSnapshot.getChildren()){
                                article = post.getValue(Article.class);
                            }
                            if(article != null) {
                                Intent intent = new Intent(getContext(), DetailsActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("ID", result.getText());
                                bundle.putString("DSCR", article.getDescription());
                                bundle.putString("COLOR", article.getColor());
                                bundle.putFloat("PRICE", article.getPrice());
                                intent.putExtras(bundle);
                                startActivity(intent);
                                //Toast.makeText(getContext(), article.getColor(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Intent intent = new Intent(getContext(), NewArticleActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("ID", result.getText());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
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
