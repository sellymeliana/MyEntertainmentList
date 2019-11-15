package com.dicoding.picodiploma.myentertainmentlist;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.dicoding.picodiploma.myentertainmentlist.entity.Movie;
import com.dicoding.picodiploma.myentertainmentlist.entity.TVShow;
import com.dicoding.picodiploma.myentertainmentlist.ui.main.SectionsPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class Main2Activity extends AppCompatActivity
        implements MovieFragment.OnListFragmentInteractionListener,
        TVShowFragment.OnListFragmentInteractionListener,
        FavMovieFragment.OnFragmentInteractionListener,
        FavTvShowFragment.OnFragmentInteractionListener {

    private int REQUEST_CODE = 100;
    Toolbar mtoolbar;
    private SectionsPagerAdapter sectionsPagerAdapter;
    private MainActivity.OnActivityListener mOnActivityListener;
    private MainActivity.OnTVActivityListener mOnTVActivityListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_movie, R.id.navigation_tvshow, R.id.navigation_fav)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
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

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE) {
//            Log.d("refreshMovies", "Request Code");
//            mOnActivityListener.onActivityRefreshListener();
//            mOnTVActivityListener.onTVActivityRefreshListener();
//        }
//    }

    @Override
    public void onListFragmentInteraction(Movie item) {
        Intent moveToDetailIntent = new Intent(this, MovieDetailActivity.class);
        moveToDetailIntent.putExtra(MovieDetailActivity.MOVIE_DETAIL, item);
        startActivity(moveToDetailIntent);
    }

    @Override
    public void onListFragmentInteraction(TVShow item) {
        Intent moveToDetailIntent = new Intent(this, TVShowDetailActivity.class);
        moveToDetailIntent.putExtra(TVShowDetailActivity.TVSHOW_DETAIL, item);
        startActivity(moveToDetailIntent);
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

    public void setOnActivityListener(MainActivity.OnActivityListener mOnActivityListener){
        this.mOnActivityListener = mOnActivityListener;
    }

    public interface OnActivityListener {
        // TODO: Update argument type and name
        void onActivityRefreshListener();
    }

    public void setOnTVActivityListener(MainActivity.OnTVActivityListener mOnActivityListener){
        this.mOnTVActivityListener = mOnActivityListener;
    }

    public interface OnTVActivityListener {
        // TODO: Update argument type and name
        void onTVActivityRefreshListener();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
