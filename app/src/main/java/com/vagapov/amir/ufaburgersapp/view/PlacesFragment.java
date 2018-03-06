package com.vagapov.amir.ufaburgersapp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import com.vagapov.amir.ufaburgersapp.R;
import com.vagapov.amir.ufaburgersapp.model.Burgers;
import com.vagapov.amir.ufaburgersapp.model.PlacesLocationObservable;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;

import static com.vagapov.amir.ufaburgersapp.model.MockBurgersList.mockPlacelist;


public class PlacesFragment extends Fragment {

    @BindView(R.id.best_recycler_view)
    RecyclerView bestRecyclerList;
    @BindView(R.id.new_recycler_view)
    RecyclerView favouriteRecyclerList;
    @BindView(R.id.all_recycler_view)
    RecyclerView allRecyclerList;

    private Unbinder unbinder;
    private ArrayList<Burgers> mockBurgers = new ArrayList<>();
    private Subscription subscription;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_places, container, false);
        unbinder = ButterKnife.bind(this, v);
        Log.d("TAG", "ONCREATE");
        mockBurgers = mockPlacelist();
        createRecyclerView();

        return v;
    }



    private void createRecyclerView() {
        bestRecyclerList.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));
        favouriteRecyclerList.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));
        allRecyclerList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        allRecyclerList.setOnFlingListener(new RecyclerView.OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                allRecyclerList.dispatchNestedFling(velocityX, velocityY, false);
                return false;
            }
        });
        PlacesAdapter adapter = new PlacesAdapter(mockBurgers);


        bestRecyclerList.setAdapter(adapter);
        allRecyclerList.setAdapter(adapter);
        setFavouriteAdapter();
    }


    private void setFavouriteAdapter() {
        subscription = PlacesLocationObservable.getPlaces()
                .filter(Burgers::isFavourite)
                .reduce(new ArrayList<Burgers>(), (list, burgers) -> {
                    list.add(burgers);
                    return list;
                }).subscribe(list -> setAdapterList(favouriteRecyclerList, list),
                throwable -> setAdapterList(favouriteRecyclerList, new ArrayList<>()));
    }

    private void setAdapterList(RecyclerView recyclerList, ArrayList<Burgers> list) {
        recyclerList.setAdapter(new PlacesAdapter(list));
    }


    @NonNull
    public static PlacesFragment newInstance() {
        return new PlacesFragment();
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
                PlacesLocationObservable.getPlaces()
                        .filter(burgers -> burgers.getName().toLowerCase().contains(newText.toLowerCase()))
                        .scan(new ArrayList<Burgers>(), (burgersList, burgers) -> {
                            burgersList.add(burgers);
                            return burgersList;
                        }).subscribe(list ->
                    setAdapterList(allRecyclerList, list),
                        throwable -> Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show());
                        return true;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if(subscription != null && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }
}

