package com.cmrl.metro;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.cmrl.metro.fragments.HomeFragment;
import com.cmrl.metro.fragments.MapFragment;
import com.cmrl.metro.fragments.PlannerFragment;
import com.cmrl.metro.fragments.ProfileFragment;
import com.cmrl.metro.fragments.TicketsFragment;
import com.cmrl.metro.models.EmergencyContact;
import com.cmrl.metro.repositories.SafetyRepository;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private BottomNavigationView bottomNav;
    private SafetyRepository safetyRepository;
    private FusedLocationProviderClient fusedLocationClient;
    private SensorManager sensorManager;
    private float accelerationValue;
    private float lastAccelerationValue;
    private float shake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        androidx.core.splashscreen.SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(this::onNavItemSelected);

        ExtendedFloatingActionButton fabSos = findViewById(R.id.fab_sos);
        safetyRepository = new SafetyRepository(getApplication());
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        setupShakeDetection();

        fabSos.setOnClickListener(v -> triggerSOS());

        // Load home fragment on start
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }
    }

    private void setupShakeDetection() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerationValue = SensorManager.GRAVITY_EARTH;
        lastAccelerationValue = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;
    }

    private void triggerSOS() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.SEND_SMS}, 100);
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            StringBuilder sb = new StringBuilder("I need help! My location: ");
            if (location != null) {
                sb.append("https://maps.google.com/?q=")
                  .append(location.getLatitude())
                  .append(",")
                  .append(location.getLongitude());
            } else {
                sb.append("Location unavailable.");
            }
            sendSOSToContacts(sb.toString());
        });
    }

    private void sendSOSToContacts(String message) {
        safetyRepository.getAllContacts().observe(this, new Observer<List<EmergencyContact>>() {
            @Override
            public void onChanged(List<EmergencyContact> contacts) {
                if (contacts == null || contacts.isEmpty()) {
                    Toast.makeText(MainActivity.this, "No emergency contacts found!", Toast.LENGTH_SHORT).show();
                } else {
                    SmsManager smsManager = SmsManager.getDefault();
                    for (EmergencyContact contact : contacts) {
                        smsManager.sendTextMessage(contact.getPhoneNumber(), null, message, null, null);
                    }
                    Toast.makeText(MainActivity.this, "SOS Sent!", Toast.LENGTH_LONG).show();
                }
                // Important: remove observer to avoid multiple sends if data changes later
                safetyRepository.getAllContacts().removeObserver(this);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sensorManager != null) {
            sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        lastAccelerationValue = accelerationValue;
        accelerationValue = (float) Math.sqrt(x * x + y * y + z * z);
        float delta = accelerationValue - lastAccelerationValue;
        shake = shake * 0.9f + delta;
        if (shake > 12) {
            triggerSOS();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    private boolean onNavItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            fragment = new HomeFragment();
        } else if (id == R.id.nav_plan) {
            fragment = new PlannerFragment();
        } else if (id == R.id.nav_map) {
            fragment = new MapFragment();
        } else if (id == R.id.nav_tickets) {
            fragment = new TicketsFragment();
        } else if (id == R.id.nav_profile) {
            fragment = new ProfileFragment();
        }

        if (fragment != null) {
            loadFragment(fragment);
            return true;
        }
        return false;
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit();
    }
}
