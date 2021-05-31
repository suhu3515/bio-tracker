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
import androidx.fragment.app.Fragment;

public class MoreFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    TextView textViewUserName, textViewUserPhone;
    User user;
    Button buttonAddFarm, buttonViewOrders, buttonLogout, buttonTutorials;

    public MoreFragment() {
    }

    public static MoreFragment newInstance(String param1, String param2)
    {
        MoreFragment fragment = new MoreFragment();
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

        user = SharedPrefManager.getInstance(getContext()).getUser();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_more, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewUserName = view.findViewById(R.id.text_view_profile_user_name);
        textViewUserPhone = view.findViewById(R.id.text_view_profile_user_phone);

        textViewUserName.setText(user.getUserName());
        textViewUserPhone.setText(user.getUserMobile());

        buttonAddFarm = view.findViewById(R.id.button_farm);
        buttonViewOrders = view.findViewById(R.id.button_orders);
        buttonLogout = view.findViewById(R.id.button_logout);
        buttonTutorials = view.findViewById(R.id.button_tutorials);

        buttonAddFarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent farmIntent = new Intent(getContext(),FarmActivity.class);
                startActivity(farmIntent);
            }
        });

        buttonViewOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent orderIntent = new Intent(getContext(), ViewOrdersActivity.class);
                startActivity(orderIntent);

            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().finishAffinity();
                SharedPrefManager.getInstance(getContext()).logout();

            }
        });

        buttonTutorials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent tutorialIntent = new Intent(getContext(),ViewTutorialsActivity.class);
                startActivity(tutorialIntent);

            }
        });

    }
}
