package com.example.assignment3;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    String accessId;
    String nickName;
    Integer level;

    User() {

    }

    User(String accessId, String nickName, int level) {
        this.accessId = accessId;
        this.nickName = nickName;
        this.level = level;

    }


    protected User(Parcel in) {
        accessId = in.readString();
        nickName = in.readString();
        level = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(accessId);
        parcel.writeString(nickName);
        parcel.writeInt(level);
    }
}
