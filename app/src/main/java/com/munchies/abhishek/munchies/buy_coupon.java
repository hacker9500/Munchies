package com.munchies.abhishek.munchies;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;
/**
 * A simple {@link Fragment} subclass.
 */
public class buy_coupon extends Fragment {


    public buy_coupon() {
        // Required empty public constructor
    }

    View vi = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vi = inflater.inflate(R.layout.fragment_buy_coupon, container, false);
        // Inflate the layout for this fragment
        String[] SPINNERLIST = {"Android Material Design", "Material Design Spinner", "Spinner Using Material Library", "Material Spinner Example"};

        //@Override
        //protected void onCreate(Bundle savedInstanceState) {
          //  super.onCreate(savedInstanceState);

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item, SPINNERLIST);
            MaterialBetterSpinner materialDesignSpinner = (MaterialBetterSpinner) vi.findViewById(R.id.android_material_design_spinner);
            materialDesignSpinner.setAdapter(arrayAdapter);

        return vi;
    }
}
