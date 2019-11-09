package com.dicoding.picodiploma.myentertainmentlist.dummy;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Cast implements Parcelable {

    private String cast_photo_1;
    private String cast_photo_2;
    private String cast_photo_3;
    private String cast_name_1;
    private String cast_name_2;
    private String cast_name_3;

    public Cast () {}

    protected Cast(Parcel in) {
        cast_photo_1 = in.readString();
        cast_photo_2 = in.readString();
        cast_photo_3 = in.readString();

        cast_name_1 = in.readString();
        cast_name_2 = in.readString();
        cast_name_3 = in.readString();
    }

    public String getCast_name_1() {
        return cast_name_1;
    }

    public void setCast_name_1(String cast_name_1) {
        this.cast_name_1 = cast_name_1;
    }

    public String getCast_name_2() {
        return cast_name_2;
    }

    public void setCast_name_2(String cast_name_2) {
        this.cast_name_2 = cast_name_2;
    }

    public String getCast_photo_1() {
        return cast_photo_1;
    }

    public void setCast_photo_1(String cast_photo_1) {
        this.cast_photo_1 = cast_photo_1;
    }

    public String getCast_photo_2() {
        return cast_photo_2;
    }

    public void setCast_photo_2(String cast_photo_2) {
        this.cast_photo_2 = cast_photo_2;
    }

    public String getCast_photo_3() {
        return cast_photo_3;
    }

    public void setCast_photo_3(String cast_photo_3) {
        this.cast_photo_3 = cast_photo_3;
    }

    public String getCast_name_3() {
        return cast_name_3;
    }

    public void setCast_name_3(String cast_name_3) {
        this.cast_name_3 = cast_name_3;
    }

    public static final Creator<Cast> CREATOR = new Creator<Cast>() {
        @Override
        public Cast createFromParcel(Parcel in) {
            return new Cast(in);
        }

        @Override
        public Cast[] newArray(int size) {
            return new Cast[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(cast_photo_1);
        parcel.writeString(cast_photo_2);
        parcel.writeString(cast_photo_3);

        parcel.writeString(cast_name_1);
        parcel.writeString(cast_name_2);
        parcel.writeString(cast_name_3);
    }
}
