package com.isunican.eventossantander.presenter.events;

import android.widget.ListView;

import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.model.EventsRepository;
import com.isunican.eventossantander.view.Listener;
import com.isunican.eventossantander.view.events.IEventsContract;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class EventsPresenter implements IEventsContract.Presenter {

    private final IEventsContract.View view;
    private List<Event> cachedEvents;
    public enum OrderType { DATE_ASC, DATE_DESC }

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

    /**
     * Takes the Filter & Order options selected from the menu, applies
     * them to the events list and reloads it.
     * @param options Filter & Order options selected from the menu.
     */
    @Override
    public void onApplyOptions(Options options) {
        // Works with cachedEvents unless there are filters applies
        List<Event> eventList = cachedEvents;

        // Creates a filtered list if filters were selected
        if (options.isFilterChecked()) {
            eventList = onApplyFilter(options.getFilterOptions());
        }

        // Orders the list if an order type was selected
        if (options.isOrderChecked()) {
            onApplyOrder(eventList, options.getOrderTypeOptions(), options.isDateFirst());
        }

        // Reloads the events with the filters & order applied
        view.onEventsLoaded(eventList);
        view.onLoadSuccess(eventList.size());
    }

    /**
     * Takes the event list and order options and orders the list according to them.
     * @param eventList Event list to be ordered.
     * @param type Order type selected.
     * @param isDateFirst == true -> Show events without dates first in the list.
     *                    == false-> Show events without dates last in the list.
     */
    private void onApplyOrder(List<Event> eventList, OrderType type, boolean isDateFirst) {
    }

    private int onApplyOrderWithoutDate(boolean fecha1IsNull, boolean fecha2IsNull, boolean isDateFirst) {
        return -1;
    }
    /**
     * Takes the string date from an Event and converts it to data type Date.
     * @param strDate An Events date in data type String.
     * @return the Events date in data type Date.
     */
    private Date stringToDate(String strDate) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        String[] aux;

        aux = strDate.split(" ");   // Splits raw date string in different words
        // Cleans the date from the string (2nd word) and converts it to Date
        return simpledateformat.parse(aux[1].substring(0, aux[1].length() - 1), new ParsePosition(0));
    }
    /**
     * Aux method that checks if a string is null or empty.
     * @param text Text that has to be checked.
     * @return true if null or empty, false otherwise.
     */
    private boolean nullOrEmpty(String text) {
        return (text == null || text.equals("") || text.trim().equals(""));
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

    /**
     * FOR TESTING PURPOSES ONLY: Sets this presenter's list to the one passed as argument
     * @param events List of events given to the presenter.
     */
    @Override
    public void setList(List<Event> events) {
        this.cachedEvents = events;
    }

    /**
     * FOR TESTING PURPOSES ONLY: Gets this presenter's list
     * @result this presenter's list of events.
     */
    @Override
    public List<Event> getList() {
        return this.cachedEvents;
    }

}
