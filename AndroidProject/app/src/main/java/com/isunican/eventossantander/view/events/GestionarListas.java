package com.isunican.eventossantander.view.events;

import android.content.Context;
import android.content.SharedPreferences;

import com.isunican.eventossantander.model.Event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class GestionarListas implements IGestionarListas {

    private SharedPreferences sharedPref;
    private Context context;
    private Map<String, String> nombresListas;

    // contexto y nombre
    public GestionarListas(Context context) {
        this.context = context;
        nombresListas = new HashMap<>();
    }

    // Devuelve un string con el id de los eventos favoritos, sino devuelve nulo
    @Override
    public String createList(String listName) {
        //Crear un patrón para buscar (/d) donde /d cualquier digito o serie de digitos
        String name = listName;
        sharedPref = context.getSharedPreferences("LISTS", Context.MODE_PRIVATE);
        if(sharedPref.contains(listName)) {
            name = nombresListas.get(listName);
            //Si el nombre cumple el patrón, se le suma uno al número de dentro
        }
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(name, "");
        editor.apply();
        editor = context.getSharedPreferences("LISTS", Context.MODE_PRIVATE).edit();
        editor.putString(name, name);
        editor.apply();
        nombresListas.put(listName, name);
        return name;
    }

    @Override
    public boolean checkListExists(String listName) {
        sharedPref = context.getSharedPreferences("LISTS", Context.MODE_PRIVATE);
        return sharedPref.contains(listName);
    }

}
