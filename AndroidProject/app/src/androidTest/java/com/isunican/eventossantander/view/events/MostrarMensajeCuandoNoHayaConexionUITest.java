package com.isunican.eventossantander.view.events;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import com.isunican.eventossantander.R;
import com.isunican.eventossantander.model.EventsRepository;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

public class MostrarMensajeCuandoNoHayaConexionUITest {
    @BeforeClass
    public static void setUp() {
        EventsRepository.setLocalSource();
        IdlingRegistry.getInstance().register(EventsRepository.getIdlingResource());
    }

    @Rule
    public ActivityScenarioRule<EventsActivity> activityRule = new ActivityScenarioRule(EventsActivity.class);

    /**
     * Historia de Usuario: Mostrar mensaje cuando no haya conexion.
     * Identificador: "UIT1.a".
     * Autor: Ivan Gonzalez del Pozo.
     */
    @Test
    public void showNoConnectionMessageTest() {
        // Turn off the Internet connection.
        InstrumentationRegistry.getInstrumentation().getUiAutomation().executeShellCommand("svc wifi disable");
        InstrumentationRegistry.getInstrumentation().getUiAutomation().executeShellCommand("svc data disable");
        // Check that the error message appears.
        Assert.assertTrue(EventsActivity.getDialog().isShowing());
    }

    /**
     * Historia de Usuario: Mostrar mensaje cuando no haya conexion.
     * Identificador: "UIT1.b".
     * Autor: Ivan Gonzalez del Pozo.
     */
    @Test
    public void showNoConnectionMessageAndRechargeTest() {
        // Turn off the Internet connection.
        InstrumentationRegistry.getInstrumentation().getUiAutomation().executeShellCommand("svc wifi disable");
        InstrumentationRegistry.getInstrumentation().getUiAutomation().executeShellCommand("svc data disable");
        // Check that the error message appears.
        Assert.assertTrue(EventsActivity.getDialog().isShowing());
        // Close the Dialog.
        EventsActivity.getDialog().dismiss;
        // Turn the Internet connection back on.
        InstrumentationRegistry.getInstrumentation().getUiAutomation().executeShellCommand("svc wifi enable");
        InstrumentationRegistry.getInstrumentation().getUiAutomation().executeShellCommand("svc data enable");
        // Reload the app.
        onData(anything()).inAdapterView(withId(R.id.menu_refresh)).perform(click());
        // Check that events are loaded and displayed.
        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(0).onChildView(withId(R.id.item_event_title)).check(matches(withText("Abierto el plazo de inscripción para el Concurso Internacional de Piano de Santander Paloma O'Shea")));
    }
}
