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

    import org.junit.BeforeClass;
    import org.junit.Rule;
    import org.junit.Test;

    public class MarcarEventoUITest {

        @BeforeClass
        public static void setUp() {
            EventsRepository.setLocalSource();
            IdlingRegistry.getInstance().register(EventsRepository.getIdlingResource());
        }

        @Rule
        public ActivityScenarioRule<EventsActivity> activityRule = new ActivityScenarioRule(EventsActivity.class);

        /**
         * Historia de Usuario: Marcar Evento.
         * Identificador: "UIT1.a".
         * Autor: Ivan Gonzalez del Pozo.
         */
        @Test
        public void markEventSuccessTest() {
            // Mark first event as favourite.
            onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(0).onChildView(withId(R.id.btn_event_fav)).perform(click());
            // Check that the "Favourite" button's image changed.
            onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(0).onChildView(withId(R.id.btn_event_fav)).check(matches(withTagValue(equalTo(R.drawable.ic_baseline_star_24))));
        }
    }
