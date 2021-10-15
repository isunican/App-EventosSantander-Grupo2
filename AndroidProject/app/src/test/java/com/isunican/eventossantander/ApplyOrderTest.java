package com.isunican.eventossantander;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import android.util.Log;

import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.model.EventsRepository;
import com.isunican.eventossantander.presenter.events.EventsPresenter;
import com.isunican.eventossantander.view.events.IEventsContract;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

public class ApplyOrderTest {

    private static Event e1,e2,e3,e4;
    private static List<Event> events;
    private static List<Event> emptyList;
    private static List<Event> oneInList;
    private static List<Event> eventsIncludeNoDate;

    private static List<Event> expectedAsc;
    private static List<Event> expectedDesc;
    private static List<Event> expectedOne;
    private static List<Event> expectedNoEvents;
    private static List<Event> expectedNoDate;
    private static List<Event> expectedIncludeNoDate;


    @Mock
    private static IEventsContract.View mockView;

    @Rule
    public MockitoRule mockitoRule= MockitoJUnit.rule();

    private static IEventsContract.Presenter presenter;

    @BeforeClass
    public static void setup() {
        //EventsRepository.setLocalSource();
        events = new ArrayList<Event>();
        emptyList = new ArrayList<Event>();
        oneInList = new ArrayList<Event>();
        eventsIncludeNoDate = new ArrayList<Event>();
        expectedDesc = new ArrayList<Event>();
        expectedAsc = new ArrayList<Event>();
        expectedNoEvents = new ArrayList<Event>();
        expectedOne = new ArrayList<Event>();
        expectedIncludeNoDate = new ArrayList<Event>();

        e1 = new Event();
        e1.setFecha("Domingo 31/10/2021, todo el día");
        e1.setNombre("Evento 1");

        e2 = new Event();
        e2.setFecha("Jueves 30/09/2021, a las 20:00h");
        e2.setNombre("Evento 2");

        e3 = new Event();
        e3.setFecha("Martes 31/08/2021, de 17:00 a 20:00h");
        e3.setNombre("Evento 3");

        e4 = new Event();
        e4.setNombre("Evento 4");

        events.add(e1);
        events.add(e2);
        events.add(e3);

        oneInList.add(e2);

        eventsIncludeNoDate.add(e1);
        eventsIncludeNoDate.add(e2);
        eventsIncludeNoDate.add(e4);
        eventsIncludeNoDate.add(e3);

        expectedAsc.add(e3);
        expectedAsc.add(e2);
        expectedAsc.add(e1);

        expectedDesc.add(e1);
        expectedDesc.add(e2);
        expectedDesc.add(e3);

        expectedOne.add(e2);

        expectedIncludeNoDate.add(e1);
        expectedIncludeNoDate.add(e2);
        expectedIncludeNoDate.add(e3);
        expectedIncludeNoDate.add(e4);

        expectedNoDate.add(e4);
        expectedNoDate.add(e1);
        expectedNoDate.add(e2);
        expectedNoDate.add(e3);

        presenter = new EventsPresenter(mockView);
    }

    @AfterClass
    public static void clean() {
        events.clear();
    }


    @Test
    public void testAscOrder() {
        presenter.onApplyOrder(EventsPresenter.OrderType.DATE_ASC, events);
        assertEquals(expectedAsc, events);
        //Log.d("DEBUG", events.toString());
        //System.out.println(events.toString());
    }

    @Test
    public void testDescOrder() {
        presenter.onApplyOrder(EventsPresenter.OrderType.DATE_DESC, events);
        assertEquals(expectedDesc, events);
    }

    @Test
    public void testEmptyOrder() {
        presenter.onApplyOrder(EventsPresenter.OrderType.DATE_ASC, emptyList);
        assertEquals(expectedNoEvents, emptyList);
    }

    @Test
    public void testOneEventOrder() {
        presenter.onApplyOrder(EventsPresenter.OrderType.DATE_ASC, oneInList);
        assertEquals(expectedOne, oneInList);
    }

    @Test
    public void testIncludeNoDateOrder() {
        presenter.onApplyOrder(EventsPresenter.OrderType.DATE_DESC, eventsIncludeNoDate);
        assertEquals(expectedIncludeNoDate, eventsIncludeNoDate);
    }

    @Test
    public void testNoDateOrder() {
        presenter.onApplyOrder(EventsPresenter.OrderType.NO_DATE_FIRST, eventsIncludeNoDate);
        assertEquals(expectedNoDate, eventsIncludeNoDate);
    }
}
