package com.isunican.eventossantander;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.os.Build;

import androidx.test.core.app.ApplicationProvider;

import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.model.EventsRepository;
import com.isunican.eventossantander.presenter.events.EventsPresenter;
import com.isunican.eventossantander.view.events.IEventsContract;
import com.isunican.eventossantander.view.favourites.GestionarListasUsuario;
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
import java.util.List;
import java.util.concurrent.Phaser;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1})
public class EventsPresenterITest {

    @Mock
    private IEventsContract.View view;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    private EventsPresenter presenter;

    private List<Event> events;
    public static Phaser lock = EventsRepository.getPhaser();
    Context context = ApplicationProvider.getApplicationContext();

    // Necesario para tener una lista creada para probar
    private GestionarListasUsuario gestionarListasUsuario;
    private final String NOMBRE_LISTA_EXISTE = "Lista1";

    @Before
    public void setUp() {
        presenter = new EventsPresenter(view);
        gestionarListasUsuario = new GestionarListasUsuario(context);
        GestionarListasUsuario.cleanSetPreferences(context);
        gestionarListasUsuario.createList(NOMBRE_LISTA_EXISTE);

        // Lista de eventos
        events = new ArrayList<>();
        Event e0 = new Event();
        e0.setCategoria("Cultura científica");
        e0.setIdentificador(0);
        Event e1 = new Event();
        e1.setIdentificador(1);
        Event e2 = new Event();
        e2.setCategoria("Cultura científica");
        e2.setIdentificador(2);
        Event e3 = new Event();
        e3.setIdentificador(3);

        events.add(e0);
        events.add(e1);
        events.add(e2);
        events.add(e3);
    }

    @After
    public void clean() {
        GestionarListasUsuario.cleanSetPreferences(context);
    }

    @Test
    public void loadEventsCorrect() throws InterruptedException {
        ArgumentCaptor<List<Event>> eventos = ArgumentCaptor.forClass(List.class) ;
        EventsRepository.setLocalSource();
        presenter = new EventsPresenter(view);
        lock.arriveAndAwaitAdvance();
        verify(view).onEventsLoaded(eventos.capture());
        assertEquals(345, eventos.getValue().size());

    }

    @Test
    public void loadEventsNoCorret() throws InterruptedException {
        when(view.isConectionAvailable()).thenReturn(true);
        ArgumentCaptor<List<Event>> eventos = ArgumentCaptor.forClass(List.class) ;
        EventsRepository.setFakeSource();
        presenter = new EventsPresenter(view);
        lock.arriveAndAwaitAdvance();
        verify(view).onLoadError();
    }

    /**
     * Historia de usuario: Anhadir evento a lista
     * Identificador: "IT.1"
     * Autor: Sara Grela Carrera
     */
    @Test
    public void onAddEventClickedTest() {

        String nombreListaNoExiste = "Lista2";
        presenter.setList(events);

        // Identificador: "UT.1a"
        assertEquals(presenter.onAddEventClicked(1, gestionarListasUsuario, NOMBRE_LISTA_EXISTE), true);
        assertEquals(gestionarListasUsuario.getEventsList(NOMBRE_LISTA_EXISTE), "1,");

        // Identificador: "UT.1b"
        assertEquals(presenter.onAddEventClicked(1, gestionarListasUsuario, NOMBRE_LISTA_EXISTE), false);
        assertEquals(gestionarListasUsuario.getEventsList(NOMBRE_LISTA_EXISTE), "1,");

        // Identificador: "UT.1c"
        assertEquals(presenter.onAddEventClicked(2, gestionarListasUsuario, NOMBRE_LISTA_EXISTE), true);
        assertEquals(gestionarListasUsuario.getEventsList(NOMBRE_LISTA_EXISTE), "1,2,");

        // Identificador: "UT.1d"
        presenter.onAddEventClicked(1, gestionarListasUsuario, nombreListaNoExiste);
        assertEquals(gestionarListasUsuario.checkListExists(nombreListaNoExiste), false);
        assertEquals(gestionarListasUsuario.getEventsList(NOMBRE_LISTA_EXISTE), "1,2,");

        // Identificador: "UT.1e"
        assertEquals(presenter.onAddEventClicked(999999, gestionarListasUsuario, NOMBRE_LISTA_EXISTE), false);
        assertEquals(gestionarListasUsuario.getEventsList(NOMBRE_LISTA_EXISTE), "1,2,");

        // Identificador: "UT.1f"
        assertEquals(presenter.onAddEventClicked(-1, gestionarListasUsuario, NOMBRE_LISTA_EXISTE), false);
        assertEquals(gestionarListasUsuario.getEventsList(NOMBRE_LISTA_EXISTE), "1,2,");

        // Identificador: "UT.1g"
        assertEquals(presenter.onAddEventClicked(0, gestionarListasUsuario, NOMBRE_LISTA_EXISTE), true);
        assertEquals(gestionarListasUsuario.getEventsList(NOMBRE_LISTA_EXISTE), "1,2,0,");

        // Identificador: "UT.1h"
        assertEquals(presenter.onAddEventClicked(events.size()-1, gestionarListasUsuario, NOMBRE_LISTA_EXISTE), true);
        String id = String.valueOf(events.size()-1) + ",";
        assertEquals(gestionarListasUsuario.getEventsList(NOMBRE_LISTA_EXISTE), "1,2,0," + id);

        // Identificador: "UT.1i"
        assertEquals(presenter.onAddEventClicked(events.size(), gestionarListasUsuario, NOMBRE_LISTA_EXISTE), false);
        assertEquals(gestionarListasUsuario.getEventsList(NOMBRE_LISTA_EXISTE), "1,2,0,"+ id);

    }
}
