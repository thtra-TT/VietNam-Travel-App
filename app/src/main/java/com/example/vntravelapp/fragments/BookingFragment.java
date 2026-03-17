package com.example.vntravelapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.vntravelapp.R;
import com.example.vntravelapp.database.DatabaseHelper;
import com.google.android.material.textfield.TextInputEditText;

public class BookingFragment extends Fragment {

    private static final String ARG_TRIP_ID = "trip_id";
    private int tripId;
    private DatabaseHelper dbHelper;

    public static BookingFragment newInstance(int tripId) {
        BookingFragment fragment = new BookingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TRIP_ID, tripId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tripId = getArguments().getInt(ARG_TRIP_ID);
        }
        dbHelper = new DatabaseHelper(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking, container, false);

        TextInputEditText etName = view.findViewById(R.id.etCustomerName);
        TextInputEditText etPickup = view.findViewById(R.id.etPickupPoint);
        TextInputEditText etPhone = view.findViewById(R.id.etPhone);
        Button btnConfirm = view.findViewById(R.id.btnConfirmBooking);

        btnConfirm.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String pickup = etPickup.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();

            if (name.isEmpty() || pickup.isEmpty() || phone.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            long result = dbHelper.bookTicket(tripId, name, pickup, phone);
            if (result != -1) {
                Toast.makeText(getContext(), "Đặt vé thành công!", Toast.LENGTH_SHORT).show();
                // Chuyển sang TripFragment để xem vé đã mua
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new TripFragment())
                            .commit();
                }
            } else {
                Toast.makeText(getContext(), "Đặt vé thất bại, vui lòng thử lại", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
