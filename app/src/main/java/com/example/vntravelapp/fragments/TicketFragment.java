package com.example.vntravelapp.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.vntravelapp.R;
import com.example.vntravelapp.adapters.TicketOfferAdapter;
import com.example.vntravelapp.adapters.TripAdapter;
import com.example.vntravelapp.database.DatabaseHelper;
import com.example.vntravelapp.models.TicketOffer;
import com.example.vntravelapp.models.Trip;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TicketFragment extends Fragment {

    private DatabaseHelper dbHelper;
    private RecyclerView rvOffers, rvTrips, rvMyTrips;
    private TextView tvDeparture, tvDestination, tvDepartureDate, tvNoTrips;
    private View rlSearchResultsHeader;
    private TabLayout tabLayoutMyTrips;
    private String selectedDep = "Hà Nội", selectedDest = "TP. Hồ Chí Minh", selectedDate = "2024-08-12";

    private final String[] provinces = {
        "Hà Nội", "TP. Hồ Chí Minh", "Đà Nẵng", "Hải Phòng", "Cần Thơ", 
        "An Giang", "Bà Rịa - Vũng Tàu", "Bắc Giang", "Bắc Kạn", "Bạc Liêu", 
        "Bắc Ninh", "Bến Tre", "Bình Định", "Bình Dương", "Bình Phước", 
        "Bình Thuận", "Cà Mau", "Cao Bằng", "Đắk Lắk", "Đắk Nông", 
        "Điện Biên", "Đồng Nai", "Đồng Tháp", "Gia Lai", "Hà Giang", 
        "Hà Nam", "Hà Tĩnh", "Hải Dương", "Hậu Giang", "Hòa Bình", 
        "Hưng Yên", "Khánh Hòa", "Kiên Giang", "Kon Tum", "Lai Châu", 
        "Lâm Đồng", "Lạng Sơn", "Lào Cai", "Long An", "Nam Định", 
        "Nghệ An", "Ninh Bình", "Ninh Thuận", "Phú Thọ", "Quảng Bình", 
        "Quảng Nam", "Quảng Ngãi", "Quảng Ninh", "Quảng Trị", "Sóc Trăng", 
        "Sơn La", "Tây Ninh", "Thái Bình", "Thái Nguyên", "Thanh Hóa", 
        "Thừa Thiên Huế", "Tiền Giang", "Trà Vinh", "Tuyên Quang", "Vĩnh Long", 
        "Vĩnh Phúc", "Yên Bái", "Phú Yên"
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket, container, false);
        dbHelper = new DatabaseHelper(getContext());

        initViews(view);
        setupClickListeners(view);
        loadOffers();
        loadMyTrips("upcoming"); // Mặc định load chuyến đi sắp tới

        return view;
    }

    private void initViews(View view) {
        rvOffers = view.findViewById(R.id.rvOffers);
        rvTrips = view.findViewById(R.id.rvTrips);
        rvMyTrips = view.findViewById(R.id.rvMyTrips);
        tvDeparture = view.findViewById(R.id.tvDeparture);
        tvDestination = view.findViewById(R.id.tvDestination);
        tvDepartureDate = view.findViewById(R.id.tvDepartureDate);
        tvNoTrips = view.findViewById(R.id.tvNoTrips);
        rlSearchResultsHeader = view.findViewById(R.id.rlSearchResultsHeader);
        tabLayoutMyTrips = view.findViewById(R.id.tabLayoutMyTrips);

        rvOffers.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvTrips.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMyTrips.setLayoutManager(new LinearLayoutManager(getContext()));
        
        tvDeparture.setText(selectedDep);
        tvDestination.setText(selectedDest);
        tvDepartureDate.setText("12 Th08, 2024");

        // Cập nhật tab để có 3 trạng thái
        if (tabLayoutMyTrips.getTabCount() == 2) {
            tabLayoutMyTrips.addTab(tabLayoutMyTrips.newTab().setText("Đã huỷ"));
        }

        tabLayoutMyTrips.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0: loadMyTrips("upcoming"); break;
                    case 1: loadMyTrips("completed"); break;
                    case 2: loadMyTrips("cancelled"); break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void loadMyTrips(String status) {
        List<Trip> booked = dbHelper.getBookedTrips(status);
        if (booked.isEmpty()) {
            rvMyTrips.setVisibility(View.GONE);
            tvNoTrips.setVisibility(View.VISIBLE);
        } else {
            rvMyTrips.setVisibility(View.VISIBLE);
            tvNoTrips.setVisibility(View.GONE);
            TripAdapter adapter = new TripAdapter(booked, true, trip -> {
                new AlertDialog.Builder(getContext())
                    .setTitle("Huỷ chuyến")
                    .setMessage("Bạn có chắc chắn muốn huỷ chuyến đi này không?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        dbHelper.cancelBooking(trip.getId());
                        loadMyTrips("upcoming"); // Reload list sau khi huỷ
                        Toast.makeText(getContext(), "Đã huỷ chuyến thành công", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Không", null)
                    .show();
            });
            rvMyTrips.setAdapter(adapter);
        }
    }

    private void setupClickListeners(View view) {
        view.findViewById(R.id.ivBack).setOnClickListener(v -> {
            if (getActivity() != null) getActivity().getSupportFragmentManager().popBackStack();
        });

        view.findViewById(R.id.llDeparture).setOnClickListener(v -> showLocationDialog(true));
        view.findViewById(R.id.llDestination).setOnClickListener(v -> showLocationDialog(false));
        
        view.findViewById(R.id.llDepartureDate).setOnClickListener(v -> showDatePicker());
        
        view.findViewById(R.id.btnSearchTrips).setOnClickListener(v -> performSearch());
        
        view.findViewById(R.id.ivSwapLocations).setOnClickListener(v -> {
            String temp = selectedDep;
            selectedDep = selectedDest;
            selectedDest = temp;
            tvDeparture.setText(selectedDep);
            tvDestination.setText(selectedDest);
        });
    }

    private void showLocationDialog(boolean isDeparture) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_select_location, null);
        builder.setView(dialogView);
        
        AutoCompleteTextView actv = dialogView.findViewById(R.id.actvLocation);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, provinces);
        actv.setAdapter(adapter);
        actv.setHint(isDeparture ? "Chọn điểm đi" : "Chọn điểm đến");

        AlertDialog dialog = builder.create();
        dialogView.findViewById(R.id.btnConfirmLocation).setOnClickListener(v -> {
            String val = actv.getText().toString();
            if (!val.isEmpty()) {
                if (isDeparture) {
                    selectedDep = val;
                    tvDeparture.setText(val);
                } else {
                    selectedDest = val;
                    tvDestination.setText(val);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showDatePicker() {
        Calendar c = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
            selectedDate = year + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth);
            tvDepartureDate.setText(dayOfMonth + " Th" + String.format("%02d", month + 1) + ", " + year);
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private void performSearch() {
        List<Trip> results = dbHelper.searchTrips(selectedDep, selectedDest, selectedDate);
        if (results.isEmpty()) {
            Toast.makeText(getContext(), "Không tìm thấy chuyến đi phù hợp", Toast.LENGTH_SHORT).show();
            rvTrips.setVisibility(View.GONE);
            rlSearchResultsHeader.setVisibility(View.GONE);
        } else {
            TripAdapter tripAdapter = new TripAdapter(results, trip -> {
                BookingFragment fragment = BookingFragment.newInstance(trip.getId());
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            });
            rvTrips.setAdapter(tripAdapter);
            rvTrips.setVisibility(View.VISIBLE);
            rlSearchResultsHeader.setVisibility(View.VISIBLE);
            rvTrips.post(() -> rvTrips.smoothScrollToPosition(0));
        }
    }

    private void loadOffers() {
        List<TicketOffer> offers = dbHelper.getAllTickets();
        TicketOfferAdapter offerAdapter = new TicketOfferAdapter(offers);
        rvOffers.setAdapter(offerAdapter);
    }
}
