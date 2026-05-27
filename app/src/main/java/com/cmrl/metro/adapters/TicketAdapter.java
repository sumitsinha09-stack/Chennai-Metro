package com.cmrl.metro.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cmrl.metro.R;
import com.cmrl.metro.models.Ticket;
import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {

    public interface OnTicketClickListener {
        void onTicketClick(Ticket ticket);
    }

    private List<Ticket> tickets;
    private final OnTicketClickListener listener;

    public TicketAdapter(List<Ticket> tickets, OnTicketClickListener listener) {
        this.tickets  = tickets;
        this.listener = listener;
    }

    public void updateData(List<Ticket> newTickets) {
        this.tickets = newTickets;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_ticket, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ticket ticket = tickets.get(position);

        holder.tvFrom.setText(ticket.getFromStation());
        holder.tvTo.setText(ticket.getToStation());
        holder.tvFare.setText("₹" + ticket.getFareINR());
        holder.tvDate.setText(ticket.getDate());
        holder.tvStatus.setText(ticket.getStatus().name());

        int statusColor = ticket.getStatus() == Ticket.Status.ACTIVE
            ? Color.parseColor("#1B8B2E")
            : Color.parseColor("#607D9F");
        holder.tvStatus.setTextColor(statusColor);

        holder.itemView.setOnClickListener(v -> listener.onTicketClick(ticket));
    }

    @Override
    public int getItemCount() { return tickets.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFrom, tvTo, tvFare, tvDate, tvStatus;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFrom   = itemView.findViewById(R.id.tv_from);
            tvTo     = itemView.findViewById(R.id.tv_to);
            tvFare   = itemView.findViewById(R.id.tv_fare);
            tvDate   = itemView.findViewById(R.id.tv_date);
            tvStatus = itemView.findViewById(R.id.tv_status);
        }
    }
}
