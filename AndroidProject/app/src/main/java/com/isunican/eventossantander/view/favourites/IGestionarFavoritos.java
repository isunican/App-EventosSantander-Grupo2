package com.isunican.eventossantander.view.favourites;

import com.isunican.eventossantander.model.Event;

import java.util.List;

public interface IGestionarFavoritos {

    String getFavourites();

    void setFavourite(int eventIndex, List<Event> cachedEvents);

    boolean isFavourite(int eventId);

}
