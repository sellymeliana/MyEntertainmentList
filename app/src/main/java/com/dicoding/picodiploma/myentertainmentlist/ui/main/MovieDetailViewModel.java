package com.dicoding.picodiploma.myentertainmentlist.ui.main;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dicoding.picodiploma.myentertainmentlist.entity.Cast;
import com.dicoding.picodiploma.myentertainmentlist.entity.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class MovieDetailViewModel extends ViewModel {
    private static final String API_KEY = "9ac9069d0b4aea71d158e1c987d85923";
    private MutableLiveData<Cast> castMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Movie> movieDetailMutableLiveData = new MutableLiveData<>();

    public void setMovieCasts (int movieId) {
        AsyncHttpClient client = new AsyncHttpClient();

        String url = "https://api.themoviedb.org/3/movie/" + movieId + "/credits?api_key=" + API_KEY ;

        Log.d("Cast", url);
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("cast");

                    Cast casts = new Cast();

                    Log.d("Cast Name 1", list.getJSONObject(0).getString("name"));

                    casts.setCast_photo_1(list.getJSONObject(0).getString("profile_path"));
                    casts.setCast_photo_2(list.getJSONObject(1).getString("profile_path"));
                    casts.setCast_photo_3(list.getJSONObject(2).getString("profile_path"));

                    casts.setCast_name_1(list.getJSONObject(0).getString("name"));
                    casts.setCast_name_2(list.getJSONObject(1).getString("name"));
                    casts.setCast_name_3(list.getJSONObject(2).getString("name"));

                    castMutableLiveData.postValue(casts);
                } catch (Exception e) {
                    Log.d("Tidak Sukses", e.getMessage());
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    public void setMovieDetail (int movieId) {
        AsyncHttpClient client = new AsyncHttpClient();

        Locale locale = Locale.getDefault();
        String countryCode = locale.getCountry();
        String langCode = countryCode == "ID" ? "id-ID" : "en-US";

        String url = "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + API_KEY + "&language=" + langCode;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);

                    Movie movie = new Movie();

                    movie.setTitle(responseObject.getString("title"));
                    movie.setDescription(responseObject.getString("overview"));
                    movie.setPoster(responseObject.getString("poster_path"));

                    JSONArray genres = responseObject.getJSONArray("genres");
                    String genreStr = "";
                    JSONObject genre;
                    for (int ii=0; ii<genres.length()-1; ii++) {
                        genre = genres.getJSONObject(ii);
                        genreStr += genre.getString("name") + ", ";
                    }
                    genre = genres.getJSONObject(genres.length()-1);
                    genreStr += genre.getString("name");

                    movie.setCategory(genreStr);
                    movie.setTrivia(responseObject.getString("tagline"));

                    movieDetailMutableLiveData.postValue(movie);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    public LiveData<Movie> getMovieDetail() {
        return movieDetailMutableLiveData;
    }

    public LiveData<Cast> getCast() {
        return castMutableLiveData;
    }
}
