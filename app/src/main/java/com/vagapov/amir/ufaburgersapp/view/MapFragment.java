package com.vagapov.amir.ufaburgersapp.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends SupportMapFragment {

    private GoogleMap map;
    private Location currentLocation;
    private PermissionDialogFragment permissionDialog;

    private static final String[] LOCATION_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private static final int REQUEST_LOCATION_PERMISSION = 0;


    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);



        createPermissionDialog();
        checkPermissions();

        getMapAsync(googleMap -> {
            map = googleMap;
            getLocation();
        });
    }

    private void createPermissionDialog() {
        permissionDialog = PermissionDialogFragment.newInstance();
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
        LatLng myPoint = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        MarkerOptions myMarker = new MarkerOptions().position(myPoint);

        map.clear();
        map.addMarker(myMarker);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(myPoint, 13.0f);
        map.animateCamera(cameraUpdate);
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
}
