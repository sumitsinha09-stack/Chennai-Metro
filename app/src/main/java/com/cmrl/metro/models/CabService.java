package com.cmrl.metro.models;

public class CabService {
    private String name;
    private String estimatedFare;
    private String estimatedTime;
    private String distance;
    private int iconResId;
    private String deepLink;
    private String packageName;

    public CabService(String name, String estimatedFare, String estimatedTime, String distance, int iconResId, String deepLink, String packageName) {
        this.name = name;
        this.estimatedFare = estimatedFare;
        this.estimatedTime = estimatedTime;
        this.distance = distance;
        this.iconResId = iconResId;
        this.deepLink = deepLink;
        this.packageName = packageName;
    }

    public String getName() { return name; }
    public String getEstimatedFare() { return estimatedFare; }
    public String getEstimatedTime() { return estimatedTime; }
    public String getDistance() { return distance; }
    public int getIconResId() { return iconResId; }
    public String getDeepLink() { return deepLink; }
    public String getPackageName() { return packageName; }
}
