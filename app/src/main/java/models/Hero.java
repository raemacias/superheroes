package models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "favorites")
public class Hero {

    @PrimaryKey
    @SerializedName("name")
    @NonNull private String name;

    @ColumnInfo
    @SerializedName("realname")
    private String realname;

    @ColumnInfo
    @SerializedName("team")
    private String team;

    @ColumnInfo
    @SerializedName("firstappearance")
    private String firstappearance;

    @ColumnInfo
    @SerializedName("createdby")
    private String createdby;

    @ColumnInfo
    @SerializedName("publisher")
    private String publisher;

    @ColumnInfo
    @SerializedName("imageurl")
    private String imageurl;

    @ColumnInfo
    @SerializedName("bio")
    private String bio;


    public Hero(@NonNull String name, String realname, String team, String firstappearance, String createdby, String publisher, String imageurl, String bio) {
        this.name = name;
        this.realname = realname;
        this.team = team;
        this.firstappearance = firstappearance;
        this.createdby = createdby;
        this.publisher = publisher;
        this.imageurl = imageurl;
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public String getRealname() {
        return realname;
    }

    public String getTeam() {
        return team;
    }

    public String getFirstappearance() {
        return firstappearance;
    }

    public String getCreatedby() {
        return createdby;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getImageurl() {
        return imageurl;
    }

    public String getBio() {
        return bio;
    }
}