package com.example.myplane.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myplane.R;
import com.example.myplane.databinding.ActivityMainBinding;
import com.example.myplane.fragments.AbstractFragment;
import com.example.myplane.fragments.AirlineFragment;
import com.example.myplane.fragments.AirplaneFragment;
import com.example.myplane.models.Airline;
import com.example.myplane.models.Plane;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MainActivity extends AppCompatActivity {

    AbstractFragment containedFragment;
    public static List<Airline> airlines = new ArrayList<>();
    public static List<Plane> planes = new ArrayList<>();
    Button searchButton;
    EditText searchEditText;
    TextView mainSearchTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchButton = findViewById(R.id.btnSearch);
        searchEditText = findViewById(R.id.etSearch);
        mainSearchTextView = findViewById(R.id.tvSearch);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        replaceFragment(new AirlineFragment());
        mainSearchTextView.setText("Airline");
        bottomNavigationView.setOnItemSelectedListener(item -> {

            searchEditText.setText("");
            switch (item.getItemId()) {
                case R.id.airline_menu: {
                    replaceFragment(new AirlineFragment());
                    mainSearchTextView.setText("Airline");
                    break;
                }
                case R.id.airplane_menu: {
                    replaceFragment(new AirplaneFragment());
                    mainSearchTextView.setText("Manufacturer");
                    break;
                }
                default:
                    break;
            }

            return true;
        });

        searchButton.setOnClickListener(view -> {
            String searchText = Optional.of(searchEditText.getText().toString().trim()).orElse("");
            containedFragment.searchRelevantInformation(searchText);
        });
    }

    private void replaceFragment(AbstractFragment fragment) {
        containedFragment = fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}