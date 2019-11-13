package com.dicoding.picodiploma.myentertainmentlist;

import android.content.Intent;
import android.os.Bundle;

import com.dicoding.picodiploma.myentertainmentlist.entity.Movie;
import com.dicoding.picodiploma.myentertainmentlist.entity.TVShow;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.dicoding.picodiploma.myentertainmentlist.ui.main.SectionsPagerAdapter;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements MovieFragment.OnListFragmentInteractionListener,
        TVShowFragment.OnListFragmentInteractionListener {

    Toolbar mtoolbar;
    private int REQUEST_CODE = 100;
    private SectionsPagerAdapter sectionsPagerAdapter;
    private OnActivityListener mOnActivityListener;
    private OnTVActivityListener mOnTVActivityListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);

        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        Locale current = getResources().getConfiguration().locale;
        String lang = current.getISO3Language();
        Log.d("locale", getResources().getConfiguration().locale.getCountry());

    }

    @Override
    public void onListFragmentInteraction(Movie item) {
        Intent moveToDetailIntent = new Intent(MainActivity.this, MovieDetailActivity.class);
        moveToDetailIntent.putExtra(MovieDetailActivity.MOVIE_DETAIL, item);
        startActivity(moveToDetailIntent);
    }

    @Override
    public void onListFragmentInteraction(TVShow item) {
        Intent moveToDetailIntent = new Intent(MainActivity.this, TVShowDetailActivity.class);
        moveToDetailIntent.putExtra(TVShowDetailActivity.TVSHOW_DETAIL, item);
        startActivity(moveToDetailIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivityForResult(mIntent, REQUEST_CODE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            Log.d("refreshMovies", "Request Code");
            mOnActivityListener.onActivityRefreshListener();
            mOnTVActivityListener.onTVActivityRefreshListener();
        }
    }

    public void setOnActivityListener(OnActivityListener mOnActivityListener){
        this.mOnActivityListener = mOnActivityListener;
    }

    public interface OnActivityListener {
        // TODO: Update argument type and name
        void onActivityRefreshListener();
    }

    public void setOnTVActivityListener(OnTVActivityListener mOnActivityListener){
        this.mOnTVActivityListener = mOnActivityListener;
    }

    public interface OnTVActivityListener {
        // TODO: Update argument type and name
        void onTVActivityRefreshListener();
    }
}


