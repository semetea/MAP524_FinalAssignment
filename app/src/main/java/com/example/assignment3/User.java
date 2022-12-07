package com.example.assignment3;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    int id;

    String accessId;
    String nickName;
    Integer level;

    User() {

    }

    public User(int id, String accessId, String nickName, Integer level) {
        this.id = id;
        this.accessId = accessId;
        this.nickName = nickName;
        this.level = level;
    }

    protected User(Parcel in) {
        id = in.readInt();
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
        parcel.writeInt(id);
        parcel.writeString(accessId);
        parcel.writeString(nickName);
        parcel.writeInt(level);
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
