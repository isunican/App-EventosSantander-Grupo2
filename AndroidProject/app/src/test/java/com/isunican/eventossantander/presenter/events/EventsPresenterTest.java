package com.isunican.eventossantander.presenter.events;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.os.Build;

import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.view.favourites.GestionarListasUsuario;
import com.isunican.eventossantander.view.events.IEventsContract;
import com.isunican.eventossantander.view.favourites.IGestionarListasUsuario;

import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
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
    private List<Event> eventsExpectedCulturaCientifica, eventsExpectedNoCategory,eventsExpectedEmpty;
    private Options options, options2, options3;
    private Map<String, Boolean> categories1, categories2,categories3;

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

        categories1.put("Cultura científica",true);
        categories1.put("Música",false);
        categories2.put("Cultura científica",false);
        categories2.put("Música",true);
        categories3.put("Cultura científica",false);
        categories3.put("Música",false);

        options = new Options(categories1, null, true);
        options2 = new Options(categories2, null, true);
        options3 = new Options(categories3, null, true);
    }

    @After
    public void clear() {

    }

    @Test
    public void applyOptionsFound() {
        ArgumentCaptor<List<Event>> eventos = ArgumentCaptor.forClass(List.class) ;
        presenter.setList(events);
        presenter.onApplyOptions(options);
        verify(view).onEventsLoaded(eventos.capture());
        assertEquals(eventsExpectedCulturaCientifica, eventos.getValue());
    }

    @Test
    public void applyOptionsNotFound() {
        ArgumentCaptor<List<Event>> eventos = ArgumentCaptor.forClass(List.class) ;
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

        String nombreListaExiste = "Lista1";
        String nombreListaNoExiste = "Lista2";

        // Identificador: "UT.1a"
        presenter.setList(events);
        presenter.onAddEventClicked(1, sharedPref, nombreListaExiste);
        verify(sharedPref).addEvent(eq(1), any(), eq(nombreListaExiste));

        // Identificador: "UT.1b"
        presenter.onAddEventClicked(1, sharedPref, nombreListaExiste);
        verify(sharedPref).addEvent(eq(1), any(), eq(nombreListaExiste));

        /// Vaciar lista y meterle los eventos 2, 3 y 4
        // Identificador: "UT.1c"
        presenter.onAddEventClicked(1, sharedPref, nombreListaExiste);
        verify(sharedPref).addEvent(eq(1), any(), eq(nombreListaExiste));

        // Identificador: "UT.1d"
        presenter.onAddEventClicked(1, sharedPref, nombreListaNoExiste);
        verify(sharedPref, never()).addEvent(eq(1), any(), eq(nombreListaNoExiste));

        // Vaciar la Lista1
        // Identificador: "UT.1e"
        presenter.onAddEventClicked(999999, sharedPref, nombreListaExiste);
        verify(sharedPref).addEvent(eq(1), any(), eq(nombreListaExiste));

        // Vaciar la Lista1
        // Identificador: "UT.1f"
        presenter.onAddEventClicked(-1, sharedPref, nombreListaExiste);
        verify(sharedPref, never()).addEvent(eq(1), any(), eq(nombreListaExiste));
    }
}