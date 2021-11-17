package com.isunican.eventossantander.presenter.events;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.os.Build;

import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.view.events.IEventsContract;
import com.isunican.eventossantander.view.favourites.IGestionarListasUsuario;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1})
public class EventsPresenterTest {
    private List<Event> events;
    private List<Event> eventsExpectedCulturaCientifica, eventsExpectedNoCategory, eventsExpectedEmpty;
    private Options options, options2, options3;
    private Map<String, Boolean> categories1, categories2, categories3;
    private final String NOMBRE_LISTA_EXISTE = "Lista1";

    @Mock
    private IEventsContract.View view;

    @Mock
    private IGestionarListasUsuario sharedPref;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    private EventsPresenter presenter;


    @Before
    public void setup() {
        presenter = new EventsPresenter(view);
        events = new ArrayList<>();
        List<Event> emptyEvents = new ArrayList<>();
        List<Event> eventsCulturaCientifica = new ArrayList<>();
        List<Event> eventsNoCategory = new ArrayList<>();

        eventsExpectedCulturaCientifica = new ArrayList<>();
        eventsExpectedEmpty = new ArrayList<>();
        eventsExpectedNoCategory = new ArrayList<>();

        categories1 = new HashMap<>();
        categories2 = new HashMap<>();
        categories3 = new HashMap<>();

        Event e1 = new Event();
        e1.setCategoria("Cultura científica");
        e1.setIdentificador(1);
        Event e2 = new Event();
        e2.setIdentificador(2);
        Event e3 = new Event();
        e3.setCategoria("Cultura científica");
        e3.setIdentificador(3);
        Event e4 = new Event();
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
        eventsExpectedNoCategory.add(e1);
        eventsExpectedNoCategory.add(e2);
        eventsExpectedNoCategory.add(e3);
        eventsExpectedNoCategory.add(e4);

        categories1.put("Cultura científica", true);
        categories1.put("Música", false);
        categories2.put("Cultura científica", false);
        categories2.put("Música", true);
        categories3.put("Cultura científica", false);
        categories3.put("Música", false);

        options = new Options(categories1, null, true);
        options2 = new Options(categories2, null, true);
        options3 = new Options(categories3, null, true);


    }

    @After
    public void clear() {

    }

    // Identificador: "UGIC.1g"
    @Test
    public void orderTest() {
        List<Event> events, eventsExpectedAsc, eventsFilteredOrdered;
        Options options, options2;
        Event e1, e2, e3;
        Map<String, Boolean> categories, categoriesOnline;


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

        ArgumentCaptor<List<Event>> listCaptor = ArgumentCaptor.forClass(List.class);
        presenter.setList(events);
        presenter.onApplyOptions(options);
        verify(view, times(1)).onEventsLoaded(listCaptor.capture());
        assertEquals(eventsExpectedAsc, presenter.getList());
    }

    // Identificador: "UIT.1h"
    @Test
    public void orderFilterTest() {
        List<Event> events, eventsExpectedAsc, eventsFilteredOrdered;
        Options options, options2;
        Event e1, e2, e3;
        Map<String, Boolean> categories, categoriesOnline;


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

        ArgumentCaptor<List<Event>> listCaptor = ArgumentCaptor.forClass(List.class);
        presenter.setList(events);
        presenter.onApplyOptions(options2);
        verify(view, times(1)).onEventsLoaded(listCaptor.capture());
        assertEquals(eventsFilteredOrdered, listCaptor.getValue());
    }

    // Identificador: "UGIC.1a"
    @Test
    public void applyOrderAsc() {
        List<Event> events, emptyEvents, oneEvent, eventsNoDate;
        List<Event> eventsExpectedAsc, eventsExpectedDesc, eventsExpectedEmpty, eventsExpectedOne, eventsExpectedNoDateF, eventsExpectedNoDateT;
        Options options, options2, options3;
        Event e1, e2, e3, e4;
        Map<String, Boolean> categories;

        presenter = new EventsPresenter(view);
        events = new ArrayList<>();
        emptyEvents = new ArrayList<>();
        oneEvent = new ArrayList<>();
        eventsNoDate = new ArrayList<>();

        eventsExpectedAsc = new ArrayList<>();
        eventsExpectedDesc = new ArrayList<>();
        eventsExpectedEmpty = new ArrayList<>();
        eventsExpectedOne = new ArrayList<>();
        eventsExpectedNoDateF = new ArrayList<>();
        eventsExpectedNoDateT = new ArrayList<>();

        categories = new HashMap<>();

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

        options = new Options(categories, Utilities.OrderType.DATE_ASC, false);
        options2 = new Options(categories, Utilities.OrderType.DATE_DESC, false);
        options3 = new Options(categories, Utilities.OrderType.DATE_ASC, true);

        presenter.setList(events);
        presenter.onApplyOptions(options);
        assertEquals(eventsExpectedAsc, presenter.getList());
    }

