package com.isunican.eventossantander;

import static org.junit.Assert.assertEquals;

import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.presenter.events.EventsPresenter;
import com.isunican.eventossantander.presenter.events.Options;
import com.isunican.eventossantander.view.events.IEventsContract;

import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ApplyFilterTest {

    private List<Event> events, emptyEvents, eventsCulturaCientifica, eventsNoCategory;
    private List<Event> eventsExpectedCulturaCientifica, eventsExpectedNoCategory,eventsExpectedEmpty;
    private Event e1, e2, e3, e4;
    private Options options, options2, options3;
    private Set<String> categories1, categories2,categories3;

    @Mock
    private IEventsContract.View view;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    private IEventsContract.Presenter presenter;

    @Before
    public void setup() {
        presenter = new EventsPresenter(view);
        events = new ArrayList<Event>();
        emptyEvents = new ArrayList<Event>();
        eventsCulturaCientifica = new ArrayList<Event>();
        eventsNoCategory = new ArrayList<Event>();

        eventsExpectedCulturaCientifica = new ArrayList<Event>();
        eventsExpectedEmpty = new ArrayList<Event>();
        eventsExpectedNoCategory = new ArrayList<Event>();

        categories1 = new HashSet<>();
        categories2 = new HashSet<>();
        categories3 = new HashSet<>();


        e1 = new Event();
        e1.setCategoria("Cultura científica");
        e1.setIdentificador(1);
        e2 = new Event();
        e2.setIdentificador(2);
        e3 = new Event();
        e3.setCategoria("Cultura científica");
        e3.setIdentificador(3);
        e4 = new Event();
        e4.setIdentificador(4);

        events.add(e1);
        events.add(e2);
        events.add(e3);
        events.add(e4);
        eventsCulturaCientifica.add(e1);
        eventsCulturaCientifica.add(e3);
        eventsNoCategory.add(e2);
        eventsNoCategory.add(e4);


        eventsExpectedCulturaCientifica.add(e1);
        eventsExpectedCulturaCientifica.add(e3);
        eventsExpectedNoCategory.add(e2);
        eventsExpectedNoCategory.add(e4);

        categories1.add("Cultura científica");
        categories2.add("Encuentas");



        options = new Options(categories1, null, true);
        options2 = new Options(categories2, null, true);
        options3 = new Options(categories3, null, true);
    }

    @After
    public void clear() {

    }

    @Test
    public void applyOptionsFound() {
        presenter.setList(events);
        presenter.onApplyOptions(options);
        assertEquals(eventsExpectedCulturaCientifica, presenter.getList());
    }

    @Test
    public void applyOptionsNotFound() {
        presenter.setList(events);
        presenter.onApplyOptions(options2);
        assertEquals(eventsExpectedEmpty, presenter.getList());
    }

    @Test
    public void applyOptionsNoOptions() {
        presenter.setList(events);
        presenter.onApplyOptions(options3);
        assertEquals(eventsExpectedNoCategory, presenter.getList());
    }

    @Test
    public void applyFilterFound() {
        presenter.setList(events);
        assertEquals(eventsExpectedCulturaCientifica, presenter.onApplyFilter(categories1));

    }

    @Test
    public void applyFilterNotFound() {
        presenter.setList(events);

        assertEquals(eventsExpectedEmpty, presenter.onApplyFilter(categories2));
    }

    @Test
    public void applyFilterNoFilter() {
        presenter.setList(events);
        assertEquals(eventsExpectedNoCategory, presenter.onApplyFilter(categories3));
    }
}
