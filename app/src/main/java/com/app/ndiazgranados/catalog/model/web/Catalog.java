package com.app.ndiazgranados.catalog.model.web;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Catalog {

    @SerializedName("feed")
    @Expose
    private Feed feed;

    /**
     * No args constructor for use in serialization
     */
    public Catalog() {
    }

    /**
     * @param feed
     */
    public Catalog( Feed feed) {
        this.feed = feed;
    }

    /**
     * @return The feed
     */
    public  Feed getFeed() {
        return feed;
    }

    /**
     * @param feed The feed
     */
    public void setFeed( Feed feed) {
        this.feed = feed;
    }

}
