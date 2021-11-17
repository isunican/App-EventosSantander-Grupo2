package com.isunican.eventossantander.presenter.favourites;

import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.model.EventsRepository;
import com.isunican.eventossantander.presenter.PresenterComun;
import com.isunican.eventossantander.presenter.events.Options;
import com.isunican.eventossantander.presenter.events.Utilities;
import com.isunican.eventossantander.view.Listener;
import com.isunican.eventossantander.view.events.IEventsContract;
import com.isunican.eventossantander.view.favourites.IFavoriteEventsContract;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FavoriteEventsPresenter implements IFavoriteEventsContract.Presenter {

    private final IEventsContract.View view;
    private List<Event> cachedEvents;

    public FavoriteEventsPresenter(IEventsContract.View view) {
        this.view = view;
        loadData();
    }

    private void loadData() {
        EventsRepository.getEvents(new Listener<>() {
            @Override
            public void onSuccess(List<Event> data) {
                // Orders events with default options:
                //   Dates closer to further & events without dates last.

                PresenterComun.onApplyOrder(data, Utilities.OrderType.DATE_ASC, false);

                // Los eventos cacheados los filtro con los ids que vengan
                String ids = view.getSharedPref().getFavourites();
                List<Event> filtered = filterFavourites(data, ids);

                if (filtered.isEmpty() == true) {
                    view.showEmptyListMessage();
                }

                view.onEventsLoaded(filtered);
                view.onLoadSuccess(filtered.size());
                cachedEvents = filtered;
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

    private List<Event> filterFavourites(List<Event> unfiltered, String idEvents) {

        Set<Event> filtered = new HashSet<>();
        String[] ids = idEvents.split(",");

        for (Event e: unfiltered) {
            for (String id: ids) {
                if (String.valueOf(e.getIdentificador()).equals(id)) {
                    filtered.add(e);
                }
            }
        }
        return new ArrayList<>(filtered);
    }

    /**
     * Filter the events by the selected categories
     * @param categorias map with the categories that want to be filtered and not
     * @return list with the events which category is selected in the filters
     */
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
}
