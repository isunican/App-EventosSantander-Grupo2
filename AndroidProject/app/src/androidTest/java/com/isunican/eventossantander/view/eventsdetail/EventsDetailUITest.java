package com.isunican.eventossantander.view.eventsdetail;

import android.content.pm.ActivityInfo;
import android.util.Log;
import android.widget.TextView;

import com.isunican.eventossantander.R;
import com.isunican.eventossantander.model.EventsRepository;
import com.isunican.eventossantander.view.events.EventsActivity;
import com.isunican.eventossantander.view.events.IEventsContract;
import com.squareup.picasso.Picasso;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;

public class EventsDetailUITest {

    private static final String TITLE = "Abierto el plazo de inscripción para el Concurso Internacional de Piano de Santander Paloma O'Shea";
    private static final String DESC = "Hasta el 18 de octubre de 2021, estará abierto el plazo de inscripción a la vigésima edición del Concurso Internacional de Piano de Santander Paloma ";
    private static final String DATE = "Sábado 31/07/2021, todo el día. ";
    private static final String CATEGORIA = "Música";
    private static final String LINK = "http://www.santandercreativa.com/mobile/index.php?evento=abierto-el-plazo-de-inscripcion";

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
    public void eventDetailTest(){
        onData(anything()).inAdapterView(ViewMatchers.withId(R.id.eventsListView)).atPosition(0).perform(click()); //Open first event from the list
        onView(withId(R.id.event_detail_title)).check(matches(withText(TITLE))); //Check event name
        onView(withId(R.id.event_detail_date)).check(matches(withText(DATE))); //Check event date
        onView(withId(R.id.event_detail_description)).check(matches(withText(containsString(DESC))));; //Check description shown contains first part of the event desc
        onView(withId(R.id.event_detail_categoria)).check(matches(withText(CATEGORIA))); //Check event category
        onView(withId(R.id.event_detail_link)).check(matches(withText(LINK))); //Check event link
    }
}
