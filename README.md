# Chennai Metro Android Studio (Java) Project

## Project Structure

app/src/main/
├── java/com/cmrl/metro/
│   ├── MainActivity.java              — Entry point, BottomNav, SOS FAB, Shake Detection
│   ├── models/
│   │   ├── Station.java               — Station data model
│   │   ├── Ticket.java                — Ticket data model
│   │   ├── Journey.java               — Journey + Segment models
│   │   ├── CabService.java            — Cab integration model (Uber, Ola, etc.)
│   │   └── EmergencyContact.java      — Room Entity for safety contacts
│   ├── data/
│   │   └── MetroData.java             — All 30 stations, line orders, coordinate data
│   ├── fragments/
│   │   ├── HomeFragment.java          — Home screen with Quick Actions
│   │   ├── PlannerFragment.java       — Route planner + fare calculator
│   │   ├── MapFragment.java           — Interactive Google Map with Metro Lines
│   │   ├── TicketsFragment.java       — Active/past QR tickets
│   │   ├── ProfileFragment.java       — User profile + settings
│   │   ├── LastMileFragment.java      — Cab discovery + Google Places Search
│   │   ├── SafetyCenterFragment.java  — Emergency contact management + Helplines
│   │   └── NearbyServicesFragment.java — Police/Hospitals on Google Map
│   ├── viewmodels/
│   │   ├── ConnectivityViewModel.java — ViewModel for Cab & Last Mile logic
│   │   └── SafetyViewModel.java       — ViewModel for Room DB safety operations
│   ├── repositories/
│   │   ├── ConnectivityRepository.java — Dynamic fare engine & Cab data
│   │   └── SafetyRepository.java       — Clean API for database operations
│   ├── storage/
│   │   ├── AppDatabase.java           — Room Database configuration
│   │   ├── EmergencyContactDao.java   — DAO for contact CRUD operations
│   │   └── TicketStore.java           — In-memory ticket store (singleton)
│   ├── adapters/
│   │   ├── TicketAdapter.java         — RecyclerView adapter for tickets
│   │   ├── RouteAdapter.java          — RecyclerView adapter for popular routes
│   │   ├── CabServiceAdapter.java     — List for Uber/Ola/Rapido options
│   │   └── EmergencyContactAdapter.java — List for managing safety contacts
│   └── utils/
│       ├── FareCalculator.java        — CMRL fare table + duration estimate
│       ├── JourneyPlanner.java        — Route finding (same-line + interchange)
│       └── QRGenerator.java           — ZXing QR code bitmap generator
├── res/
│   ├── layout/
│   │   ├── activity_main.xml          — Root layout with BottomNav + Global SOS FAB
│   │   ├── fragment_last_mile.xml     — Last Mile Connectivity UI
│   │   ├── fragment_safety_center.xml — Women's Safety Center UI
│   │   ├── fragment_nearby_services.xml — Maps view for nearby safety services
│   │   ├── item_cab_service.xml       — Cab service list item
│   │   └── item_emergency_contact.xml — Safety contact list item
│   └── values/
│       ├── colors.xml                 — Brand colors (Blue, Green, Safety Red)
│       └── strings.xml                — App strings & secure API configuration
└── AndroidManifest.xml                — Permissions & App Query configurations

## Key Dependencies (app/build.gradle)

| Library | Purpose |
|---------|---------|
| `material:1.12.0` | Material Design 3 components (BottomNav, Cards, Chips) |
| `zxing` | QR code generation for digital ticketing |
| `play-services-maps` | Interactive Metro maps and safety service mapping |
| `play-services-location` | GPS coordinate capturing for SOS alerts |
| `google-places` | Google-powered address search and autocomplete |
| `room-runtime` | Local SQLite storage for emergency contacts |
| `lifecycle (MVVM)` | LiveData and ViewModel for reactive data flow |
| `retrofit` | Network connectivity and API communication |

## Features Implemented

- **Home Screen** — Modern dashboard with Greeting, system status, and feature shortcuts.
- **Journey Planner** — Intelligent route finding across Blue/Green lines with interchange alerts and fare calculation.
- **Interactive Metro Map** — Fully functional Google Map showing all metro lines (Polylines) and stations with interchange markers.
- **Last Mile Connectivity** — Integrated Google Places search to find cabs (Uber, Ola, Rapido) from metro stations with real-time distance-based fare estimation and deep linking.
- **Women's Safety Center** — A dedicated safety hub with:
    - **One-Tap SOS:** Global FAB to send live location via SMS to emergency contacts.
    - **Shake Detection:** Automatically triggers SOS on vigorous device movement.
    - **Contact Management:** Full CRUD interface for personal emergency contacts using Room DB.
    - **Nearby Services:** Map view to find the nearest Police Stations and Hospitals.
    - **Emergency Helplines:** Instant dial buttons for 112, 1091, and 181.
- **My Tickets** — QR-based ticketing system with active/history tracking and BottomSheet viewer.

## Technical Architecture

- **MVVM Pattern** — Decoupled UI and business logic for scalability and testability.
- **Clean Architecture** — Use of Repositories to abstract data sources (Room, Mock APIs, System Sensors).
- **Secure Key Management** — Sensitive API keys are managed via `local.properties` and injected during build time to ensure zero-exposure on GitHub.
- **Modern Permissions** — Reactive permission handling using `ActivityResultLauncher`.


