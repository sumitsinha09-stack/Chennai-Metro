package com.cmrl.metro.utils;

import com.cmrl.metro.data.MetroData;
import com.cmrl.metro.models.Journey;
import com.cmrl.metro.models.Station;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JourneyPlanner {

    private static final List<String> INTERCHANGES = Arrays.asList("CC", "AGDMS", "AL");

    /**
     * Plan a journey from one station to another.
     * Returns null if no route is found.
     */
    public static Journey plan(String fromId, String toId) {
        if (fromId.equals(toId)) return null;

        Station from = MetroData.getStation(fromId);
        Station to   = MetroData.getStation(toId);
        if (from == null || to == null) return null;

        List<Journey.Segment> segments = findSegments(fromId, toId);
        if (segments == null || segments.isEmpty()) return null;

        int totalStations = 0;
        for (Journey.Segment seg : segments) {
            totalStations += seg.getStationCount() - 1;
        }

        int fare        = FareCalculator.calculate(totalStations);
        int durationMin = FareCalculator.estimateDuration(totalStations, segments.size());

        return new Journey(from, to, segments, totalStations, fare, durationMin);
    }

    private static List<Journey.Segment> findSegments(String fromId, String toId) {
        List<String> blueLine  = MetroData.BLUE_LINE;
        List<String> greenLine = MetroData.GREEN_LINE;

        int blueFrom = blueLine.indexOf(fromId);
        int blueTo   = blueLine.indexOf(toId);

        // Both on Blue Line
        if (blueFrom != -1 && blueTo != -1) {
            List<String> stationIds = sublist(blueLine, blueFrom, blueTo);
            String direction = blueFrom < blueTo ? "towards Chennai Airport" : "towards Wimco Nagar";
            return singleSegment("blue", stationIds, direction);
        }

        int greenFrom = greenLine.indexOf(fromId);
        int greenTo   = greenLine.indexOf(toId);

        // Both on Green Line
        if (greenFrom != -1 && greenTo != -1) {
            List<String> stationIds = sublist(greenLine, greenFrom, greenTo);
            String direction = greenFrom < greenTo ? "towards St. Thomas Mount" : "towards Wimco Nagar";
            return singleSegment("green", stationIds, direction);
        }

        // Blue → Green via interchange
        if (blueFrom != -1 && greenTo != -1) {
            return findInterchangeRoute("blue", blueFrom, fromId, "green", greenTo, toId);
        }

        // Green → Blue via interchange
        if (greenFrom != -1 && blueTo != -1) {
            return findInterchangeRoute("green", greenFrom, fromId, "blue", blueTo, toId);
        }

        return null;
    }

    private static List<Journey.Segment> findInterchangeRoute(
            String line1, int fromIdx, String fromId,
            String line2, int toIdx, String toId) {

        List<String> l1 = line1.equals("blue") ? MetroData.BLUE_LINE  : MetroData.GREEN_LINE;
        List<String> l2 = line2.equals("blue") ? MetroData.BLUE_LINE  : MetroData.GREEN_LINE;

        for (String ic : INTERCHANGES) {
            int icOnL1 = l1.indexOf(ic);
            int icOnL2 = l2.indexOf(ic);
            if (icOnL1 == -1 || icOnL2 == -1) continue;

            List<String> seg1Ids = sublist(l1, fromIdx, icOnL1);
            List<String> seg2Ids = sublist(l2, icOnL2, toIdx);

            String dir1 = fromIdx < icOnL1
                ? (line1.equals("blue") ? "towards Chennai Airport" : "towards St. Thomas Mount")
                : "towards Wimco Nagar";
            String dir2 = icOnL2 < toIdx
                ? (line2.equals("blue") ? "towards Chennai Airport" : "towards St. Thomas Mount")
                : "towards Wimco Nagar";

            List<Journey.Segment> result = new ArrayList<>();
            result.add(makeSegment(line1, seg1Ids, dir1));
            result.add(makeSegment(line2, seg2Ids, dir2));
            return result;
        }

        return null;
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private static List<String> sublist(List<String> line, int from, int to) {
        int lo = Math.min(from, to);
        int hi = Math.max(from, to);
        List<String> slice = new ArrayList<>(line.subList(lo, hi + 1));
        if (from > to) {
            java.util.Collections.reverse(slice);
        }
        return slice;
    }

    private static List<Journey.Segment> singleSegment(String line, List<String> ids, String dir) {
        List<Journey.Segment> list = new ArrayList<>();
        list.add(makeSegment(line, ids, dir));
        return list;
    }

    private static Journey.Segment makeSegment(String line, List<String> ids, String dir) {
        List<Station> stations = new ArrayList<>();
        for (String id : ids) {
            Station s = MetroData.getStation(id);
            if (s != null) stations.add(s);
        }
        return new Journey.Segment(line, stations, dir);
    }
}
