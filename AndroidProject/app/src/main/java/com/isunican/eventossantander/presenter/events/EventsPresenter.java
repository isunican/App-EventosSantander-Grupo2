package com.isunican.eventossantander.presenter.events;

import android.widget.ListView;

import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.model.EventsRepository;
import com.isunican.eventossantander.view.Listener;
import com.isunican.eventossantander.view.events.IEventsContract;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EventsPresenter implements IEventsContract.Presenter {

    private final IEventsContract.View view;
    private List<Event> cachedEvents;

    public EventsPresenter(IEventsContract.View view) {
        this.view = view;
        loadData();
    }

    private void loadData() {
        EventsRepository.getEvents(new Listener<List<Event>>() {
            @Override
            public void onSuccess(List<Event> data) {
                view.onEventsLoaded(data);
                view.onLoadSuccess(data.size());
                cachedEvents = data;
            }

            @Override
            public void onFailure() {
                view.onLoadError();
                cachedEvents = null;
            }
        });
    }

    public List<Event> onApplyFilter(Map<String, Boolean> categorias){

                List<Event> filteredEvents = new ArrayList<Event>();
                List<Event> listaEntera = cachedEvents;
                if (categorias.containsValue(true)) {
                    for (Event e: listaEntera) {
                        //Si la categoria de ese evento esta registrada y es true en el mapa,
                        // se añade el evento a la lista
                        if (categorias.containsKey(e.getCategoria())) {
                            if (categorias.get(e.getCategoria())) {
                                filteredEvents.add(e);
                            }
                        }
                    }
                } else {
                    filteredEvents = listaEntera;
                }
                return filteredEvents;
    }

    @Override
    public void onEventClicked(int eventIndex) {
        if (cachedEvents != null && eventIndex < cachedEvents.size()) {
            Event event = cachedEvents.get(eventIndex);
            view.openEventDetails(event);
        }
    }

    @Override
    public void onReloadClicked() {
        loadData();
    }

    @Override
    public void onInfoClicked() {
        view.openInfoView();
    }
}
