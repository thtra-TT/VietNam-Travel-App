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
import com.example.vntravelapp.adapters.ComboAdapter;
import com.example.vntravelapp.database.DatabaseHelper;
import com.example.vntravelapp.models.Combo;
import java.util.List;

public class ComboFragment extends Fragment {

    private DatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_combo, container, false);
        dbHelper = new DatabaseHelper(getContext());

        view.findViewById(R.id.ivBack).setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        RecyclerView rvCombos = view.findViewById(R.id.rvCombos);
        rvCombos.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Combo> combos = dbHelper.getAllCombos();
        ComboAdapter adapter = new ComboAdapter(combos);
        rvCombos.setAdapter(adapter);

        return view;
    }
}
