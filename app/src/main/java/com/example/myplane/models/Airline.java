package com.example.myplane.models;

import java.util.Map;

public class Airline {
    private Map<String, Integer> airlineFleet;

    private String name;
    private String iata;
    private String icao;
    private String logo;

    public Airline(Map<String, Integer> airlineFleet, String name, String iata, String icao) {
        this.airlineFleet = airlineFleet;
        this.name = name;
        this.iata = iata;
        this.icao = icao;
        this.logo = "";
    }

    public Map<String, Integer> getAirlineFleet() {
        return airlineFleet;
    }

    public void setAirlineFleet(Map<String, Integer> airlineFleet) {
        this.airlineFleet = airlineFleet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLogo() {
        return logo;
    }

    @Override
    public String toString() {
        return "Airline{" +
                "airlineFleet=" + airlineFleet +
                ", name='" + name + '\'' +
                ", iata='" + iata + '\'' +
                ", icao='" + icao + '\'' +
                '}';
    }
}
