package com.vagapov.amir.ufaburgersapp;



import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.vagapov.amir.ufaburgersapp.view.FragmentClickOpenPlaceInterface;
import com.vagapov.amir.ufaburgersapp.view.MapFragment;
import com.vagapov.amir.ufaburgersapp.view.PlacesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends SingleFragmentActivity implements
        NavigationView.OnNavigationItemSelectedListener, FragmentClickOpenPlaceInterface{

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.nav_view)
    NavigationView nav_view;

    private ActionBarDrawerToggle toggle;

    @Override
    @IdRes
    public int getFragmentContainer() {
        return R.id.container;
    }

    @Override
    @LayoutRes
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onStart() {
        super.onStart();
        ButterKnife.bind(this);
        initDrawerLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        toggle.syncState();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    private void initDrawerLayout() {
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawer_layout.addDrawerListener(toggle);


        nav_view.setNavigationItemSelectedListener(this);
    }

    @Override
    public Fragment createFragment() {
        return PlacesFragment.newInstance();
    }



    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return toggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawer_layout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()){
            case R.id.nav_show_on_map:
                openFragment(MapFragment.newInstance());
                return true;
            case R.id.nav_show_photo:
                Toast.makeText(this, "Show photo", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.nav_show_new_burgers:
                Toast.makeText(this, "New burgers", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.nav_show_favourite:
                Toast.makeText(this, "Favourite places", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.nav_share:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.nav_callback:
                Toast.makeText(this, "Callback", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return true;
        }

    }

    @Override
    public void openFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container, fragment).commit();
    }
}
