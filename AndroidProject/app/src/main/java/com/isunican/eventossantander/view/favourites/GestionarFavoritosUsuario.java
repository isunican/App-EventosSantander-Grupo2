package com.isunican.eventossantander.view.favourites;

import android.content.Context;
import android.content.SharedPreferences;

import com.isunican.eventossantander.model.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GestionarFavoritosUsuario implements IGestionarFavoritos {

    private SharedPreferences sharedPref;
    private String idFavouriteEvents;
    private final String FAVORITOS = "favourites";

    // contexto y nombre
    public GestionarFavoritosUsuario(Context context) {
        sharedPref = context.getSharedPreferences(FAVORITOS, Context.MODE_PRIVATE);
    }

    // Devuelve un string con el id de los eventos favoritos, sino devuelve nulo
    @Override
    public String getFavourites() {
        String defaultValue = "";
        idFavouriteEvents = sharedPref.getString(FAVORITOS, defaultValue);
        return idFavouriteEvents;
    }

    // Coloca un evento en la lista de favoritos
    @Override
    public void setFavourite(int eventIndex, List<Event> cachedEvents) {
        idFavouriteEvents = getFavourites();
        SharedPreferences.Editor editor = sharedPref.edit();

        idFavouriteEvents = idFavouriteEvents.concat(cachedEvents.get(eventIndex).getIdentificador() + ",");

        editor.putString(FAVORITOS, idFavouriteEvents);
        editor.apply();
    }

    @Override
    public boolean isFavourite(int eventId) {
        boolean result = false;
        if (idFavouriteEvents != null) {
            result = idFavouriteEvents.contains(String.valueOf(eventId));
        }
        return result;
    }

    @Override
    public ArrayList<String> getLists() {
        ArrayList<String> listas = new ArrayList<>();

        for (Map.Entry<String, ?> pair : sharedPref.getAll().entrySet()) {
            //Despues del merge con la opcion de crear listas descomentar
           // if (!pair.getKey().equals(FAVORITOS)){
                listas.add(pair.getKey());
           // }
        }
        return listas;
    }

    @Override
    public boolean addEvent(int eventIndex, List<Event> cachedEvents, String listaEscogida) {
        boolean estaEnLista;
        String defaultValue = "";
        String eventosLista = sharedPref.getString(listaEscogida, defaultValue);
        if (eventosLista != null) {
            estaEnLista = eventosLista.contains(String.valueOf(eventIndex));
            if(estaEnLista){
                SharedPreferences.Editor editor = sharedPref.edit();
                eventosLista = eventosLista.concat(cachedEvents.get(eventIndex).getIdentificador() + ",");
                editor.putString(listaEscogida, eventosLista);
                editor.apply();
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    @Override
    public void removeFavourite(int eventId, List<Event> cachedEvents) {
        // TODO
    }
}
