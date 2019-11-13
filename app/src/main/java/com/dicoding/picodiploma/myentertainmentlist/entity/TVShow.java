package com.dicoding.picodiploma.myentertainmentlist.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class TVShow implements Parcelable {
    private String poster;
    private String title;
    private String description;

    private int tvshow_id;

    private String category;
    private String episodes;

    public TVShow() {}

    protected TVShow(Parcel in) {

        poster = in.readString();
        title = in.readString();
        description = in.readString();

        tvshow_id = in.readInt();

        category = in.readString();
        episodes = in.readString();
    }

    public int getTVShow_id() {
        return tvshow_id;
    }

    public void setTVShow_id(int tvshow_id) {
        this.tvshow_id = tvshow_id;
    }

    public String getEpisodes() {
        return episodes;
    }

    public void setEpisodes(String episodes) {
        this.episodes = episodes;
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

        parcel.writeInt(tvshow_id);

        parcel.writeString(category);
        parcel.writeString(episodes);
    }

    public static final Creator<TVShow> CREATOR = new Creator<TVShow>() {
        @Override
        public TVShow createFromParcel(Parcel in) {
            return new TVShow(in);
        }

        @Override
        public TVShow[] newArray(int size) {
            return new TVShow[size];
        }
    };
}
