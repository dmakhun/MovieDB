package com.tmdb.moviedb.controller;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tmdb.moviedb.MDB;
import com.tmdb.moviedb.R;
import com.tmdb.moviedb.adapter.MovieListRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

import static com.tmdb.moviedb.MDB.API_KEY;
import static com.tmdb.moviedb.MDB.LANGUAGE_DEFAULT;
import static info.movito.themoviedbapi.TmdbMovies.MovieMethod.*;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class MovieListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnFragmentInteractionListener mListener;
    private List<MovieDb> movieList = new ArrayList<>();
    private static int i = 1;
    MovieListRecyclerAdapter adapter;

    private boolean loading = false;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private int visibleThreshold = 5;
    private String title;
    private String currentList;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieListFragment() {
    }

    public static MovieListFragment newInstance(int columnCount) {
        MovieListFragment fragment = new MovieListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        try {
            new MovieListAsyncTask().execute(1).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        adapter = new MovieListRecyclerAdapter(movieList, mListener);
        // Set the adapter
        if (view instanceof RecyclerView) {
            final Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(linearLayoutManager);
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(adapter);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                    if (dy > 0) { //check for scroll down
                        visibleItemCount = recyclerView.getChildCount();
                        totalItemCount = linearLayoutManager.getItemCount();
                        pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
                        if (!loading && (totalItemCount - visibleItemCount) <= (pastVisiblesItems +
                                visibleThreshold)) {
                            loading = true;
                            updateList();
                        }

                    }
                }
            });
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), // separator
                    linearLayoutManager.getOrientation());
            recyclerView.addItemDecoration(dividerItemDecoration);
        }
        return view;
    }

    private void updateList() {
        final MovieListAsyncTask movieListAsyncTask = new MovieListAsyncTask();
        try {
            movieListAsyncTask.execute(++i).get();
            loading = false;
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    class MovieListAsyncTask extends AsyncTask<Integer, Void, String> {
        @Override
        protected String doInBackground(Integer... i) {
            TmdbMovies movies = new TmdbApi(API_KEY).getMovies();
//            movies.getMovie(78, "en", credits, images, similar);

            MovieResultsPage movieResultsPage;
            switch (getCurrentList()) {
                case "popular":
                    movieResultsPage = movies.getPopularMovies(LANGUAGE_DEFAULT, i[0]);
                    break;
                case "now_playing":
                    movieResultsPage = movies.getNowPlayingMovies(LANGUAGE_DEFAULT, i[0], "");
                    break;
                case "upcoming":
                    movieResultsPage = movies.getUpcoming(LANGUAGE_DEFAULT, i[0], "");
                    break;
                case "top_rated":
                    movieResultsPage = movies.getTopRatedMovies(LANGUAGE_DEFAULT, i[0]);
                    break;
                default:
                    movieResultsPage = movies.getPopularMovies(LANGUAGE_DEFAULT, i[0]);
            }
            if (movieResultsPage != null) {
                movieList.addAll(movieResultsPage.getResults());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * Update the title. We use this method to save our title and then to set it on the Toolbar.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setCurrentList(String currentList) {
        this.currentList = currentList;
    }

    public String getCurrentList() {
        return currentList;
    }
}
