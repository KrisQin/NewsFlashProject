package com.blockadm.common.bean.live.responseBean;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Kris on 2019/5/9
 *
 * @Describe TODO {  }
 */
public class LiveMsgInfo implements Parcelable {

    private String name;
    private Uri uri;

    public LiveMsgInfo() {

    }

    public LiveMsgInfo(String name) {
        this.name = name;
    }

    protected LiveMsgInfo(Parcel in) {
        name = in.readString();
        uri = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<LiveMsgInfo> CREATOR = new Creator<LiveMsgInfo>() {
        @Override
        public LiveMsgInfo createFromParcel(Parcel in) {
            return new LiveMsgInfo(in);
        }

        @Override
        public LiveMsgInfo[] newArray(int size) {
            return new LiveMsgInfo[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeParcelable(uri, flags);
    }
}
