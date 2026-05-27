package com.cmrl.metro.models;

import java.util.List;

public class Station {
    private String id;
    private String name;
    private List<String> lines;
    private boolean isInterchange;
    private int zone;
    private double lat;
    private double lon;

    public Station(String id, String name, List<String> lines,
                   boolean isInterchange, int zone, double lat, double lon) {
        this.id = id;
        this.name = name;
        this.lines = lines;
        this.isInterchange = isInterchange;
        this.zone = zone;
        this.lat = lat;
        this.lon = lon;
    }

    public String getId()          { return id; }
    public String getName()        { return name; }
    public List<String> getLines() { return lines; }
    public boolean isInterchange() { return isInterchange; }
    public int getZone()           { return zone; }
    public double getLat()         { return lat; }
    public double getLon()         { return lon; }
}
