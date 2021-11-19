package com.isunican.eventossantander.view.events;

import android.app.Dialog;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.isunican.eventossantander.R;
import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.presenter.events.EventsPresenter;
import com.isunican.eventossantander.presenter.events.Utilities;
import com.isunican.eventossantander.view.EventsActivityComun;
import com.isunican.eventossantander.view.favourites.FavoriteEventsActivity;
import com.isunican.eventossantander.view.favourites.GestionarListasUsuario;
import com.isunican.eventossantander.view.favourites.IGestionarListasUsuario;

import java.util.List;
import java.util.Map;

public class EventsActivity extends AppCompatActivity implements IEventsContract.View {
    private IEventsContract.Presenter presenter;
    private IGestionarListasUsuario sharedPref;
    private boolean isFilterMenuVisible;
    private Dialog lastDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPref = new GestionarListasUsuario(this);
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
        Map<String, Boolean> categorias = EventsActivityComun.categorias(layoutFiltroCategoria);



        // Handler to show the filters for categories
        btnFiltroCategoriaDown.setOnClickListener(view -> EventsActivityComun.filtroDown(btnFiltroCategoriaDown,btnFiltroCategoriaUp,layoutFiltroCategoria));

        // Handler to hide the filters for categories
        btnFiltroCategoriaUp.setOnClickListener(view ->
            EventsActivityComun.filtroUp(btnFiltroCategoriaDown,btnFiltroCategoriaUp,layoutFiltroCategoria)
        );

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

            EventsActivityComun.manageFiltrosOrder(layoutFiltroCategoria,categorias,rbOrdenarLejana,checkBoxSinFecha,
                    presenter,null,menuFiltros);

        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            EventsActivityComun.nuevaActivity(item, this);
            return false;

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
        EventsActivityComun.onLoadSucces(this, elementsLoaded);
    }

    @Override
    public void openEventDetails(Event event) {
        EventsActivityComun.openEventDetails(event,this);
    }

    @Override
    public void openInfoView() {
        EventsActivityComun.openInfoView(this);
    }

    @Override
    public void openFavouritesView() {
        Intent intent = new Intent(this, FavoriteEventsActivity.class);
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
            case R.id.crear_lista:
                lastDialog = Utilities.createListPopUp(this, "Introduzca el título de la lista a crear", 2);
                lastDialog.show();
                return true;
            case R.id.listaFav:
                presenter.onFavouritesClicked();
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

    @Override
    public IGestionarListasUsuario getSharedPref(){
        return sharedPref;
    }

    @Override
    public Dialog getLastDialog(){
        return lastDialog;
    }

    @Override
    public boolean isConectionAvailable() {
        if (Utilities.isConnected(this)) {
            return true;
        }
        return false;
    }

    @Override
    public void onConnectionError() {
         Utilities.createPopUp(this, Utilities.CONNECTION_ERROR_MESSAGE, 1).show();
    }

    @Override
    public void errorEventAlreadyExists() {
        Utilities.createPopUp(this, Utilities.ERROR_EVENT_ALREADY_EXISTS, 1).show();

    }

    @Override
    public void errorEventIndexOutOfBounds() {
        Utilities.createPopUp(this, Utilities.ERROR_EVENT_INDEX_OUT_OF_BOUNDS, 1).show();
    }
}