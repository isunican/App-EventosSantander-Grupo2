package com.isunican.eventossantander.view.favourites;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.isunican.eventossantander.model.Event;

import java.util.List;

public class GestionarFavoritosUsuario implements IGestionarFavoritos {

    private SharedPreferences sharedPref;
    private String idFavouriteEvents;

    // contexto y nombre
    public GestionarFavoritosUsuario(Context context) {
        sharedPref = context.getSharedPreferences("favourites", Context.MODE_PRIVATE);
    }

    // Devuelve un string con el id de los eventos favoritos, sino devuelve nulo
    @Override
    public String getFavourites() {
        String defaultValue = "";
        idFavouriteEvents = sharedPref.getString("favourites", defaultValue);
        return idFavouriteEvents;
    }

    // Coloca un evento en la lista de favoritos
    @Override
    public void setFavourite(int eventIndex, List<Event> cachedEvents) {
        idFavouriteEvents = getFavourites();
        SharedPreferences.Editor editor = sharedPref.edit();
        Log.i("setFavourite", "idFavouriteEvents : " + idFavouriteEvents);
        idFavouriteEvents = idFavouriteEvents.concat(cachedEvents.get(eventIndex).getIdentificador() + ",");

        editor.putString(idFavouriteEvents, "favourites");
        editor.apply();
    }

    @Override
    public boolean isFavourite(int eventId) {
        boolean result = false;
        Log.i("isFavourite", "eventId . " + eventId +" idFavouriteEvents : " + idFavouriteEvents);
        if (idFavouriteEvents != null) {
            result = idFavouriteEvents.contains(String.valueOf(eventId));
        }
        return result;
    }

}
