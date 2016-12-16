package com.tmdb.moviedb;

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
import com.tmdb.moviedb.MovieListFragment.OnListFragmentInteractionListener;

import java.util.List;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.Utils;
import info.movito.themoviedbapi.model.Artwork;
import info.movito.themoviedbapi.model.Credits;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.Multi;
import info.movito.themoviedbapi.model.Video;

import static info.movito.themoviedbapi.TmdbMovies.MovieMethod.credits;
import static info.movito.themoviedbapi.TmdbMovies.MovieMethod.images;
import static info.movito.themoviedbapi.TmdbMovies.MovieMethod.releases;
import static info.movito.themoviedbapi.TmdbMovies.MovieMethod.reviews;
import static info.movito.themoviedbapi.TmdbMovies.MovieMethod.similar;
import static info.movito.themoviedbapi.TmdbMovies.MovieMethod.videos;

/**
 * {@link RecyclerView.Adapter} that can display a {@link MovieDb} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {
    private final List<MovieDb> moviesList;
    private final OnListFragmentInteractionListener mListener;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    public MovieRecyclerViewAdapter(List<MovieDb> items, OnListFragmentInteractionListener listener) {
        moviesList = items;
        mListener = listener;

        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                // Bitmaps in RGB_565 consume 2 times less memory than in ARGB_8888. Caching images in memory else
                // flicker while toolbar hiding
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY)
                .cacheInMemory(false)
                .showImageOnLoading(null)
                .showImageForEmptyUri(null)
                .showImageOnFail(null)
                .cacheOnDisk(true)
                .build();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.movieDb = moviesList.get(position);
        holder.movieTitle.setText(moviesList.get(position).getOriginalTitle());
        holder.releaseDate.setText(moviesList.get(position).getReleaseDate().substring(0, 4));
//        TmdbApi tmdbApi = new TmdbApi(MDB.API_KEY);

//        String imagePath = Utils.createImageUrl(tmdbApi, moviesList.get(position).getPosterPath(), "w154").toString();
        imageLoader.displayImage("https://image.tmdb.org/t/p/w500/kqjL17yufvn9OVLyXYpvtyrFfak.jpg", holder.posterPath, options);

        List<Video> videos = moviesList.get(position).getVideos();
        Multi.MediaType mediaTypes = moviesList.get(position).getMediaType();
        List<Artwork> images = moviesList.get(position).getImages();
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.movieDb);
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
