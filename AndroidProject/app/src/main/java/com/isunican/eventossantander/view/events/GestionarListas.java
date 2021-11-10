package com.isunican.eventossantander.view.events;

import android.content.Context;
import android.content.SharedPreferences;

import com.isunican.eventossantander.model.Event;

import java.util.List;

public class GestionarListas implements IGestionarListas {

    private SharedPreferences sharedPref;
    private Context context;

    // contexto y nombre
    public GestionarListas(Context context) {
        this.context = context;
    }

    // Devuelve un string con el id de los eventos favoritos, sino devuelve nulo
    @Override
    public boolean createList(String listName) {
        Boolean newList = true;
        sharedPref = context.getSharedPreferences(listName, Context.MODE_PRIVATE);
        if(sharedPref.contains(listName)) {
            newList = false;
        }
        else {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(listName, "");
            editor.apply();
            editor = context.getSharedPreferences("LISTS", Context.MODE_PRIVATE).edit();
            editor.putString(listName, listName);
            editor.apply();
        }
        return newList;
    }

    @Override
    public boolean checkListExists(String listName) {
        sharedPref = context.getSharedPreferences("LISTS", Context.MODE_PRIVATE);
        return sharedPref.contains(listName);
    }

}
