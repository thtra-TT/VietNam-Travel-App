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
import com.example.vntravelapp.database.DatabaseHelper;
import com.example.vntravelapp.models.Tour;
import java.util.List;

public class HomeFragment extends Fragment {

    private DatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        dbHelper = new DatabaseHelper(getContext());

        // Setup Categories
        setupCategory(view.findViewById(R.id.catTour), "Tour", android.R.drawable.ic_menu_directions);
        setupCategory(view.findViewById(R.id.catHotel), "Khách sạn", android.R.drawable.ic_menu_myplaces);
        setupCategory(view.findViewById(R.id.catTicket), "Vé", android.R.drawable.ic_menu_agenda);
        setupCategory(view.findViewById(R.id.catCombo), "Combo", android.R.drawable.ic_menu_save);

        // Add Click Listeners
        view.findViewById(R.id.catHotel).setOnClickListener(v -> switchFragment(new HotelFragment()));
        view.findViewById(R.id.catTicket).setOnClickListener(v -> switchFragment(new TicketFragment()));
        view.findViewById(R.id.catCombo).setOnClickListener(v -> switchFragment(new ComboFragment()));

        // Setup Tours RecyclerView from SQLite
        RecyclerView rvTours = view.findViewById(R.id.rvTours);
        rvTours.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Tour> tours = dbHelper.getAllTours();
        TourAdapter adapter = new TourAdapter(tours);
        rvTours.setAdapter(adapter);

        return view;
    }

    private void switchFragment(Fragment fragment) {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
        }
    }

    private void setupCategory(View view, String name, int iconRes) {
        TextView tv = view.findViewById(R.id.tvCategoryName);
        ImageView iv = view.findViewById(R.id.ivCategoryIcon);
        tv.setText(name);
        iv.setImageResource(iconRes);
    }
}
