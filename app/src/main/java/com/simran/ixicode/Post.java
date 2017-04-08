package com.simran.ixicode;

import com.google.gson.annotations.SerializedName;
import java.util.Date;
import java.util.List;

/**
 * Created by Simranjit Kour on 8/4/17.
 */
public class Post {
    @SerializedName("id")
    long ID;

    @SerializedName("date")
    Date dateCreated;

    String title;
    String author;
    String url;
    String body;
}
