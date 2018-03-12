package com.vagapov.amir.ufaburgersapp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.vagapov.amir.ufaburgersapp.R;
import com.vagapov.amir.ufaburgersapp.model.Burgers;
import com.vagapov.amir.ufaburgersapp.model.Place;
import com.vagapov.amir.ufaburgersapp.presenter.CommentPresenterImpl;
import com.vagapov.amir.ufaburgersapp.presenter.interfaces.CommentPresenter;
import com.vagapov.amir.ufaburgersapp.view.interfaces.CommentFragmentView;


public class CommentFragment extends MvpFragment<CommentFragmentView, CommentPresenter>
        implements CommentFragmentView{
    private static final String ARGS = "place";
    private Place place;


    public static CommentFragment newInstance(Place place) {
        Bundle args = new Bundle();
        args.putSerializable(ARGS, place);
        CommentFragment fragment = new CommentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public CommentPresenter createPresenter() {
        return new CommentPresenterImpl();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        place = (Burgers) getArguments().getSerializable(ARGS);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_comment, container, false);
        return v;
    }
}
