package com.tmdb.moviedb.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;

import com.tmdb.moviedb.R;
import com.tmdb.moviedb.controller.MovieListFragment;

/**
 * Created by dmak on 10.01.17.
 */
public class MovieSlideAdapter extends FragmentPagerAdapter {
    private String[] navMenuTitles;
    private FragmentManager fragmentManager;
    private FragmentTransaction mCurTransaction = null;
    private Resources res;

    public MovieSlideAdapter(FragmentManager fm, Resources res) {
        super(fm);
        this.fragmentManager = fm;
        navMenuTitles = res.getStringArray(R.array.moviesTabs);
        this.res = res;
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position Position within this adapter
     * @return Unique identifier for the item at position
     */
    @Override
    public Fragment getItem(int position) {
        String popular = "popular";
        String nowPlaying = "now_playing";
        String upcoming = "upcoming";
        String topRated = "top_rated";
        Bundle args = new Bundle();
        switch (position) {
            case 0:
                args.putString("currentList", "popular");
                MovieListFragment popularList = new MovieListFragment();
                popularList.setTitle(res.getString(R.string.moviesTitle));
                popularList.setArguments(args);
                popularList.setCurrentList(popular);
                return popularList;
            case 1:
                args.putString("currentList", "nowPlaying");
                MovieListFragment nowPlayingList = new MovieListFragment();
                nowPlayingList.setTitle(res.getString(R.string.moviesTitle));
                nowPlayingList.setArguments(args);
                nowPlayingList.setCurrentList(nowPlaying);
                return nowPlayingList;
            case 2:
                args.putString("currentList", "upcoming");
                MovieListFragment upcomingList = new MovieListFragment();
                upcomingList.setTitle(res.getString(R.string.moviesTitle));
                upcomingList.setArguments(args);
                upcomingList.setCurrentList(upcoming);
                return upcomingList;
            case 3:
                args.putString("currentList", "topRated");
                MovieListFragment topRatedList = new MovieListFragment();
                topRatedList.setTitle(res.getString(R.string.moviesTitle));
                topRatedList.setArguments(args);
                topRatedList.setCurrentList(topRated);
                return topRatedList;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    /**
     * This method may be called by the ViewPager to obtain a title string to describe the specified page.
     *
     * @param position The position of the title requested
     * @return A title for the requested page
     */
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return navMenuTitles[0];
            case 1:
                return navMenuTitles[1];
            case 2:
                return navMenuTitles[2];
            case 3:
                return navMenuTitles[3];
            default:
                return navMenuTitles[0];
        }
    }
}
