package com.cmrl.metro.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cmrl.metro.R;
import com.cmrl.metro.data.MetroData;
import com.cmrl.metro.models.Station;
import java.util.List;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.ViewHolder> {

    public interface OnRouteClickListener {
        void onRouteClick(String fromId, String toId);
    }

    private final List<String[]> routes;   // each entry: [fromId, toId]
    private final OnRouteClickListener listener;

    public RouteAdapter(List<String[]> routes, OnRouteClickListener listener) {
        this.routes   = routes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_route, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String[] route = routes.get(position);
        Station from   = MetroData.getStation(route[0]);
        Station to     = MetroData.getStation(route[1]);

        if (from != null) holder.tvFrom.setText(from.getName());
        if (to != null)   holder.tvTo.setText(to.getName());

        holder.itemView.setOnClickListener(v -> listener.onRouteClick(route[0], route[1]));
    }

    @Override
    public int getItemCount() { return routes.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFrom, tvTo;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFrom = itemView.findViewById(R.id.tv_from);
            tvTo   = itemView.findViewById(R.id.tv_to);
        }
    }
}
