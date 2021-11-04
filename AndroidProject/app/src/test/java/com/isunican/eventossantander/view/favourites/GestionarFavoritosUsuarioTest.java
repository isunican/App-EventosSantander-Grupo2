package com.isunican.eventossantander.view.favourites;

import static org.junit.Assert.assertEquals;

import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.presenter.events.EventsPresenter;
import com.isunican.eventossantander.view.events.IEventsContract;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

public class GestionarFavoritosUsuarioTest {
    private List<Event> events;

    @Mock
    private IEventsContract.View view;

    @Mock
    private IGestionarFavoritos sharedPref;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    private IEventsContract.Presenter presenter;

    @Before
    public void setup() {
        presenter = new EventsPresenter(view);
        events = new ArrayList<>();

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
    }

    /**
     * Historia de Usuario: Marcar Evento.
     * Identificador: "UT.2".
     * Autor: Ivan Gonzalez del Pozo.
     */
    @Test
    public void testOnAddFavourite() {
        // Identificador: "UT.2a"
        presenter.setList(events);
        presenter.onFavouriteClicked(3, true, sharedPref);
        assertEquals("3", sharedPref.getFavourites());

        // Identificador: "UT.2b"
        /**try {
            presenter.onFavouriteClicked(null, true, sharedPref);
            Assert.fail("Should have thrown an exception.");
        } catch (Exception e) {
            assertEquals("3", sharedPref.getFavourites());
        }*/
    }
}
