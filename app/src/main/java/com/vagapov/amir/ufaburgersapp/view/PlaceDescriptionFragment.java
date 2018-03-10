package com.vagapov.amir.ufaburgersapp.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateFragment;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.RetainingLceViewState;
import com.vagapov.amir.ufaburgersapp.R;
import com.vagapov.amir.ufaburgersapp.model.Comment;
import com.vagapov.amir.ufaburgersapp.model.Place;
import com.vagapov.amir.ufaburgersapp.presenter.PlaceDescriptionPresenter;
import com.vagapov.amir.ufaburgersapp.presenter.PlaceDescriptionPresenterImpl;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PlaceDescriptionFragment extends MvpFragment<PlaceDescriptionView,
        PlaceDescriptionPresenter> implements View.OnClickListener, PlaceDescriptionView {

    private static final String URI_MAP = "geo:";
    private static final String BUNDLE_PLACE = "place";
    private Place place;
    private GoogleMap map;
    private FragmentClickOpenPlaceInterface openFragmentInterface;

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.description_map)
    MapView mapView;
    @BindView(R.id.item_description_like)
    ImageButton like;
    @BindView(R.id.nested_scroll_view_description)
    NestedScrollView nestedScrollView;
    @BindView(R.id.map_frame_description)
    FrameForMapDescription frame;
    @BindView(R.id.left_comment_button)
    ImageButton commentButton;
    @BindView(R.id.item_description_dislike)
    ImageButton dislike;
    @BindView(R.id.item_description_rating)
    TextView tvRating;
    @BindView(R.id.item_description)
    TextView tvDescription;
    @BindView(R.id.place_detail_toolbar_photo)
    ImageView placesPhoto;

    private MenuItem menuItemLike;


    public static PlaceDescriptionFragment newInstance(Place place) {
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_PLACE , place);
        PlaceDescriptionFragment fragment = new PlaceDescriptionFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("TAGF", "onAttach");
        openFragmentInterface = (FragmentClickOpenPlaceInterface) context;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        Log.d("TAGF", "onCreateOptionsMenu");
        inflater.inflate(R.menu.fragment_place_description, menu);
        menuItemLike = menu.findItem(R.id.item_menu_like);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(place.getName());
        presenter.loadPlace();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_menu_like:
                presenter.setPlacesFavourite();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public PlaceDescriptionPresenter createPresenter() {
        Log.d("TAGF", "createPresenter");
        return new PlaceDescriptionPresenterImpl(place, openFragmentInterface);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TAGF", "onCreate");
        setHasOptionsMenu(true);
        Bundle arg = getArguments();
        place = (Place) arg.getSerializable(BUNDLE_PLACE);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("TAGF", "onViewCreated");
        ButterKnife.bind(this, view);
        createMap(savedInstanceState);
        like.setOnClickListener(this);
        dislike.setOnClickListener(this);
        commentButton.setOnClickListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("TAGF", "onCreateView");
        return inflater.inflate(R.layout.fragment_place_description, container, false);
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
                presenter.openCommentFragment(CommentFragment.newInstance(place));
                break;
            case R.id.item_description_like:
                presenter.like();
                break;
            case R.id.item_description_dislike:
                presenter.dislike();
                break;
            case R.id.place_detail_toolbar_photo:
                presenter.openImage();
                break;
            default:
                break;
        }
    }

    @Override
    public void checkFavourite() {
        Log.d("TAGF", "checkFavourite");
        menuItemLike.setIcon(place.isFavourite() ?
                        getResources().getDrawable(R.drawable.ic_heart_pink) :
                        getResources().getDrawable(R.drawable.ic_heart));
    }

    @Override
    public void setName(String name) {
        tvName.setText(name);
    }

    @Override
    public void setRating(String rating) {
        tvRating.setText(rating);
    }

    @Override
    public void setDescription(String text) {
        tvDescription.setText(text);
    }


    @Override
    public void onDetach() {
        Log.d("TAG", "DETACH");
        super.onDetach();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
    }
}
