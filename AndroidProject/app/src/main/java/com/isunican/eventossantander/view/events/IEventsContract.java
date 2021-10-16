package com.isunican.eventossantander.view.events;

import android.widget.ListView;

import com.isunican.eventossantander.model.Event;

import java.util.List;
import java.util.Map;

public interface IEventsContract {

    public interface Presenter {

        void onEventClicked(int eventIndex);

        void onReloadClicked();

        void onInfoClicked();

        List<Event> onApplyFilter(Map<String, Boolean> categorias);
    }

    public interface View {

        void onEventsLoaded(List<Event> events);

        void onLoadError();

        void onLoadSuccess(int elementsLoaded);

        void openEventDetails(Event event);

        void openInfoView();

    }
}
