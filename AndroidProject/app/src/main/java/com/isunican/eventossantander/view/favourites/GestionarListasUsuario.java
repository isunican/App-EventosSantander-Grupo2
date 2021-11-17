package com.isunican.eventossantander.view.favourites;

import android.content.Context;
import android.content.SharedPreferences;

import com.isunican.eventossantander.model.Event;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestionarListasUsuario implements IGestionarListasUsuario {

    private SharedPreferences sharedPref;
    private Context context;
    private Map<String, String> nombresListas;
    private String idFavouriteEvents;
    private static final String FAVORITOS = "favourites";
    private static final String LISTAS = "LISTS";


    // contexto y nombre
    public GestionarListasUsuario(Context context) {
        sharedPref = context.getSharedPreferences(LISTAS, Context.MODE_PRIVATE);
        this.context = context;
        nombresListas = new HashMap<>();
    }

    // Devuelve un string con el id de los eventos favoritos, sino devuelve nulo
    @Override
    public String createList(String listName) {
        //Crear un patrón para buscar (/d) donde /d cualquier digito o serie de digitos
        String name = listName;
        sharedPref = context.getSharedPreferences(LISTAS, Context.MODE_PRIVATE);
        if(sharedPref.contains(listName)) {
            name = nombresListas.get(listName);
            //Si el nombre cumple el patrón, se le suma uno al número de dentro
        }
        sharedPref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(name, "");
        editor.apply();
        editor = context.getSharedPreferences(LISTAS, Context.MODE_PRIVATE).edit();
        editor.putString(name, name);
        editor.apply();
        nombresListas.put(listName, name);
        return name;
    }

    @Override
    public boolean checkListExists(String listName) {
        sharedPref = context.getSharedPreferences(LISTAS, Context.MODE_PRIVATE);
        return sharedPref.contains(listName);
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
        sharedPref = context.getSharedPreferences(FAVORITOS, Context.MODE_PRIVATE);
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
            if (!pair.getKey().equals(FAVORITOS)){
                listas.add(pair.getKey());
            }
        }
        return listas;
    }

    @Override
    public String getEventsList(String listName){
        if (!checkListExists(listName)) {
            return null;
        }
        sharedPref = context.getSharedPreferences(listName, Context.MODE_PRIVATE);
        String defaultValue = "";
        idFavouriteEvents = sharedPref.getString(listName, defaultValue);
        return idFavouriteEvents;
    }

    @Override
    public boolean addEvent(int eventIndex, List<Event> cachedEvents, String listaEscogida) {
        boolean estaEnLista;
        String defaultValue = "";
        sharedPref = context.getSharedPreferences(listaEscogida, Context.MODE_PRIVATE);
        String eventosLista = sharedPref.getString(listaEscogida, defaultValue);
        estaEnLista = eventosLista.contains(String.valueOf(cachedEvents.get(eventIndex).getIdentificador()));
        if(!estaEnLista || StringUtils.isBlank(eventosLista)){
            SharedPreferences.Editor editor = sharedPref.edit();
            eventosLista = eventosLista.concat(cachedEvents.get(eventIndex).getIdentificador() + ",");
            editor.putString(listaEscogida, eventosLista);
            editor.apply();
            return true;
        }else{
            return false;
        }

    }

    @Override
    public void removeFavourite(int eventId, List<Event> cachedEvents) {
        // TODO
    }

    public static void cleanSetPreferences(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(LISTAS, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPref.edit();
        edit.clear();
        edit.commit();
    }
}
