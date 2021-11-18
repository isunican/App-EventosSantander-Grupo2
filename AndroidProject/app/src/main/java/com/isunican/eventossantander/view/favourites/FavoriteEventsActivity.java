package com.isunican.eventossantander.view.favourites;

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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.isunican.eventossantander.R;
import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.presenter.events.Options;
import com.isunican.eventossantander.presenter.events.Utilities;
import com.isunican.eventossantander.presenter.favourites.FavoriteEventsPresenter;
import com.isunican.eventossantander.view.EventsActivityComun;
import com.isunican.eventossantander.view.events.EventsActivity;
import com.isunican.eventossantander.view.events.IEventsContract;
import com.isunican.eventossantander.view.events.OnSwipeTouchListener;
import com.isunican.eventossantander.view.eventsdetail.EventsDetailActivity;
import com.isunican.eventossantander.view.info.InfoActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavoriteEventsActivity extends AppCompatActivity implements IEventsContract.View {

    private IFavoriteEventsContract.Presenter presenter;
    private IGestionarListasUsuario sharedPref;
    private boolean isFilterMenuVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPref = new GestionarListasUsuario(this);
        presenter = new FavoriteEventsPresenter( this);

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
        btnFiltroCategoriaDown.setOnClickListener(view -> {
            EventsActivityComun.filtroDown(btnFiltroCategoriaDown,btnFiltroCategoriaUp,layoutFiltroCategoria);
        });

        // Handler to hide the filters for categories
        btnFiltroCategoriaUp.setOnClickListener(view -> {
            EventsActivityComun.filtroUp(btnFiltroCategoriaDown,btnFiltroCategoriaUp,layoutFiltroCategoria);
        });

        // Handler to control the events of sliding the finger (up, down, right, left)
        listaEventos.setOnTouchListener(new OnSwipeTouchListener(FavoriteEventsActivity.this) {
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
                    null,presenter,menuFiltros);


        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            EventsActivityComun.nuevaActivity(item, this);
            return false;
        });
    }


    @Override
    public void onEventsLoaded(List<Event> events) {
        FavoriteEventsArrayAdapter adapter = new FavoriteEventsArrayAdapter(FavoriteEventsActivity.this, 0, events);
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
        //No se ha realizado esta implementacion todavia
    }

    @Override
    public boolean isConectionAvailable() {
        return Utilities.isConnected(this);
    }

    @Override
    public void onConnectionError() {
        Utilities.createPopUp(this, Utilities.CONNECTION_ERROR_MESSAGE, 1).show();
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

    public IFavoriteEventsContract.Presenter getPresenter() {
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

    @Override
    public IGestionarListasUsuario getSharedPref(){
        return sharedPref;
    }

    @Override
    public void showEmptyListMessage() {
        Utilities.createPopUp(this, Utilities.EMPTY_FAVOURITE_MESSAGE, 1).show();
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