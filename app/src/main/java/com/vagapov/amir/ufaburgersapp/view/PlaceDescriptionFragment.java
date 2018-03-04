package com.vagapov.amir.ufaburgersapp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vagapov.amir.ufaburgersapp.R;
import com.vagapov.amir.ufaburgersapp.model.Burgers;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PlaceDescriptionFragment extends Fragment {

    private static final String BUNDLE_PLACE = "place";
    private Burgers place;
    private GoogleMap map;


    @BindView(R.id.tv_name)
    TextView tvName;

    @BindView(R.id.description_map)
    MapView mapView;

    @BindView(R.id.nested_scroll_view_description)
    NestedScrollView scroll;

    public static PlaceDescriptionFragment newInstance(Burgers place) {
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_PLACE , place);
        PlaceDescriptionFragment fragment = new PlaceDescriptionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();
        place = (Burgers) arg.getSerializable(BUNDLE_PLACE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_place_description, container, false);
        ButterKnife.bind(this, v);
        tvName.setText(place.getName());
        createMap(savedInstanceState);

        return v;
    }

    //TODO обработать тач на mapView

    private void createMap(@Nullable Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        scroll.requestDisallowInterceptTouchEvent(true);
        MapsInitializer.initialize(getActivity().getApplicationContext());
        mapView.getMapAsync(googleMap -> {
            map = googleMap;
            //TODO requestPermissions
            map.setMyLocationEnabled(true);
            LatLng ufa = new LatLng(54.7388, 55.9721);
            map.addMarker(new MarkerOptions().position(ufa).title(place.getName()).snippet(place.getName()));
            CameraPosition position = new CameraPosition.Builder().target(ufa).zoom(13).build();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(position));
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
