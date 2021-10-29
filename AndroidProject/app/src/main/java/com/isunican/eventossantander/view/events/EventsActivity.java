package com.isunican.eventossantander.view.events;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationView;
import com.isunican.eventossantander.R;
import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.presenter.events.EventsPresenter;
import com.isunican.eventossantander.presenter.events.Options;
import com.isunican.eventossantander.view.eventsdetail.EventsDetailActivity;
import com.isunican.eventossantander.view.favourites.GestionarFavoritosUsuario;
import com.isunican.eventossantander.view.favourites.IGestionarFavoritos;
import com.isunican.eventossantander.view.info.InfoActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventsActivity extends AppCompatActivity implements IEventsContract.View {

    private IEventsContract.Presenter presenter;
    private Button btnAplicarFiltroOrden;
    private ImageButton btnFiltroCategoriaDown;
    private ImageButton btnFiltroCategoriaUp;
    private LinearLayout layoutFiltroCategoria;
    private IGestionarFavoritos sharedPref;
    private boolean isFilterMenuVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPref = new GestionarFavoritosUsuario(this);
        presenter = new EventsPresenter(this);

        NavigationView menuFiltros = findViewById(R.id.menu_filtros);
        ListView listaEventos = findViewById(R.id.eventsListView);

        Button btnAplicarFiltroOrden = findViewById(R.id.btnAplicarFiltroOrden);
        ImageButton btnFiltroCategoriaUp =findViewById(R.id.btnFiltroCategoriaUp);
        ImageButton btnFiltroCategoriaDown =findViewById(R.id.btnFiltroCategoriaDown);
        LinearLayout layoutFiltroCategoria = findViewById(R.id.layoutFiltroCategoria);
        btnFiltroCategoriaUp.setVisibility(View.GONE);
        layoutFiltroCategoria.setVisibility(View.GONE);

        isFilterMenuVisible = false;

        //Map to store the categories filtered
        Map<String, Boolean> categorias = new HashMap<>();
        int pos = layoutFiltroCategoria.getChildCount();
        for (int i = 0; i < pos; i++) {
            View viewAux = layoutFiltroCategoria.getChildAt(i);

            categorias.put(((CheckBox) viewAux).getText().toString(), false);
        }


        // Handler to show the filters for categories
        btnFiltroCategoriaDown.setOnClickListener(view -> {
            btnFiltroCategoriaDown.setVisibility(View.GONE);
            btnFiltroCategoriaUp.setVisibility(View.VISIBLE);
            layoutFiltroCategoria.setVisibility(View.VISIBLE);
        });

        // Handler to hide the filters for categories
        btnFiltroCategoriaUp.setOnClickListener(view -> {
            btnFiltroCategoriaDown.setVisibility(View.VISIBLE);
            btnFiltroCategoriaUp.setVisibility(View.GONE);
            layoutFiltroCategoria.setVisibility(View.GONE);
        });
        
        // Handler to control the events of sliding the finger (up, down, right, left)
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

        // Manejador para aplicar los filtros y ordenacion
        btnAplicarFiltroOrden.setOnClickListener(view -> {
            RadioButton rbOrdenarLejana = findViewById(R.id.rbOrdenarLejana);
            CheckBox checkBoxSinFecha = findViewById(R.id.checkBoxSinFecha);

            int posi = layoutFiltroCategoria.getChildCount();
            for (int i = 0; i < posi; i++) {
                View viewAux = layoutFiltroCategoria.getChildAt(i);
                if (viewAux instanceof CheckBox) {
                    if (((CheckBox) viewAux).isChecked()) {
                        categorias.put(((CheckBox) viewAux).getText().toString(), true);
                    } else {
                        categorias.put(((CheckBox) viewAux).getText().toString(), false);
                    }
                }
            }

            // Check order type selected
            EventsPresenter.OrderType orderType = EventsPresenter.OrderType.DATE_ASC;   // 'Show events closer to current date' selected by default
            if (rbOrdenarLejana.isChecked()) {
                orderType = EventsPresenter.OrderType.DATE_DESC;    // Further away from current date
            }
            boolean isDateFirst = false;    // Events without a date are shown last by default
            if (!checkBoxSinFecha.isChecked()) {
                isDateFirst = true;                                 // Events without date first
            }

            // Apply the filters & order selected
            presenter.onApplyOptions(new Options(categorias, orderType, isDateFirst));
            menuFiltros.setVisibility(View.GONE);   // Closes the menu

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

    @Override
    public void openFilterMenuView() {
        NavigationView menuFiltros = findViewById(R.id.menu_filtros);
        menuFiltros.setVisibility(View.VISIBLE);
    }

    @Override
    public void closeFilterMenuView() {
        NavigationView menuFiltros = findViewById(R.id.menu_filtros);
        menuFiltros.setVisibility(View.GONE);
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
            case R.id.filter_menu:
                presenter.onFilterMenuClicked(isFilterMenuVisible);
                if (isFilterMenuVisible) {
                    isFilterMenuVisible = false;
                } else {
                    isFilterMenuVisible = true;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public IGestionarFavoritos getSharedPref(){
        return sharedPref;
    }
}