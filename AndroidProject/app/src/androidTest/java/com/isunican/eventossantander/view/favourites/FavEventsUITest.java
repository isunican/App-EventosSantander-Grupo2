package com.isunican.eventossantander.view.favourites;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsAnything.anything;

import android.content.Context;
import android.content.SharedPreferences;

import android.view.View;

import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.isunican.eventossantander.R;
import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.model.EventsRepository;
import com.isunican.eventossantander.view.events.EventsActivity;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class FavEventsUITest {

    @Rule
    public ActivityScenarioRule<EventsActivity> activityRule = new ActivityScenarioRule(EventsActivity.class);

    private static final String TITLE = "Museo virtual \"Luis Quintanilla, arte y memoria\"";
    private static final String DATE = "Sábado 31/07/2021, todo el día. ";
    private  final String CATEGORY = "Online";
    private final String FAVORITOS = "favourites";

    private Context context;
    private List<Event> favEvents;
    private Event e1;

    @BeforeClass
    public static void setUp2() {
        EventsRepository.setLocalSource();
        IdlingRegistry.getInstance().register(EventsRepository.getIdlingResource());
    }

    @Before
    public void setup() {
        activityRule.getScenario().onActivity(
                activity -> {
                    context = activity;
                });

        favEvents = new ArrayList<>();
        e1 = new Event();
        e1.setNombre("En busca de vida en Marte: nuevas misiones y nuevos retos ");
        e1.setFecha("Miércoles 08/09/2021, a las 19:00h. ");
        e1.setCategoria("Online");

        favEvents.add(e1);

        SharedPreferences sharedPref = context.getSharedPreferences(FAVORITOS, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPref.edit();
        edit.clear();
        edit.commit();
    }

    @AfterClass
    public static void clean() {
        EventsRepository.setOnlineSource();
        IdlingRegistry.getInstance().unregister(EventsRepository.getIdlingResource());
    }

    /**
     * Historia de usuario: Añadir boton menu.
     * Identificador: "UIT.1"
     * Autor: Jesus Ortega Zorrilla
     */
    @Test
    public void eventosFavoritosNoVacia () {

        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.eventsListView)).atPosition(1).onChildView(withId(R.id.btn_event_fav)).perform(click());

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getContext());
        onView(withText(R.string.favoritos)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(0).onChildView(withId(R.id.item_event_title)).check(matches(withText(TITLE)));
        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(0).onChildView(withId(R.id.item_event_date)).check(matches(withText(DATE)));
        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(0).onChildView(withId(R.id.item_event_categoria)).check(matches(withText(CATEGORY)));

    }


}