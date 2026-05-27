# Chennai Metro Android Studio (Java) Project

## Project Structure

```
app/src/main/
├── java/com/cmrl/metro/
│   ├── MainActivity.java              — Entry point, BottomNavigationView
│   ├── models/
│   │   ├── Station.java               — Station data model
│   │   ├── Ticket.java                — Ticket data model
│   │   └── Journey.java               — Journey + Segment models
│   ├── data/
│   │   └── MetroData.java             — All 30 Chennai Metro stations, line orders
│   ├── fragments/
│   │   ├── HomeFragment.java          — Home screen with popular routes
│   │   ├── PlannerFragment.java       — Route planner + fare calculator
│   │   ├── MapFragment.java           — Station list by line
│   │   ├── TicketsFragment.java       — Active/past QR tickets
│   │   ├── ProfileFragment.java       — User profile + settings
│   │   └── QRTicketBottomSheet.java   — QR ticket viewer (BottomSheet)
│   ├── adapters/
│   │   ├── TicketAdapter.java         — RecyclerView adapter for tickets
│   │   └── RouteAdapter.java          — RecyclerView adapter for popular routes
│   ├── storage/
│   │   └── TicketStore.java           — In-memory ticket store (singleton)
│   └── utils/
│       ├── FareCalculator.java        — CMRL fare table + duration estimate
│       ├── JourneyPlanner.java        — Route finding (same-line + interchange)
│       └── QRGenerator.java           — ZXing QR code bitmap generator
├── res/
│   ├── layout/
│   │   ├── activity_main.xml          — Root layout with BottomNavigationView
│   │   ├── fragment_home.xml          — Home screen
│   │   ├── fragment_planner.xml       — Journey planner
│   │   ├── fragment_map.xml           — Metro map (station list)
│   │   ├── fragment_tickets.xml       — Tickets list
│   │   ├── fragment_profile.xml       — Profile & settings
│   │   ├── bottom_sheet_qr_ticket.xml — QR ticket bottom sheet
│   │   ├── item_ticket.xml            — Ticket RecyclerView item
│   │   ├── item_route.xml             — Route RecyclerView item
│   │   └── item_map_station.xml       — Station map list item
│   ├── menu/
│   │   └── bottom_nav_menu.xml        — Bottom navigation items
│   └── values/
│       ├── colors.xml                 — Chennai Metro brand colors
│       ├── strings.xml                — App strings
│       └── themes.xml                 — Material 3 theme
└── AndroidManifest.xml
```



## Key Dependencies (app/build.gradle)

| Library | Purpose |
|---------|---------|
| `material:1.12.0` | Material Design components (BottomNav, TabLayout, CardView) |
| `zxing:core:3.5.3` | QR code generation |
| `zxing-android-embedded:4.3.0` | Android QR code scanning/generation |
| `play-services-location` | GPS / Nearby station detection |
| `play-services-maps` | Google Maps for station map |
| `core-splashscreen` | Android 12+ splash screen |

## Features Implemented

- **Home Screen** — Greeting, live status, quick actions, popular routes
- **Journey Planner** — Station picker (AlertDialog), route finding, fare display, ticket booking
- **Metro Map** — Blue/Green line station list with interchange markers
- **My Tickets** — Active/history tabs, QR ticket viewer (BottomSheet + ZXing bitmap)
- **Profile** — Stats,notifications toggle,service info

## Chennai Metro Data

- **Blue Line (Corridor 1):** 23 stations — Wimco Nagar ↔ Chennai Airport
- **Green Line (Corridor 2):** 11 stations — Wimco Nagar ↔ St. Thomas Mount
- **Interchange stations:** Chennai Central, AG-DMS, Alandur
- **Fare structure:** ₹10 (0-2 stations) to ₹60 (22+ stations)


