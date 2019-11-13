package com.dicoding.picodiploma.myentertainmentlist;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.dicoding.picodiploma.myentertainmentlist.db.TVShowHelper;
import com.dicoding.picodiploma.myentertainmentlist.entity.Cast;
import com.dicoding.picodiploma.myentertainmentlist.entity.TVShow;
import com.dicoding.picodiploma.myentertainmentlist.ui.main.TVShowDetailViewModel;

import static com.dicoding.picodiploma.myentertainmentlist.db.DatabaseContract.TVShowColumns.*;

public class TVShowDetailActivity extends AppCompatActivity {

    public static final String TVSHOW_CAST = "tvshow_cast";

    public static final String EXTRA_TVSHOW = "extra_tvshow";
    public static final String TVSHOW_DETAIL = "tvshow_detail";

    private ProgressBar progressBar;
    Toolbar backToolbar;

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

    private TVShowDetailViewModel tvShowDetailViewModel;

    TVShowHelper tvShowHelper;
    private boolean IS_FAV = false;

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

        backToolbar = (Toolbar) findViewById(R.id.tb_back);
        setSupportActionBar(backToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvShow = getIntent().getParcelableExtra(TVSHOW_DETAIL);
        tvShowId = tvShow.getTVShow_id();

        cast = new Cast();

        tvShowDetailViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TVShowDetailViewModel.class);

        tvShowHelper = TVShowHelper.getInstance(getApplicationContext());
        tvShowHelper.open();

        if (tvShowHelper.queryById(String.valueOf(tvShowId)).moveToFirst()) {
            IS_FAV = true;
            invalidateOptionsMenu();
        }

        if (savedInstanceState != null) {
            //setData(movie);
        } else {
            tvShowDetailViewModel.setTVShowCasts(tvShowId);
            tvShowDetailViewModel.setTVShowDetail(tvShowId);
            showLoading(true);
        }

        tvShowDetailViewModel.getTVShowDetail().observe(this, new Observer<TVShow>() {
            @Override
            public void onChanged(TVShow tvShowParam) {
                if (tvShowParam != null) {

                    Log.d("Poster", tvShowParam.getPoster());

                    tvShow.setPoster(tvShowParam.getPoster());
                    tvShow.setDescription(tvShowParam.getDescription());
                    tvShow.setTitle(tvShowParam.getTitle());

                    tvShow.setTVShow_id(tvShowParam.getTVShow_id());

                    tvShow.setCategory(tvShowParam.getCategory());
                    tvShow.setEpisodes(tvShowParam.getEpisodes());

                    setData(tvShow);

                    showLoading(false);
                }
            }
        });

        tvShowDetailViewModel.getCast().observe(this, new Observer<Cast>() {
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
                values.put(TITLE, tvShow.getTitle());
                values.put(ID, tvShowId);

                long result = tvShowHelper.insert(values);
                if (result > 0) {
                    item.setIcon(R.drawable.ic_favorite_white_24dp);
                    IS_FAV = true;
                    Toast.makeText(this, "Berhasil menambah data", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Gagal menambah data", Toast.LENGTH_SHORT).show();
                }
            } else {
                long result = tvShowHelper.deleteById(String.valueOf(tvShowId));
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
        outState.putParcelable(TVSHOW_DETAIL, tvShow);
        outState.putParcelable(TVSHOW_CAST, cast);
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

    public void setData (TVShow tvShowParam) {

        Log.d("GLIDEPOSTER", tvShowParam.getPoster());
        Glide.with(TVShowDetailActivity.this)
                .load("https://image.tmdb.org/t/p/w185" + tvShowParam.getPoster())
                .into(ivPosterBg);

        tvTitleBg.setText(tvShowParam.getTitle());
        tvDescription.setText(HtmlCompat.fromHtml(tvShowParam.getDescription(), HtmlCompat.FROM_HTML_MODE_LEGACY));

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
