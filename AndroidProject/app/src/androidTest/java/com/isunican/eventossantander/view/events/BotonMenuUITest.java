package com.isunican.eventossantander.view.events;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.isunican.eventossantander.R;
import com.isunican.eventossantander.model.EventsRepository;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

public class BotonMenuUITest {

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
     * Historia de usuario: Anhadir boton para abrir el menu.
     * Identificador: "UIT.1"
     * Autor: Jesus Ortega Zorrilla
     */
    @Test
    public void abrirMenuBotonUITest () {
        onView(ViewMatchers.withId(R.id.filter_menu)).perform(click());
        onView(withId(R.id.txtFiltroCategoria)).check(matches(withText("Filtro categoría")));
        //Para probar que el filtrado esta plegado inicialmente
        // si estuviese desplegado no se puede hacer click
        onView(withId(R.id.btnFiltroCategoriaDown)).perform(click());
        onView(withId(R.id.textView2)).check(matches(withText("Ordenar Fecha")));
    }
}
