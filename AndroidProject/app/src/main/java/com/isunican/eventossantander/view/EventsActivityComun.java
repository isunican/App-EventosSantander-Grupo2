package com.isunican.eventossantander.view;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.isunican.eventossantander.R;
import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.presenter.events.Options;
import com.isunican.eventossantander.presenter.events.Utilities;
import com.isunican.eventossantander.view.events.EventsActivity;
import com.isunican.eventossantander.view.events.IEventsContract;
import com.isunican.eventossantander.view.eventsdetail.EventsDetailActivity;
import com.isunican.eventossantander.view.favourites.FavoriteEventsActivity;
import com.isunican.eventossantander.view.favourites.IFavoriteEventsContract;
import com.isunican.eventossantander.view.info.InfoActivity;

import java.util.HashMap;
import java.util.Map;

public interface EventsActivityComun {

    public static void openInfoView( Context c) {
        Intent intent = new Intent(c, InfoActivity.class);
        c.startActivity(intent);
    }

    public static void openEventDetails(Event event, Context c) {
        Intent intent = new Intent(c, EventsDetailActivity.class);
        intent.putExtra(EventsDetailActivity.INTENT_EVENT, event);
        c.startActivity(intent);
    }

    public static void onLoadSucces(Context c, int elementsLoaded){
        String text = String.format("Loaded %d events", elementsLoaded);
        Toast.makeText(c, text, Toast.LENGTH_SHORT).show();
    }

    public static void nuevaActivity(MenuItem item, Context c){
        if (item.getItemId() == R.id.inicioActivity) {
            Intent intent1 = new Intent(c, EventsActivity.class);
            c.startActivity(intent1);
        }
        else if (item.getItemId() == R.id.favoritosActivity) {
                Intent intent2 = new Intent(c, FavoriteEventsActivity.class);
                c.startActivity(intent2);
        }
    }

    public static void manageFiltrosOrder(LinearLayout layoutFiltroCategoria, Map<String, Boolean> categorias,
                                          RadioButton rbOrdenarLejana, CheckBox checkBoxSinFecha, IEventsContract.Presenter presenterEvent,
                                          IFavoriteEventsContract.Presenter presenterFav, NavigationView menuFiltros){
        int posi = layoutFiltroCategoria.getChildCount();
        for (int i = 0; i < posi; i++) {
            View viewAux = layoutFiltroCategoria.getChildAt(i);
            if (viewAux instanceof CheckBox) {
                categorias.put(((CheckBox) viewAux).getText().toString(), ((CheckBox) viewAux).isChecked());
            }
        }

        // Check order type selected
        Utilities.OrderType orderType = Utilities.OrderType.DATE_ASC;   // 'Show events closer to current date' selected by default
        if (rbOrdenarLejana.isChecked()) {
            orderType = Utilities.OrderType.DATE_DESC;    // Further away from current date
        }
        boolean isDateFirst = false;    // Events without a date are shown last by default
        if (!checkBoxSinFecha.isChecked()) {
            isDateFirst = true;                                 // Events without date first
        }

        // Apply the filters & order selected
        if(presenterEvent == null){
            presenterFav.onApplyOptions(new Options(categorias, orderType, isDateFirst));
        }else{
            presenterEvent.onApplyOptions(new Options(categorias, orderType, isDateFirst));

        }
        menuFiltros.setVisibility(View.GONE);   // Closes the menu
    }

    public static Map<String, Boolean> categorias(LinearLayout layoutFiltroCategoria){
        Map<String, Boolean> categorias = new HashMap<>();
        int pos = layoutFiltroCategoria.getChildCount();
        for (int i = 0; i < pos; i++) {
            View viewAux = layoutFiltroCategoria.getChildAt(i);

            categorias.put(((CheckBox) viewAux).getText().toString(), false);
        }
        return categorias;
    }

    public static void filtroUp(ImageButton btnFiltroCategoriaDown,ImageButton btnFiltroCategoriaUp,LinearLayout layoutFiltroCategoria){
        btnFiltroCategoriaDown.setVisibility(View.VISIBLE);
        btnFiltroCategoriaUp.setVisibility(View.GONE);
        layoutFiltroCategoria.setVisibility(View.GONE);
    }
    public static void filtroDown(ImageButton btnFiltroCategoriaDown,ImageButton btnFiltroCategoriaUp,LinearLayout layoutFiltroCategoria){
        btnFiltroCategoriaDown.setVisibility(View.GONE);
        btnFiltroCategoriaUp.setVisibility(View.VISIBLE);
        layoutFiltroCategoria.setVisibility(View.VISIBLE);
    }
}
