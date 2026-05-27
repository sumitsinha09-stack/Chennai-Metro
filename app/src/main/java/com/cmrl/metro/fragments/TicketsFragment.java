package com.cmrl.metro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.google.android.material.tabs.TabLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cmrl.metro.R;
import com.cmrl.metro.adapters.TicketAdapter;
import com.cmrl.metro.models.Ticket;
import com.cmrl.metro.storage.TicketStore;
import java.util.ArrayList;
import java.util.List;

public class TicketsFragment extends Fragment {

    private TabLayout tabLayout;
    private RecyclerView rvTickets;
    private LinearLayout layoutEmpty;
    private TicketAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tickets, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout    = view.findViewById(R.id.tab_layout);
        rvTickets    = view.findViewById(R.id.rv_tickets);
        layoutEmpty  = view.findViewById(R.id.layout_empty);

        adapter = new TicketAdapter(new ArrayList<>(), this::onTicketClick);
        rvTickets.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvTickets.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Active"));
        tabLayout.addTab(tabLayout.newTab().setText("History"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override public void onTabSelected(TabLayout.Tab tab) { refreshList(tab.getPosition()); }
            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });

        view.findViewById(R.id.btn_plan_journey).setOnClickListener(v ->
            requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new PlannerFragment())
                .addToBackStack(null)
                .commit()
        );

        refreshList(0);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshList(tabLayout.getSelectedTabPosition());
    }

    private void refreshList(int tabIndex) {
        List<Ticket> all     = TicketStore.getInstance().getTickets();
        List<Ticket> display = new ArrayList<>();

        for (Ticket t : all) {
            if (tabIndex == 0 && t.getStatus() == Ticket.Status.ACTIVE)  display.add(t);
            if (tabIndex == 1 && t.getStatus() != Ticket.Status.ACTIVE)  display.add(t);
        }

        adapter.updateData(display);
        layoutEmpty.setVisibility(display.isEmpty() ? View.VISIBLE : View.GONE);
        rvTickets.setVisibility(display.isEmpty() ? View.GONE : View.VISIBLE);
    }

    private void onTicketClick(Ticket ticket) {
        QRTicketBottomSheet sheet = QRTicketBottomSheet.newInstance(ticket);
        sheet.show(getChildFragmentManager(), "QRTicket");
    }
}
