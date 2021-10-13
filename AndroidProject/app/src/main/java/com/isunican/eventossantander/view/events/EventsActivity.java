package com.isunican.eventossantander.view.events;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.isunican.eventossantander.R;
import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.presenter.events.EventsPresenter;
import com.isunican.eventossantander.view.eventsdetail.EventsDetailActivity;
import com.isunican.eventossantander.view.info.InfoActivity;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventsActivity extends AppCompatActivity implements IEventsContract.View {

    private IEventsContract.Presenter presenter;
    private List<Event> events;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new EventsPresenter(this);
        NavigationView menuFiltros = findViewById(R.id.menu_filtros);
        ListView listaEventos = findViewById(R.id.eventsListView);
        Button btnAplicar = findViewById(R.id.btnAplicarFiltroOrden);

        // Listener for 'Apply filters/order by' button
        btnAplicar.setOnClickListener(view -> {     // Applies filters & order by when pressed
            RadioButton rbOrdenarProxima = findViewById(R.id.rbOrdenarProxima);
            RadioButton rbOrdenarLejana = findViewById(R.id.rbOrdenarLejana);
            RadioButton rbSinFecha = findViewById(R.id.rbSinFecha);
            List<Event> lista = null;

            // TODO: lista tiene que ser lo que devuelva el filtrado

            // Check 'order by' type selected
            if (rbOrdenarProxima.isChecked()) {
                presenter.onApplyOrder(EventsPresenter.OrderType.DATE_ASC, lista);      // Closer to current date
            } else if (rbOrdenarLejana.isChecked()) {
                presenter.onApplyOrder(EventsPresenter.OrderType.DATE_DESC, lista);     // Further away from current date
            } else if (rbSinFecha.isChecked()) {
                presenter.onApplyOrder(EventsPresenter.OrderType.NO_DATE_FIRST, lista); // Events without date first
            }

            onEventsLoaded(events);                 // Reloads the events
            menuFiltros.setVisibility(View.GONE);   // Closes the menu
        });

        // Listener for swipe menu
        listaEventos.setOnTouchListener(new OnSwipeTouchListener(EventsActivity.this) {
            @Override
            public void onSwipeRight() {
                menuFiltros.setVisibility(View.VISIBLE);
            }
            @Override
            public void onSwipeLeft() {
                menuFiltros.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onEventsLoaded(List<Event> events) {
        this.events = events;
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