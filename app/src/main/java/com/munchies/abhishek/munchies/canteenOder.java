package com.munchies.abhishek.munchies;


import android.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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
public class canteenOder extends Fragment {
    View vi = null;

    CheckBox recv, delv, prep;
    TextView total;
    ListView itemList;
    CustomAdapter ca;

    ArrayList <menuItem> data;
    int finalAmount,stage;

    public canteenOder() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        data = new ArrayList<>();
        // Inflate the layout for this fragment
        vi = inflater.inflate(R.layout.fragment_canteen_oder, container, false);
        recv = (CheckBox) vi.findViewById(R.id.recv);
        delv = (CheckBox) vi.findViewById(R.id.delv);
        prep = (CheckBox) vi.findViewById(R.id.prep);
        total = (TextView) vi.findViewById(R.id.total);
        itemList = (ListView) vi.findViewById(R.id.itemList);

        prep.setClickable(false);
        delv.setClickable(false);
        recv.setClickable(false);

        ca = new CustomAdapter(getContext(), R.layout.order_item);
        itemList.setAdapter(ca);

        return vi;
    }

    class CustomAdapter extends ArrayAdapter<menuItem>{


        public CustomAdapter(Context context, int resource) {
            super(context, resource);
        }


        @Override
        public menuItem getItem(int position) {
            return data.get(position);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView  == null){
                LayoutInflater li = LayoutInflater.from(getContext());
                convertView = li.inflate(R.layout.order_item, null);
            }

            menuItem mi = this.getItem(position);




            return convertView;
        }
    }

    class Import extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connect = (HttpURLConnection) url.openConnection();
                InputStream in = connect.getInputStream();
                InputStreamReader ir = new InputStreamReader(in);
                String result = "";
                int data = ir.read();
                while(data != -1){
                    char c = (char) data;
                    result += c;
                    data = ir.read();
                }
                JSONObject json = new JSONObject(result);
                finalAmount = json.getInt("total");
                JSONArray js = json.getJSONArray("meal");
                stage = json.getInt("stage");
                for(int i=0 ; i< js.length(); i++){
                    JSONObject jo = (JSONObject) js.get(i);
                    menuItem mi = new menuItem();
                }

                menuItem mi = new menuItem;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
