package com.dicoding.picodiploma.myentertainmentlist.dummy;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.core.text.HtmlCompat;

public class Movie implements Parcelable {
    private String poster;
    private String title;
    private String description;

    private int movie_id;

    private String category;
    private String trivia;

    public Movie() {}

    protected Movie(Parcel in) {

        poster = in.readString();
        title = in.readString();
        description = in.readString();

        movie_id = in.readInt();

        category = in.readString();
        trivia = in.readString();
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public String getTrivia() {
        return trivia;
    }

    public void setTrivia(String trivia) {
        this.trivia = trivia;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(poster);
        parcel.writeString(title);
        parcel.writeString(description);

        parcel.writeInt(movie_id);

        parcel.writeString(category);
        parcel.writeString(trivia);
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
