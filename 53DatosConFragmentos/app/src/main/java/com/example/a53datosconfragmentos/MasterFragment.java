package com.example.a53datosconfragmentos;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.ListFragment;

public class MasterFragment extends ListFragment {

    private OnMasterSelectedListener mOnMasterSelectedListener=null;
    public void setOnMasterSelectedListener(OnMasterSelectedListener listener) {
        mOnMasterSelectedListener=listener;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] countries = new String[] { "China", "France", "Germany", "India", "Russia", "United Kingdom", "United States" };
        ListAdapter countryAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                countries);
        setListAdapter(countryAdapter);
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //si hay lista de escuchadores, se llama al método onItemSelected
                if (mOnMasterSelectedListener != null) {
                    /*mOnMasterSelectedListener.onItemSelected(((TextView) view).getText().toString());*/
                    mOnMasterSelectedListener.onItemSelected(countries[position]);
                }
            }
        });
}


    public interface OnMasterSelectedListener {
        public void onItemSelected(String CountryName);
    }
}
