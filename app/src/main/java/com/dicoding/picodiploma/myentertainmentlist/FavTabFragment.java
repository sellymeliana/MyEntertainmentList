package com.dicoding.picodiploma.myentertainmentlist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dicoding.picodiploma.myentertainmentlist.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;


public class FavTabFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_fav, container, false);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        //tabLayout.addTab(tabLayout.newTab().setText("Movies"));
        //tabLayout.addTab(tabLayout.newTab().setText("TV Shows"));
        final ViewPager viewPager = view.findViewById(R.id.viewpager);
//        viewPager.setOffscreenPageLimit(2);
//        SectionsPagerAdapter sectionsPagerAdapter =
//                new SectionsPagerAdapter(getContext(), getFragmentManager());
        SectionsPagerAdapter sectionsPagerAdapter =
                new SectionsPagerAdapter(getContext(), getChildFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

//        viewPager.setAdapter(new PagerAdapter(getFragmentManager(), tabLayout.getTabCount()));
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

        return view;
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;

        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }


        @Override
        public Fragment getItem(int position) {
            Log.d("getitemposition", String.valueOf(position));
            switch (position) {
                case 0:
                    return new FavMovieFragment();
                case 1:
                    return new FavTvShowFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }
}