package com.tmdb.moviedb;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.tmdb.moviedb.controller.MovieDetailsFragment;
import com.tmdb.moviedb.controller.MovieListFragment;
import com.tmdb.moviedb.controller.OnFragmentInteractionListener;

import java.io.File;

import info.movito.themoviedbapi.model.MovieDb;

/**
 * Created by Dima on 05.01.2016.
 */
public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener<Object> {

    private MovieListFragment movieListFragment = new MovieListFragment();
    private MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.bringToFront();
        }

        loadImageOptions();

        displayView("MovieListFragment", 1);
    }

    private void loadImageOptions() {
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
    }

    private void displayView(String tag, int position) {
        if (position != 0) {
            FragmentManager fragmentManager = getFragmentManager();
//            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            Fragment fragment = null;

            switch (position) {
                case 1:
                    fragment = movieListFragment;
                    break;
                case 2:
                    fragment = movieDetailsFragment;
                    break;
            }

            if (fragment != null) {
                fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(tag).commit();
            }
        }
    }

    @Override
    public void onFragmentInteraction(String tag, Object data) {
        if (tag.equals("MovieDetailsFragment")) {
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.frame_container, movieDetailsFragment);
            transaction.addToBackStack("MovieDetailsFragment");
            transaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() != 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
