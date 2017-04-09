package com.simran.ixicode.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Simranjit Kour on 9/4/17.
 */
public class BestResult implements Serializable{
    private String originName;
    private String destinationName;
    private Place origin;
    private Place destination;
    private boolean noModesPossible;
    private List<Route> routes;
    private boolean directFlight;
    private boolean directTrain;
    private boolean directCar;
    private boolean directBus;

    public BestResult() {
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public Place getOrigin() {
        return origin;
    }

    public void setOrigin(Place origin) {
        this.origin = origin;
    }

    public Place getDestination() {
        return destination;
    }

    public void setDestination(Place destination) {
        this.destination = destination;
    }

    public boolean isNoModesPossible() {
        return noModesPossible;
    }

    public void setNoModesPossible(boolean noModesPossible) {
        this.noModesPossible = noModesPossible;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public String isDirectFlight() {
        return ((directFlight== true) ? "Yes" : "No");
    }

    public void setDirectFlight(boolean directFlight) {
        this.directFlight = directFlight;
    }

    public boolean isDirectTrain() {
        return directTrain;
    }

    public void setDirectTrain(boolean directTrain) {
        this.directTrain = directTrain;
    }

    public String isDirectCar() {
        String result =((directCar == true) ? "Yes" : "No");
        return result;
    }

    public void setDirectCar(boolean directCar) {
        this.directCar = directCar;
    }

    public String isDirectBus() {
        return ((directBus== true) ? "Yes" : "No");
    }

    public void setDirectBus(boolean directBus) {
        this.directBus = directBus;
    }
}
