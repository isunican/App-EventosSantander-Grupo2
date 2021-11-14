package com.isunican.eventossantander.view.events;

import android.app.Dialog;
import android.view.MenuInflater;

import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.presenter.events.Options;
import com.isunican.eventossantander.view.favourites.IGestionarFavoritos;

import java.util.List;

public interface IEventsContract {

    interface Presenter {

        void onEventClicked(int eventIndex);

        void onReloadClicked();

        void onInfoClicked();

        void onFavouritesClicked();

        void onFilterMenuClicked(boolean isFilterMenuVisible);

        void onApplyOptions(Options options);

        void onFavouriteClicked(int eventIndex, Boolean isClicked, IGestionarFavoritos sharedPref);
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

        IGestionarFavoritos getSharedPref();

        MenuInflater getMenuInflater();

        Dialog getLastDialog();

        boolean isConectionAvailable();

        void onConnectionError();

    }
}