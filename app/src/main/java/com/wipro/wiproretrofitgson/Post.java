package com.wipro.wiproretrofitgson;

import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("name")
    private String movieName;
    @SerializedName("realname")
    private String realName;
    private String team;
    @SerializedName("firstappearance")
    private String firstAppearance;
    @SerializedName("createdby")
    private String createdBy;
    private String publisher;
    @SerializedName("imageurl")
    private String imageUrl;
    @SerializedName("bio")
    private String biography;

    // *********** Create a Constructor ************
    public Post(String name, String realName, String team, String firstAppearance, String createdBy, String publisher, String imageUrl, String bio) {
        this.movieName = name;
        this.realName = realName;
        this.team = team;
        this.firstAppearance = firstAppearance;
        this.createdBy =  createdBy;
        this.publisher = publisher;
        this.imageUrl = imageUrl;
        this.biography = bio;
    }

    // ************** Getter methods ****************
    String getMovieName() {
        return movieName;
    }

    String getRealName() {
        return realName;
    }

    String getTeam() {
        return team;
    }

    String getFirstAppearance() {
        return firstAppearance;
    }

    String getCreatedBy() { return createdBy; }

    String getPublisher() {
        return publisher;
    }

    String getImageUrl() {
        return imageUrl;
    }

    String getBiography() {
        return biography;
    }
}