    // Identificador: "UGIC.1b"
    @Test
    public void applyOrderDesc() {
        List<Event> events, emptyEvents, oneEvent, eventsNoDate;
        List<Event> eventsExpectedAsc, eventsExpectedDesc, eventsExpectedEmpty, eventsExpectedOne, eventsExpectedNoDateF, eventsExpectedNoDateT;
        Options options, options2, options3;
        Event e1, e2, e3, e4;
        Map<String, Boolean> categories;

        presenter = new EventsPresenter(view);
        events = new ArrayList<>();
        emptyEvents = new ArrayList<>();
        oneEvent = new ArrayList<>();
        eventsNoDate = new ArrayList<>();

        eventsExpectedAsc = new ArrayList<>();
        eventsExpectedDesc = new ArrayList<>();
        eventsExpectedEmpty = new ArrayList<>();
        eventsExpectedOne = new ArrayList<>();
        eventsExpectedNoDateF = new ArrayList<>();
        eventsExpectedNoDateT = new ArrayList<>();

        categories = new HashMap<>();

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

        options = new Options(categories, Utilities.OrderType.DATE_ASC, false);
        options2 = new Options(categories, Utilities.OrderType.DATE_DESC, false);
        options3 = new Options(categories, Utilities.OrderType.DATE_ASC, true);

        presenter.setList(events);
        presenter.onApplyOptions(options2);
        assertEquals(eventsExpectedDesc, presenter.getList());
    }

    // Identificador: "UGIC.1c"
    @Test
    public void applyOrderEmpty() {
        List<Event> events, emptyEvents, oneEvent, eventsNoDate;
        List<Event> eventsExpectedAsc, eventsExpectedDesc, eventsExpectedEmpty, eventsExpectedOne, eventsExpectedNoDateF, eventsExpectedNoDateT;
        Options options, options2, options3;
        Event e1, e2, e3, e4;
        Map<String, Boolean> categories;

        presenter = new EventsPresenter(view);
        events = new ArrayList<>();
        emptyEvents = new ArrayList<>();
        oneEvent = new ArrayList<>();
        eventsNoDate = new ArrayList<>();

        eventsExpectedAsc = new ArrayList<>();
        eventsExpectedDesc = new ArrayList<>();
        eventsExpectedEmpty = new ArrayList<>();
        eventsExpectedOne = new ArrayList<>();
        eventsExpectedNoDateF = new ArrayList<>();
        eventsExpectedNoDateT = new ArrayList<>();

        categories = new HashMap<>();

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

        options = new Options(categories, Utilities.OrderType.DATE_ASC, false);
        options2 = new Options(categories, Utilities.OrderType.DATE_DESC, false);
        options3 = new Options(categories, Utilities.OrderType.DATE_ASC, true);

        presenter.setList(emptyEvents);
        presenter.onApplyOptions(options2);
        assertEquals(eventsExpectedEmpty, presenter.getList());
    }

    // Identificador: "UGIC.1d"
    @Test
    public void applyOrderOne() {
        List<Event> events, emptyEvents, oneEvent, eventsNoDate;
        List<Event> eventsExpectedAsc, eventsExpectedDesc, eventsExpectedEmpty, eventsExpectedOne, eventsExpectedNoDateF, eventsExpectedNoDateT;
        Options options, options2, options3;
        Event e1, e2, e3, e4;
        Map<String, Boolean> categories;

        presenter = new EventsPresenter(view);
        events = new ArrayList<>();
        emptyEvents = new ArrayList<>();
        oneEvent = new ArrayList<>();
        eventsNoDate = new ArrayList<>();

        eventsExpectedAsc = new ArrayList<>();
        eventsExpectedDesc = new ArrayList<>();
        eventsExpectedEmpty = new ArrayList<>();
        eventsExpectedOne = new ArrayList<>();
        eventsExpectedNoDateF = new ArrayList<>();
        eventsExpectedNoDateT = new ArrayList<>();

        categories = new HashMap<>();

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

        options = new Options(categories, Utilities.OrderType.DATE_ASC, false);
        options2 = new Options(categories, Utilities.OrderType.DATE_DESC, false);
        options3 = new Options(categories, Utilities.OrderType.DATE_ASC, true);

        presenter.setList(oneEvent);
        presenter.onApplyOptions(options2);
        assertEquals(eventsExpectedOne, presenter.getList());
    }

