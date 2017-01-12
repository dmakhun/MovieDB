package com.tmdb.moviedb.controller;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tmdb.moviedb.MainActivity;
import com.tmdb.moviedb.R;
import com.tmdb.moviedb.adapter.MovieSlideAdapter;

/**
 * Created by dmak on 11.01.17.
 */

public class MovieSlideTab extends Fragment {
    private ViewPager viewPager;
    private MovieSlideAdapter adapter;

    private MainActivity activity;
    private Bundle savedInstanceState;
    private int currPos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.moviepager, container, false);
        adapter = new MovieSlideAdapter(getFragmentManager(), getResources());
        viewPager = (ViewPager) view.findViewById(R.id.moviePager);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        activity = (MainActivity) getActivity();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.savedInstanceState = savedInstanceState;

        currPos = activity.getCurrentMovViewPagerPos();
        viewPager.setCurrentItem(currPos);
        activity.setMovieSlideTab(this);

    }
}
