package com.vagapov.amir.ufaburgersapp.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vagapov.amir.ufaburgersapp.R;
import com.vagapov.amir.ufaburgersapp.model.Burgers;
import com.vagapov.amir.ufaburgersapp.model.Place;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlaceHolder> {

    private ArrayList<Place> places;

    private FragmentClickOpenPlaceInterface mFragmentClickOpenPlaceInterface;



    public void setPlaces(ArrayList<Place> list){
        places = list;
        notifyDataSetChanged();
    }

    public ArrayList<Place> getPlaces(){
        return places;
    }

    @Override
    public PlaceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_recycler_list, parent, false);
        mFragmentClickOpenPlaceInterface = (FragmentClickOpenPlaceInterface) parent.getContext();
        return new PlaceHolder(v);
    }

    @Override
    public void onBindViewHolder(PlaceHolder holder, int position) {
        Place place = places.get(position);
        holder.bind(place);
    }

    @Override
    public int getItemCount() {
        return places == null ? 0 : places.size();
    }

    class PlaceHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.item_place_photo)
        ImageView photo;
        @BindView(R.id.item_places_name)
        TextView name;

        private Place place;


        PlaceHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            photo = itemView.findViewById(R.id.item_place_photo);
            name = itemView.findViewById(R.id.item_places_name);
        }

        void bind(Place place) {
            this.place = place;
            name.setText(place.getName());
        }

        @Override
        public void onClick(View view) {
            mFragmentClickOpenPlaceInterface
                    .openFragment(PlaceDescriptionFragment.newInstance(place));
        }
    }
}
