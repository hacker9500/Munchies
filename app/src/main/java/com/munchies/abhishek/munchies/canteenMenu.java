package com.munchies.abhishek.munchies;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class canteenMenu extends Fragment implements View.OnClickListener{

    ListView menuList;
    View view;
    ArrayList<menuItem> menu;
    CustomAdapter ca;
    Order order;

    int total = 0,price;
    String id = "";
    menuItem mt;


    public canteenMenu() {
        // Required empty public constructor

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.fab){
            if(total == 0){
                Toast.makeText(getContext(), "Please select items first", Toast.LENGTH_SHORT).show();
            }
            else{

                final View vi = getActivity().getLayoutInflater().inflate(R.layout.aproove_order,null);
                final RadioButton cash, paytm, room, haveit;
                final Button checkout, cancel;
                cash = (RadioButton) vi.findViewById(R.id.cash);
                paytm = (RadioButton) vi.findViewById(R.id.paytm);
                room = (RadioButton) vi.findViewById(R.id.roomDelivery);
                haveit = (RadioButton) vi.findViewById(R.id.haveIt);
                checkout = (Button) vi.findViewById(R.id.checkout);
                cancel = (Button) vi.findViewById(R.id.cancel);
                AlertDialog.Builder bui = new AlertDialog.Builder(getActivity());
                final AlertDialog buii = bui.setView(vi).show();
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        order = new Order();
                        total = 0;
                        buii.dismiss();
                    }
                });
                checkout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        order.delivery = (room.isChecked()) ? true : false;
                        order.paymentMode = (cash.isChecked()) ? true : false;
                        buii.dismiss();

                        String status = order.submit();
                        order = new Order();
                        total = 0;
                        if(status != null){
                            if(status == "404"){
                                Toast.makeText(getContext(), "User not found !!!",Toast.LENGTH_SHORT).show();
                            }
                            else if( status == "306"){
                                Toast.makeText(getContext(), "your previous ordr is not yet completed", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getContext(), "Purshase made of Rs."+String.valueOf(total)+"/-",Toast.LENGTH_SHORT).show();
                            }
                        }
                        total = 0;
                    }
                });
            }
        }
    }

    class Import extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... params) {
            String st = "";
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connect = (HttpURLConnection) url.openConnection();
                InputStream in = connect.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while(data != -1){
                    char c = (char)data;
                    st += c;
                    data = reader.read();
                }
                JSONArray ja = new JSONArray(st);
                menu.clear();
                ca.clear();

                for(int i=0; i< ja.length(); i++){
                    JSONObject jObject = ja.getJSONObject(i);
                    menuItem mi = new menuItem(jObject.getString("itemName"), jObject.getInt("rating"), jObject.getString("itemPic"), jObject.getInt("itemPrice"),jObject.getString("_id"));
                    menu.add(mi);
                    ca.add(mi);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_canteen_menu, container, false);

        menuList = (ListView) view.findViewById(R.id.canteenmenu);
        menu = new ArrayList<>();
//        menu.add(new menuItem("bhature",3,"http://",45));
//        menu.add(new menuItem("chature",4,"http://",45));
//        menu.add(new menuItem("mature",5,"http://",46));
//        menu.add(new menuItem("sature",1,"http://",47));
//        menu.add(new menuItem("lature",3,"http://",48));
//        menu.add(new menuItem("kature",2,"http://",49));
        order = new Order();

        ca = new CustomAdapter(getContext(), R.layout.canteen_menu_item,menu);
        menuList.setAdapter(ca);
        try {
            new Import().execute("http://192.168.2.0:4000/canteenMenu").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(this);

        return view;
    }

    class CustomAdapter extends ArrayAdapter<menuItem>{

        ArrayList<menuItem> data;
        public CustomAdapter(Context context, int resource, ArrayList<menuItem> data) {
            super(context, resource);
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
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
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                LayoutInflater li = LayoutInflater.from(getContext());
                convertView = li.inflate(R.layout.canteen_menu_item,null);
            }

            final menuItem mi = this.getItem(position);

            if(mi != null){
                TextView tv = (TextView) convertView.findViewById(R.id.item_name);
                ImageButton bt = (ImageButton) convertView.findViewById(R.id.item_detail);
                TextView price = (TextView) convertView.findViewById(R.id.price);
                price.setText("Rs."+String.valueOf(mi.price)+"/-");
                tv.setText(mi.itemName);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getContext(), confirmItem.class);
                        i.putExtra("total", total);
                        i.putExtra("price", mi.price);
                        i.putExtra("name", mi.itemName);
                        mt = mi;
                        id = mi.id;
                        startActivityForResult(i, 0);
                    }
                });

                bt.setOnClickListener((new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent in = new Intent(getContext(), ItemDetail.class);
                        in.putExtra("url", mi.url);
                        in.putExtra("name", mi.itemName);
                        in.putExtra("rating", mi.rating);

                        startActivity(in);
                    }
                }
                ));

            }

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            return convertView;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        int qt = data.getIntExtra("qty", 10);
//        total += data.getIntExtra("price",10)*qt;
//        Log.d("total", String.valueOf(total));
        int qt = confirmItem.qt;
        total = confirmItem.tot;
        int price = confirmItem.price;
        Log.d("qty", String.valueOf(qt));
        Log.d("total", String.valueOf(total));
        order.insert(mt, qt);
    }

}
