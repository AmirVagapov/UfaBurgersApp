package com.vagapov.amir.ufaburgersapp.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageButton;
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


public class PlaceDescriptionFragment extends Fragment implements View.OnClickListener {

    private static final String URI_MAP = "geo:";
    private static final String BUNDLE_PLACE = "place";
    private Burgers place;
    private GoogleMap map;
    private FragmentClickOpenPlaceInterface openFragmentInterface;
    private View v;

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.description_map)
    MapView mapView;
    @BindView(R.id.item_description_like)
    ImageButton like;
    @BindView(R.id.nested_scroll_view_description)
    NestedScrollView nestedScrollView;
    @BindView(R.id.map_frame_description)
    MyFrame frame;
    @BindView(R.id.left_comment_button)
    ImageButton commentButton;



    public static PlaceDescriptionFragment newInstance(Burgers place) {
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_PLACE , place);
        PlaceDescriptionFragment fragment = new PlaceDescriptionFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        openFragmentInterface = (FragmentClickOpenPlaceInterface) context;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_place_description, menu);
        MenuItem like = menu.findItem(R.id.item_menu_like);
        checkFavourite(like);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(place.getName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_menu_like:
                setFavourite(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle arg = getArguments();
        place = (Burgers) arg.getSerializable(BUNDLE_PLACE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_place_description, container, false);
        ButterKnife.bind(this, v);
        tvName.setText(place.getName());
        createMap(savedInstanceState);
        like.setOnClickListener(this);
        commentButton.setOnClickListener(this);
        return v;
    }



    private void createMap(@Nullable Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        MapsInitializer.initialize(getActivity().getApplicationContext());
        mapView.requestDisallowInterceptTouchEvent(true);
        mapView.getMapAsync(googleMap -> {
            map = googleMap;
            map.addMarker(new MarkerOptions()
                    .position(place.getLatLng())
                    .title(place.getName()));
            CameraPosition position = new CameraPosition.Builder()
                    .target(place.getLatLng()).zoom(13).build();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(position));
            map.setOnMapClickListener(latLng -> {
                String uriLocation = createUriLocation(place.getLatLng());
                Uri intentUri = Uri.parse(uriLocation);
                Intent intent = new Intent(Intent.ACTION_VIEW, intentUri);
                intent.setPackage(getString(R.string.google_intent_package));
                if(intent.resolveActivity(getActivity().getPackageManager()) != null){
                    startActivity(intent);
                }
            });
        });

    }

    @NonNull
    private String createUriLocation(LatLng location) {
        StringBuilder builder = new StringBuilder(URI_MAP);
        builder.append(location.latitude)
                .append(", ")
                .append(location.longitude)
                .append("?q=")
                .append(place.getName());
        return builder.toString();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.left_comment_button:
                openFragmentInterface.openFragment(CommentFragment.newInstance(place));

                break;
            default:
                break;
        }
    }

    private void setFavourite(MenuItem item) {
        Log.d("TAG", "setFavourite: ");
        place.setFavourite(!place.isFavourite());
        checkFavourite(item);
    }

    private void checkFavourite(MenuItem item) {
        item.setIcon(place.isFavourite() ?
                        getResources().getDrawable(R.drawable.ic_heart_pink) :
                        getResources().getDrawable(R.drawable.ic_heart));
    }


    @Override
    public void onDetach() {
        Log.d("TAG", "DETACH");
        super.onDetach();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
    }




}
