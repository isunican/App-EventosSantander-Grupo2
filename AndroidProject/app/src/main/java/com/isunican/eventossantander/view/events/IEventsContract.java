package com.isunican.eventossantander.view.events;

import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.presenter.events.Options;
import com.isunican.eventossantander.view.favourites.IGestionarListasUsuario;

import java.util.List;

public interface IEventsContract {

    interface Presenter {

        void onEventClicked(int eventIndex);

        void onReloadClicked();

        void onInfoClicked();

        void onFavouritesClicked();

        void onFilterMenuClicked(boolean isFilterMenuVisible);

        void onApplyOptions(Options options);

        void onFavouriteClicked(int eventIndex, Boolean isClicked, IGestionarListasUsuario sharedPref);

        boolean onAddEventClicked(int i, IGestionarListasUsuario sharedPref, String listaEscogida);
    }

    interface View {

        void onEventsLoaded(List<Event> events);

        void onLoadError();

        void onLoadSuccess(int elementsLoaded);

        void openEventDetails(Event event);

        void openInfoView();

        void openFavouritesView();

        void openFilterMenuView();

        void closeFilterMenuView();

        IGestionarListasUsuario getSharedPref();

        boolean isConectionAvailable();

        void onConnectionError();

    }
}