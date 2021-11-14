package com.isunican.eventossantander.view.events;

import static android.service.autofill.Validators.not;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;


import static org.hamcrest.CoreMatchers.is;

import android.app.Dialog;
import android.app.Instrumentation;
import android.content.DialogInterface;
import android.widget.Button;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.rule.ActivityTestRule;


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
        String mensaje = "Se ha creado la lista Conciertos con éxito";
        // Identificador: "UIT.1a"

        onView(withId(R.id.crear_lista)).perform(click());
        // Introducir el nombre en el campo de texto
        // Clickar en el boton de aceptar
        // Comprobar que se muestra un mensaje con la lista creada
        // onView(withText(mensaje))
        //      .inRoot(withDecorView(not(getActivity().getWindow().getDecorView())))
        //      .check(matches(isDisplayed()));

        // Identificador: "UIT.1b"
        // Identificador: "UIT.1c"
        // Identificador: "UIT.1d"
    }
}
