package com.dicoding.picodiploma.myentertainmentlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dicoding.picodiploma.myentertainmentlist.db.MovieHelper;
import com.dicoding.picodiploma.myentertainmentlist.entity.Cast;
import com.dicoding.picodiploma.myentertainmentlist.entity.Movie;
import com.dicoding.picodiploma.myentertainmentlist.ui.main.MovieDetailViewModel;

import static com.dicoding.picodiploma.myentertainmentlist.db.DatabaseContract.MovieColumns.*;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String MOVIE_CAST = "movie_cast";
    public static final String MOVIE_DETAIL = "movie_detail";

    private ProgressBar progressBar;
    Toolbar backToolbar;
    private int movieId;

    private ImageView ivPosterBg;
    private TextView tvTitleBg;
    private TextView tvDescription;

    private ImageView ivCastPhoto1;
    private ImageView ivCastPhoto2;
    private ImageView ivCastPhoto3;

    private TextView tvCastText1;
    private TextView tvCastText2;
    private TextView tvCastText3;

    private TextView tvCategory;
    private TextView tvTrivia;

    Movie movie;
    Cast cast;

    private MovieDetailViewModel movieDetailViewModel;

    MovieHelper movieHelper;
    private boolean IS_FAV = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        progressBar = findViewById(R.id.progressBar);

        ivPosterBg = findViewById(R.id.img_poster_bg);
        tvTitleBg = findViewById(R.id.txt_title_bg);
        tvDescription = findViewById(R.id.txt_description);

        ivCastPhoto1 = findViewById(R.id.img_cast_1);
        ivCastPhoto2 = findViewById(R.id.img_cast_2);
        ivCastPhoto3 = findViewById(R.id.img_cast_3);

        tvCastText1 = findViewById(R.id.txt_cast_1);
        tvCastText2 = findViewById(R.id.txt_cast_2);
        tvCastText3 = findViewById(R.id.txt_cast_3);

        tvCategory = findViewById(R.id.txt_category);
        tvTrivia = findViewById(R.id.txt_trivia);

        backToolbar = (Toolbar) findViewById(R.id.tb_back);
        setSupportActionBar(backToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        movie = getIntent().getParcelableExtra(MOVIE_DETAIL);
        movieId = movie.getMovie_id();

        cast = new Cast();

        movieDetailViewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(MovieDetailViewModel.class);

        movieHelper = MovieHelper.getInstance(getApplicationContext());
        movieHelper.open();

        if (movieHelper.queryById(String.valueOf(movieId)).moveToFirst()) {
            IS_FAV = true;
            invalidateOptionsMenu();
        }

        if (savedInstanceState != null) {

        } else {
            movieDetailViewModel.setMovieCasts(movieId);
            movieDetailViewModel.setMovieDetail(movieId);
            showLoading(true);
        }

        movieDetailViewModel.getMovieDetail().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movieParam) {
                if (movieParam != null) {

                    Log.d("Poster", movieParam.getPoster());

                    movie.setPoster(movieParam.getPoster());
                    movie.setDescription(movieParam.getDescription());
                    movie.setTitle(movieParam.getTitle());

                    movie.setMovie_id(movieParam.getMovie_id());

                    movie.setCategory(movieParam.getCategory());
                    movie.setTrivia(movieParam.getTrivia());

                    setData(movie);

                    showLoading(false);
                }
            }
        });

        movieDetailViewModel.getCast().observe(this, new Observer<Cast>() {
            @Override
            public void onChanged(Cast castParam) {
                if (castParam != null) {
                    Log.d("GET CAST PHOTO 1", castParam.getCast_photo_1());
                    cast.setCast_photo_1(castParam.getCast_photo_1());
                    cast.setCast_photo_2(castParam.getCast_photo_2());
                    cast.setCast_photo_3(castParam.getCast_photo_3());
                    cast.setCast_name_1(castParam.getCast_name_1());
                    cast.setCast_name_2(castParam.getCast_name_2());
                    cast.setCast_name_3(castParam.getCast_name_3());

                    setCastData(cast);

                    showLoading(false);
                } else {
                    Log.d("CAST NULL", "CAST NULL");
                }
            }
        });

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(IS_FAV)
            menu.findItem(R.id.action_fav).setIcon(R.drawable.ic_favorite_white_24dp);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_fav) {
            if (IS_FAV == false) {
                ContentValues values = new ContentValues();
                values.put(TITLE, movie.getTitle());
                values.put(ID, movieId);

                long result = movieHelper.insert(values);
                if (result > 0) {
                    item.setIcon(R.drawable.ic_favorite_white_24dp);
                    IS_FAV = true;
                    Toast.makeText(this, "Berhasil menambah data", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Gagal menambah data", Toast.LENGTH_SHORT).show();
                }
            } else {
                long result = movieHelper.deleteById(String.valueOf(movieId));
                if (result > 0) {
                    item.setIcon(R.drawable.ic_favorite_border_white_24dp);
                    IS_FAV = false;
                    Toast.makeText(this, "Berhasil menghapus data", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Gagal menghapus data", Toast.LENGTH_SHORT).show();
                }

            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE) {
//            Log.d("refreshMovies", "Request Code");
//            mOnActivityListener.onActivityRefreshListener();
//            mOnTVActivityListener.onTVActivityRefreshListener();
//        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MOVIE_DETAIL, movie);
        outState.putParcelable(MOVIE_CAST, cast);
    }

    public void setCastData(Cast castParam) {

        Glide.with(MovieDetailActivity.this)
                .load("https://image.tmdb.org/t/p/w185" + castParam.getCast_photo_1())
                .into(ivCastPhoto1);

        Glide.with(MovieDetailActivity.this)
                .load("https://image.tmdb.org/t/p/w185" + castParam.getCast_photo_2())
                .into(ivCastPhoto2);

        Glide.with(MovieDetailActivity.this)
                .load("https://image.tmdb.org/t/p/w185" + castParam.getCast_photo_3())
                .into(ivCastPhoto3);

        tvCastText1.setText(castParam.getCast_name_1());
        tvCastText2.setText(castParam.getCast_name_2());
        tvCastText3.setText(castParam.getCast_name_3());

    }

    public void setData(Movie movieParam) {

        Glide.with(MovieDetailActivity.this)
                .load("https://image.tmdb.org/t/p/w185" + movieParam.getPoster())
                .into(ivPosterBg);

        tvTitleBg.setText(movieParam.getTitle());
        tvDescription.setText(HtmlCompat.fromHtml(movieParam.getDescription(), HtmlCompat.FROM_HTML_MODE_LEGACY));

        tvCategory.setText(movie.getCategory());
        tvTrivia.setText(movie.getTrivia());

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        movieHelper.close();
    }
}
