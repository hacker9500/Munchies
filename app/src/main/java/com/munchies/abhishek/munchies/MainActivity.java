package com.munchies.abhishek.munchies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Toolbar.OnMenuItemClickListener {

    CircleImageView dp;
    TextView userName, userEmail;

    public static String name, email, dpPath;

    //MenuItem poll;
    public void Poll(View v){
        Log.i("running","ok");
    }

    NavigationView navigationView = null;
    Toolbar toolbar = null;
    //FragmentTransaction transaction = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = getLayoutInflater().inflate(R.layout.nav_header_main,null);

        name = "Shubham Goswami";
        email = "shubham14100@iiitd.ac.in";
        dpPath = "https://lh4.googleusercontent.com/-f_BKM9EgYz8/AAAAAAAAAAI/AAAAAAAAC5g/EgAeeDNbnbw/photo.jpg";

//        try {
//            Intent i = getIntent();
//            name = i.getStringExtra("name");
//            email = i.getStringExtra("email");
//            dpPath = i.getStringExtra("dpPath");
//        }catch(Exception e){
//            e.printStackTrace();
//        }

        dp = (CircleImageView) header.findViewById(R.id.dp);
        userName = (TextView) header.findViewById(R.id.userName);
        userEmail = (TextView) header.findViewById(R.id.userEmail);
        userName.setText(name);
        userEmail.setText(email);
        Bitmap bm = null;
        try {
            bm = new dpImport().execute(dpPath).get();
            if(bm != null) {
                dp.setScaleType(ImageView.ScaleType.CENTER_CROP);
                dp.setImageBitmap(bm);
                Log.d("dpNull", "true");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        navigationView.addHeaderView(header);


        //View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);


//        poll = (MenuItem) findViewById(R.id.nav_poll);
//        poll.setEnabled(true);
        android.support.v4.app.FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
        pollFragment poll = new pollFragment(false);
        transaction.replace(R.id.fragment_container, poll);
        transaction.commit();

        FloatingActionButton fb = (FloatingActionButton) findViewById(R.id.fab);
        fb.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);
        if (id == R.id.nav_poll) {
            pollFragment poll = new pollFragment(false);
            transaction.replace(R.id.fragment_container, poll);
            transaction.commit();
        } else if (id == R.id.nav_coupon) {
            couponFragment poll = new couponFragment();
            transaction.replace(R.id.fragment_container,poll);
            transaction.commit();
        }
        else if (id == R.id.messmenu) {
            messMenu poll = new messMenu();
            transaction.replace(R.id.fragment_container,poll);
            transaction.commit();
        } else if (id == R.id.canteenmenu) {
            canteenMenu poll = new canteenMenu();
            fab.setVisibility(View.VISIBLE);
            transaction.replace(R.id.fragment_container,poll);
            transaction.commit();
        } else if (id == R.id.nav_order) {
            canteenOder poll = new canteenOder();
            transaction.replace(R.id.fragment_container,poll);
            transaction.commit();
        } else if ( id == R.id.nav_buy){
            buy_coupon bc = new buy_coupon();
            transaction.replace(R.id.fragment_container,bc);
            transaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.signout){
            // signout from this activity
            try {
                new signOut().execute("http://192.168.2.0:4000/logout/"+email).get();
                SharedPreferences sp = getApplication().getSharedPreferences("com.munchies.abhishek", Context.MODE_PRIVATE);
                sp.edit().putBoolean("login", false);
                this.finish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    class dpImport extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpsURLConnection connect = (HttpsURLConnection) url.openConnection();
                InputStream in = connect.getInputStream();
                Bitmap bm = BitmapFactory.decodeStream(in);
                //dp.setImageBitmap(bm);
                return bm;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    class signOut extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connect = (HttpURLConnection) url.openConnection();
                InputStream in = connect.getInputStream();
                InputStreamReader ir = new InputStreamReader(in);
                String str = "";
                int data = ir.read();
                while (data != -1){
                    char c = (char)data;
                    str += data;
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
    }
}
