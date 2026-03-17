package com.example.vntravelapp.adapters;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.vntravelapp.R;
import com.example.vntravelapp.fragments.BookingFragment;
import com.example.vntravelapp.models.Trip;
import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {

    public interface OnTripClickListener {
        void onTripClick(Trip trip);
    }

    public interface OnCancelClickListener {
        void onCancelClick(Trip trip);
    }

    private List<Trip> trips;
    private boolean isMyTrips;
    private OnTripClickListener listener;
    private OnCancelClickListener cancelListener;

    public TripAdapter(List<Trip> trips) {
        this.trips = trips;
        this.isMyTrips = false;
    }

    public TripAdapter(List<Trip> trips, boolean isMyTrips) {
        this.trips = trips;
        this.isMyTrips = isMyTrips;
    }

    public TripAdapter(List<Trip> trips, boolean isMyTrips, OnCancelClickListener cancelListener) {
        this.trips = trips;
        this.isMyTrips = isMyTrips;
        this.cancelListener = cancelListener;
    }

    public TripAdapter(List<Trip> trips, OnTripClickListener listener) {
        this.trips = trips;
        this.listener = listener;
        this.isMyTrips = false;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trip, parent, false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        Trip trip = trips.get(position);
        holder.tvBrandName.setText(trip.getBrandName());
        holder.tvPrice.setText(trip.getPrice());
        holder.tvDepartureTime.setText(trip.getDepartureTime());
        holder.tvRoute.setText(trip.getDepartureLocation() + " - " + trip.getDestinationLocation());
        holder.tvAvailableSeats.setText("Còn " + trip.getAvailableSeats() + " ghế");

        if (isMyTrips) {
            String status = trip.getStatus();
            if ("upcoming".equals(status)) {
                holder.btnBook.setText("Huỷ chuyến");
                holder.btnBook.setEnabled(true);
                holder.btnBook.setBackgroundTintList(holder.itemView.getContext().getResources().getColorStateList(android.R.color.holo_red_dark));
                holder.btnBook.setOnClickListener(v -> {
                    if (cancelListener != null) {
                        cancelListener.onCancelClick(trip);
                    }
                });
            } else if ("completed".equals(status)) {
                holder.btnBook.setText("Đã đi");
                holder.btnBook.setEnabled(false);
                holder.btnBook.setBackgroundTintList(holder.itemView.getContext().getResources().getColorStateList(android.R.color.darker_gray));
            } else if ("cancelled".equals(status)) {
                holder.btnBook.setText("Đã huỷ");
                holder.btnBook.setEnabled(false);
                holder.btnBook.setBackgroundTintList(holder.itemView.getContext().getResources().getColorStateList(android.R.color.darker_gray));
            }
        } else {
            holder.btnBook.setText("Chọn chuyến");
            holder.btnBook.setEnabled(true);
            holder.btnBook.setBackgroundTintList(null); // Reset to default if needed
            holder.btnBook.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onTripClick(trip);
                } else {
                    AppCompatActivity activity = getAppCompatActivity(v.getContext());
                    if (activity != null) {
                        BookingFragment fragment = BookingFragment.newInstance(trip.getId());
                        activity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, fragment)
                                .addToBackStack(null)
                                .commit();
                    }
                }
            });
        }
    }

    private AppCompatActivity getAppCompatActivity(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof AppCompatActivity) {
                return (AppCompatActivity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    static class TripViewHolder extends RecyclerView.ViewHolder {
        TextView tvBrandName, tvPrice, tvDepartureTime, tvRoute, tvAvailableSeats;
        Button btnBook;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBrandName = itemView.findViewById(R.id.tvBrandName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvDepartureTime = itemView.findViewById(R.id.tvDepartureTime);
            tvRoute = itemView.findViewById(R.id.tvRoute);
            tvAvailableSeats = itemView.findViewById(R.id.tvAvailableSeats);
            btnBook = itemView.findViewById(R.id.btnBook);
        }
    }
}
