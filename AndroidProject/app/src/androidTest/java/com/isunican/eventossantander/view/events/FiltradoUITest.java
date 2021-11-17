package com.isunican.eventossantander.view.events;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsAnything.anything;
import static java.lang.Thread.sleep;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.isunican.eventossantander.R;
import com.isunican.eventossantander.model.EventsRepository;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

public class FiltradoUITest {

    private final String TITLE = "En busca de vida en Marte: nuevas misiones y nuevos retos ";
    private  final String DATE = "Miércoles 08/09/2021, a las 19:00h. ";
    private  final String TITLE_2 = "\"Retratos de buques\", exposición temporal";
    private  final String DATE_2 = "Sábado 31/07/2021, de 10:00 a 19:30h. ";
    private  final String TITLE_3 = "Museo Marítimo del Cantábrico";
    private  final String DATE_3 = "Sábado 31/07/2021, de 10:00 a 19:30h. ";
    private  final String TITLE_4 = "\"Vida y Muerte en Cantabria\", exposición temporal";
    private  final String DATE_4 = "Sábado 31/07/2021, de 10:00 a 14:00h. ";
    private  final String TITLE_5 = "Concurso infantil de dibujo vida extraterrestre";
    private  final String DATE_5 = "Sábado 31/07/2021, todo el día. ";
    private  final String CATEGORY = "Cultura científica";
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



    @Test
    public void eventosCulturaCientifica () throws InterruptedException {
        onView(withId(R.id.filter_menu)).perform(click());
        //onView(withId(R.id.eventsListView)).perform(swipeRight());
        //Simulamos el gesto, recomendado por el profesor
        onView(withId(R.id.btnFiltroCategoriaDown)).perform(click());

        onView(withId(R.id.checkBoxCulturaCientifica)).perform(click());
        onView(withId(R.id.menu_filtros)).perform(swipeUp());
        sleep(2000);
        onView(withId(R.id.rbOrdenarLejana)).perform(click());
        onView(withId(R.id.btnAplicarFiltroOrden)).perform(click());

        onView(withId(R.id.eventsListView)).perform(swipeLeft());
        sleep(2000);
        //Check the events
        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(0).onChildView(withId(R.id.item_event_title)).check(matches(withText(TITLE)));
        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(0).onChildView(withId(R.id.item_event_date)).check(matches(withText(DATE)));
        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(0).onChildView(withId(R.id.item_event_categoria)).check(matches(withText(CATEGORY)));

        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(1).onChildView(withId(R.id.item_event_title)).check(matches(withText(TITLE_2)));
        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(1).onChildView(withId(R.id.item_event_date)).check(matches(withText(DATE_2)));
        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(1).onChildView(withId(R.id.item_event_categoria)).check(matches(withText(CATEGORY)));

        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(2).onChildView(withId(R.id.item_event_title)).check(matches(withText(TITLE_3)));
        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(2).onChildView(withId(R.id.item_event_date)).check(matches(withText(DATE_3)));
        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(2).onChildView(withId(R.id.item_event_categoria)).check(matches(withText(CATEGORY)));

        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(3).onChildView(withId(R.id.item_event_title)).check(matches(withText(TITLE_4)));
        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(3).onChildView(withId(R.id.item_event_date)).check(matches(withText(DATE_4)));
        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(3).onChildView(withId(R.id.item_event_categoria)).check(matches(withText(CATEGORY)));

        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(4).onChildView(withId(R.id.item_event_title)).check(matches(withText(TITLE_5)));
        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(4).onChildView(withId(R.id.item_event_date)).check(matches(withText(DATE_5)));
        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(4).onChildView(withId(R.id.item_event_categoria)).check(matches(withText(CATEGORY)));




    }


}
