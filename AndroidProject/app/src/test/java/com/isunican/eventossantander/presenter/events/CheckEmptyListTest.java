package com.isunican.eventossantander.presenter.events;

import android.os.Build;

import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.presenter.events.EventsPresenter;
import com.isunican.eventossantander.view.events.IEventsContract;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1})
public class CheckEmptyListTest {

    private IEventsContract.Presenter presenter, presenter2;
    private List<Event> emptyEvents, favEvents;
    private Event e1, e2;

    @Mock
    private IEventsContract.View mockView;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Before
    public void setup() {
        presenter = new EventsPresenter(mockView);
        //presenter2 = new EventsPresenter(EventsActivity.class);
        emptyEvents = new ArrayList<>();
        favEvents = new ArrayList<>();
        e1 = new Event();
        e2 = new Event();

        /*
        e1.setFecha("Domingo 31/07/2021, todo el día");
        e1.setIdentificador(1);
        e1.setCategoria("Online");
        e2.setFecha("Sábado 30/07/2021, todo el día");
        e2.setIdentificador(2);
        e2.setCategoria("Música")*/

        favEvents.add(e1);
        favEvents.add(e2);
    }

    @Test
    public void emptyListTest() {
        // Set the favourites list empty
        presenter.setFavEventsList(emptyEvents);
        //Descomentar siguiente linea cuando se implemente el metodo checkEmptyList
        //assertEquals(presenter.checkEmptyList(), true);
    }

    @Test
    public void NotEmptyListTest() {
        // Set the favourites list NOT empty
        presenter.setFavEventsList(favEvents);
        //Descomentar siguiente linea cuando se implemente el metodo checkEmptyList
        //assertEquals(presenter.checkEmptyList(), false);
    }
}
