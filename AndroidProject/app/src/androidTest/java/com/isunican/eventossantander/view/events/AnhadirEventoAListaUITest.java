package com.isunican.eventossantander.view.events;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.equalTo;
import static java.lang.Thread.sleep;

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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

public class AnhadirEventoAListaUITest {


    private View decorView;
    private Context context;

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

    @After
    public void clean2() {
        GestionarListasUsuario.cleanSetPreferences(context);
    }

    @Rule
    public ActivityScenarioRule<EventsActivity> activityRule = new ActivityScenarioRule(EventsActivity.class);

    @Before
    public void setUp2() {
        activityRule.getScenario().onActivity(
                activity -> {
                    decorView = activity.getWindow().getDecorView();
                    context = activity;
                });
    }

    /**
     * Historia de Usuario: Anhadir Evento a Lista.
     * Identificador: "UIT1".
     * Autor: Sara Grela Carrera ("UIT1.a" y "UIT1.b") e Iv??n Gonzalez del Pozo ("UIT1.c").
     */
    @Test
    public void anhadirEventoAListaTest () {
        GestionarListasUsuario.cleanSetPreferences(context);

        // Identificador: "UIT1.c"
        // Borrar las listas creadas
        GestionarListasUsuario.cleanSetPreferences(context);
        // Intentar anhadir el primer evento a una lista
        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(0).onChildView(withId(R.id.btn_add_list_event)).perform(click());
        // Comprobar que ya no aparece la lista para seleccionar
        onView(withText("Lista1")).check(doesNotExist());
        // Cerrar el mensaje abierto
        onView(withText("Cancelar")).perform(click());

        // Identificador: "UIT1.a"
        // Se crea una lista
        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        onView(withText(R.string.crear_lista)).perform(click());
        onView(withTagValue(equalTo("InputDialog"))).perform(typeText("Lista1"));
        onView(withText("Aceptar")).perform(click());

        // Anhadir el primer evento a la Lista 1
        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(0).onChildView(withId(R.id.btn_add_list_event)).perform(click());
        // Seleccionar la primera lista que exista
        onView(withText("Lista1")).perform(click());

        // Comprobar que se muestra el mensaje al usuario
        onView(withText("Se ha a??adido un evento a la lista Lista1")).inRoot(RootMatchers.withDecorView(CoreMatchers.not(decorView))).check(matches(isDisplayed()));

        // Identificador: "UIT1.b"
        // Anhadir el primer evento a la Lista 1
        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(0).onChildView(withId(R.id.btn_add_list_event)).perform(click());
        // Seleccionar la primera lista que exista
        onView(withText("Lista1")).perform(click());

        // Comprobar que se muestra el mensaje al usuario
        onView(withText("El evento seleccionado ya est?? en la lista")).inRoot(RootMatchers.withDecorView(CoreMatchers.not(decorView))).check(matches(isDisplayed()));
    }
}
