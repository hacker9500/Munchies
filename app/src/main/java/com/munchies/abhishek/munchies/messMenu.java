package com.munchies.abhishek.munchies;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class messMenu extends Fragment implements AdapterView.OnItemClickListener{

    View view;
    MaterialBetterSpinner week,meal;
    ArrayList<String> weekList,mealList;
    ArrayList<menuItem> menuData;
    ListView menuList;
    menuAdapter ma;

    public messMenu() {
        // Required empty public constructor
    }

    class Import extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connect = (HttpURLConnection) url.openConnection();
                InputStream in = connect.getInputStream();
                InputStreamReader ir = new InputStreamReader(in);

                String str = "";
                int data = ir.read();
                while(data != -1){
                    char c = (char)data;
                    str += c;
                    data = ir.read();
                }
                return str;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s != null) {
                try {
                    //JSONObject jo = new JSONObject(s);
                    JSONArray ja = new JSONArray(s);
                    ma.clear();
                    menuData.clear();
                    int rating  = 0;
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jObject = ja.getJSONObject(i);
                        menuData.add(new menuItem(jObject.getString("item"), jObject.getInt("rating")));
                        ma.add(new menuItem(jObject.getString("item"), jObject.getInt("rating")));
                        rating += jObject.getInt("rating");
                    }
                    RatingBar bar = (RatingBar) view.findViewById(R.id.ratingBar);
                    bar.setRating((int)(rating/ja.length()));
                    bar.setEnabled(false);
                    Log.i("menudata", menuData.get(0).itemName.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mess_menu, container, false);
        week = (MaterialBetterSpinner) view.findViewById(R.id.day_selector);
        meal = (MaterialBetterSpinner) view.findViewById(R.id.meal_selector);



        weekList = new ArrayList<>();
        mealList = new ArrayList<>();

        menuList = (ListView) view.findViewById(R.id.menuList);

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

        week.setOnItemClickListener(this);
        meal.setOnItemClickListener(this);

        menuData = new ArrayList<>();

//        menuData.add(new menuItem("Aloo", 5));
//        menuData.add(new menuItem("Gobhi", 2));
//        menuData.add(new menuItem("muter", 3));
//        menuData.add(new menuItem("paratha", 3));
//        menuData.add(new menuItem("sabzi", 4));
//        menuData.add(new menuItem("Muli", 1));

        RatingBar bar = (RatingBar) view.findViewById(R.id.ratingBar);
        bar.setEnabled(false);



        ma = new menuAdapter(getContext(),R.layout.mess_menu_item, menuData);
        menuList.setAdapter(ma);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("meal",meal.getText().toString()+ " " +weekList.contains(week.getText().toString()) + " " +mealList.contains(meal.getText().toString()));
        if(weekList.contains(week.getText().toString()) && mealList.contains(meal.getText().toString())) {
            Log.d("if-else","true");
            String str = "http://192.168.2.0:4000/messMenu/"+week.getText().toString().toLowerCase()+"/"+meal.getText().toString().toLowerCase()+"/";
            Log.d("string",str);
            new Import().execute(str);
        }
    }

    class menuItem{
        public String itemName;
        public int rating;
        menuItem(String name, int rating){
            this.itemName = name;
            this.rating = rating;
        }
    }

    class menuAdapter extends ArrayAdapter<menuItem>{

        ArrayList<menuItem> data;

        public menuAdapter(Context context, int resource, ArrayList<menuItem> data) {
            super(context, resource);
            this.data = data;
        }

        @Override
        public menuItem getItem(int position) {
            return data.get(position);
        }

        @Override
        public int getPosition(menuItem item) {
            return super.getPosition(item);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                LayoutInflater li = LayoutInflater.from(getContext());
                convertView = li.inflate(R.layout.mess_menu_item,null);
            }
            menuItem mi = this.getItem(position);

            if(mi != null){
                TextView tv = (TextView) convertView.findViewById(R.id.itemText);
                //RatingBar rb = (RatingBar) convertView.findViewById(R.id.itemRating);

                tv.setText(mi.itemName);
                //rb.setRating((float) mi.rating);

                //rb.setEnabled(false);
            }

            return convertView;
        }
    }
}
