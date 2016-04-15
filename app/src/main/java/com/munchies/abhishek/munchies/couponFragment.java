package com.munchies.abhishek.munchies;


import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class couponFragment extends Fragment implements View.OnClickListener{

    Button breakfast_share;
    Button lunch_share;
    Button snacks_share;
    Button dinner_share;

    ProgressBar breakfast_left;
    ProgressBar lunch_left;
    ProgressBar snacks_left;
    ProgressBar dinner_left;

    TextView breakfast_text;
    TextView lunch_text;
    TextView snacks_text;
    TextView dinner_text;

    View view;

    public couponFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_coupon, container, false);

        breakfast_share = (Button) view.findViewById(R.id.breakfast_share);
        lunch_share = (Button) view.findViewById(R.id.lunch_share);
        snacks_share = (Button) view.findViewById(R.id.snacks_share);
        dinner_share = (Button) view.findViewById(R.id.dinner_share);

        breakfast_left = (ProgressBar) view.findViewById(R.id.breakfast_left);
        lunch_left = (ProgressBar) view.findViewById(R.id.lunch_left);
        snacks_left = (ProgressBar) view.findViewById(R.id.snacks_left);
        dinner_left = (ProgressBar) view.findViewById(R.id.dinner_left);

        breakfast_text = (TextView) view.findViewById(R.id.breakfast_text);
        lunch_text = (TextView) view.findViewById(R.id.lunch_text);
        snacks_text = (TextView) view.findViewById(R.id.snacks_text);
        dinner_text = (TextView) view.findViewById(R.id.dinner_text);

        breakfast_share.setOnClickListener(this);
        lunch_share.setOnClickListener(this);
        snacks_share.setOnClickListener(this);
        dinner_share.setOnClickListener(this);

        try {
            new Import1().execute("http://192.168.2.0:4000/couponLeft/"+MainActivity.email).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return view;
    }

    public void showBox(String meal){
        final View vi = getActivity().getLayoutInflater().inflate(R.layout.custom_alert,null);
        TextView ml = (TextView)vi.findViewById(R.id.mealText);
        ml.setText(meal);
        Button share;
        share = (Button) vi.findViewById(R.id.share);
        AlertDialog.Builder bui = new AlertDialog.Builder(getActivity());
        final AlertDialog buii = bui.setView(vi).show();
        final String me = meal;
        share.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText email,count;
                email = (EditText) vi.findViewById(R.id.email);
                count = (EditText) vi.findViewById(R.id.count);
                String str = "http://192.168.2.0:4000/couponLeft/share/"+MainActivity.email+"/"+email.getText().toString()+"/"+me+"/"+count.getText().toString();
                String result = "fail";
                try {
                    result = new Import().execute(str).get();
                    Log.i("result",result);
                    if(!result.equals("fail")){
                        Log.i("ter","ter");
                        Toast.makeText(getContext(), "Success!!!", Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(getContext(), "Oops!!!", Toast.LENGTH_SHORT).show();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Oops!!!", Toast.LENGTH_SHORT).show();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Oops!!!", Toast.LENGTH_SHORT).show();
                }
                buii.dismiss();
                    //Toast.makeText(getContext(), "Oops!!!", Toast.LENGTH_SHORT).show();
            }
        });
        //new customAlert(getContext(), R.layout.custom_alert).show();

    }

    class Import extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... params) {
            String st = "";
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connect = (HttpURLConnection) url.openConnection();
                connect.setRequestMethod("POST");
                InputStream in = connect.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while(data != -1){
                    char c = (char)data;
                    st += c;
                    data = reader.read();
                }
                return st;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "fail";
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!= null){
                Log.i("json",s);
                try {
                    JSONObject jo = new JSONObject(s);
                    JSONObject coupons = jo.getJSONObject("coupons");
                    breakfast_text.setText(String.valueOf(coupons.getInt("breakfast"))+ "/30");
                    lunch_text.setText(String.valueOf(coupons.getInt("lunch"))+ "/30");
                    snacks_text.setText(String.valueOf(coupons.getInt("snacks"))+ "/30");
                    dinner_text.setText(String.valueOf(coupons.getInt("dinner"))+ "/30");
                    breakfast_left.setMax(30);
                    lunch_left.setMax(30);
                    dinner_left.setMax(30);
                    breakfast_left.setProgress(coupons.getInt("breakfast"));
                    lunch_left.setProgress(coupons.getInt("lunch"));
                    snacks_left.setProgress(coupons.getInt("snacks"));
                    dinner_left.setProgress(coupons.getInt("dinner"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    class Import1 extends AsyncTask<String, Void, String>{
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
                return st;
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
            if(s!= null){
                try {
                    JSONObject jo = new JSONObject(s);
                    JSONObject coupons = jo.getJSONObject("coupons");
                    breakfast_text.setText(String.valueOf(coupons.getInt("breakfast"))+ "/30");
                    lunch_text.setText(String.valueOf(coupons.getInt("lunch"))+ "/30");
                    snacks_text.setText(String.valueOf(coupons.getInt("snacks"))+ "/30");
                    dinner_text.setText(String.valueOf(coupons.getInt("dinner"))+ "/30");
                    breakfast_left.setMax(30);
                    lunch_left.setMax(30);
                    dinner_left.setMax(30);
                    breakfast_left.setProgress(coupons.getInt("breakfast"));
                    lunch_left.setProgress(coupons.getInt("lunch"));
                    snacks_left.setProgress(coupons.getInt("snacks"));
                    dinner_left.setProgress(coupons.getInt("dinner"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.breakfast_share:
                showBox("breakfast");
                break;
            case R.id.lunch_share:
                showBox("lunch");
                break;
            case R.id.snacks_share:
                showBox("snacks");
                break;
            case R.id.dinner_share:
                showBox("dinner");
                break;
        }
    }

    class customAlert extends AlertDialog{

        public customAlert(Context context, int themeResId) {
            super(context, themeResId);
        }

        EditText email,count;
        Button share;

        @Override
        public void setView(View view) {
            LayoutInflater li = LayoutInflater.from(getContext());
            view = li.inflate(R.layout.custom_alert,null);
            email = (EditText) view.findViewById(R.id.email);
            count = (EditText) view.findViewById(R.id.count);
            share = (Button) view.findViewById(R.id.share);
            super.setView(view);
        }
    }
}
