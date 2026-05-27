package com.cmrl.metro.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.cmrl.metro.R;
import com.cmrl.metro.data.MetroData;
import com.cmrl.metro.models.Station;
import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment {

    private RadioGroup rgLines;
    private LinearLayout llStations;
    private String selectedLine = "blue";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rgLines    = view.findViewById(R.id.rg_lines);
        llStations = view.findViewById(R.id.ll_stations);

        renderLine("blue");

        rgLines.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_blue_line) {
                selectedLine = "blue";
            } else if (checkedId == R.id.rb_green_line) {
                selectedLine = "green";
            }
            renderLine(selectedLine);
        });
    }

    private void renderLine(String lineId) {
        llStations.removeAllViews();
        List<String> stationIds = lineId.equals("blue")
            ? MetroData.BLUE_LINE : MetroData.GREEN_LINE;
        String lineColor = lineId.equals("blue")
            ? MetroData.BLUE_COLOR : MetroData.GREEN_COLOR;

        for (int i = 0; i < stationIds.size(); i++) {
            String stationId = stationIds.get(i);
            Station station  = MetroData.getStation(stationId);
            if (station == null) continue;

            boolean isFirst    = i == 0;
            boolean isLast     = i == stationIds.size() - 1;
            boolean isTerminal = isFirst || isLast;

            View stationView = LayoutInflater.from(requireContext())
                .inflate(R.layout.item_map_station, llStations, false);

            TextView tvName       = stationView.findViewById(R.id.tv_station_name);
            View     vDot         = stationView.findViewById(R.id.v_station_dot);
            View     vLineTop     = stationView.findViewById(R.id.v_line_top);
            View     vLineBottom  = stationView.findViewById(R.id.v_line_bottom);
            TextView tvBadge      = stationView.findViewById(R.id.tv_badge);

            tvName.setText(station.getName());
            tvName.setTextColor(isTerminal
                ? Color.parseColor(lineColor)
                : ContextCompat.getColor(requireContext(), R.color.text_primary));
            tvName.setTypeface(null, isTerminal
                ? android.graphics.Typeface.BOLD : android.graphics.Typeface.NORMAL);

            vDot.setBackgroundColor(Color.parseColor(
                isTerminal ? lineColor
                : station.isInterchange() ? "#F59E0B"
                : lineColor + "88"
            ));

            vLineTop.setVisibility(isFirst ? View.INVISIBLE : View.VISIBLE);
            vLineBottom.setVisibility(isLast ? View.INVISIBLE : View.VISIBLE);
            vLineTop.setBackgroundColor(Color.parseColor(lineColor));
            vLineBottom.setBackgroundColor(Color.parseColor(lineColor));

            if (isTerminal) {
                tvBadge.setVisibility(View.VISIBLE);
                tvBadge.setText("Terminal");
                tvBadge.setTextColor(Color.parseColor(lineColor));
            } else if (station.isInterchange()) {
                tvBadge.setVisibility(View.VISIBLE);
                tvBadge.setText("Interchange");
                tvBadge.setTextColor(Color.parseColor("#F59E0B"));
            } else {
                tvBadge.setVisibility(View.GONE);
            }

            llStations.addView(stationView);
        }
    }
}
