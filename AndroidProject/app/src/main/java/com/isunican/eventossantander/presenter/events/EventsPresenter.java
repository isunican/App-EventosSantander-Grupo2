package com.isunican.eventossantander.presenter.events;

import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.model.EventsRepository;
import com.isunican.eventossantander.presenter.PresenterComun;
import com.isunican.eventossantander.view.Listener;
import com.isunican.eventossantander.view.events.IEventsContract;
import com.isunican.eventossantander.view.favourites.IGestionarListasUsuario;

import java.util.List;
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

    @Override
    public void onFavouriteClicked(int eventIndex, Boolean isClicked, IGestionarListasUsuario sharedPref) {
         if(eventIndex >= 0 && eventIndex < cachedEvents.size() && Boolean.FALSE.equals(isClicked)) {
                 sharedPref.setFavourite(eventIndex, cachedEvents);
         }
    }

    @Override
    public boolean onAddEventClicked(int eventIndex, IGestionarListasUsuario sharedPref, String listaEscogida) {
        if(eventIndex >= 0 && eventIndex< cachedEvents.size()){
            boolean result = sharedPref.addEvent(eventIndex,cachedEvents,listaEscogida);
            if(!result){
                view.errorEventAlreadyExists();
            }
            return result;
        } else {
            view.errorEventIndexOutOfBounds();
            return false;
        }
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
