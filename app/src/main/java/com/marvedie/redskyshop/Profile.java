package com.marvedie.redskyshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.marvedie.redskyshop.fragments.DashboardFragment;
import com.marvedie.redskyshop.fragments.HomeFragment;
import com.marvedie.redskyshop.fragments.NotificationsFragment;
import com.marvedie.redskyshop.fragments.ProfileFragment;
import com.marvedie.redskyshop.shoppingcart.ShoppingActivity;
import com.marvedie.redskyshop.take2.PostsListActivity;

public class Profile extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        //loading the default fragment
        loadFragment(new ProfileFragment());

        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_home:
                startActivity(new Intent(this, PostsListActivity.class));
                //fragment = new HomeFragment();
                break;

            case R.id.navigation_cart:
                startActivity(new Intent(this, ShoppingActivity.class));
                //fragment = new DashboardFragment();
                break;

            case R.id.navigation_sell:
                fragment = new NotificationsFragment();
                break;

            case R.id.navigation_profile:
                fragment = new ProfileFragment();
                break;
        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}