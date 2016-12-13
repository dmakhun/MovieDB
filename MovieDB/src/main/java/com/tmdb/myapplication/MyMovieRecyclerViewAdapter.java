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
 * TODO: Replace the implementation with code for your data type.
 */
public class MyMovieRecyclerViewAdapter extends RecyclerView.Adapter<MyMovieRecyclerViewAdapter.ViewHolder> {

    private final List<MovieDb> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyMovieRecyclerViewAdapter(List<MovieDb> items, OnListFragmentInteractionListener listener) {
        mValues = items;
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
        holder.mItem = mValues.get(position);
        holder.movieTitle.setText(mValues.get(position).getOriginalTitle());
        holder.releaseDate.setText(mValues.get(position).getReleaseDate().substring(0, 4));

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
        return mValues.size();
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
