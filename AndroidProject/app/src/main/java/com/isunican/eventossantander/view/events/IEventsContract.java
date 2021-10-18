package com.isunican.eventossantander.view.events;

import android.widget.ListView;

import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.presenter.events.Options;

import java.util.List;
import java.util.Map;

public interface IEventsContract {

    public interface Presenter {

        void onEventClicked(int eventIndex);

        void onReloadClicked();

        void onInfoClicked();

        List<Event> onApplyFilter(Map<String, Boolean> categorias);

        void onApplyOptions(Options options);


        void setList(List<Event> events);

        List<Event> getList();
    }

    public interface View {

        void onEventsLoaded(List<Event> events);

        void onLoadError();

        void onLoadSuccess(int elementsLoaded);

        void openEventDetails(Event event);

        void openInfoView();

    }
}
