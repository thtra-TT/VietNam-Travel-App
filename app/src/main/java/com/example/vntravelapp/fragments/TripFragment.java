package com.example.vntravelapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.vntravelapp.R;
import com.example.vntravelapp.adapters.TripAdapter;
import com.example.vntravelapp.database.DatabaseHelper;
import com.example.vntravelapp.models.Trip;
import java.util.ArrayList;
import java.util.List;

public class TripFragment extends Fragment {

    private DatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip, container, false);

        dbHelper = new DatabaseHelper(getContext());
        RecyclerView rvTrips = view.findViewById(R.id.rvTrips);
        rvTrips.setLayoutManager(new LinearLayoutManager(getContext()));

        // Lấy danh sách vé đã đặt từ database
        List<Trip> bookedTrips = dbHelper.getBookedTrips();
        
        // Hiển thị danh sách vé đã mua (isMyTrips = true)
        TripAdapter adapter = new TripAdapter(bookedTrips, true);
        rvTrips.setAdapter(adapter);

        // Hiển thị view thông báo nếu không có vé
        View emptyView = view.findViewById(R.id.tvEmpty); // Giả sử có tvEmpty trong layout
        if (emptyView != null) {
            emptyView.setVisibility(bookedTrips.isEmpty() ? View.VISIBLE : View.GONE);
        }

        return view;
    }
}
