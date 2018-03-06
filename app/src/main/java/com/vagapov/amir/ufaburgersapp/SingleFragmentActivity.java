package com.vagapov.amir.ufaburgersapp;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;


public abstract class SingleFragmentActivity extends AppCompatActivity {

    public  abstract Fragment createFragment();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int errorCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if(errorCode != ConnectionResult.SUCCESS){
            Dialog errorDialog = apiAvailability
                    .getErrorDialog(this, errorCode,0, dialogInterface -> finish());
            errorDialog.show();
        }
        setContentView(getLayoutRes());

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(getFragmentContainer());

        if(fragment == null){
            fragment = createFragment();
            fm.beginTransaction().replace(getFragmentContainer(), fragment).commit();
        }
    }

    public abstract int getFragmentContainer();

    public abstract int getLayoutRes();
}
