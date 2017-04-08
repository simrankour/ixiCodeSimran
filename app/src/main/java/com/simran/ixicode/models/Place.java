package com.simran.ixicode.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Simranjit Kour on 8/4/17.
 */

public class Place implements Serializable {

    private String address;
//    categoryNames": [
//            "Shopping",
//            "Places To Visit",
//            "History & Culture",
//            "Things To Do"
//            ],
    private String cityName;
    private String countryName;
    private String description;
    private String howToReach;
    private String cityId;
    private String xid;
    private String keyImageUrl;
    private String whyToVisit;
    private String latitude;
    private String longitude;
    private String minimumPrice;
    private String name;
    private String url;
    private String stateName;
    private String shortDescription;
    private String id;

    public Place() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHowToReach() {
        return howToReach;
    }

    public void setHowToReach(String howToReach) {
        this.howToReach = howToReach;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getXid() {
        return xid;
    }

    public void setXid(String xid) {
        this.xid = xid;
    }

    public String getKeyImageUrl() {
        return keyImageUrl;
    }

    public void setKeyImageUrl(String keyImageUrl) {
        this.keyImageUrl = keyImageUrl;
    }

    public String getWhyToVisit() {
        return whyToVisit;
    }

    public void setWhyToVisit(String whyToVisit) {
        this.whyToVisit = whyToVisit;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(String minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
