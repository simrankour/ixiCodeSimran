package com.simran.ixicode.models;

import java.util.List;

/**
 * Created by Simranjit Kour on 8/4/17.
 */
public class CityDetails {
    public String title ="simran";
    private List<City> Cities;

    public CityDetails() {
    }

    public List<City> getCities() {
        return Cities;
    }

    public void setCities(List<City> cities) {
        Cities = cities;
    }
}
