package com.isunican.eventossantander;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.presenter.events.EventsPresenter;
import com.isunican.eventossantander.presenter.events.Options;
import com.isunican.eventossantander.view.events.EventsActivity;
import com.isunican.eventossantander.view.events.IEventsContract;

import org.junit.BeforeClass;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ApplyOrderFilterTest {

    private static List<Event> events, eventsExpectedAsc, eventsFilteredOrdered;
    private static Options options, options2;

    private static final IEventsContract.View view = mock(EventsActivity.class);

    private static IEventsContract.Presenter presenter;

    @BeforeClass
    public static void setup() {
        Event e1, e2, e3;
        Map<String,Boolean> categories, categoriesOnline;


        presenter = new EventsPresenter(view);
        events = new ArrayList<Event>();
        eventsExpectedAsc = new ArrayList<Event>();
        eventsFilteredOrdered = new ArrayList<Event>();

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

        options = new Options(categories, EventsPresenter.OrderType.DATE_ASC, false);
        options2 = new Options(categoriesOnline, EventsPresenter.OrderType.DATE_DESC, false);
    }

    @Test
    public void orderTest() {
        presenter.setList(events);
        presenter.onApplyOptions(options);
        assertEquals(eventsExpectedAsc, presenter.getList());
    }

    @Test
    public void orderFilterTest() {
        presenter.setList(events);
        presenter.onApplyOptions(options2);
        assertEquals(eventsFilteredOrdered, presenter.getList());
    }
}
