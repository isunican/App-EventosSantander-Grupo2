package com.isunican.eventossantander.presenter.events;

import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.model.EventsRepository;
import com.isunican.eventossantander.view.Listener;
import com.isunican.eventossantander.view.events.IEventsContract;
import com.isunican.eventossantander.view.favourites.IGestionarFavoritos;

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
    private IGestionarFavoritos sharedPref;
    private List<Event> cachedEvents;
    private List<Event> favEvents;

    public EventsPresenter(IEventsContract.View view) {
        this.view = view;
        this.sharedPref = sharedPref;
        loadData();
    }

    private void loadData() {
        EventsRepository.getEvents(new Listener<>() {
            @Override
            public void onSuccess(List<Event> data) {
                // Orders events with default options:
                //   Dates closer to further & events without dates last.

                onApplyOrder(data, Utilities.OrderType.DATE_ASC, false);
                view.onEventsLoaded(data);
                view.onLoadSuccess(data.size());
                cachedEvents = data;
            }

            @Override
            public void onFailure() {
                if (!view.isConectionAvailable()) {
                    view.onConnectionError();
                } else {
                    view.onLoadError();
                }
                cachedEvents = null;
            }
        });
    }

    /**
     * Filter the events by the selected categories
     * @param categorias map with the categories that want to be filtered and not
     * @return list with the events which category is selected in the filters
     */
    public List<Event> onApplyFilter(Map<String, Boolean> categorias){

                List<Event> filteredEvents = new ArrayList();

                List<Event> listaEntera = cachedEvents;
                //If no filter is selected it finishes
                if (categorias.containsValue(true)) {
                    //For every events registered
                    for (Event e: listaEntera) {
                        //If the category of the current event exists and is selected by the user,
                        // the vents is added to the list of filtered events

                        if (categorias.containsKey(e.getCategoria()) && Boolean.TRUE.equals(categorias.get(e.getCategoria()))) { //Unboxed conversion. See: https://docs.oracle.com/javase/specs/jls/se8/html/jls-5.html#jls-5.1.8

                            filteredEvents.add(e);
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

    @Override
    public void onFavouritesClicked() {
        view.openFavouritesView();
    }

    @Override
    public void onFilterMenuClicked(boolean isFilterMenuVisible) {
        if (isFilterMenuVisible) {
            view.closeFilterMenuView();
        } else {
            view.openFilterMenuView();
        }
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
      private void onApplyOrder(List<Event> eventList, Utilities.OrderType type, boolean isDateFirst) {
        Collections.sort(eventList, (e1, e2) -> {
            int result;
            boolean fecha1IsNull = nullOrEmpty(e1.getFecha());
            boolean fecha2IsNull = nullOrEmpty(e2.getFecha());

            if (fecha1IsNull || fecha2IsNull) {         // One of the events does not have a date
                  result = onApplyOrderWithoutDate(fecha1IsNull, fecha2IsNull, isDateFirst);

            } else {                                    // Both events have dates
                Date date1 = stringToDate(e1.getFecha());
                Date date2 = stringToDate(e2.getFecha());
                if (type == Utilities.OrderType.DATE_ASC) {   // Show events closer to current date first
                    result = date1.compareTo(date2);
                } else {                            // Show further away from current date first
                    result = date2.compareTo(date1);
                }
            }
            return result;
        });
    }

    /**
     * Sets a sorting behaviour for ordering events when one or more of them
     * do not have dates established.
     * @param fecha1IsNull == true -> Event1 does not have a date.
     *                     == false-> Event1 has a date.
     * @param fecha2IsNull == true -> Event2 does not have a date.
     *                     == false-> Event2 has a date.
     * @param isDateFirst == true -> Events without dates have to be shown first on the list.
     *                    == false-> Events without dates have to be shown last on the list.
     * @return 1 if Event1 has to be shown last, -1 if Event1 has to be shown first and 0 if
     * both events are equal.
     */
     private int onApplyOrderWithoutDate(boolean fecha1IsNull, boolean fecha2IsNull, boolean isDateFirst) {
        int result = 0;

        // Shows events without dates last by default
        if (fecha1IsNull) {                 // Event1 does not have a date
            result = fecha2IsNull ? 0 : 1;
        } else if (fecha2IsNull) {          // Event1 has a date but Event2 doesn't
            result = -1;
        }

        // If 'Show events without dates first' was selected, the order is inverted
        if (isDateFirst) {
            result = result * -1;
        }

        return result;
    }

    @Override
    public void onFavouriteClicked(int eventIndex, Boolean isClicked, IGestionarFavoritos sharedPref) {
         if(eventIndex > 0 && eventIndex <= cachedEvents.size()) {
             // isClicked = true -> Quitar evento de favoritos
             // isClicked = false -> Anhadir evento a favoritos
             if (isClicked) {
                 // TODO
             } else {
                 sharedPref.setFavourite(eventIndex, cachedEvents);
             }
         }
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

    /**
     * FOR TESTING PURPOSES ONLY: Sets this presenter's list to the one passed as argument
     * @param events List of events given to the presenter.
     */
    public void setList(List<Event> events) {
        this.cachedEvents = events;
    }

    /**
     * FOR TESTING PURPOSES ONLY: Gets this presenter's list
     * @result this presenter's list of events.
     */
    public List<Event> getList() {
        return this.cachedEvents;
    }

    /**
     * FOR TESTING PURPOSES ONLY: Sets this presenter's list to the one passed as argument
     * @param events List of events given to the presenter.
     */
    public void setFavEventsList(List<Event> events) {
        this.favEvents = events;
    }

    /**
     * FOR TESTING PURPOSES ONLY: Gets this presenter's list
     * @result this presenter's list of events.
     */
    public List<Event> getFavEventsList() {
        return this.favEvents;
    }
}
