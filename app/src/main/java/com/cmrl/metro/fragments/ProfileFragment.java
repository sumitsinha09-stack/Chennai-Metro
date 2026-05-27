package com.cmrl.metro.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;
import androidx.fragment.app.Fragment;
import com.cmrl.metro.R;
import com.cmrl.metro.data.MetroData;
import com.cmrl.metro.models.Ticket;
import com.cmrl.metro.storage.TicketStore;
import java.util.List;

public class ProfileFragment extends Fragment {

    private SharedPreferences prefs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        String username = prefs.getString("username", "Metro Rider");

        TextView tvName     = view.findViewById(R.id.tv_username);
        TextView tvInitials = view.findViewById(R.id.tv_initials);
        TextView tvTrips    = view.findViewById(R.id.tv_trips);
        TextView tvSpent    = view.findViewById(R.id.tv_spent);
        CompoundButton swNotif    = view.findViewById(R.id.sw_notifications);

        tvName.setText(username);
        tvInitials.setText(getInitials(username));

        List<Ticket> tickets = TicketStore.getInstance().getTickets();
        tvTrips.setText(String.valueOf(tickets.size()));

        int totalSpent = 0;
        for (Ticket t : tickets) totalSpent += t.getFareINR();
        tvSpent.setText("₹" + totalSpent);

        swNotif.setChecked(prefs.getBoolean("notifications", true));
        swNotif.setOnCheckedChangeListener((btn, checked) ->
            prefs.edit().putBoolean("notifications", checked).apply()
        );

        // Service Info
        view.findViewById(R.id.row_first_train).setOnClickListener(v -> {});
        view.findViewById(R.id.row_helpline).setOnClickListener(v -> {
            android.widget.Toast.makeText(requireContext(), "Helpline: 044-24918220", android.widget.Toast.LENGTH_LONG).show();
        });
        view.findViewById(R.id.row_about).setOnClickListener(v -> {
            android.widget.Toast.makeText(requireContext(), "Chennai Metro Rail Ltd. v1.0.0", android.widget.Toast.LENGTH_SHORT).show();
        });

        TextView tvFirstTrain = view.findViewById(R.id.tv_first_train_value);
        TextView tvLastTrain  = view.findViewById(R.id.tv_last_train_value);
        tvFirstTrain.setText(MetroData.FIRST_TRAIN);
        tvLastTrain.setText(MetroData.LAST_TRAIN);
    }

    private String getInitials(String name) {
        if (name == null || name.isEmpty()) return "MR";
        String[] parts = name.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String p : parts) {
            if (!p.isEmpty()) sb.append(p.charAt(0));
        }
        return sb.toString().toUpperCase().substring(0, Math.min(2, sb.length()));
    }
}
