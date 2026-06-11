package com.cmrl.metro.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.cmrl.metro.R;
import com.cmrl.metro.data.MetroData;
import com.cmrl.metro.models.Station;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import java.util.List;
import java.util.Objects;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap googleMap;
    private String selectedLine = "blue";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RadioGroup rgLines = view.findViewById(R.id.rg_lines);
        rgLines.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_blue_line) {
                selectedLine = "blue";
            } else if (checkedId == R.id.rb_green_line) {
                selectedLine = "green";
            }
            drawMetroLine(selectedLine);
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        drawMetroLine("blue");
    }

    private void drawMetroLine(String lineId) {
        if (googleMap == null) return;
        googleMap.clear();

        List<String> stationIds = Objects.equals(lineId, "blue")
                ? MetroData.BLUE_LINE : MetroData.GREEN_LINE;
        String lineColor = Objects.equals(lineId, "blue")
                ? MetroData.BLUE_COLOR : MetroData.GREEN_COLOR;

        PolylineOptions lineOptions = new PolylineOptions()
                .width(12)
                .color(Color.parseColor(lineColor))
                .geodesic(true);

        LatLng initialStation = null;

        for (String id : stationIds) {
            Station station = MetroData.getStation(id);
            if (station != null) {
                LatLng pos = new LatLng(station.getLat(), station.getLon());
                lineOptions.add(pos);
                if (initialStation == null) initialStation = pos;

                googleMap.addMarker(new MarkerOptions()
                        .position(pos)
                        .title(station.getName())
                        .snippet(station.isInterchange() ? "Interchange Station" : "")
                        .icon(BitmapDescriptorFactory.defaultMarker(
                                station.isInterchange() ? BitmapDescriptorFactory.HUE_ORANGE : 
                                Objects.equals(lineId, "blue") ? BitmapDescriptorFactory.HUE_AZURE : BitmapDescriptorFactory.HUE_GREEN
                        )));
            }
        }

        googleMap.addPolyline(lineOptions);
        if (initialStation != null) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(initialStation, 12));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapView != null) mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mapView != null) mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
