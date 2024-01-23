package com.example.a54fragmentosexamen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    //control de orientación
    boolean mDualPane;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MasterFragment masterFragment = null;
        @SuppressLint({"MissingInfaltedId", "LocalSuppress"})
        FrameLayout frameLayout = findViewById(R.id.frameLayout);


        if (frameLayout != null) {
            mDualPane = false;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            masterFragment = (MasterFragment) getSupportFragmentManager().findFragmentById("MASTER");
            if (masterFragment == null) {
                masterFragment = new MasterFragment();
                fragmentTransaction.add(R.id.frameLayout, masterFragment, "MASTER");
            }
            DetailFragment detailFragment = (DetailFragment)
                    getSupportFragmentManager().findFragmentById(R.id.frameLayoutDetail);
            if (detailFragment

        }
        //LLAMAMAMOS a set Listener y pasamos una referencia a la interfaz
        masterFragment.setOnMasterSelectedListener(new MasterFragment.OnMasterSelectedListener() {
            @Override
            public void onItemSelected(String CountryName) {
                DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.frameLayoutDetail);
                if (detailFragment != null) {
                    detailFragment.showSelectedCountry(CountryName);
                } else {
                    DetailFragment newDetailFragment = new DetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(DetailFragment.KEY_COUNTRY_NAME, CountryName);
                    newDetailFragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, newDetailFragment, "DETAIL");
                    //manda el fragmento
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        }