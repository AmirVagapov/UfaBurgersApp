package com.vagapov.amir.ufaburgersapp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.vagapov.amir.ufaburgersapp.MainActivity;
import com.vagapov.amir.ufaburgersapp.R;
import com.vagapov.amir.ufaburgersapp.model.Burgers;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class PlacesFragment extends Fragment{

    @BindView(R.id.best_recycler_view)
    RecyclerView bestRecyclerList;
    @BindView(R.id.new_recycler_view)
    RecyclerView newRecyclerList;
    @BindView(R.id.all_recycler_view)
    RecyclerView allRecyclerList;

    private Unbinder unbinder;
    private ArrayList<Burgers> mockList = new ArrayList<>();
    private FragmentClickInterface fragmentClickInterface;

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

        mockPlacelist();
        createRecyclerView();

        return v;
    }

    // TODO: 02.03.2018 delete mockPlacelist()
    private void mockPlacelist() {
        mockList.add(new Burgers("BurgerHeroes"));
        mockList.add(new Burgers("GBP"));
        mockList.add(new Burgers("Shaxta"));
        mockList.add(new Burgers("PrimeBurgers"));
        mockList.add(new Burgers("GurmanBurgers"));
        mockList.add(new Burgers("Tesla"));
        mockList.add(new Burgers("Morris"));
        mockList.add(new Burgers("Harat's"));
        mockList.add(new Burgers("MarcoPolo"));
        mockList.add(new Burgers("BurgerHeroes"));
        mockList.add(new Burgers("GBP"));
        mockList.add(new Burgers("Shaxta"));
        mockList.add(new Burgers("PrimeBurgers"));
        mockList.add(new Burgers("GurmanBurgers"));
        mockList.add(new Burgers("Tesla"));
        mockList.add(new Burgers("Morris"));
        mockList.add(new Burgers("Harat's"));
        mockList.add(new Burgers("MarcoPolo"));
    }

    private void createRecyclerView() {
        bestRecyclerList.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));
        newRecyclerList.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));
        allRecyclerList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        allRecyclerList.setOnFlingListener(new RecyclerView.OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                allRecyclerList.dispatchNestedFling(velocityX, velocityY,false);
                return false;
            }
        });

        PlacesAdapter adapter = new PlacesAdapter(mockList);
        adapter.setFragmentClickInterface(fragment -> {
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction().addToBackStack(null).replace(R.id.container, fragment).commit();
        });
        bestRecyclerList.setAdapter(adapter);
        newRecyclerList.setAdapter(adapter);
        allRecyclerList.setAdapter(adapter);
    }


    public static PlacesFragment newInstance() {
        return new PlacesFragment();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_places, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        SearchView searchViewMenu = (SearchView) searchItem.getActionView();
        searchViewMenu.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

