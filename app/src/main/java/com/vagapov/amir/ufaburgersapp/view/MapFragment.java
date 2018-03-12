package com.vagapov.amir.ufaburgersapp.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vagapov.amir.ufaburgersapp.R;
import com.vagapov.amir.ufaburgersapp.model.Place;
import com.vagapov.amir.ufaburgersapp.model.PlacesModelImpl;
import com.vagapov.amir.ufaburgersapp.module.interfaces.DaggerMapComponent;
import com.vagapov.amir.ufaburgersapp.module.MapModule;
import com.vagapov.amir.ufaburgersapp.module.interfaces.MapComponent;
import com.vagapov.amir.ufaburgersapp.map_delegate.MapDelegate;
import com.vagapov.amir.ufaburgersapp.view.interfaces.FragmentClickOpenPlaceInterface;
import com.vagapov.amir.ufaburgersapp.view.interfaces.MapMarkerLoaderInterface;

public class MapFragment extends SupportMapFragment implements GoogleMap.OnInfoWindowClickListener,
        MapMarkerLoaderInterface{

    private GoogleMap map;
    private Location currentLocation;
    private PermissionLocationDialogFragment permissionDialog;
    private FragmentClickOpenPlaceInterface mFragmentClickOpenPlaceInterface;
    private static final String[] LOCATION_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private static final int REQUEST_LOCATION_PERMISSION = 0;
    private MapDelegate delegate;

    @NonNull
    public static MapFragment newInstance() {
        return new MapFragment();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mFragmentClickOpenPlaceInterface = (FragmentClickOpenPlaceInterface) activity;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRetainInstance(true);
        MapComponent component = DaggerMapComponent
                .builder().mapModule(new MapModule(this, new PlacesModelImpl())).build();
        component.inject(this);
        delegate = component.delegate();
        createPermissionDialog();
        checkPermissions();

        getMapAsync(googleMap -> {
            map = googleMap;
            map.setOnInfoWindowClickListener(this);
            map.getUiSettings().setZoomControlsEnabled(true);
            getLocation();
        });

    }

    private void createPermissionDialog() {
        permissionDialog = PermissionLocationDialogFragment.newInstance();
        permissionDialog.setContext(getActivity());
        permissionDialog
                .setRequestInterface(() ->
                        requestPermissions(LOCATION_PERMISSIONS, REQUEST_LOCATION_PERMISSION));
    }


    private void checkPermissions() {
        if (hasLocationPermission()) {
            getLocation();
        } else {
            if (shouldShowRequestPermissionRationale(LOCATION_PERMISSIONS[0])) {
                permissionDialog.onCreateDialog(null).show();
            } else {
                requestPermissions(LOCATION_PERMISSIONS, REQUEST_LOCATION_PERMISSION);
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        FusedLocationProviderClient fusedLocationProviderClient
                = LocationServices.getFusedLocationProviderClient(getActivity());
        fusedLocationProviderClient
                .getLastLocation()
                .addOnSuccessListener(getActivity(), location -> {
                    currentLocation = location;
                    updateUI();
                });
    }

    private boolean hasLocationPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), LOCATION_PERMISSIONS[0]);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void updateUI() {
        if (map == null) {
            return;
        }
        map.clear();
        delegate.loadData();
    }

    @Override
    public void addMarkers(Place place){
        MarkerOptions marker = new MarkerOptions()
                .position(place.getLatLng())
                .title(place.getName())
                .snippet(getString(R.string.give_more_informations));
        Marker m = map.addMarker(marker);
        m.showInfoWindow();
    }

    @Override
    public void showErrorLoading(Throwable throwable){
        Toast.makeText(getActivity(),
                throwable.getMessage(),
                Toast.LENGTH_SHORT).show();
    }


    @Override
    public void postMarkers() {
        LatLng myLocation = new LatLng(currentLocation.getLatitude(),
                currentLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions()
                .position(myLocation)
                .title(getString(R.string.your_location))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .zIndex(1.0f);
        Marker marker = map.addMarker(markerOptions);
        marker.showInfoWindow();
        CameraUpdate cameraUpdate = CameraUpdateFactory
                .newLatLngZoom(myLocation, 13.0f);
        map.animateCamera(cameraUpdate);
        Toast.makeText(getActivity(), R.string.location_loaded, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION_PERMISSION && hasLocationPermission()) {
            updateUI();
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        delegate.tryToUnsubscribe();
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        String title = marker.getTitle();
        delegate.loadPlace(title);
    }

    @Override
    public void openPlace(Place place){
        mFragmentClickOpenPlaceInterface
                .openFragment(PlaceDescriptionFragment.newInstance(place));
    }
}
