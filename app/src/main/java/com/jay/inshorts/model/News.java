package com.jay.inshorts.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jay on 16-09-2017.
 */

public class News {
    @SerializedName("ID")
    private long id;
    @SerializedName("TITLE")
    private String title;
    @SerializedName("URL")
    private String url;
    @SerializedName("PUBLISHER")
    private String publisher;
    @SerializedName("CATEGORY")
    private String category;
    @SerializedName("HOSTNAME")
    private String hostname;
    @SerializedName("TIMESTAMP")
    private long timestamp;
    private boolean favorite;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
