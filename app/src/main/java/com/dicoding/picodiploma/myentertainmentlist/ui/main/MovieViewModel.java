package com.dicoding.picodiploma.myentertainmentlist.ui.main;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dicoding.picodiploma.myentertainmentlist.entity.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class MovieViewModel extends ViewModel {
    private static final String API_KEY = "9ac9069d0b4aea71d158e1c987d85923";
    private MutableLiveData<ArrayList<Movie>> listMovies = new MutableLiveData<>();

    public void setMovies () {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listItems = new ArrayList<>();

        Locale locale = Locale.getDefault();
        String countryCode = locale.getCountry();
        String langCode = countryCode == "ID" ? "id-ID" : "en-US";
        String url = "https://api.themoviedb.org/3/movie/popular?api_key=" + API_KEY + "&language=" + langCode;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        Movie movies = new Movie();

                        movies.setPoster(movie.getString("poster_path"));
                        movies.setTitle(movie.getString("title"));
                        movies.setDescription(movie.getString("overview"));
                        movies.setMovie_id(movie.getInt("id"));

                        Log.d("MovieID", "" + movies.getMovie_id());

                        listItems.add(movies);
                    }
                    listMovies.postValue(listItems);
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

    public LiveData<ArrayList<Movie>> getMovies() {
        Log.d("refreshMovies", "getMovies");
        return listMovies;
    }

}
