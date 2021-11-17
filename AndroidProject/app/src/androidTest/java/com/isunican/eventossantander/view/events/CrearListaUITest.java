package com.isunican.eventossantander.view.events;

import static java.lang.Thread.sleep;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;

import android.content.Context;
import android.view.View;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;


import com.isunican.eventossantander.R;
import com.isunican.eventossantander.model.EventsRepository;
import com.isunican.eventossantander.view.favourites.GestionarListasUsuario;

import org.hamcrest.CoreMatchers;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

public class CrearListaUITest {

    @Rule
    public ActivityScenarioRule<EventsActivity> activityRule = new ActivityScenarioRule(EventsActivity.class);

    private Context context;
    private View decorView;
    private String mensaje;


    @BeforeClass
    public static void setUp2() {
        EventsRepository.setLocalSource();
        IdlingRegistry.getInstance().register(EventsRepository.getIdlingResource());
    }
    /**
     * Load known events json
     * https://personales.unican.es/rivasjm/resources/agenda_cultural.json
     */
    @Before
    public void setUp() {
        activityRule.getScenario().onActivity(
                activity -> {
                    decorView = activity.getWindow().getDecorView();
                    context = activity;
                });
        GestionarListasUsuario.cleanSetPreferences(context);
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
        // Identificador: "UIT.1a"
        mensaje = "Se ha creado la lista Conciertos con éxito";
        // Abrir el menu
        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        // Abrir la opcion "Crear lista"
        onView(withText(R.string.crear_lista)).perform(click());
        // Introducir el nombre en el campo de texto
        onView(withTagValue(equalTo("InputDialog"))).perform(typeText("Conciertos"));
        // Clicar en el boton de aceptar
        onView(withText("Aceptar")).perform(click());
        // Comprobar que se muestra un mensaje con la lista creada
        onView(withText(mensaje)).inRoot(RootMatchers.withDecorView(CoreMatchers.not(decorView))).check(matches(isDisplayed()));

        // Identificador: "UIT.1b"
        mensaje = "Se ha creado la lista Conciertos(1) con éxito";
        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        onView(withText(R.string.crear_lista)).perform(click());
        onView(withTagValue(equalTo("InputDialog"))).perform(typeText("Conciertos"));
        onView(withText("Aceptar")).perform(click());
        // Comprobar que se muestra un mensaje con la lista creada
        onView(withText(mensaje)).inRoot(RootMatchers.withDecorView(CoreMatchers.not(decorView))).check(matches(isDisplayed()));

        // Identificador: "UIT.1c"
        mensaje = "No se ha creado la lista, introduzca un nombre válido.";
        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        onView(withText(R.string.crear_lista)).perform(click());
        onView(withTagValue(equalTo("InputDialog"))).perform(typeText(""));
        onView(withText("Aceptar")).perform(click());
        // Comprobar que se muestra un mensaje mostrando el error
        onView(withText(mensaje)).inRoot(RootMatchers.withDecorView(CoreMatchers.not(decorView))).check(matches(isDisplayed()));

        // Identificador: "UIT.1d"
        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        onView(withText(R.string.crear_lista)).perform(click());
        onView(withText("Cancelar")).perform(click());
    }
}
