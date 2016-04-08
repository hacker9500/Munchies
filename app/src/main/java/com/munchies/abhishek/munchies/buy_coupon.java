package com.munchies.abhishek.munchies;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;
/**
 * A simple {@link Fragment} subclass.
 */
public class buy_coupon extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {


    public buy_coupon() {
        // Required empty public constructor
    }

    View view;
    MaterialBetterSpinner week,meal;
    ArrayList<String> weekList,mealList;
    EditText count;
    Button buy;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_buy_coupon, container, false);
        // Inflate the layout for this fragment

        week = (MaterialBetterSpinner) view.findViewById(R.id.week);
        meal = (MaterialBetterSpinner) view.findViewById(R.id.meal);
        count = (EditText) view.findViewById(R.id.count);
        buy = (Button) view.findViewById(R.id.buy);

        weekList = new ArrayList<>();
        mealList = new ArrayList<>();

        weekList.add("Monday");
        weekList.add("Tuesday");
        weekList.add("Wednesday");
        weekList.add("Thursday");
        weekList.add("Friday");
        weekList.add("Saturday");
        weekList.add("Sunday");

        mealList.add("Breakfast");
        mealList.add("Lunch");
        mealList.add("Snacks");
        mealList.add("Dinner");

        ArrayAdapter<String> weekAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,weekList);
        ArrayAdapter<String> mealAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,mealList);

        week.setAdapter(weekAdapter);
        meal.setAdapter(mealAdapter);

        week.setOnItemClickListener(this);
        meal.setOnItemClickListener(this);

        buy.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.buy) {
            if (week.getText().toString().isEmpty() || meal.getText().toString().isEmpty() || count.getText().toString().isEmpty()){
                Toast.makeText(getContext(),"Please fill the fields correctly",Toast.LENGTH_SHORT).show();
            }
            else{
                String st = "http://192.168.2.0:4000/buyCoupon/shubham14100@iiitd.ac.in/" + meal.getText().toString().toLowerCase()+"/"+count.getText().toString();
                Log.i("true", st);
                try {
                    st = new Import().execute(st).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                if(st.equals("ok"))
                    Toast.makeText(getContext(), "Success!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class Import extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connect = (HttpURLConnection) url.openConnection();
                connect.setRequestMethod("POST");
                InputStream in = connect.getInputStream();
                InputStreamReader ir = new InputStreamReader(in);
                String str = "";
                int data = ir.read();
                while( data != -1){
                    char c = (char)data;
                    str += c;
                    data =  ir.read();
                }
                return "ok";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "notOk";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
