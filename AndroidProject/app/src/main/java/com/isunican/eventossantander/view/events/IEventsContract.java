package com.isunican.eventossantander.view.events;

import android.app.Dialog;
import android.widget.ListView;

import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.presenter.events.Options;

import java.util.List;
import java.util.Map;

public interface IEventsContract {

    interface Presenter {

        void onEventClicked(int eventIndex);

        void onReloadClicked();

        void onInfoClicked();

        void onApplyOptions(Options options);

        void setList(List<Event> events);

        List<Event> getList();

        List<Event> onApplyFilter(Map<String, Boolean> categorias);

        void setFavEventsList(List<Event> favEvents);
    }

    interface View {

        void onEventsLoaded(List<Event> events);

        void onLoadError();

        void onLoadSuccess(int elementsLoaded);

        void openEventDetails(Event event);

        void openInfoView();

        boolean isConectionAvailable();

        void onConnectionError();

    }
}