    // Identificador: "UGIC.1e"
    @Test
    public void applyOrderNoDate() {
        List<Event> events, emptyEvents, oneEvent, eventsNoDate;
        List<Event> eventsExpectedAsc, eventsExpectedDesc, eventsExpectedEmpty, eventsExpectedOne, eventsExpectedNoDateF, eventsExpectedNoDateT;
        Options options, options2, options3;
        Event e1, e2, e3, e4;
        Map<String, Boolean> categories;

        presenter = new EventsPresenter(view);
        events = new ArrayList<>();
        emptyEvents = new ArrayList<>();
        oneEvent = new ArrayList<>();
        eventsNoDate = new ArrayList<>();

        eventsExpectedAsc = new ArrayList<>();
        eventsExpectedDesc = new ArrayList<>();
        eventsExpectedEmpty = new ArrayList<>();
        eventsExpectedOne = new ArrayList<>();
        eventsExpectedNoDateF = new ArrayList<>();
        eventsExpectedNoDateT = new ArrayList<>();

        categories = new HashMap<>();

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

        options = new Options(categories, Utilities.OrderType.DATE_ASC, false);
        options2 = new Options(categories, Utilities.OrderType.DATE_DESC, false);
        options3 = new Options(categories, Utilities.OrderType.DATE_ASC, true);

        presenter.setList(eventsNoDate);
        presenter.onApplyOptions(options);
        assertEquals(eventsExpectedNoDateF, presenter.getList());
    }

    // Identificador: "UGIC.1f"
    @Test
    public void applyOrderNoDateFirst() {
        List<Event> events, emptyEvents, oneEvent, eventsNoDate;
        List<Event> eventsExpectedAsc, eventsExpectedDesc, eventsExpectedEmpty, eventsExpectedOne, eventsExpectedNoDateF, eventsExpectedNoDateT;
        Options options, options2, options3;
        Event e1, e2, e3, e4;
        Map<String, Boolean> categories;

        presenter = new EventsPresenter(view);
        events = new ArrayList<>();
        emptyEvents = new ArrayList<>();
        oneEvent = new ArrayList<>();
        eventsNoDate = new ArrayList<>();

        eventsExpectedAsc = new ArrayList<>();
        eventsExpectedDesc = new ArrayList<>();
        eventsExpectedEmpty = new ArrayList<>();
        eventsExpectedOne = new ArrayList<>();
        eventsExpectedNoDateF = new ArrayList<>();
        eventsExpectedNoDateT = new ArrayList<>();

        categories = new HashMap<>();

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

        options = new Options(categories, Utilities.OrderType.DATE_ASC, false);
        options2 = new Options(categories, Utilities.OrderType.DATE_DESC, false);
        options3 = new Options(categories, Utilities.OrderType.DATE_ASC, true);

        presenter.setList(eventsNoDate);
        presenter.onApplyOptions(options3);
        assertEquals(eventsExpectedNoDateT, presenter.getList());
    }

    @Test
    public void getOrderCategoriesTest() {
        Options options, options2;
        Map<String, Boolean> categories = new HashMap<>();

        options = new Options(categories, Utilities.OrderType.DATE_ASC, false);
        options2 = new Options(categories, null, false);

        assertEquals(Utilities.OrderType.DATE_ASC, options.getOrderTypeOptions());
    }

    @Test
    public void getNullOrderCategoriesTest() {
        Options options, options2;
        Map<String, Boolean> categories = new HashMap<>();

        options = new Options(categories, Utilities.OrderType.DATE_ASC, false);
        options2 = new Options(categories, null, false);

        assertNull(options2.getOrderTypeOptions());

    }

    @Test
    public void applyOptionsFound() {
        ArgumentCaptor<List<Event>> eventos = ArgumentCaptor.forClass(List.class);
        presenter.setList(events);
        presenter.onApplyOptions(options);
        verify(view).onEventsLoaded(eventos.capture());
        assertEquals(eventsExpectedCulturaCientifica, eventos.getValue());
    }

