package com.cmrl.metro.data;

import com.cmrl.metro.models.Station;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetroData {

    // ── Station IDs ─────────────────────────────────────────────────────────
    public static final String WN    = "WN";
    public static final String TV    = "TV";
    public static final String TVT   = "TVT";
    public static final String KP    = "KP";
    public static final String TG    = "TG";
    public static final String WND   = "WND";
    public static final String NP    = "NP";
    public static final String DA    = "DA";
    public static final String MA    = "MA";
    public static final String CC    = "CC";
    public static final String GE    = "GE";
    public static final String LIC   = "LIC";
    public static final String TL    = "TL";
    public static final String AGDMS = "AGDMS";
    public static final String TP    = "TP";
    public static final String ND    = "ND";
    public static final String SP    = "SP";
    public static final String LM    = "LM";
    public static final String GU    = "GU";
    public static final String AL    = "AL";
    public static final String NR    = "NR";
    public static final String MB    = "MB";
    public static final String CA    = "CA";
    public static final String EK    = "EK";
    public static final String STM   = "STM";
    public static final String KK    = "KK";
    public static final String AG    = "AG";
    public static final String VA    = "VA";
    public static final String AS_ST = "AS";
    public static final String EJ    = "EJ";

    // ── All Stations ─────────────────────────────────────────────────────────
    private static final List<Station> ALL_STATIONS = Arrays.asList(
        new Station(WN,    "Wimco Nagar",           Arrays.asList("blue","green"), false, 1, 13.1560, 80.2930),
        new Station(TV,    "Thiruvottiyur",          Arrays.asList("blue"),        false, 1, 13.1500, 80.2990),
        new Station(TVT,   "Thiruvottiyur Theradi",  Arrays.asList("blue"),        false, 1, 13.1440, 80.2960),
        new Station(KP,    "Kaladipet",              Arrays.asList("blue"),        false, 1, 13.1380, 80.2940),
        new Station(TG,    "Tollgate",               Arrays.asList("blue"),        false, 1, 13.1320, 80.2910),
        new Station(WND,   "Wimco Nagar Depot",      Arrays.asList("blue"),        false, 1, 13.1260, 80.2890),
        new Station(NP,    "Nehru Park",             Arrays.asList("blue"),        false, 1, 13.1200, 80.2860),
        new Station(DA,    "Dr. Ambedkar",           Arrays.asList("blue"),        false, 1, 13.1140, 80.2840),
        new Station(MA,    "Mannadi",                Arrays.asList("blue"),        false, 2, 13.0900, 80.2760),
        new Station(CC,    "Chennai Central",        Arrays.asList("blue","green"), true,  2, 13.0827, 80.2707),
        new Station(GE,    "Government Estate",      Arrays.asList("blue"),        false, 2, 13.0790, 80.2700),
        new Station(LIC,   "LIC",                    Arrays.asList("blue"),        false, 2, 13.0720, 80.2660),
        new Station(TL,    "Thousand Lights",        Arrays.asList("blue"),        false, 2, 13.0660, 80.2630),
        new Station(AGDMS, "AG-DMS",                 Arrays.asList("blue","green"), true,  2, 13.0600, 80.2600),
        new Station(TP,    "Teynampet",              Arrays.asList("blue"),        false, 2, 13.0520, 80.2560),
        new Station(ND,    "Nandanam",               Arrays.asList("blue"),        false, 2, 13.0460, 80.2530),
        new Station(SP,    "Saidapet",               Arrays.asList("blue"),        false, 3, 13.0400, 80.2500),
        new Station(LM,    "Little Mount",           Arrays.asList("blue"),        false, 3, 13.0320, 80.2480),
        new Station(GU,    "Guindy",                 Arrays.asList("blue"),        false, 3, 13.0080, 80.2200),
        new Station(AL,    "Alandur",                Arrays.asList("blue","green"), true,  3, 12.9980, 80.2090),
        new Station(NR,    "Nanganallur Road",       Arrays.asList("blue"),        false, 3, 12.9900, 80.2040),
        new Station(MB,    "Meenambakkam",           Arrays.asList("blue"),        false, 3, 12.9800, 80.1980),
        new Station(CA,    "Chennai Airport",        Arrays.asList("blue"),        false, 3, 12.9941, 80.1709),
        new Station(EK,    "Ekkaduthangal",          Arrays.asList("green"),       false, 3, 13.0000, 80.2150),
        new Station(STM,   "St. Thomas Mount",       Arrays.asList("green"),       false, 3, 12.9940, 80.2000),
        new Station(KK,    "Koyambedu",              Arrays.asList("green"),       false, 2, 13.0720, 80.2130),
        new Station(AG,    "Arumbakkam",             Arrays.asList("green"),       false, 2, 13.0750, 80.2180),
        new Station(VA,    "Vadapalani",             Arrays.asList("green"),       false, 2, 13.0500, 80.2120),
        new Station(AS_ST, "Ashok Nagar",            Arrays.asList("green"),       false, 2, 13.0380, 80.2230),
        new Station(EJ,    "Ezhilagam",              Arrays.asList("green"),       false, 2, 13.0450, 80.2490)
    );

    // ── Station Map ───────────────────────────────────────────────────────────
    private static final Map<String, Station> STATION_MAP = new HashMap<>();
    static {
        for (Station s : ALL_STATIONS) {
            STATION_MAP.put(s.getId(), s);
        }
    }

    // ── Line Orders ───────────────────────────────────────────────────────────
    public static final List<String> BLUE_LINE = Arrays.asList(
        WN, TV, TVT, KP, TG, WND, NP, DA, MA, CC, GE, LIC, TL, AGDMS, TP, ND, SP, LM, GU, AL, NR, MB, CA
    );

    public static final List<String> GREEN_LINE = Arrays.asList(
        WN, KK, AG, VA, AS_ST, EJ, AGDMS, CC, AL, EK, STM
    );

    public static final String BLUE_COLOR  = "#0052CC";
    public static final String GREEN_COLOR = "#1B8B2E";

    public static final String FIRST_TRAIN        = "05:30 AM";
    public static final String LAST_TRAIN         = "11:00 PM";
    public static final String PEAK_FREQUENCY     = "Every 5 min";
    public static final String OFFPEAK_FREQUENCY  = "Every 10 min";

    // ── Accessors ─────────────────────────────────────────────────────────────
    public static List<Station> getAllStations()            { return ALL_STATIONS; }
    public static Map<String, Station> getStationMap()     { return STATION_MAP; }
    public static Station getStation(String id)            { return STATION_MAP.get(id); }
}
