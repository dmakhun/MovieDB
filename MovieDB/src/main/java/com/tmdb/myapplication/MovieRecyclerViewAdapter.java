package com.tmdb.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tmdb.myapplication.MovieListFragment.OnListFragmentInteractionListener;

import java.util.List;

import info.movito.themoviedbapi.model.MovieDb;

/**
 * {@link RecyclerView.Adapter} that can display a {@link MovieDb} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {

    private final List<MovieDb> moviesList;
    private final OnListFragmentInteractionListener mListener;

    public MovieRecyclerViewAdapter(List<MovieDb> items, OnListFragmentInteractionListener listener) {
        moviesList = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = moviesList.get(position);
        holder.movieTitle.setText(moviesList.get(position).getOriginalTitle());
        holder.releaseDate.setText(moviesList.get(position).getReleaseDate().substring(0, 4));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView movieTitle;
        public final TextView releaseDate;
        public MovieDb mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            movieTitle = (TextView) view.findViewById(R.id.movieTitle);
            releaseDate = (TextView) view.findViewById(R.id.releaseDate);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + releaseDate.getText() + "'";
        }
    }
}
