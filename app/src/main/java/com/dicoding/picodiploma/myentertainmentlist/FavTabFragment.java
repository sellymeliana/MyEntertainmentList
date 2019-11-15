package com.dicoding.picodiploma.myentertainmentlist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
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
        final ViewPager viewPager = view.findViewById(R.id.viewpager);
        SectionsPagerAdapter sectionsPagerAdapter =
                new SectionsPagerAdapter(getContext(), getChildFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


        return view;
    }
}