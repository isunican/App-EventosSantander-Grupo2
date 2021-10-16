package com.isunican.eventossantander.view.events;

import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.presenter.events.Options;

import java.util.List;

public interface IEventsContract {

    interface Presenter {

        void onEventClicked(int eventIndex);

        void onReloadClicked();

        void onInfoClicked();

        void onApplyOptions(Options options);

    }

    interface View {

        void onEventsLoaded(List<Event> events);

        void onLoadError();

        void onLoadSuccess(int elementsLoaded);

        void openEventDetails(Event event);

        void openInfoView();

    }
}
