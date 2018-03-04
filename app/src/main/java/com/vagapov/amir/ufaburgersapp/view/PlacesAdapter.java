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

    private ArrayList<Burgers> places;

    private FragmentClickInterface fragmentClickInterface;

    public void setFragmentClickInterface(FragmentClickInterface fragmentClickInterface) {
        this.fragmentClickInterface = fragmentClickInterface;
    }

    PlacesAdapter(ArrayList<Burgers> places) {
        this.places = places;
    }

    @Override
    public PlaceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_list, parent, false);
        return new PlaceHolder(v);
    }

    @Override
    public void onBindViewHolder(PlaceHolder holder, int position) {
        Burgers place = new Burgers(places.get(position).getName());
        holder.bind(place);
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    class PlaceHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.item_place_photo)
        ImageView photo;
        @BindView(R.id.item_places_name)
        TextView name;

        Burgers place;


        PlaceHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            photo = itemView.findViewById(R.id.item_place_photo);
            name = itemView.findViewById(R.id.item_places_name);
        }

        void bind(Burgers place) {
            this.place = place;
            name.setText(place.getName());
        }

        @Override
        public void onClick(View view) {
            fragmentClickInterface
                    .openFragment(PlaceDescriptionFragment.newInstance(place));
        }
    }
}
