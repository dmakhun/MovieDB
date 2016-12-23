package com.tmdb.moviedb.adapter;

import android.app.FragmentManager;
import android.content.res.Resources;
import android.app.Fragment;
import android.os.Parcelable;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;

import com.tmdb.moviedb.MainActivity;
import com.tmdb.moviedb.R;

/**
 * Created by dmak on 21.12.16.
 */
public class MovieDetailsSlideAdapter extends FragmentStatePagerAdapter {
    private String[] navMenuTitles;
    /*
     * We use registeredFragments so we can get our active fragments from the app.
     */
    private SparseArray<Fragment> registeredFragments = new SparseArray<>();
    private MainActivity activity;

    public MovieDetailsSlideAdapter(FragmentManager fm, Resources res, MainActivity activity) {
        super(fm);
        navMenuTitles = res.getStringArray(R.array.detailTabs);
        this.activity = activity;
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return navMenuTitles[0];
            default:
                return navMenuTitles[0];
        }
    }

    /**
     * Create the page for the given position. The adapter is responsible for adding the view to the container given here,
     * although it only must ensure this is done by the time it returns from finishUpdate(ViewGroup).
     *
     * @param container The containing View in which the page will be shown.
     * @param position  The page position to be instantiated.
     * @return Returns an Object representing the new page. This does not need to be a View, but can be some other container of the page.
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    /**
     * Remove a page for the given position. The adapter is responsible for removing the view from its container,
     * although it only must ensure this is done by the time it returns from finishUpdate(ViewGroup)
     *
     * @param container The containing View from which the page will be removed.
     * @param position  The page position to be removed.
     * @param object    The same object that was returned by instantiateItem(View, int).
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

/*    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        try {
            if (activity.getRestoreMovieDetailsAdapterState()) {
                super.restoreState(state, loader);
            } else {
                activity.setRestoreMovieDetailsAdapterState(true);
            }
        } catch (java.lang.IllegalStateException e) {

        }
    }*/
}
