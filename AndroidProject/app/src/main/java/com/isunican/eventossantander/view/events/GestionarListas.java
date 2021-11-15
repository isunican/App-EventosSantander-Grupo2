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

    // contexto y nombre
    public GestionarListas(Context context) {
        this.context = context;
    }

    // Devuelve un string con el id de los eventos favoritos, sino devuelve nulo
    @Override
    public String createList(String listName) {
        String name = listName;
        String[] noParenthesis = new String[50];
        noParenthesis[0] = listName;
        int numberLists = -1;

        if(name.contains("(")) {
            noParenthesis = listName.split("\\(");
        }
        sharedPref = context.getSharedPreferences("LISTS", Context.MODE_PRIVATE);
        //Si la lista cumpla un patrón (paréntesis con número) ese patrón se quita.
        if(sharedPref.contains(noParenthesis[0])) { //Se comprueba si el nombre sin nada está en el mapa
            numberLists = sharedPref.getInt(noParenthesis[0], -1);
            name = noParenthesis[0] + "(" + (numberLists + 1) + ")";
        }
        //Se mete el nombre en los sharedPref y se añade o suma una instancia en el mapa
        sharedPref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(name, "");
        editor.apply();
        sharedPref = context.getSharedPreferences("LISTS", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putInt(name, -1);
        editor.putInt(noParenthesis[0], numberLists + 1);
        editor.apply();
        return name;
    }

    @Override
    public boolean checkListExists(String listName) {
        sharedPref = context.getSharedPreferences("LISTS", Context.MODE_PRIVATE);
        return sharedPref.contains(listName);
    }

}
