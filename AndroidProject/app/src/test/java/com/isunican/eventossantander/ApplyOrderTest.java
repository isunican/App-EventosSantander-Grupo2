package com.isunican.eventossantander;

import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.presenter.events.EventsPresenter;
import com.isunican.eventossantander.presenter.events.Options;
import com.isunican.eventossantander.view.events.EventsActivity;
import com.isunican.eventossantander.view.events.IEventsContract;


import org.junit.BeforeClass;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ApplyOrderTest {
    private static List<Event> events, emptyEvents, oneEvent, eventsNoDate;
    private static List<Event> eventsExpectedAsc, eventsExpectedDesc, eventsExpectedEmpty, eventsExpectedOne, eventsExpectedNoDateF, eventsExpectedNoDateT;
    private static Options options, options2, options3;

    private static final IEventsContract.View view = mock(EventsActivity.class);

    private static IEventsContract.Presenter presenter;

    @BeforeClass
    public static void setup() {
        Event e1, e2, e3, e4;
        Set<String> categories;

        presenter = new EventsPresenter(view);
        events = new ArrayList<Event>();
        emptyEvents = new ArrayList<Event>();
        oneEvent = new ArrayList<Event>();
        eventsNoDate = new ArrayList<Event>();

        eventsExpectedAsc = new ArrayList<Event>();
        eventsExpectedDesc = new ArrayList<Event>();
        eventsExpectedEmpty = new ArrayList<Event>();
        eventsExpectedOne = new ArrayList<Event>();
        eventsExpectedNoDateF = new ArrayList<Event>();
        eventsExpectedNoDateT = new ArrayList<Event>();

        categories = new HashSet<>();

        e1 = new Event();
        e1.setFecha("Domingo 31/07/2021, todo el día");
        e1.setIdentificador(1);
        e2 = new Event();
        e2.setFecha("Sábado 30/07/2021, todo el día");
        e2.setIdentificador(2);
        e3 = new Event();
        e3.setFecha("Viernes 29/07/2021, todo el día");
        e3.setIdentificador(3);
        e4 = new Event();
        e4.setIdentificador(4);

        events.add(e2);
        events.add(e1);
        events.add(e3);
        oneEvent.add(e2);
        eventsNoDate.add(e1);
        eventsNoDate.add(e2);
        eventsNoDate.add(e4);
        eventsNoDate.add(e3);

        eventsExpectedAsc.add(e3);
        eventsExpectedAsc.add(e2);
        eventsExpectedAsc.add(e1);
        eventsExpectedDesc.add(e1);
        eventsExpectedDesc.add(e2);
        eventsExpectedDesc.add(e3);
        eventsExpectedOne.add(e2);
        eventsExpectedNoDateF.add(e3);
        eventsExpectedNoDateF.add(e2);
        eventsExpectedNoDateF.add(e1);
        eventsExpectedNoDateF.add(e4);
        eventsExpectedNoDateT.add(e4);
        eventsExpectedNoDateT.add(e3);
        eventsExpectedNoDateT.add(e2);
        eventsExpectedNoDateT.add(e1);

        options = new Options(categories, EventsPresenter.OrderType.DATE_ASC, false);
        options2 = new Options(categories, EventsPresenter.OrderType.DATE_DESC, false);
        options3 = new Options(categories, EventsPresenter.OrderType.DATE_ASC, true);
    }

    @Test
    public void applyOrderAsc() {
        presenter.setList(events);
        presenter.onApplyOptions(options);
        assertEquals(eventsExpectedAsc, presenter.getList());
    }

    @Test
    public void applyOrderDesc() {
        presenter.setList(events);
        presenter.onApplyOptions(options2);
        assertEquals(eventsExpectedDesc, presenter.getList());
    }

    @Test
    public void applyOrderEmpty() {
        presenter.setList(emptyEvents);
        presenter.onApplyOptions(options2);
        assertEquals(eventsExpectedEmpty, presenter.getList());
    }

    @Test
    public void applyOrderOne() {
        presenter.setList(oneEvent);
        presenter.onApplyOptions(options2);
        assertEquals(eventsExpectedOne, presenter.getList());
    }

    @Test
    public void applyOrderNoDate() {
        presenter.setList(eventsNoDate);
        presenter.onApplyOptions(options);
        assertEquals(eventsExpectedNoDateF, presenter.getList());
    }

    @Test
    public void applyOrderNoDateFirst() {
        presenter.setList(eventsNoDate);
        presenter.onApplyOptions(options3);
        assertEquals(eventsExpectedNoDateT, presenter.getList());
    }
}
