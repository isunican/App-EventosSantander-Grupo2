package com.isunican.eventossantander.view.events;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.NoMatchingViewException;
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
     * This test corresponds to the planned UI test "UIT1.a".
     */
    @Test
    public void markEventSuccessTest() {
        // Mark first event as favourite.
        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(0).onChildView(withId(R.id.btn_event_fav)).perform(click());
        // Check that the "Favourite" button changed.
        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(0).onChildView(withId(R.id.btn_event_nofav)).check(matches(isDisplayed()));
    }

    /**
     * This test corresponds to the planned UI test "UIT1.b".
     */
    @Test (expected = NoMatchingViewException.class)
    public void markEventFailureTest() {
        // Mark first event as favourite.
        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(0).onChildView(withId(R.id.btn_event_fav)).perform(click());
        // Try to mark the same event again. It should throw an exception because the button is no longer visible.
        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(0).onChildView(withId(R.id.btn_event_fav)).perform(click());
    }
}
