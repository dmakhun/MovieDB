package com.tmdb.myapplication;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;

/**
 * Created by Dima on 05.01.2016.
 */
public class MainActivity extends Activity implements MovieListFragment.OnListFragmentInteractionListener{

    public static String API_KEY = "480d5ed7806eae2f698579a1af802964";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MovieListFragment movieListFragment = new MovieListFragment();
        fragmentTransaction.add(R.id.list, movieListFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onListFragmentInteraction(MovieDb item) {

    }

    class AsyncMovie extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... api_key) {
            TmdbMovies movies = new TmdbApi(api_key[0]).getMovies();
            MovieDb movie = movies.getMovie(5353, "en");
            return movie.getTitle();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
