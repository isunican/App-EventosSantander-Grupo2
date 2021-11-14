package com.isunican.eventossantander.view.events;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.equalTo;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.isunican.eventossantander.R;
import com.isunican.eventossantander.model.EventsRepository;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

public class AnhadirEventoAListaUITest {

    /**
     * Load known events json
     * https://personales.unican.es/rivasjm/resources/agenda_cultural.json
     */
    @BeforeClass
    public static void setUp() {
        EventsRepository.setLocalSource();
        IdlingRegistry.getInstance().register(EventsRepository.getIdlingResource());
    }

    @AfterClass
    public static void clean() {
        EventsRepository.setOnlineSource();
        IdlingRegistry.getInstance().unregister(EventsRepository.getIdlingResource());
    }

    @Rule
    public ActivityScenarioRule<EventsActivity> activityRule = new ActivityScenarioRule(EventsActivity.class);

    /**
     * Historia de Usuario: Anhadir Evento a Lista.
     * Identificador: "UIT1.a".
     * Autor: Sara Grela Carrera.
     */
    @Test
    public void anhadirEventoAListaTest () {
        // Anhadir el primer evento a una lista
        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(0).onChildView(withId(R.id.btn_add_list_event)).perform(click());
        // Seleccionar la primera lista que exista
        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(0).onChildView(withId(R.id.btn_event_fav)).check(matches(withTagValue(equalTo(R.drawable.ic_baseline_star_24))));
        // Comprobar que se muestra el mensaje al usuario
    }
}
