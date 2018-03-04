package com.vagapov.amir.ufaburgersapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;


public abstract class SingleFragmentActivity extends AppCompatActivity {

    public  abstract Fragment createFragment();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
