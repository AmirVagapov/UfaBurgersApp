package com.vagapov.amir.ufaburgersapp;



import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.vagapov.amir.ufaburgersapp.map_service.MapService;
import com.vagapov.amir.ufaburgersapp.model.Place;
import com.vagapov.amir.ufaburgersapp.module.interfaces.DaggerMapServiceComponent;
import com.vagapov.amir.ufaburgersapp.module.interfaces.MapServiceComponent;
import com.vagapov.amir.ufaburgersapp.preferences.QueryPreferences;
import com.vagapov.amir.ufaburgersapp.view.PlaceDescriptionFragment;
import com.vagapov.amir.ufaburgersapp.view.SettingsFragment;
import com.vagapov.amir.ufaburgersapp.view.interfaces.FragmentClickOpenPlaceInterface;
import com.vagapov.amir.ufaburgersapp.view.MapFragment;
import com.vagapov.amir.ufaburgersapp.view.PlaceListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, FragmentClickOpenPlaceInterface{

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.nav_view)
    NavigationView nav_view;

    private ActionBarDrawerToggle toggle;
    private MapServiceComponent component;


    @IdRes
    public int getFragmentContainer() {
        return R.id.container;
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        Intent i = getIntent();
//        Place place = (Place) i.getSerializableExtra(MapService.PLACE);
//        if(place != null) {
//            Log.d("LOCATIONSERVICE", place.getName());
//            openFragment(PlaceDescriptionFragment
//                    .newInstance(place));
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("LOCATIONSERVICE", "OnActivityResult");
        if(requestCode == 0){
            Place place = (Place) data.getSerializableExtra(MapService.PLACE);
            openFragment(PlaceDescriptionFragment
                    .newInstance(place));
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        component = DaggerMapServiceComponent.create();
        ButterKnife.bind(this);
        checkService();
        initDrawerLayout();
        initGoogleApi();


        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(getFragmentContainer());



        if(fragment == null){
            fragment = PlaceListFragment.newInstance();
            fm.beginTransaction().replace(getFragmentContainer(), fragment).commit();
        }
    }

    private void checkService() {
        component.service().setServiceAlarm(this, QueryPreferences.isNotifiactionsOn(this));
    }

    private void initGoogleApi() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int errorCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if(errorCode != ConnectionResult.SUCCESS){
            Dialog errorDialog = apiAvailability
                    .getErrorDialog(this, errorCode,0, dialogInterface -> finish());
            errorDialog.show();
        }
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
            case R.id.nav_settings:
                openFragment(SettingsFragment.newInstance());
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
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null).replace(R.id.container, fragment)
                .commit();
    }

    public static Intent newIntent(Context context){
        return new Intent(context, MainActivity.class);
    }
}
