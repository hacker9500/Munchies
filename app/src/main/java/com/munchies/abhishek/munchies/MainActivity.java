package com.munchies.abhishek.munchies;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
}
