package com.tmdb.myapplication;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import info.movito.themoviedbapi.model.MovieDb;

/**
 * Created by Dima on 05.01.2016.
 */
public class MainActivity extends Activity implements MovieListFragment.OnListFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MovieListFragment movieListFragment = new MovieListFragment();
        fragmentTransaction.add(R.id.movieList, movieListFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onListFragmentInteraction(MovieDb item) {
    }
}
