package com.isunican.eventossantander.view.events;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.isunican.eventossantander.R;
import com.isunican.eventossantander.presenter.events.BottomNavigationViewHelper;
import com.isunican.eventossantander.view.navigation.BottomNavigationBar;

import android.content.Intent;
import android.os.Bundle;

public class FavoriteEventsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_events);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()) {
                case R.id.inicioActivity:
                    Intent intent1 = new Intent(this, EventsActivity.class);
                    startActivity(intent1);
                    break;

                case R.id.favoritosActivity:
                    Intent intent2 = new Intent(this, FavoriteEventsActivity.class);
                    startActivity(intent2);
                    break;
            }

            return false;
        });
    }
}