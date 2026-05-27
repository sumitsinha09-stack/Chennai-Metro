package com.cmrl.metro.models;

import java.util.List;

public class Journey {
    public static class Segment {
        private String line;           // "blue" or "green"
        private List<Station> stations;
        private String direction;

        public Segment(String line, List<Station> stations, String direction) {
            this.line = line;
            this.stations = stations;
            this.direction = direction;
        }

        public String getLine()              { return line; }
        public List<Station> getStations()   { return stations; }
        public String getDirection()         { return direction; }

        public int getStationCount() {
            return stations != null ? stations.size() : 0;
        }
    }

    private Station from;
    private Station to;
    private List<Segment> segments;
    private int totalStations;
    private int fareINR;
    private int durationMin;

    public Journey(Station from, Station to, List<Segment> segments,
                   int totalStations, int fareINR, int durationMin) {
        this.from = from;
        this.to = to;
        this.segments = segments;
        this.totalStations = totalStations;
        this.fareINR = fareINR;
        this.durationMin = durationMin;
    }

    public Station getFrom()             { return from; }
    public Station getTo()               { return to; }
    public List<Segment> getSegments()   { return segments; }
    public int getTotalStations()        { return totalStations; }
    public int getFareINR()              { return fareINR; }
    public int getDurationMin()          { return durationMin; }
}
