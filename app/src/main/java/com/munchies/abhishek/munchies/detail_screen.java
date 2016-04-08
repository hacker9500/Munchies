package com.munchies.abhishek.munchies;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class detail_screen extends Fragment implements View.OnClickListener{

    View view;

    ImageView image;
    RatingBar rating;
    Button back;
    TextView itemName;

    public detail_screen() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_detail_screen, container, false);;

        itemName = (TextView) view.findViewById(R.id.itemName);
        rating = (RatingBar) view.findViewById(R.id.rating);
        back = (Button) view.findViewById(R.id.back);
        image = (ImageView) view.findViewById(R.id.image);

        back.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.back ){

        }
    }
}
