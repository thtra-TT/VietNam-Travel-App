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
import com.bumptech.glide.Glide;
import com.example.vntravelapp.R;

public class DetailFragment extends Fragment {

    private String title, location, price, description, imageUrl;
    private int imageRes;
    private float rating;
    private int reviews;

    public static DetailFragment newInstance(String title, String location, String price, String description, int imageRes, String imageUrl, float rating, int reviews) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("location", location);
        args.putString("price", price);
        args.putString("description", description);
        args.putInt("imageRes", imageRes);
        args.putString("imageUrl", imageUrl);
        args.putFloat("rating", rating);
        args.putInt("reviews", reviews);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString("title");
            location = getArguments().getString("location");
            price = getArguments().getString("price");
            description = getArguments().getString("description");
            imageRes = getArguments().getInt("imageRes");
            imageUrl = getArguments().getString("imageUrl");
            rating = getArguments().getFloat("rating");
            reviews = getArguments().getInt("reviews");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        ImageView ivImage = view.findViewById(R.id.ivDetailImage);
        ImageView ivBack = view.findViewById(R.id.ivBack);
        TextView tvTitle = view.findViewById(R.id.tvDetailTitle);
        TextView tvLocation = view.findViewById(R.id.tvDetailLocation);
        TextView tvPrice = view.findViewById(R.id.tvDetailPrice);
        TextView tvDescription = view.findViewById(R.id.tvDetailDescription);
        TextView tvRating = view.findViewById(R.id.tvDetailRating);
        TextView tvReviews = view.findViewById(R.id.tvDetailReviews);

        tvTitle.setText(title);
        tvLocation.setText(location);
        tvPrice.setText(price);
        tvDescription.setText(description);
        tvRating.setText("⭐ " + rating);
        tvReviews.setText("(" + reviews + " đánh giá)");

        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(this).load(imageUrl).placeholder(android.R.drawable.ic_menu_gallery).into(ivImage);
        } else if (imageRes != 0) {
            ivImage.setImageResource(imageRes);
        }

        ivBack.setOnClickListener(v -> {
            if (getActivity() != null) getActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }
}
