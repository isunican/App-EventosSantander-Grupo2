package com.isunican.eventossantander.view.favourites;

import android.content.Context;
import android.content.SharedPreferences;

import com.isunican.eventossantander.model.Event;

import java.util.List;

public class UserSharedPreferences implements ISharedPreferences {

    private SharedPreferences sharedPref;
    private String idFavouriteEvents;

    public UserSharedPreferences(Context context) {
        sharedPref = context.getSharedPreferences("favourites", Context.MODE_PRIVATE);
    }

    // Devuelve un string con el id de los eventos favoritos, sino devuelve nulo
    @Override
    public String getFavourites() {
        String defaultValue = null;
        idFavouriteEvents = sharedPref.getString("favourites", defaultValue);
        return idFavouriteEvents;
    }

    // Coloca un evento en la lista de favoritos
    @Override
    public void setFavourite(int eventIndex, List<Event> cachedEvents) {
        idFavouriteEvents = getFavourites();
        SharedPreferences.Editor editor = sharedPref.edit();

        idFavouriteEvents = idFavouriteEvents + cachedEvents.get(eventIndex).getIdentificador() + ",";

        editor.putString(idFavouriteEvents, "favourites");
        editor.apply();
    }
}
