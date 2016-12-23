package com.tmdb.moviedb.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.tmdb.moviedb.MDB;
import com.tmdb.moviedb.controller.OnFragmentInteractionListener;
import com.tmdb.moviedb.R;

import java.util.List;

import info.movito.themoviedbapi.model.MovieDb;

/**
 * {@link RecyclerView.Adapter} that can display a {@link MovieDb} and makes a call to the
 * specified {@link OnFragmentInteractionListener}.
 */
public class MovieListRecyclerAdapter extends RecyclerView.Adapter<MovieListRecyclerAdapter.ViewHolder> {
    private final List<MovieDb> moviesList;
    private final OnFragmentInteractionListener mListener;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private Context context;

    public MovieListRecyclerAdapter(List<MovieDb> items, OnFragmentInteractionListener listener) {
        moviesList = items;
        mListener = listener;

        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                // Bitmaps in RGB_565 consume 2 times less memory than in ARGB_8888. Caching images in memory else
                // flicker while toolbar hiding
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY)
                .cacheInMemory(true)
                .showImageOnLoading(null)
                .showImageForEmptyUri(null)
                .showImageOnFail(null)
                .cacheOnDisk(true)
                .build();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_fragment_movie, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.movieDb = moviesList.get(position);
        holder.movieTitle.setText(moviesList.get(position).getOriginalTitle());
        holder.releaseDate.setText(moviesList.get(position).getReleaseDate().substring(0, 4));
        imageLoader.displayImage(createImageURI(position), holder.posterPath, options);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onFragmentInteraction("MovieDetailsFragment", holder.movieDb);
                }
            }
        });
    }

    private String createImageURI(int position) {
        return MDB.imageUrl + context.getResources().getString(R.string.imageSize) + moviesList.get(position).getPosterPath();
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView movieTitle;
        public final TextView releaseDate;
        public ImageView posterPath;
        public MovieDb movieDb;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            movieTitle = (TextView) view.findViewById(R.id.movieTitle);
            releaseDate = (TextView) view.findViewById(R.id.releaseDate);
            posterPath = (ImageView) view.findViewById(R.id.posterPath);
        }
    }
}
