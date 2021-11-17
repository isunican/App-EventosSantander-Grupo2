package com.isunican.eventossantander.presenter.events;

import static org.junit.Assert.assertEquals;
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

        EventsRepository.setLocalSource();
        presenter = new EventsPresenter(view);
        lock.arriveAndAwaitAdvance();
        List<Event> eventos = presenter.getList();

        // Identificador: "UT.1a"
        assertEquals(true, presenter.onAddEventClicked(1, gestionarListasUsuario, NOMBRE_LISTA_EXISTE));
        String id1 = String.valueOf(eventos.get(1).getIdentificador()) + ",";
        assertEquals(gestionarListasUsuario.getEventsList(NOMBRE_LISTA_EXISTE), id1);

        // Identificador: "UT.1b"
        assertEquals(false,presenter.onAddEventClicked(1, gestionarListasUsuario, NOMBRE_LISTA_EXISTE));
        assertEquals(gestionarListasUsuario.getEventsList(NOMBRE_LISTA_EXISTE), id1);

        // Identificador: "UT.1c"
        assertEquals(true, presenter.onAddEventClicked(2, gestionarListasUsuario, NOMBRE_LISTA_EXISTE));
        String id2 = id1.concat(String.valueOf(eventos.get(2).getIdentificador()) + ",");
        assertEquals(id2, gestionarListasUsuario.getEventsList(NOMBRE_LISTA_EXISTE));

        // Identificador: "UT.1d"
        presenter.onAddEventClicked(1, gestionarListasUsuario, nombreListaNoExiste);
        assertEquals(false, gestionarListasUsuario.checkListExists(nombreListaNoExiste));
        assertEquals(id2, gestionarListasUsuario.getEventsList(NOMBRE_LISTA_EXISTE));

        // Identificador: "UT.1e"
        assertEquals(false,presenter.onAddEventClicked(999999, gestionarListasUsuario, NOMBRE_LISTA_EXISTE));
        assertEquals(id2, gestionarListasUsuario.getEventsList(NOMBRE_LISTA_EXISTE));

        // Identificador: "UT.1f"
        assertEquals(false, presenter.onAddEventClicked(-1, gestionarListasUsuario, NOMBRE_LISTA_EXISTE));
        assertEquals(id2, gestionarListasUsuario.getEventsList(NOMBRE_LISTA_EXISTE));

        // Identificador: "UT.1g"
        assertEquals(true, presenter.onAddEventClicked(0, gestionarListasUsuario, NOMBRE_LISTA_EXISTE));
        String id3 = id2.concat(String.valueOf(eventos.get(0).getIdentificador()) + ",");
        assertEquals(id3, gestionarListasUsuario.getEventsList(NOMBRE_LISTA_EXISTE));

        // Identificador: "UT.1h"
        assertEquals(true, presenter.onAddEventClicked(eventos.size()-1, gestionarListasUsuario, NOMBRE_LISTA_EXISTE));
        String id4 = id3.concat(String.valueOf(eventos.get(eventos.size()-1).getIdentificador()) + ",");
        assertEquals(id4, gestionarListasUsuario.getEventsList(NOMBRE_LISTA_EXISTE));

        // Identificador: "UT.1i"
        assertEquals(false, presenter.onAddEventClicked(eventos.size(), gestionarListasUsuario, NOMBRE_LISTA_EXISTE));
        assertEquals(id4, gestionarListasUsuario.getEventsList(NOMBRE_LISTA_EXISTE));

    }
}
