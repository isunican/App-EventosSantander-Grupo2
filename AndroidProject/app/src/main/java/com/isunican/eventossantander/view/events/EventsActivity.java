package com.isunican.eventossantander.view.events;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.isunican.eventossantander.R;
import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.presenter.events.EventsPresenter;
import com.isunican.eventossantander.view.eventsdetail.EventsDetailActivity;
import com.isunican.eventossantander.view.info.InfoActivity;

import java.util.List;

public class EventsActivity extends AppCompatActivity implements IEventsContract.View {

    private IEventsContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new EventsPresenter(this);
        NavigationView menuFiltros = findViewById(R.id.menu_filtros);
        ListView listaEventos = findViewById(R.id.eventsListView);
        listaEventos.setOnTouchListener(new OnSwipeTouchListener(EventsActivity.this) {
            public void onSwipeTop() {
                Toast.makeText(EventsActivity.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                menuFiltros.setVisibility(View.VISIBLE);
            }
            public void onSwipeLeft() {
                menuFiltros.setVisibility(View.GONE);
            }
            public void onSwipeBottom() {
                Toast.makeText(EventsActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });
    }


    @Override
    public void onEventsLoaded(List<Event> events) {
        EventArrayAdapter adapter = new EventArrayAdapter(EventsActivity.this, 0, events);
        ListView listView = findViewById(R.id.eventsListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> presenter.onEventClicked(position));
    }

    @Override
    public void onLoadError() {
        //Todavia no existe una gestion de errores planificada
        throw new UnsupportedOperationException();
    }

    @Override
    public void onLoadSuccess(int elementsLoaded) {
        String text = String.format("Loaded %d events", elementsLoaded);
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openEventDetails(Event event) {
        Intent intent = new Intent(this, EventsDetailActivity.class);
        intent.putExtra(EventsDetailActivity.INTENT_EVENT, event);
        startActivity(intent);
    }

    @Override
    public void openInfoView() {
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }

    public IEventsContract.Presenter getPresenter() {
        return presenter;
    }

    /*
    Menu Handling
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                presenter.onReloadClicked();
                return true;
            case R.id.menu_info:
                presenter.onInfoClicked();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}