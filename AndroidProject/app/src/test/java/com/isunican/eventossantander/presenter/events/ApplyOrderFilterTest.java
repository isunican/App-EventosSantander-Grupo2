package com.isunican.eventossantander.presenter.events;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import android.os.Build;

import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.view.events.EventsActivity;
import com.isunican.eventossantander.view.events.IEventsContract;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1})
public class ApplyOrderFilterTest {

    private List<Event> events, eventsExpectedAsc, eventsFilteredOrdered;
    private Options options, options2;

    private IEventsContract.View view = mock(EventsActivity.class);

    private EventsPresenter presenter;

    @Before
    public void setup() {
        Event e1, e2, e3;
        Map<String,Boolean> categories, categoriesOnline;

        presenter = new EventsPresenter(view);
        events = new ArrayList<>();
        eventsExpectedAsc = new ArrayList<>();
        eventsFilteredOrdered = new ArrayList<>();

        categories = new HashMap<>();
        categoriesOnline = new HashMap<>();

        e1 = new Event();
        e1.setFecha("Domingo 31/07/2021, todo el día");
        e1.setIdentificador(1);
        e1.setCategoria("Online");
        e2 = new Event();
        e2.setFecha("Sábado 30/07/2021, todo el día");
        e2.setIdentificador(2);
        e3 = new Event();
        e3.setFecha("Viernes 29/07/2021, todo el día");
        e3.setIdentificador(3);
        e3.setCategoria("Online");

        events.add(e2);
        events.add(e1);
        events.add(e3);

        eventsExpectedAsc.add(e3);
        eventsExpectedAsc.add(e2);
        eventsExpectedAsc.add(e1);

        eventsFilteredOrdered.add(e1);
        eventsFilteredOrdered.add(e3);

        categoriesOnline.put("Online", true);

        options = new Options(categories, Utilities.OrderType.DATE_ASC, false);
        options2 = new Options(categoriesOnline, Utilities.OrderType.DATE_DESC, false);
    }

    @Test
    public void orderTest() {
        presenter.setList(events);
        presenter.onApplyOptions(options);
        assertEquals(eventsExpectedAsc, presenter.getList());
    }

    @Test
    public void orderFilterTest() {
        ArgumentCaptor<List<Event>> listCaptor = ArgumentCaptor.forClass(List.class);
        presenter.setList(events);
        presenter.onApplyOptions(options2);
        verify(view, times(1)).onEventsLoaded(listCaptor.capture());
        assertEquals(eventsFilteredOrdered, listCaptor.getValue());
    }
}
