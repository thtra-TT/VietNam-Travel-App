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
import com.example.vntravelapp.models.Trip;
import java.util.ArrayList;
import java.util.List;

public class TripFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip, container, false);

        RecyclerView rvTrips = view.findViewById(R.id.rvTrips);
        rvTrips.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Trip> dummyTrips = new ArrayList<>();
        dummyTrips.add(new Trip(
            "Du thuyền Vịnh Hạ Long 2N1Đ", 
            "Quảng Ninh", 
            "25/01/2026", 
            "Đã xác nhận", 
            "VHL250126", 
            "2.999.000đ", 
            R.mipmap.ic_launcher_vinhhalong, 
            false
        ));
        dummyTrips.add(new Trip(
            "Đà Nẵng - Hội An 3N2Đ", 
            "Đà Nẵng", 
            "05/02/2026", 
            "Chờ thanh toán", 
            "DNA050226", 
            "3.999.000đ", 
            R.mipmap.ic_launcher_hoian,
            true
        ));

        TripAdapter adapter = new TripAdapter(dummyTrips);
        rvTrips.setAdapter(adapter);

        return view;
    }
}