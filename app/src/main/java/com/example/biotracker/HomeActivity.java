package com.example.biotracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(HomeFragment.newInstance("",""));
    }

    public void openFragment(Fragment fragment)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId())
            {
                case R.id.navigation_store:
                    openFragment(StoreFragment.newInstance("",""));
                    return true;

                case R.id.navigation_community:
                    openFragment(CommunityFragment.newInstance("",""));
                    return true;

                case R.id.navigation_home:
                    openFragment(HomeFragment.newInstance("",""));
                    return true;

                case R.id.navigation_reports:
                    openFragment(ReportsFragment.newInstance("",""));
                    return true;

                case R.id.navigation_more:
                    openFragment(MoreFragment.newInstance("",""));
                    return true;
            }
            return false;
        }
    };
}