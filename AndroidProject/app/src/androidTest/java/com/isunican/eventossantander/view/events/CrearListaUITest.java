package com.isunican.eventossantander.view.events;

import static android.service.autofill.Validators.not;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;


import static org.hamcrest.CoreMatchers.is;

import android.app.Dialog;
import android.app.Instrumentation;
import android.content.DialogInterface;
import android.service.autofill.Validator;
import android.view.View;
import android.widget.Button;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.rule.ActivityTestRule;


import com.isunican.eventossantander.R;
import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.model.EventsRepository;

import org.hamcrest.Matcher;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

public class CrearListaUITest {

    @Rule
    public static ActivityScenarioRule<EventsActivity> activityRule = new ActivityScenarioRule(EventsActivity.class);

    private static View decorView;

    /**
     * Load known events json
     * https://personales.unican.es/rivasjm/resources/agenda_cultural.json
     */
    @BeforeClass
    public static void setUp() {
        EventsRepository.setLocalSource();
        IdlingRegistry.getInstance().register(EventsRepository.getIdlingResource());
        activityRule.getScenario().onActivity(
                activity -> decorView = activity.getWindow().getDecorView()
        );
    }
    @AfterClass
    public static void clean() {
        EventsRepository.setOnlineSource();
        IdlingRegistry.getInstance().unregister(EventsRepository.getIdlingResource());
    }


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
        // Dialog dialog = onView(withTagValue("InputDialog"));
        // Clickar en el boton de aceptar
        // Comprobar que se muestra un mensaje con la lista creada
        onView(withText(mensaje)).inRoot(RootMatchers.withDecorView((Matcher<View>) not((Validator) decorView))).check(matches(isDisplayed()));


        // Identificador: "UIT.1b"
        mensaje = "Se ha creado la lista Conciertos(1) con éxito";
        // Comprobar que se muestra un mensaje con la lista creada
        onView(withText(mensaje)).inRoot(RootMatchers.withDecorView((Matcher<View>) not((Validator) decorView))).check(matches(isDisplayed()));

        // Identificador: "UIT.1c"
        mensaje = "No se ha creado la lista, introduzca un nombre válido";
        // Comprobar que se muestra un mensaje mostrando el error
        onView(withText(mensaje)).inRoot(RootMatchers.withDecorView((Matcher<View>) not((Validator) decorView))).check(matches(isDisplayed()));

        // Identificador: "UIT.1d"

    }
}
