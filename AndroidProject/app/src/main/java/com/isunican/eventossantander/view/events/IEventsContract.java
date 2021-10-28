package com.isunican.eventossantander.view.events;

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

        void onFilterMenuClicked(boolean isFilterMenuVisible);

        void onApplyOptions(Options options);

        void setList(List<Event> events);

        List<Event> getList();

        List<Event> onApplyFilter(Map<String, Boolean> categorias);
    }

    interface View {

        void onEventsLoaded(List<Event> events);

        void onLoadError();

        void onLoadSuccess(int elementsLoaded);

        void openEventDetails(Event event);

        void openInfoView();

        void openFilterMenuView();

        void closeFilterMenuView();

    }
}
