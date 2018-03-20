package com.vagapov.amir.ufaburgersapp.view;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.MvpLceViewStateFragment;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.RetainingLceViewState;
import com.vagapov.amir.ufaburgersapp.R;
import com.vagapov.amir.ufaburgersapp.model.Place;
import com.vagapov.amir.ufaburgersapp.module.interfaces.DaggerPlaceListComponent;
import com.vagapov.amir.ufaburgersapp.module.interfaces.PlaceListComponent;
import com.vagapov.amir.ufaburgersapp.presenter.interfaces.PlaceListPresenter;
import com.vagapov.amir.ufaburgersapp.view.interfaces.PlaceListView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.vagapov.amir.ufaburgersapp.view.MapFragment.LOCATION_PERMISSIONS;
import static com.vagapov.amir.ufaburgersapp.view.MapFragment.REQUEST_LOCATION_PERMISSION;


public class PlaceListFragment extends MvpLceViewStateFragment<NestedScrollView, ArrayList<Place>,
PlaceListView, PlaceListPresenter> implements PlaceListView {

    @BindView(R.id.top_recycler_view)
    RecyclerView topRecyclerList;
    @BindView(R.id.new_recycler_view)
    RecyclerView favouriteRecyclerList;
    @BindView(R.id.all_recycler_view)
    RecyclerView allRecyclerList;
    private Unbinder unbinder;
    private PlacesAdapter adapterAll;
    private PlacesAdapter adapterTop;
    private PlacesAdapter adapterFav;
    private PlaceListComponent component;


    @Inject
    PlacesAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TAG", "onCreate");
        setHasOptionsMenu(true);
        setRetainInstance(true);
        component = DaggerPlaceListComponent.builder().build();
        component.inject(this);
        if(!checkPermission()) {
            requestPermissions(LOCATION_PERMISSIONS, REQUEST_LOCATION_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION_PERMISSION && checkPermission()) {
            Toast.makeText(getActivity(),
                            getResources().getString(R.string.geo_is_allow),
                            Toast.LENGTH_SHORT)
                    .show();
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), LOCATION_PERMISSIONS[0]);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d("TAG", "OnCreateView");
        return inflater.inflate(R.layout.fragment_place_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("TAG", "onViewCreated");
        unbinder = ButterKnife.bind(this, view);
        createRecyclerView();
    }

    private void createRecyclerView() {
        adapterAll = component.adapter();
        adapterTop = component.adapter();
        adapterFav = component.adapter();
        topRecyclerList.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));

        topRecyclerList.setAdapter(adapterTop);
        favouriteRecyclerList.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));
        favouriteRecyclerList.setAdapter(adapterFav);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            allRecyclerList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        }else {
            allRecyclerList.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        }
        allRecyclerList.setAdapter(adapterAll);
        allRecyclerList.setOnFlingListener(new RecyclerView.OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                allRecyclerList.dispatchNestedFling(velocityX, velocityY, false);
                return false;
            }
        });
    }

    @NonNull
    public static PlaceListFragment newInstance() {
        return new PlaceListFragment();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_places, menu);
        createMenu(menu);
    }

    private void createMenu(Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        SearchView searchViewMenu = (SearchView) searchItem.getActionView();
        searchViewMenu.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return presenter.searchPlace(newText);
            }
        });
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return e.getMessage();
    }

    @Override
    public PlaceListPresenter createPresenter() {
        Log.d("TAG", "createPresenter");
        return component.presenter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public LceViewState<ArrayList<Place>, PlaceListView> createViewState() {
        Log.d("TAG", "createViewState");
        return new RetainingLceViewState<>();
    }

    @Override
    public ArrayList<Place> getData() {
        Log.d("TAG", "getData");
        return adapterAll == null ? null : adapterAll.getPlaces();
    }

    @Override
    public void setData(ArrayList<Place> data) {
        Log.d("TAG", "setData");
        adapterAll.setPlaces(data);
        ArrayList<Place> placesFav = new ArrayList<>();
        ArrayList<Place> placeTop = new ArrayList<>();
        for (Place place : data) {
            if(place.isFavourite()){
                placesFav.add(place);
            }
            if(place.getRating() > 7.0f){
                placeTop.add(place);
            }
        }
        adapterFav.setPlaces(placesFav);
        adapterTop.setPlaces(placeTop);

    }

    @Override
    public void loadData(boolean pullToRefresh) {
        Log.d("TAG", "loadData");
        showLoading(true);
        presenter.loadPlaces();
    }

}

