package com.isunican.eventossantander.view.favourites;


import com.isunican.eventossantander.model.Event;

import java.util.ArrayList;
import java.util.List;

public interface IGestionarListasUsuario {

    String getFavourites();

    void setFavourite(int eventIndex, List<Event> cachedEvents);

    void removeFavourite(int eventId, List<Event> cachedEvents);

    boolean isFavourite(int eventId);

    ArrayList<String> getLists();

    String getEventsList(String listName);

    boolean addEvent(int eventIndex, List<Event> cachedEvents, String listaEscogida);

    String createList(String listName);

    boolean checkListExists(String listName);
}
