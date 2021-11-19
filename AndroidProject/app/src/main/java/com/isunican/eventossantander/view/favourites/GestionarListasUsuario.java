package com.isunican.eventossantander.view.favourites;

import android.content.Context;
import android.content.SharedPreferences;

import com.isunican.eventossantander.model.Event;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GestionarListasUsuario implements IGestionarListasUsuario {

    private SharedPreferences sharedPref;
    private Context context;
    private String idFavouriteEvents;
    private static final String FAVORITOS = "favourites";
    private static final String LISTAS = "LISTS";


    // contexto y nombre
    public GestionarListasUsuario(Context context) {
        sharedPref = context.getSharedPreferences(LISTAS, Context.MODE_PRIVATE);
        this.context = context;
    }

    // Devuelve un string con el id de los eventos favoritos, sino devuelve nulo
    @Override
    public String createList(String listName) {
        String name = listName;
        String[] noParenthesis = new String[50];
        noParenthesis[0] = listName;
        int numberLists = -1;
        if (listName != null) {
            if (!listName.isEmpty()) {
                if (name.contains("(")) {
                    noParenthesis = listName.split("\\(");
                }
                sharedPref = context.getSharedPreferences(LISTAS, Context.MODE_PRIVATE);
                //Si la lista cumpla un patrón (paréntesis con número) ese patrón se quita.
                if (sharedPref.contains(noParenthesis[0])) { //Se comprueba si el nombre sin nada está en el mapa
                    numberLists = sharedPref.getInt(noParenthesis[0], -1);
                    name = noParenthesis[0] + "(" + (numberLists + 1) + ")";
                }
                //Se mete el nombre en los sharedPref y se añade o suma una instancia en el mapa
                sharedPref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(name, "");
                editor.apply();
                sharedPref = context.getSharedPreferences(LISTAS, Context.MODE_PRIVATE);
                editor = sharedPref.edit();
                editor.putInt(name, -1);
                editor.putInt(noParenthesis[0], numberLists + 1);
                editor.apply();
            }
        }
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
        sharedPref = context.getSharedPreferences(FAVORITOS, Context.MODE_PRIVATE);
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
        idFavouriteEvents = getFavourites();
        if (idFavouriteEvents != null) {
            result = idFavouriteEvents.contains(String.valueOf(eventId));
        }
        return result;
    }

    @Override
    public ArrayList<String> getLists() {
        sharedPref = context.getSharedPreferences(LISTAS, Context.MODE_PRIVATE);
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
