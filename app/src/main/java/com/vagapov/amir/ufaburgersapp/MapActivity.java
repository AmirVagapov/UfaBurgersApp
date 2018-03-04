package com.vagapov.amir.ufaburgersapp;


import android.app.Dialog;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.vagapov.amir.ufaburgersapp.view.MapFragment;

public class MapActivity extends SingleFragmentActivity{

    @Override
    protected void onResume() {
        super.onResume();
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int errorCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if(errorCode != ConnectionResult.SUCCESS){
            Dialog errorDialog = apiAvailability
                    .getErrorDialog(this, errorCode,0, dialogInterface -> finish());
            errorDialog.show();
        }
    }

    @IdRes
    public int getFragmentContainer() {
        return R.id.sub_container;
    }

    @LayoutRes
    public int getLayoutRes() {
        return R.layout.activity_sub;
    }

    @NonNull
    public MapFragment createFragment() {
        return MapFragment.newInstance();
    }
}
