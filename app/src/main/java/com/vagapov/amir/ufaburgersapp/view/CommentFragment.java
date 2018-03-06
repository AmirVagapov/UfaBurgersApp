package com.vagapov.amir.ufaburgersapp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vagapov.amir.ufaburgersapp.R;
import com.vagapov.amir.ufaburgersapp.model.Burgers;


public class CommentFragment extends Fragment {
    private static final String ARGS = "place";
    private Burgers place;


    public static CommentFragment newInstance(Burgers place) {
        Bundle args = new Bundle();
        args.putSerializable(ARGS, place);
        CommentFragment fragment = new CommentFragment();
        fragment.setArguments(args);
        return fragment;
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
