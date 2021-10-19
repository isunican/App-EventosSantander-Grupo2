package com.isunican.eventossantander.view.events;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentProviderClient;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.isunican.eventossantander.R;
import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.presenter.events.EventsPresenter;
import com.isunican.eventossantander.presenter.events.Options;
import com.isunican.eventossantander.view.eventsdetail.EventsDetailActivity;
import com.isunican.eventossantander.view.info.InfoActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class EventsActivity extends AppCompatActivity implements IEventsContract.View {

    private IEventsContract.Presenter presenter;
    private Button btnAplicarFiltroOrden;
    private CheckBox checkBoxMusica;
    private CheckBox checkBoxOnline;
    private CheckBox checkBoxArtesPlasticas;
    private CheckBox checkBoxFormacionTalleres;
    private CheckBox checkBoxArquitectura;
    private CheckBox checkBoxInfantil;
    private CheckBox checkBoxCineAudiovisual;
    private CheckBox checkBoxArtesEscenicas;
    private CheckBox checkBoxCulturaCientifica;
    private CheckBox checkBoxEdicionLiteratura;
    private CheckBox checkBoxFotografia;
    private CheckBox checkBoxOtros;
    private ImageButton btnFiltroCategoriaDown;
    private ImageButton btnFiltroCategoriaUp;
    private LinearLayout layoutFiltroCategoria;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new EventsPresenter(this);
        NavigationView menuFiltros = findViewById(R.id.menu_filtros);
        ListView listaEventos = findViewById(R.id.eventsListView);
        btnAplicarFiltroOrden = findViewById(R.id.btnAplicarFiltroOrden);
        checkBoxMusica = findViewById(R.id.checkBoxMusica);
        checkBoxOnline = findViewById(R.id.checkBoxOnline);
        checkBoxArtesPlasticas = findViewById(R.id.checkBoxArtesPlasticas);
        checkBoxFormacionTalleres = findViewById(R.id.checkBoxFormacionTalleres);
        checkBoxArquitectura = findViewById(R.id.checkBoxArquitectura);
        checkBoxInfantil = findViewById(R.id.checkBoxInfantil);
        checkBoxCineAudiovisual = findViewById(R.id.checkBoxCineAudiovisual);
        checkBoxArtesEscenicas = findViewById(R.id.checkBoxArtesEscenicas);
        checkBoxCulturaCientifica = findViewById(R.id.checkBoxCulturaCientifica);
        checkBoxEdicionLiteratura = findViewById(R.id.checkBoxEdicionLiteratura);
        checkBoxFotografia = findViewById(R.id.checkBoxFotografia);
        checkBoxOtros = findViewById(R.id.checkBoxOtros);
        btnFiltroCategoriaUp =findViewById(R.id.btnFiltroCategoriaUp);
        btnFiltroCategoriaDown =findViewById(R.id.btnFiltroCategoriaDown);
        layoutFiltroCategoria = findViewById(R.id.layoutFiltroCategoria);
        btnFiltroCategoriaUp.setVisibility(View.GONE);
        layoutFiltroCategoria.setVisibility(View.GONE);

        menuFiltros.setVisibility(View.VISIBLE); //Para las pruebas de Interfaz de Usuario

        /**
         * Manejador para mostrar los filtros por categoria
         */
        btnFiltroCategoriaDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnFiltroCategoriaDown.setVisibility(View.GONE);
                btnFiltroCategoriaUp.setVisibility(View.VISIBLE);
                layoutFiltroCategoria.setVisibility(View.VISIBLE);
            }
        });

        /**
         * Manejador para ocultar los filtros por categoria
         */
        btnFiltroCategoriaUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnFiltroCategoriaDown.setVisibility(View.VISIBLE);
                btnFiltroCategoriaUp.setVisibility(View.GONE);
                layoutFiltroCategoria.setVisibility(View.GONE);
            }
        });


        /**
         * Manejador para controlar los eventos de deslizar el dedo por la pantalla (arriba, abajo, izq, der)
         */
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

        /**
         * Manejador para aplicar los filtros y ordenacion
         */
        btnAplicarFiltroOrden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton rbOrdenarLejana = findViewById(R.id.rbOrdenarLejana);
                CheckBox checkBoxSinFecha = findViewById(R.id.checkBoxSinFecha);

                Map<String, Boolean> categorias = new HashMap<String, Boolean>();
                List<Event>filteredEvents = new ArrayList<Event>();

                if (checkBoxMusica.isChecked()) {
                    categorias.put("Música", true); //Añade un elemento al Map
                }
                else{
                    categorias.put("Música", false); // Añade un elemento al Map
                }
                if (checkBoxOnline.isChecked()) {
                    categorias.put("Online", true); //Añade un elemento al Map
                }
                else{
                    categorias.put("Online", false); // Añade un elemento al Map
                }
                if (checkBoxArtesPlasticas.isChecked()) {
                    categorias.put("Artes plásticas", true); //Añade un elemento al Map
                }
                else{
                    categorias.put("Artes plásticas", false); // Añade un elemento al Map
                }
                if (checkBoxFormacionTalleres.isChecked()) {
                    categorias.put("Formación/Talleres", true); //Añade un elemento al Map
                }
                else{
                    categorias.put("Formación/Talleres", false); // Añade un elemento al Map
                }
                if (checkBoxArquitectura.isChecked()) {
                    categorias.put("Arquitectura", true); //Añade un elemento al Map
                }
                else{
                    categorias.put("Arquitectura", false); // Añade un elemento al Map
                }
                if (checkBoxInfantil.isChecked()) {
                    categorias.put("Infantil", true); //Añade un elemento al Map
                }
                else{
                    categorias.put("Infantil", false); // Añade un elemento al Map
                }
                if (checkBoxCineAudiovisual.isChecked()) {
                    categorias.put("Cine/Audiovisual", true); //Añade un elemento al Map
                }
                else{
                    categorias.put("Cine/Audiovisual", false); // Añade un elemento al Map
                }
                if (checkBoxArtesEscenicas.isChecked()) {
                    categorias.put("Artes escénicas", true); //Añade un elemento al Map
                }
                else{
                    categorias.put("Artes escénicas", false); // Añade un elemento al Map
                }
                if (checkBoxCulturaCientifica.isChecked()) {
                    categorias.put("Cultura científica", true); //Añade un elemento al Map
                }
                else{
                    categorias.put("Cultura científica", false); // Añade un elemento al Map
                }
                if (checkBoxEdicionLiteratura.isChecked()) {
                    categorias.put("Edición/Literatura", true); //Añade un elemento al Map
                }
                else{
                    categorias.put("Edición/Literatura", false); // Añade un elemento al Map
                }
                if (checkBoxFotografia.isChecked()) {
                    categorias.put("Fotografía", true); //Añade un elemento al Map
                }
                else{
                    categorias.put("Fotografía", false); // Añade un elemento al Map
                }
                if (checkBoxOtros.isChecked()) {
                    categorias.put("Otros", true); //Añade un elemento al Map
                }
                else{
                    categorias.put("Otros", false); // Añade un elemento al Map
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