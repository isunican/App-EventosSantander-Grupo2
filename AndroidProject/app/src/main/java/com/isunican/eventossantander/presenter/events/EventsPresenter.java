package com.isunican.eventossantander.presenter.events;

import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.model.EventsRepository;
import com.isunican.eventossantander.view.Listener;
import com.isunican.eventossantander.view.events.IEventsContract;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventsPresenter implements IEventsContract.Presenter {

    private final IEventsContract.View view;
    private List<Event> cachedEvents;
    public enum OrderType { DATE_ASC, DATE_DESC, NO_DATE_FIRST }

    public EventsPresenter(IEventsContract.View view) {
        this.view = view;
        loadData();
    }

    private void loadData() {
        EventsRepository.getEvents(new Listener<>() {
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
    public void onApplyOrder(OrderType type, List<Event> eventList) {
        List<Event> lista = cachedEvents;
        if (eventList != null) {
            lista = eventList;
        }

        switch (type) {
            case DATE_ASC:
                applyOrderDate(lista, true);   // Closer to current date
                break;
            case DATE_DESC:
                applyOrderDate(lista, false);  // Further away from current date
                break;
            case NO_DATE_FIRST:
                applyOrderWithoutDate(lista);               // Events without date first
                break;
            default:
                break;
        }
    }

    /**
     * Orders the event list based on event dates.
     * @param events Event list to be ordered.
     * @param closerToFurther == true -> Show closer to current date first
     *                        == false-> Show further away from current date first
     */
    private void applyOrderDate(List<Event> events, boolean closerToFurther) {
        Collections.sort(events, (e1, e2) -> {
            int result = 0;
            if (!nullOrEmpty(e1.getFecha()) && !nullOrEmpty(e2.getFecha())) {   // Check both dates aren't null
                Date date1 = stringToDate(e1.getFecha());
                Date date2 = stringToDate(e2.getFecha());

                if (closerToFurther) {
                    result = date1.compareTo(date2);    // Show events closer to current date first
                } else {
                    result = date2.compareTo(date1);    // Show further away from current date first
                }
            }
            return result;
        });
    }

    /**
     * Orders the event list to show events without dates first.
     * @param events Event list to be ordered.
     */
    private void applyOrderWithoutDate(List<Event> events) {
        Collections.sort(events, (e1, e2) -> {
            boolean fecha2IsNull = nullOrEmpty(e2.getFecha());
            int result = 0;

            if (nullOrEmpty(e1.getFecha())) {
                result = fecha2IsNull ? 0 : -1;
            } else if (fecha2IsNull) {
                result = 1;
            }
            return result;
        });
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
}
