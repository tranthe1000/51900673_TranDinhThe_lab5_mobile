package com.example.bai2;


import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable {
    private String title;
    private String room;
    private String date;
    private String time;
    private boolean isEnable;

    public Event(String title, String room, String date, String time) {
        this.title = title;
        this.room = room;
        this.date = date;
        this.time = time;
        this.isEnable = false;
    }

    protected Event(Parcel in) {
        title = in.readString();
        room = in.readString();
        date = in.readString();
        time = in.readString();
        isEnable = in.readByte() != 0;
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String datetime) {
        this.date = date;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(room);
        parcel.writeString(date);
        parcel.writeString(time);
        parcel.writeByte((byte) (isEnable ? 1 : 0));
    }
}
