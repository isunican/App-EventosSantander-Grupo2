package com.isunican.eventossantander;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static java.lang.Thread.sleep;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.isunican.eventossantander.model.EventsRepository;
import com.isunican.eventossantander.view.events.EventsActivity;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

public class OrderDateUITest {

    private static final String TITLE = "Abierto el plazo de inscripción para el Concurso Internacional de Piano de Santander Paloma O'Shea";
    private static final String DATE = "Sábado 31/07/2021, todo el día. ";
    private static final String TITLE_2 = "Museo virtual \"Luis Quintanilla, arte y memoria\"";
    private static final String DATE_2 = "Sábado 31/07/2021, todo el día. ";
    private static final String TITLE_3 = "Gabinete de estampas virtual de la UC";
    private static final String DATE_3 = "Sábado 31/07/2021, todo el día. ";

    @BeforeClass
    public static void setUp() {
        EventsRepository.setLocalSource();
        IdlingRegistry.getInstance().register(EventsRepository.getIdlingResource());
    }

    @Rule
    public ActivityScenarioRule<EventsActivity> activityRule = new ActivityScenarioRule(EventsActivity.class);

    @Test
    public void orderAscTest() throws InterruptedException {
        onView(withId(R.id.eventsListView)).perform(swipeRight());
        onView(withId(R.id.rbOrdenarProxima)).perform(click()); //Checked by default in this version, so not really needed but this might change in the future
        sleep(2000);
		onView(withId(R.id.btnAplicarFiltroOrden)).perform(click());
        //Check the order was applied by checking the first three events in the list
		sleep(2000);
        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(0).onChildView(withId(R.id.item_event_title)).check(matches(withText(TITLE)));
        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(0).onChildView(withId(R.id.item_event_date)).check(matches(withText(DATE)));
        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(1).onChildView(withId(R.id.item_event_title)).check(matches(withText(TITLE_2)));
        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(1).onChildView(withId(R.id.item_event_date)).check(matches(withText(DATE_2)));
        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(2).onChildView(withId(R.id.item_event_title)).check(matches(withText(TITLE_3)));
        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(2).onChildView(withId(R.id.item_event_date)).check(matches(withText(DATE_3)));
    }
}
