package com.example.a55fragmentosstack;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public class FragmentOne extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmento_one, container, false);
    }
}
