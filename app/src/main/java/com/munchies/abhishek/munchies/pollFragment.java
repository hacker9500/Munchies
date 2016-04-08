package com.munchies.abhishek.munchies;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class pollFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, RatingBar.OnRatingBarChangeListener{

    View view = null;

    ScrollView sv;

    LinearLayout card1;
    LinearLayout card2;
    LinearLayout card3;
    LinearLayout card4;
    LinearLayout card5;
    LinearLayout card6;
    LinearLayout card7;
    LinearLayout card8;

    Button card1_menu;
    Button card2_menu;
    Button card3_menu;
    Button card4_menu;
    Button card5_menu;
    Button card6_menu;
    Button card7_menu;
    Button card8_menu;

    TextView card1_meal;
    TextView card2_meal;
    TextView card3_meal;
    TextView card4_meal;
    TextView card5_meal;
    TextView card6_meal;
    TextView card7_meal;
    TextView card8_meal;

    TextView card1_timer;
    TextView card2_timer;
    TextView card3_timer;
    TextView card4_timer;
    TextView card5_timer;
    TextView card6_timer;
    TextView card7_timer;
    TextView card8_timer;

    RatingBar card1_rating;
    RatingBar card2_rating;
    RatingBar card3_rating;
    RatingBar card4_rating;
    RatingBar card5_rating;
    RatingBar card6_rating;
    RatingBar card7_rating;
    RatingBar card8_rating;

    Switch card1_vote;
    Switch card2_vote;
    Switch card3_vote;
    Switch card4_vote;
    Switch card5_vote;
    Switch card6_vote;
    Switch card7_vote;
    Switch card8_vote;

    boolean connected;


    public pollFragment(boolean connected) {
        // Required empty public constructor
        super();
        this.connected = connected;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_poll, container, false);

        card1_menu = (Button) view.findViewById(R.id.card1_menu);
        card2_menu = (Button) view.findViewById(R.id.card2_menu);
        card3_menu = (Button) view.findViewById(R.id.card3_menu);
        card4_menu = (Button) view.findViewById(R.id.card4_menu);
        card5_menu = (Button) view.findViewById(R.id.card5_menu);
        card6_menu = (Button) view.findViewById(R.id.card6_menu);
        card7_menu = (Button) view.findViewById(R.id.card7_menu);
        card8_menu = (Button) view.findViewById(R.id.card8_menu);

        card1_meal = (TextView) view.findViewById(R.id.card1_meal);
        card2_meal = (TextView) view.findViewById(R.id.card2_meal);
        card3_meal = (TextView) view.findViewById(R.id.card3_meal);
        card4_meal = (TextView) view.findViewById(R.id.card4_meal);
        card5_meal = (TextView) view.findViewById(R.id.card5_meal);
        card6_meal = (TextView) view.findViewById(R.id.card6_meal);
        card7_meal = (TextView) view.findViewById(R.id.card7_meal);
        card8_meal = (TextView) view.findViewById(R.id.card8_meal);

        card1_rating = (RatingBar) view.findViewById(R.id.card1_rating);
        card2_rating = (RatingBar) view.findViewById(R.id.card2_rating);
        card3_rating = (RatingBar) view.findViewById(R.id.card3_rating);
        card4_rating = (RatingBar) view.findViewById(R.id.card4_rating);
        card5_rating = (RatingBar) view.findViewById(R.id.card5_rating);
        card6_rating = (RatingBar) view.findViewById(R.id.card6_rating);
        card7_rating = (RatingBar) view.findViewById(R.id.card7_rating);
        card8_rating = (RatingBar) view.findViewById(R.id.card8_rating);

        card1_vote = (Switch) view.findViewById(R.id.card1_vote);
        card2_vote = (Switch) view.findViewById(R.id.card2_vote);
        card3_vote = (Switch) view.findViewById(R.id.card3_vote);
        card4_vote = (Switch) view.findViewById(R.id.card4_vote);
        card5_vote = (Switch) view.findViewById(R.id.card5_vote);
        card6_vote = (Switch) view.findViewById(R.id.card6_vote);
        card7_vote = (Switch) view.findViewById(R.id.card7_vote);
        card8_vote = (Switch) view.findViewById(R.id.card8_vote);

        card1_timer = (TextView) view.findViewById(R.id.card1_timer);
        card2_timer = (TextView) view.findViewById(R.id.card2_timer);
        card3_timer = (TextView) view.findViewById(R.id.card3_timer);
        card4_timer = (TextView) view.findViewById(R.id.card4_timer);
        card5_timer = (TextView) view.findViewById(R.id.card5_timer);
        card6_timer = (TextView) view.findViewById(R.id.card6_timer);
        card7_timer = (TextView) view.findViewById(R.id.card7_timer);
        card8_timer = (TextView) view.findViewById(R.id.card8_timer);


        card1_menu.setOnClickListener(this);
        card2_menu.setOnClickListener(this);
        card3_menu.setOnClickListener(this);
        card4_menu.setOnClickListener(this);
        card5_menu.setOnClickListener(this);
        card6_menu.setOnClickListener(this);
        card7_menu.setOnClickListener(this);
        card8_menu.setOnClickListener(this);

        card1_vote.setOnCheckedChangeListener(this);
        card2_vote.setOnCheckedChangeListener(this);
        card3_vote.setOnCheckedChangeListener(this);
        card4_vote.setOnCheckedChangeListener(this);
        card5_vote.setOnCheckedChangeListener(this);
        card6_vote.setOnCheckedChangeListener(this);
        card7_vote.setOnCheckedChangeListener(this);
        card8_vote.setOnCheckedChangeListener(this);

        card1_rating.setOnRatingBarChangeListener(this);
        card2_rating.setOnRatingBarChangeListener(this);
        card3_rating.setOnRatingBarChangeListener(this);
        card4_rating.setOnRatingBarChangeListener(this);
        card5_rating.setOnRatingBarChangeListener(this);
        card6_rating.setOnRatingBarChangeListener(this);
        card7_rating.setOnRatingBarChangeListener(this);
        card8_rating.setOnRatingBarChangeListener(this);

        card1_rating.setStepSize(1f);
        card2_rating.setStepSize(1f);
        card3_rating.setStepSize(1f);
        card4_rating.setStepSize(1f);
        card5_rating.setStepSize(1f);
        card6_rating.setStepSize(1f);
        card7_rating.setStepSize(1f);
        card8_rating.setStepSize(1f);

        card1 = (LinearLayout) view.findViewById(R.id.card1);
        card2 = (LinearLayout) view.findViewById(R.id.card2);
        card3 = (LinearLayout) view.findViewById(R.id.card3);
        card4 = (LinearLayout) view.findViewById(R.id.card4);
        card5 = (LinearLayout) view.findViewById(R.id.card5);
        card6 = (LinearLayout) view.findViewById(R.id.card6);
        card7 = (LinearLayout) view.findViewById(R.id.card7);
        card8 = (LinearLayout) view.findViewById(R.id.card8);

        sv = (ScrollView) view.findViewById(R.id.poll);
        sv.setVisibility(View.VISIBLE);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void onStop() {
        super.onPause();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.card1_menu:
                break;
            case R.id.card2_menu:
                break;
            case R.id.card3_menu:
                break;
            case R.id.card4_menu:
                break;
            case R.id.card5_menu:
                break;
            case R.id.card6_menu:
                break;
            case R.id.card7_menu:
                break;
            case R.id.card8_menu:
                break;
            default:
                Log.e("error","menu not clicked");
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton v, boolean isChecked) {
        switch(v.getId()) {
            case R.id.card1_menu:
                break;
            case R.id.card2_menu:
                break;
            case R.id.card3_menu:
                break;
            case R.id.card4_menu:
                break;
            case R.id.card5_menu:
                break;
            case R.id.card6_menu:
                break;
            case R.id.card7_menu:
                break;
            case R.id.card8_menu:
                break;
            default:
                Log.e("error", "menu not clicked");
        }
    }

    @Override
    public void onRatingChanged(RatingBar v, float rating, boolean fromUser) {
        switch(v.getId()) {
            case R.id.card1_menu:
                break;
            case R.id.card2_menu:
                break;
            case R.id.card3_menu:
                break;
            case R.id.card4_menu:
                break;
            case R.id.card5_menu:
                break;
            case R.id.card6_menu:
                break;
            case R.id.card7_menu:
                break;
            case R.id.card8_menu:
                break;
            default:
                Log.e("error", "menu not clicked");
        }
    }
}
