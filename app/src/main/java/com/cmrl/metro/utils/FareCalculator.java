package com.cmrl.metro.utils;

public class FareCalculator {

    private static final int[][] FARE_TABLE = {
        {2,  10},
        {4,  15},
        {6,  20},
        {8,  25},
        {12, 30},
        {16, 40},
        {22, 50},
        {99, 60},
    };

    /**
     * Calculate fare for a given number of stations travelled.
     * @param stationCount number of station hops (not including the start station)
     * @return fare in INR
     */
    public static int calculate(int stationCount) {
        for (int[] entry : FARE_TABLE) {
            if (stationCount <= entry[0]) return entry[1];
        }
        return 60;
    }

    /**
     * Calculate total fare for multiple passengers.
     */
    public static int calculate(int stationCount, int passengers) {
        return calculate(stationCount) * passengers;
    }

    /**
     * Estimate travel duration in minutes.
     * Approximation: ~2.2 min per station, 3 min interchange penalty per segment.
     */
    public static int estimateDuration(int totalStations, int segmentCount) {
        return Math.round(totalStations * 2.2f + segmentCount * 3);
    }
}
