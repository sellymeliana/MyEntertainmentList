package com.dicoding.picodiploma.myentertainmentlist;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.dicoding.picodiploma.myentertainmentlist.dummy.Cast;
import com.dicoding.picodiploma.myentertainmentlist.dummy.TVShow;
import com.dicoding.picodiploma.myentertainmentlist.ui.main.TVShowDetailViewModel;

public class TVShowDetailActivity extends AppCompatActivity {

    public static final String MOVIE_CAST = "movie_cast";

    public static final String EXTRA_TVSHOW = "extra_tvshow";
    public static final String TVSHOW_DETAIL = "tvshow_detail";

    private ProgressBar progressBar;
    private int tvShowId;

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
    private TextView tvEpisodes;

    TVShow tvShow;
    Cast cast;

    private TVShowDetailViewModel movieDetailViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_detail);

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
        tvEpisodes = findViewById(R.id.txt_episodes);

        Toolbar backToolbar = (Toolbar) findViewById(R.id.tb_back);
        setSupportActionBar(backToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvShow = getIntent().getParcelableExtra(TVSHOW_DETAIL);
        tvShowId = tvShow.getTVShow_id();

        cast = new Cast();
        //cast = getIntent().getParcelableExtra(MOVIE_CAST);

        movieDetailViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TVShowDetailViewModel.class);

        if (savedInstanceState != null) {
            //setData(movie);
        } else {
            movieDetailViewModel.setTVShowCasts(tvShowId);
            movieDetailViewModel.setTVShowDetail(tvShowId);
            showLoading(true);
        }

        movieDetailViewModel.getTVShowDetail().observe(this, new Observer<TVShow>() {
            @Override
            public void onChanged(TVShow movieParam) {
                if (movieParam != null) {

                    Log.d("Poster", movieParam.getPoster());

                    tvShow.setPoster(movieParam.getPoster());
                    tvShow.setDescription(movieParam.getDescription());
                    tvShow.setTitle(movieParam.getTitle());

                    tvShow.setTVShow_id(movieParam.getTVShow_id());

                    tvShow.setCategory(movieParam.getCategory());
                    tvShow.setEpisodes(movieParam.getEpisodes());

                    setData(tvShow);

                    showLoading(false);
                }
            }
        });

        movieDetailViewModel.getCast().observe(this, new Observer<Cast>() {
            @Override
            public void onChanged(Cast castParam) {
                if (castParam != null) {
                    cast.setCast_photo_1(castParam.getCast_photo_1());
                    cast.setCast_photo_2(castParam.getCast_photo_2());
                    cast.setCast_photo_3(castParam.getCast_photo_3());
                    cast.setCast_name_1(castParam.getCast_name_1());
                    cast.setCast_name_2(castParam.getCast_name_2());
                    cast.setCast_name_3(castParam.getCast_name_3());

                    setCastData(cast);

                    showLoading(false);
                }
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(TVSHOW_DETAIL, tvShow);
        outState.putParcelable(MOVIE_CAST, cast);
    }

    public void setCastData (Cast castParam) {

        Glide.with(TVShowDetailActivity.this)
                .load("https://image.tmdb.org/t/p/w185" + castParam.getCast_photo_1())
                .into(ivCastPhoto1);

        Glide.with(TVShowDetailActivity.this)
                .load("https://image.tmdb.org/t/p/w185" + castParam.getCast_photo_2())
                .into(ivCastPhoto2);

        Glide.with(TVShowDetailActivity.this)
                .load("https://image.tmdb.org/t/p/w185" + castParam.getCast_photo_3())
                .into(ivCastPhoto3);

        tvCastText1.setText(castParam.getCast_name_1());
        tvCastText2.setText(castParam.getCast_name_2());
        tvCastText3.setText(castParam.getCast_name_3());

    }

    public void setData (TVShow movieParam) {

        Log.d("GLIDEPOSTER", movieParam.getPoster());
        Glide.with(TVShowDetailActivity.this)
                .load("https://image.tmdb.org/t/p/w185" + movieParam.getPoster())
                .into(ivPosterBg);

        tvTitleBg.setText(movieParam.getTitle());
        tvDescription.setText(HtmlCompat.fromHtml(movieParam.getDescription(), HtmlCompat.FROM_HTML_MODE_LEGACY));

        tvCategory.setText(tvShow.getCategory());
        tvEpisodes.setText(tvShow.getEpisodes());

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
}
