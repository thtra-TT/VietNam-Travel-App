package com.example.vntravelapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.vntravelapp.R;
import com.example.vntravelapp.adapters.TourAdapter;
import com.example.vntravelapp.models.Tour;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Setup Categories
        setupCategory(view.findViewById(R.id.catTour), "Tour", android.R.drawable.ic_menu_directions);
        setupCategory(view.findViewById(R.id.catHotel), "Khách sạn", android.R.drawable.ic_menu_myplaces);
        setupCategory(view.findViewById(R.id.catTicket), "Vé", android.R.drawable.ic_menu_agenda);
        setupCategory(view.findViewById(R.id.catCombo), "Combo", android.R.drawable.ic_menu_save);

        // Setup Tours RecyclerView
        RecyclerView rvTours = view.findViewById(R.id.rvTours);
        rvTours.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Tour> dummyTours = new ArrayList<>();
        dummyTours.add(new Tour("Du thuyền Vịnh Hạ Long 2N1Đ", "Quảng Ninh", "2 ngày 1 đêm", "2.999.000đ", R.mipmap.ic_launcher_vinhhalong, 4.8f, 234));
        dummyTours.add(new Tour("Đà Nẵng - Hội An 3N2Đ", "Đà Nẵng", "3 ngày 2 đêm", "3.999.000đ", R.mipmap.ic_launcher_hoian, 4.7f, 342));

        TourAdapter adapter = new TourAdapter(dummyTours);
        rvTours.setAdapter(adapter);

        return view;
    }

    private void setupCategory(View view, String name, int iconRes) {
        TextView tv = view.findViewById(R.id.tvCategoryName);
        ImageView iv = view.findViewById(R.id.ivCategoryIcon);
        tv.setText(name);
        iv.setImageResource(iconRes);
    }
}