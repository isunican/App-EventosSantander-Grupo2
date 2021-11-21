package com.isunican.eventossantander.presenter;

import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.presenter.events.Options;
import com.isunican.eventossantander.presenter.events.Utilities;
import com.isunican.eventossantander.view.events.IEventsContract;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface PresenterComun extends IEventsContract.Presenter {

    /**
     * Filter the events by the selected categories
     * @param categorias map with the categories that want to be filtered and not
     * @return list with the events which category is selected in the filters
     */
    //Debe ser público debido a los tests
    public static List<Event> onApplyFilter(Map<String, Boolean> categorias, List<Event> cachedEvents){

        List<Event> filteredEvents = new ArrayList<>();

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


    public static void onEventClicked(int eventIndex, List<Event> cachedEvents, IEventsContract.View view) {
        if (cachedEvents != null && eventIndex < cachedEvents.size()) {
            Event event = cachedEvents.get(eventIndex);
            view.openEventDetails(event);
        }
    }


    public static void onFilterMenuClicked(boolean isFilterMenuVisible, IEventsContract.View view) {
        if (isFilterMenuVisible) {
            view.closeFilterMenuView();
        } else {
            view.openFilterMenuView();
        }
    }

    public static void onApplyOptions(Options options, List<Event> cachedEvents, IEventsContract.View view) {
        // Works with cachedEvents unless there are filters applies
        List<Event> eventList = cachedEvents;

        // Creates a filtered list if filters were selected
        if (options.isFilterChecked()) {
            eventList = PresenterComun.onApplyFilter(options.getFilterOptions(), cachedEvents);
        }

        // Orders the list if an order type was selected
        if (options.isOrderChecked()) {
            onApplyOrder(eventList, options.getOrderTypeOptions(), options.isDateFirst());
        }

        // Reloads the events with the filters & order applied
        view.onEventsLoaded(eventList);
        view.onLoadSuccess(eventList.size());
    }

    public static void onApplyOrder(List<Event> eventList, Utilities.OrderType type, boolean isDateFirst) {
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

    private static boolean nullOrEmpty(String text) {
        return (text == null || text.equals("") || text.trim().equals(""));
    }

    private static Date stringToDate(String strDate) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        String[] aux;

        aux = strDate.split(" ");   // Splits raw date string in different words
        // Cleans the date from the string (2nd word) and converts it to Date
        return simpledateformat.parse(aux[1].substring(0, aux[1].length() - 1), new ParsePosition(0));
    }

    private static int onApplyOrderWithoutDate(boolean fecha1IsNull, boolean fecha2IsNull, boolean isDateFirst) {
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
}
