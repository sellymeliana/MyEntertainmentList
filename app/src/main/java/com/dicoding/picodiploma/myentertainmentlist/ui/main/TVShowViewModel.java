package com.dicoding.picodiploma.myentertainmentlist.ui.main;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dicoding.picodiploma.myentertainmentlist.dummy.Movie;
import com.dicoding.picodiploma.myentertainmentlist.dummy.TVShow;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class TVShowViewModel extends ViewModel {
    private static final String API_KEY = "9ac9069d0b4aea71d158e1c987d85923";
    private MutableLiveData<ArrayList<TVShow>> listTVShow = new MutableLiveData<>();

    public void setTVShow() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TVShow> listItems = new ArrayList<>();

        Locale locale = Locale.getDefault();
        String countryCode = locale.getCountry();
        String langCode = countryCode == "ID" ? "id-ID" : "en-US";
        String url = "https://api.themoviedb.org/3/tv/popular?api_key=" + API_KEY + "&language=" + langCode;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        TVShow tvShow = new TVShow();

                        tvShow.setPoster(movie.getString("poster_path"));
                        tvShow.setTitle(movie.getString("name"));
                        tvShow.setDescription(movie.getString("overview"));
                        tvShow.setTVShow_id(movie.getInt("id"));

                        listItems.add(tvShow);
                    }
                    listTVShow.postValue(listItems);
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

    public LiveData<ArrayList<TVShow>> getTVShow() {
        return listTVShow;
    }

}
