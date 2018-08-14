package qr.hiram.qreader;

import android.support.design.widget.TabLayout;

import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;

import qr.hiram.qreader.adapters.SectionAdapter;
import qr.hiram.qreader.fragments.CartFragment;
import qr.hiram.qreader.fragments.ScannerFragment;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    SectionAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager){
        adapter = new SectionAdapter(getSupportFragmentManager());
        adapter.addFragment(new ScannerFragment(), "QR Scanner");
        adapter.addFragment(new CartFragment(), "Shopping Cart");
        viewPager.setAdapter(adapter);
    }


}
