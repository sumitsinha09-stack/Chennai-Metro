package com.cmrl.metro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cmrl.metro.R;
import com.cmrl.metro.adapters.RouteAdapter;
import com.cmrl.metro.data.MetroData;
import com.cmrl.metro.models.Station;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeFragment extends Fragment {

    private TextView tvGreeting;
    private TextView tvLiveStatus;
    private TextView tvFirstTrain;
    private TextView tvLastTrain;
    private RecyclerView rvPopularRoutes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvGreeting    = view.findViewById(R.id.tv_greeting);
        tvLiveStatus  = view.findViewById(R.id.tv_live_status);
        tvFirstTrain  = view.findViewById(R.id.tv_first_train);
        tvLastTrain   = view.findViewById(R.id.tv_last_train);
        rvPopularRoutes = view.findViewById(R.id.rv_popular_routes);

        setupGreeting();
        setupServiceInfo();
        setupPopularRoutes();

        // Quick action buttons
        view.findViewById(R.id.btn_plan_journey).setOnClickListener(v ->
            requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new PlannerFragment())
                .addToBackStack(null)
                .commit()
        );

        view.findViewById(R.id.btn_route_map).setOnClickListener(v ->
            requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new MapFragment())
                .addToBackStack(null)
                .commit()
        );

        view.findViewById(R.id.btn_my_tickets).setOnClickListener(v ->
            requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new TicketsFragment())
                .addToBackStack(null)
                .commit()
        );
    }

    private void setupGreeting() {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        String greeting;
        if (hour < 12) greeting = "Good morning";
        else if (hour < 17) greeting = "Good afternoon";
        else greeting = "Good evening";
        tvGreeting.setText(greeting);
    }

    private void setupServiceInfo() {
        tvLiveStatus.setText("All systems operational");
        tvFirstTrain.setText("First train: " + MetroData.FIRST_TRAIN);
        tvLastTrain.setText("Last train: " + MetroData.LAST_TRAIN);
    }

    private void setupPopularRoutes() {
        List<String[]> popularRoutes = new ArrayList<>();
        popularRoutes.add(new String[]{"CC", "CA"});
        popularRoutes.add(new String[]{"WN", "CC"});
        popularRoutes.add(new String[]{"AGDMS", "STM"});
        popularRoutes.add(new String[]{"GU", "CC"});

        rvPopularRoutes.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvPopularRoutes.setAdapter(new RouteAdapter(popularRoutes, (fromId, toId) -> {
            PlannerFragment planner = PlannerFragment.newInstance(fromId, toId);
            requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, planner)
                .addToBackStack(null)
                .commit();
        }));
    }
}
