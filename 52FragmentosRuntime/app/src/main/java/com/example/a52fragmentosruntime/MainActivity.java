package com.example.a52fragmentosruntime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    Button button;
    FragmentoUno miFragmentoUno;
    FragmentoDos miFragmentoDos;
   int showingFragment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //creacion de fragmentos
        miFragmentoUno = new FragmentoUno();
        miFragmentoDos = new FragmentoDos();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.contenedor, miFragmentoUno);
        fragmentTransaction.commit();
        showingFragment = 1;
        button = findViewById(R.id.button);
        button.setOnClickListener(view -> cambiarFragmento(view));

    }

    private void cambiarFragmento(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (showingFragment == 1) {
            fragmentTransaction.replace(R.id.contenedor, miFragmentoDos);
            showingFragment = 2;
        } else {
            fragmentTransaction.replace(R.id.contenedor, miFragmentoUno);
            showingFragment = 1;
        }
        fragmentTransaction.commit();
    }
}