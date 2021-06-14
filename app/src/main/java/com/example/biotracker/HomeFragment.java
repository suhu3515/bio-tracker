package com.example.biotracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View root;
    TextView textViewName, textViewFarmsCount, textViewFishCount;
    Button buttonAddFarm;
    CardView cardViewFarm;
    User user;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(String param1, String param2)
    {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_home, container, false);
        textViewName = root.findViewById(R.id.textView_Heading);
        textViewFishCount = root.findViewById(R.id.fish_owned_count);
        textViewFarmsCount = root.findViewById(R.id.farms_owned_count);
        buttonAddFarm = root.findViewById(R.id.btn_add_farm);
        cardViewFarm = root.findViewById(R.id.card_farms);

        buttonAddFarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent farmsIntent = new Intent(getContext(),AddFarmActivity.class);
                startActivity(farmsIntent);
            }
        });

        cardViewFarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(),FarmActivity.class));

            }
        });
        user = SharedPrefManager.getInstance(getContext()).getUser();
        textViewName.setText(String.format("Welcome,\n%s", user.getUserName()));
        textViewFarmsCount.setText(HomeActivity.farmCount);
        textViewFishCount.setText(String.format("%s / %s", HomeActivity.fishCount, HomeActivity.fishCountTotal));

        return root;

    }
}
