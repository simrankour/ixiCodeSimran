package com.simran.ixicode;

import com.google.gson.annotations.SerializedName;
import java.util.Date;
import java.util.List;

/**
 * Created by Simranjit Kour on 8/4/17.
 */
public class Post {

    @SerializedName("id")
    public long ID;
    public String title;
    public String author;
    public String url;
    @SerializedName("date")
    public Date dateCreated;
    public String body;

    public List<Tag> tags;

    public Post() {

    }
}
