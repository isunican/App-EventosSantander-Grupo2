package com.isunican.eventossantander.view.events;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import android.app.Dialog;
import android.app.Instrumentation;
import android.content.DialogInterface;
import android.widget.Button;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.isunican.eventossantander.R;
import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.model.EventsRepository;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

public class CrearListaUITest {

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
     * Historia de usuario: Crear lista
     * Identificador: "UIT.1"
     * Autora: Marta Obregon Ruiz
     */
    @Test
    public void crearListaUITest () {
        // Identificador: "UIT.1a"
        /*
        onView(ViewMatchers.withId(R.id.crear_lista)).perform(click());
        Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(EventsActivity.class.getName(), null, false);
        EventsActivity myActivity = (EventsActivity) monitor.waitForActivityWithTimeout(2000);

        Dialog dialog = myActivity.getLastDialog();

        if (dialog.isShowing()) {
            try {
                Button okBtn = (Button) dialog.findViewById(R.id.);
                performClick(dialog.getButton(DialogInterface.BUTTON_POSITIVE));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        } */
        // Identificador: "UIT.1b"
        // Identificador: "UIT.1c"
        // Identificador: "UIT.1d"
    }
}
