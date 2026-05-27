package com.cmrl.metro;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.cmrl.metro.fragments.HomeFragment;
import com.cmrl.metro.fragments.MapFragment;
import com.cmrl.metro.fragments.PlannerFragment;
import com.cmrl.metro.fragments.ProfileFragment;
import com.cmrl.metro.fragments.TicketsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        androidx.core.splashscreen.SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(this::onNavItemSelected);

        // Load home fragment on start
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }
    }

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
