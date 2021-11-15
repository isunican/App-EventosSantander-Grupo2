package com.isunican.eventossantander;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import static java.lang.Thread.sleep;

import android.os.Build;


import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.model.EventsRepository;
import com.isunican.eventossantander.presenter.events.EventsPresenter;
import com.isunican.eventossantander.view.favourites.GestionarListasUsuario;
import com.isunican.eventossantander.view.events.IEventsContract;

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
    private IEventsContract.Presenter presenter;

    public static Phaser lock = EventsRepository.getPhaser();

    // Necesario para tener una lista creada para probar
    private GestionarListasUsuario GestionarListasUsuario;
    private final String NOMBRE_LISTA_EXISTE = "Lista1";

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
        // Se crea la Lista1
        GestionarListasUsuario.createList(NOMBRE_LISTA_EXISTE);

        ArgumentCaptor<List<Event>> eventos = ArgumentCaptor.forClass(List.class) ;
        EventsRepository.setLocalSource();
        presenter = new EventsPresenter(view);

        // presenter.onAddEventClicked(1, , );

    }
}
