package com.chandra.hidayat.todoapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ToDo implements Parcelable {
    private String title,date,description,key;

    public ToDo() {
    }

    public ToDo(String date, String description, String title,String key) {
        this.date = date;
        this.description = description;
        this.title = title;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.date);
        dest.writeString(this.description);
        dest.writeString(this.key);
    }

    protected ToDo(Parcel in) {
        this.title = in.readString();
        this.date = in.readString();
        this.description = in.readString();
        this.key = in.readString();
    }

    public static final Parcelable.Creator<ToDo> CREATOR = new Parcelable.Creator<ToDo>() {
        @Override
        public ToDo createFromParcel(Parcel source) {
            return new ToDo(source);
        }

        @Override
        public ToDo[] newArray(int size) {
            return new ToDo[size];
        }
    };
}