    @Test
    public void applyOptionsNotFound() {
        ArgumentCaptor<List<Event>> eventos = ArgumentCaptor.forClass(List.class);
        presenter.setList(events);
        presenter.onApplyOptions(options2);
        verify(view).onEventsLoaded(eventos.capture());
        assertEquals(eventsExpectedEmpty, eventos.getValue());
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

    /**
     * Historia de Usuario: Marcar Evento.
     * Identificador: "UT.1".
     * Autor: Ivan Gonzalez del Pozo.
     */
    @Test
    public void testOnFavouriteClicked() {
        // Identificador: "UT.1a"
        presenter.setList(events);
        presenter.onFavouriteClicked(1, false, sharedPref);
        verify(sharedPref).setFavourite(eq(1), any());

        // Identificador: "UT.1b"
        presenter.onFavouriteClicked(2, false, sharedPref);
        verify(sharedPref).setFavourite(eq(2), any());

        // Identificador: "UT.1d"
        presenter.onFavouriteClicked(0, false, sharedPref);
        verify(sharedPref, never()).setFavourite(eq(0), any());

        // Identificador: "UT.1e"
        presenter.onFavouriteClicked(-1, false, sharedPref);
        verify(sharedPref, never()).setFavourite(eq(-1), any());

        // Identificador: "UT.1f"
        presenter.onFavouriteClicked(999999, false, sharedPref);
        verify(sharedPref, never()).setFavourite(eq(999999), any());
    }

    /**
     * Historia de Usuario: Anhadir evento a lista.
     * Identificador: "UT.1".
     * Autor: Sara Grela Carrera.
     */
    @Test
    public void testOnAddEventClicked() {

        String nombreListaNoExiste = "Lista2";

        // Identificador: "UT.1a"
        presenter.setList(events);
        when(sharedPref.addEvent(eq(1), any(), eq(NOMBRE_LISTA_EXISTE))).thenReturn(true);
        assertEquals(presenter.onAddEventClicked(1, sharedPref, NOMBRE_LISTA_EXISTE), true);
        verify(sharedPref).addEvent(eq(1), any(), eq(NOMBRE_LISTA_EXISTE));

        // Identificador: "UT.1b"
        when(sharedPref.addEvent(eq(1), any(), eq(NOMBRE_LISTA_EXISTE))).thenReturn(false);
        assertEquals(presenter.onAddEventClicked(1, sharedPref, NOMBRE_LISTA_EXISTE), false);
        verify(sharedPref, times(2)).addEvent(eq(1), any(), eq(NOMBRE_LISTA_EXISTE));

        // Identificador: "UT.1c"
        when(sharedPref.addEvent(eq(2), any(), eq(NOMBRE_LISTA_EXISTE))).thenReturn(true);
        assertEquals(presenter.onAddEventClicked(2, sharedPref, NOMBRE_LISTA_EXISTE), true);
        verify(sharedPref).addEvent(eq(2), any(), eq(NOMBRE_LISTA_EXISTE));

        // Identificador: "UT.1d"
        assertEquals(presenter.onAddEventClicked(1, sharedPref, nombreListaNoExiste), false);
        verify(sharedPref, times(2)).addEvent(eq(1), any(), eq(NOMBRE_LISTA_EXISTE));

        // Identificador: "UT.1e"
        assertEquals(presenter.onAddEventClicked(999999, sharedPref, NOMBRE_LISTA_EXISTE), false);
        verify(sharedPref, never()).addEvent(eq(999999), any(), eq(NOMBRE_LISTA_EXISTE));

        // Identificador: "UT.1f"
        assertEquals(presenter.onAddEventClicked(-1, sharedPref, NOMBRE_LISTA_EXISTE), false);
        verify(sharedPref, never()).addEvent(eq(-1), any(), eq(NOMBRE_LISTA_EXISTE));

        // Identificador: "UT.1g"
        when(sharedPref.addEvent(eq(0), any(), eq(NOMBRE_LISTA_EXISTE))).thenReturn(true);
        assertEquals(presenter.onAddEventClicked(0, sharedPref, NOMBRE_LISTA_EXISTE), true);
        verify(sharedPref).addEvent(eq(0), any(), eq(NOMBRE_LISTA_EXISTE));

        // Identificador: "UT.1h"
        when(sharedPref.addEvent(eq(events.size() - 1), any(), eq(NOMBRE_LISTA_EXISTE))).thenReturn(true);
        assertEquals(presenter.onAddEventClicked(events.size() - 1, sharedPref, NOMBRE_LISTA_EXISTE), true);
        verify(sharedPref).addEvent(eq(events.size() - 1), any(), eq(NOMBRE_LISTA_EXISTE));

        // Identificador: "UT.1i"
        assertEquals(presenter.onAddEventClicked(events.size(), sharedPref, NOMBRE_LISTA_EXISTE), false);
        verify(sharedPref, never()).addEvent(eq(events.size()), any(), eq(NOMBRE_LISTA_EXISTE));
    }
}
