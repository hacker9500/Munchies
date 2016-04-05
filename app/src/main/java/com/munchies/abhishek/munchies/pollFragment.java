package com.munchies.abhishek.munchies;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class pollFragment extends Fragment {


    public pollFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        RecyclerView rv = (RecyclerView) getActivity().findViewById(R.id.poll_view);
//        rv.setHasFixedSize(true);
//        LinearLayoutManager lm = new LinearLayoutManager(getContext());
//        rv.setLayoutManager(lm);
//
//        CAdapter ad = new CAdapter(data);
//
//        rv.setAdapter(ad);

        return inflater.inflate(R.layout.fragment_poll, container, false);
    }

}
