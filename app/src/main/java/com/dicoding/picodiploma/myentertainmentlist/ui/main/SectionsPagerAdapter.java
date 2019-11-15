package com.dicoding.picodiploma.myentertainmentlist.ui.main;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.dicoding.picodiploma.myentertainmentlist.FavMovieFragment;
import com.dicoding.picodiploma.myentertainmentlist.FavTvShowFragment;
import com.dicoding.picodiploma.myentertainmentlist.MovieFragment;
import com.dicoding.picodiploma.myentertainmentlist.R;
import com.dicoding.picodiploma.myentertainmentlist.TVShowFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;
//    MovieFragment movieFragment;
//    TVShowFragment tvShowFragment;
    FavMovieFragment favMovieFragment;
    FavTvShowFragment favTvShowFragment;


    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        Log.d("getitemposition", String.valueOf(position));
        switch (position) {
            case 0:
                if (favMovieFragment == null) {
                    Log.d("getitemposition", "favMovieFragment is null");
                    favMovieFragment = FavMovieFragment.newInstance();
                }
                return new FavMovieFragment();
            case 1:
                if (favTvShowFragment == null) {
                    favTvShowFragment = FavTvShowFragment.newInstance();
                }
                return new FavTvShowFragment();
            default:
                return null;
        }

        //        if (position == 0) {
//            if (movieFragment == null) {
//                movieFragment = MovieFragment.newInstance();
//            }
//            return movieFragment;
//        } else {
//            if (tvShowFragment == null) {
//                tvShowFragment = TVShowFragment.newInstance();
//            }
//            return tvShowFragment;
//        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}