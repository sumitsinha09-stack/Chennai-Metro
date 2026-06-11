# Chennai Metro Android Studio (Java) Project

A comprehensive Android application for the Chennai Metro Rail Limited (CMRL) system, featuring intelligent route planning, digital ticketing, women's safety features, and last-mile connectivity solutions.

## 🚀 Quick Start

### Prerequisites
- Android Studio Arctic Fox or later
- Java JDK 11+
- Google Maps API Key
- Google Places API Key

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/sumitsinha09-stack/Chennai-Metro.git
   ```
2. Open in Android Studio
3. Add API keys to `local.properties`:
   ```properties
   MAPS_API_KEY=your_maps_api_key
   PLACES_API_KEY=your_places_api_key
   ```
4. Build and run

---

## 📁 Project Structure

```
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
```

---

## 📦 Key Dependencies

| Library | Version | Purpose |
|---------|---------|---------|
| `com.google.android.material:material` | `1.12.0` | Material Design 3 components (BottomNav, Cards, Chips) |
| `com.google.zxing:core` | Latest | QR code generation for digital ticketing |
| `com.google.android.gms:play-services-maps` | Latest | Interactive Metro maps and safety service mapping |
| `com.google.android.gms:play-services-location` | Latest | GPS coordinate capturing for SOS alerts |
| `com.google.android.libraries.places:places` | Latest | Google-powered address search and autocomplete |
| `androidx.room:room-runtime` | Latest | Local SQLite storage for emergency contacts |
| `androidx.lifecycle:lifecycle-viewmodel` | Latest | LiveData and ViewModel for reactive data flow |
| `com.squareup.retrofit2:retrofit` | Latest | Network connectivity and API communication |

---

## ✨ Features Implemented

### Core Features
- **Home Screen** — Modern dashboard with Greeting, system status, and feature shortcuts
- **Journey Planner** — Intelligent route finding across Blue/Green lines with interchange alerts and fare calculation
- **Interactive Metro Map** — Fully functional Google Map showing all metro lines (Polylines) and stations with interchange markers
- **My Tickets** — QR-based ticketing system with active/history tracking and BottomSheet viewer

### Last Mile Connectivity
- Integrated Google Places search to find cabs (Uber, Ola, Rapido) from metro stations
- Real-time distance-based fare estimation
- Deep linking to cab services

### Women's Safety Center
- **One-Tap SOS:** Global FAB to send live location via SMS to emergency contacts
- **Shake Detection:** Automatically triggers SOS on vigorous device movement
- **Contact Management:** Full CRUD interface for personal emergency contacts using Room DB
- **Nearby Services:** Map view to find the nearest Police Stations and Hospitals
- **Emergency Helplines:** Instant dial buttons for 112, 1091, and 181

---

## 🏗️ Technical Architecture

- **MVVM Pattern** — Decoupled UI and business logic for scalability and testability
- **Clean Architecture** — Use of Repositories to abstract data sources (Room, Mock APIs, System Sensors)
- **Secure Key Management** — Sensitive API keys are managed via `local.properties` and injected during build time to ensure zero-exposure on GitHub
- **Modern Permissions** — Reactive permission handling using `ActivityResultLauncher`
- **LiveData & ViewModel** — Reactive data binding for real-time UI updates
- **Room Database** — Local persistence for emergency contacts and user data

---

## 🌿 Branch Structure

| Branch | Purpose |
|--------|---------|
| `main` | Stable release branch - production-ready code |
| `develop` | Development branch - integration point for features |
| `feature/*` | Feature branches - work on specific features |
| `bugfix/*` | Bug fix branches - address identified issues |

### Git Workflow
```bash
# Create a feature branch from develop
git checkout develop
git pull origin develop
git checkout -b feature/your-feature-name

# Make changes and commit
git add .
git commit -m "feat: add your feature description"

# Push and create a Pull Request
git push origin feature/your-feature-name
```

---

## 📋 API Integration

### Google Maps API
- Metro line polylines and station markers
- Real-time location tracking for SOS
- Nearby police stations and hospitals search

### Google Places API
- Address autocomplete for emergency contacts
- Cab service location search
- Address validation

---

## 🔒 Security

- API keys stored in `local.properties` (not committed to Git)
- Room database encryption for sensitive data
- Secure SMS transmission for SOS alerts
- Permission handling following Android best practices

---

## 📱 Supported Android Versions

- **Minimum SDK:** Android 8.0 (API 26)
- **Target SDK:** Android 14 (API 34)
- **Compile SDK:** Android 14 (API 34)

---

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## 📄 License

This project is open source and available under the MIT License.

---

## 👤 Author

**Sumit Sinha**
- GitHub: [@sumitsinha09-stack](https://github.com/sumitsinha09-stack)
- Email: sumitsinha09.stack@gmail.com

---

## 🎯 Roadmap

- [ ] Offline map support
- [ ] Real-time train tracking
- [ ] Smart fare prediction
- [ ] UPI/Wallet integration for tickets
- [ ] Community safety reporting
- [ ] Accessibility improvements

---

## 📞 Support & Contact

For issues, questions, or suggestions:
- Open an [Issue](https://github.com/sumitsinha09-stack/Chennai-Metro/issues)
- Submit a [Pull Request](https://github.com/sumitsinha09-stack/Chennai-Metro/pulls)
- Contact via email for business inquiries

---

**Last Updated:** June 2026 | **Status:** Active Development
