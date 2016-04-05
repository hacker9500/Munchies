package com.munchies.abhishek.munchies;


import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class canteenOder extends Fragment {
    View vi = null;

    public canteenOder() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vi = inflater.inflate(R.layout.fragment_canteen_oder, container, false);

        /*
        Button buy = (Button) vi.findViewById(R.id.buy);

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buy_coupon bc = new buy_coupon();
                FragmentTransaction fm = getFragmentManager().beginTransaction();
                fm.replace(R.id.fragment_container,bc);
                fm.commit();

            }
        });
        */

        return vi;
    }

}
