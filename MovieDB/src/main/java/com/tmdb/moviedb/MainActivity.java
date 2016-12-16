package com.tmdb.moviedb;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

import info.movito.themoviedbapi.model.MovieDb;

/**
 * Created by Dima on 05.01.2016.
 */
public class MainActivity extends Activity implements MovieListFragment.OnListFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        // Universal Loader options and configuration.
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                // Bitmaps in RGB_565 consume 2 times less memory than in ARGB_8888.
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY)
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .build();
        Context context = this;
        File cacheDir = StorageUtils.getCacheDirectory(context);
        // Create global configuration and initialize ImageLoader with this config
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .diskCache(new UnlimitedDiskCache(cacheDir)) // default
                .defaultDisplayImageOptions(options)
                .build();
        ImageLoader.getInstance().init(config);

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
