package com.isunican.eventossantander.presenter.events;

import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.model.EventsRepository;
import com.isunican.eventossantander.presenter.PresenterComun;
import com.isunican.eventossantander.view.Listener;
import com.isunican.eventossantander.view.events.IEventsContract;
import com.isunican.eventossantander.view.favourites.IGestionarListasUsuario;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class EventsPresenter implements IEventsContract.Presenter {

    private final IEventsContract.View view;
    private List<Event> cachedEvents;
    private List<Event> favEvents;

    public EventsPresenter(IEventsContract.View view) {
        this.view = view;
        loadData();
    }

    private void loadData() {
        EventsRepository.getEvents(new Listener<>() {
            @Override
            public void onSuccess(List<Event> data) {
                // Orders events with default options:
                //  Dates closer to further & events without dates last.

                PresenterComun.onApplyOrder(data, Utilities.OrderType.DATE_ASC, false);
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
    //Debe ser público debido a los tests
    public List<Event> onApplyFilter(Map<String, Boolean> categorias){

        return PresenterComun.onApplyFilter(categorias, cachedEvents);
    }


    @Override
    public void onEventClicked(int eventIndex) {
        PresenterComun.onEventClicked(eventIndex, cachedEvents, view);
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
        PresenterComun.onFilterMenuClicked(isFilterMenuVisible, view);
    }

    /**
     * Takes the Filter & Order options selected from the menu, applies
     * them to the events list and reloads it.
     * @param options Filter & Order options selected from the menu.
     */
    @Override
    public void onApplyOptions(Options options) {
        PresenterComun.onApplyOptions(options, cachedEvents, view);
    }

    /**
     * Takes the event list and order options and orders the list according to them.
     * @param eventList Event list to be ordered.
     * @param type Order type selected.
     * @param isDateFirst == true -> Show events without dates first in the list.
     *                    == false-> Show events without dates last in the list.
     */
      private void onApplyOrder() {
        //No hace nada pq lo hace la comun
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
    public void onFavouriteClicked(int eventIndex, Boolean isClicked, IGestionarListasUsuario sharedPref) {
         if(eventIndex >= 0 && eventIndex < cachedEvents.size() && !isClicked) {
                 sharedPref.setFavourite(eventIndex, cachedEvents);
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
