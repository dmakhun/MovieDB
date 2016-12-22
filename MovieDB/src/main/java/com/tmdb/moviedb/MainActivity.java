package com.tmdb.moviedb;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.tmdb.moviedb.controller.MovieListFragment;

import java.io.File;
import java.lang.reflect.Field;

import info.movito.themoviedbapi.model.MovieDb;

/**
 * Created by Dima on 05.01.2016.
 */
public class MainActivity extends AppCompatActivity implements MovieListFragment.OnListFragmentInteractionListener {

    private MovieListFragment movieListFragment = new MovieListFragment();
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

/*        // Get the action bar title to set padding
        TextView titleTextView = null;

        try {
            Field f = toolbar.getClass().getDeclaredField("mTitleTextView");
            f.setAccessible(true);
            titleTextView = (TextView) f.get(toolbar);
        } catch (NoSuchFieldException e) {

        } catch (IllegalAccessException e) {

        }

        if (titleTextView != null) {
            float scale = getResources().getDisplayMetrics().density;
            titleTextView.setPadding((int) scale * 15, 0, 0, 0);
        }*/

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

        displayView(1);
    }

    private void displayView(int position) {
        if (position != 0) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            Fragment fragment = null;

            switch (position) {
                case 1:
                    fragment = movieListFragment;
            }

            if (fragment != null) {
                fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
            }
        }
    }

    @Override
    public void onListFragmentInteraction(MovieDb movieDb) {

    }
}
