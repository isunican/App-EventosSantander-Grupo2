package com.isunican.eventossantander;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static org.hamcrest.core.IsAnything.anything;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.presenter.events.EventsPresenter;
import com.isunican.eventossantander.view.events.EventsActivity;
import com.isunican.eventossantander.view.events.IEventsContract;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class FavEventsUITest {

    private final String TITLE = "En busca de vida en Marte: nuevas misiones y nuevos retos ";
    private final String DATE = "Miércoles 08/09/2021, a las 19:00h. ";
    private  final String CATEGORY = "Online";
    private  final String TITLE_2 = "\"Retratos de buques\", exposición temporal";
    private  final String DATE_2 = "Sábado 31/07/2021, de 10:00 a 19:30h. ";
    private  final String CATEGORY_2 = "Música";

    private IEventsContract.Presenter presenter;
    private List<Event> favEvents, emptyEvents;
    private Event e1, e2;

    @Before
    public void setup() {
        emptyEvents = new ArrayList<>();
        favEvents = new ArrayList<>();
        e1 = new Event();
        e1.setNombre("En busca de vida en Marte: nuevas misiones y nuevos retos ");
        e1.setFecha("Miércoles 08/09/2021, a las 19:00h. ");
        e1.setCategoria("Online");
        e2 = new Event();
        e2.setNombre("\"Retratos de buques\", exposición temporal");
        e2.setFecha("Sábado 31/07/2021, de 10:00 a 19:30h. ");
        e2.setCategoria("Música");

        favEvents.add(e1);
        favEvents.add(e2);

    }

    @Rule
    public ActivityScenarioRule<EventsActivity> activityRule = new ActivityScenarioRule(EventsActivity.class);

    @Test
    public void eventosFavoritosVacia () {
        presenter.setFavEventsList(emptyEvents);
        onView(withId(R.id.favoritosActivity)).perform(click());
        onView(withId(R.id.textView4)).check(matches(withText("No hay eventos favoritos")));

    }

    @Test
    public void eventosFavoritosNoVacia () throws InterruptedException {
        presenter.setFavEventsList(favEvents);
        onView(withId(R.id.favoritosActivity)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(0).onChildView(withId(R.id.item_event_title)).check(matches(withText(TITLE)));
        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(0).onChildView(withId(R.id.item_event_date)).check(matches(withText(DATE)));
        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(0).onChildView(withId(R.id.item_event_categoria)).check(matches(withText(CATEGORY)));

        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(1).onChildView(withId(R.id.item_event_title)).check(matches(withText(TITLE_2)));
        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(1).onChildView(withId(R.id.item_event_date)).check(matches(withText(DATE_2)));
        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(1).onChildView(withId(R.id.item_event_categoria)).check(matches(withText(CATEGORY_2)));

    }


}
