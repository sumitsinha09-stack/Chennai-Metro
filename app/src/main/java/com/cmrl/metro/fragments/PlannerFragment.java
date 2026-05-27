package com.cmrl.metro.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.cmrl.metro.R;
import com.cmrl.metro.data.MetroData;
import com.cmrl.metro.models.Journey;
import com.cmrl.metro.models.Station;
import com.cmrl.metro.models.Ticket;
import com.cmrl.metro.storage.TicketStore;
import com.cmrl.metro.utils.JourneyPlanner;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PlannerFragment extends Fragment {

    private static final String ARG_FROM = "from_id";
    private static final String ARG_TO   = "to_id";

    private Station selectedFrom = null;
    private Station selectedTo   = null;
    private int passengers = 1;
    private Journey currentJourney = null;

    private TextView tvFrom, tvTo, tvPassengers;
    private Button btnFindRoute, btnBookTicket;
    private View btnSwap;
    private LinearLayout layoutResult, layoutNoRoute;
    private TextView tvDuration, tvStations, tvFare, tvRouteSteps;

    public static PlannerFragment newInstance(String fromId, String toId) {
        PlannerFragment f = new PlannerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FROM, fromId);
        args.putString(ARG_TO, toId);
        f.setArguments(args);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_planner, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvFrom           = view.findViewById(R.id.tv_from_station);
        tvTo             = view.findViewById(R.id.tv_to_station);
        tvPassengers     = view.findViewById(R.id.tv_passengers);
        btnFindRoute     = view.findViewById(R.id.btn_find_route);
        btnBookTicket    = view.findViewById(R.id.btn_book_ticket);
        btnSwap          = view.findViewById(R.id.btn_swap);
        layoutResult     = view.findViewById(R.id.layout_result);
        layoutNoRoute    = view.findViewById(R.id.layout_no_route);
        tvDuration       = view.findViewById(R.id.tv_duration);
        tvStations       = view.findViewById(R.id.tv_stations);
        tvFare           = view.findViewById(R.id.tv_fare);
        tvRouteSteps     = view.findViewById(R.id.tv_route_steps);

        layoutResult.setVisibility(View.GONE);
        layoutNoRoute.setVisibility(View.GONE);
        btnBookTicket.setVisibility(View.GONE);

        // Pre-fill from arguments
        if (getArguments() != null) {
            String fromId = getArguments().getString(ARG_FROM);
            String toId   = getArguments().getString(ARG_TO);
            if (fromId != null) selectedFrom = MetroData.getStation(fromId);
            if (toId   != null) selectedTo   = MetroData.getStation(toId);
            updateSelectionUI();
        }

        tvFrom.setOnClickListener(v -> showStationPicker(true));
        tvTo.setOnClickListener(v -> showStationPicker(false));

        view.findViewById(R.id.btn_dec_pax).setOnClickListener(v -> {
            if (passengers > 1) { passengers--; tvPassengers.setText(String.valueOf(passengers)); }
        });
        view.findViewById(R.id.btn_inc_pax).setOnClickListener(v -> {
            if (passengers < 10) { passengers++; tvPassengers.setText(String.valueOf(passengers)); }
        });

        btnSwap.setOnClickListener(v -> {
            Station temp = selectedFrom;
            selectedFrom = selectedTo;
            selectedTo   = temp;
            updateSelectionUI();
            clearResult();
        });

        btnFindRoute.setOnClickListener(v -> planJourney());
        btnBookTicket.setOnClickListener(v -> bookTicket());
    }

    private void showStationPicker(boolean isFrom) {
        List<Station> stations = MetroData.getAllStations();
        String[] names = new String[stations.size()];
        for (int i = 0; i < stations.size(); i++) {
            names[i] = stations.get(i).getName();
        }

        new AlertDialog.Builder(requireContext())
            .setTitle(isFrom ? "Select Departure" : "Select Destination")
            .setItems(names, (dialog, which) -> {
                Station picked = stations.get(which);
                if (isFrom) selectedFrom = picked;
                else        selectedTo   = picked;
                updateSelectionUI();
                clearResult();
            })
            .show();
    }

    private void updateSelectionUI() {
        tvFrom.setText(selectedFrom != null ? selectedFrom.getName() : "Select departure station");
        tvTo.setText(selectedTo != null ? selectedTo.getName() : "Select destination station");
        tvPassengers.setText(String.valueOf(passengers));
    }

    private void planJourney() {
        if (selectedFrom == null || selectedTo == null) {
            Toast.makeText(requireContext(), "Please select both stations", Toast.LENGTH_SHORT).show();
            return;
        }
        currentJourney = JourneyPlanner.plan(selectedFrom.getId(), selectedTo.getId());
        if (currentJourney == null) {
            layoutResult.setVisibility(View.GONE);
            layoutNoRoute.setVisibility(View.VISIBLE);
            btnBookTicket.setVisibility(View.GONE);
        } else {
            layoutNoRoute.setVisibility(View.GONE);
            layoutResult.setVisibility(View.VISIBLE);
            btnBookTicket.setVisibility(View.VISIBLE);
            showJourneyResult(currentJourney);
        }
    }

    private void showJourneyResult(Journey journey) {
        tvDuration.setText(journey.getDurationMin() + " min");
        tvStations.setText(journey.getTotalStations() + " stations");
        tvFare.setText("₹" + (journey.getFareINR() * passengers));

        StringBuilder sb = new StringBuilder();
        for (Journey.Segment seg : journey.getSegments()) {
            String lineName = seg.getLine().equals("blue") ? "Blue Line" : "Green Line";
            sb.append("▶ ").append(lineName).append(" — ").append(seg.getDirection()).append("\n");
            List<Station> stops = seg.getStations();
            for (int i = 0; i < stops.size(); i++) {
                Station st = stops.get(i);
                boolean isFirst = i == 0;
                boolean isLast  = i == stops.size() - 1;
                if (isFirst || isLast) {
                    sb.append("  ● ").append(st.getName()).append("\n");
                } else if (st.isInterchange()) {
                    sb.append("  ◈ ").append(st.getName()).append(" (Interchange)\n");
                } else if (i == 1 && stops.size() > 3) {
                    sb.append("    ... ").append(stops.size() - 2).append(" intermediate stations ...\n");
                }
            }
            sb.append("\n");
        }
        tvRouteSteps.setText(sb.toString().trim());
    }

    private void bookTicket() {
        if (currentJourney == null) return;
        int totalFare = currentJourney.getFareINR() * passengers;
        String date   = new SimpleDateFormat("MMM dd, yyyy · hh:mm a", Locale.getDefault()).format(new Date());
        String qrData = "CMRL-" + selectedFrom.getId() + "-" + selectedTo.getId() + "-" + System.currentTimeMillis();
        Ticket ticket = new Ticket(
            "T" + System.currentTimeMillis() % 1000000,
            selectedFrom.getName(),
            selectedTo.getName(),
            totalFare, date,
            Ticket.Status.ACTIVE,
            qrData, passengers,
            Ticket.TicketType.SINGLE
        );
        TicketStore.getInstance().addTicket(ticket);
        Toast.makeText(requireContext(), "Ticket booked! ₹" + totalFare, Toast.LENGTH_LONG).show();
        btnBookTicket.setEnabled(false);
        btnBookTicket.setText("Booked ✓");
    }

    private void clearResult() {
        layoutResult.setVisibility(View.GONE);
        layoutNoRoute.setVisibility(View.GONE);
        btnBookTicket.setVisibility(View.GONE);
        currentJourney = null;
    }
}
