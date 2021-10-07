package com.isunican.eventossantander;

import android.util.Log;
import android.widget.TextView;

import com.isunican.eventossantander.model.EventsRepository;
import com.isunican.eventossantander.view.events.EventsActivity;
import com.isunican.eventossantander.view.events.IEventsContract;
import com.squareup.picasso.Picasso;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.anything;

public class EventsDetailUITest {

    private static final String TITLE = "Abierto el plazo de inscripción para el Concurso Internacional de Piano de Santander Paloma O'Shea";
    private static final String DESC = "Hasta el 18 de octubre de 2021, estará abierto el plazo de inscripción a la vigésima edición del Concurso Internacional de Piano de Santander Paloma O\"Shea.\n" +
            "    \n" +
            "    Este concurso, al que pueden presentarse en esta ocasión jóvenes pianistas de todo el mundo nacidos desde el 1 de enero de 1990 en adelante, comenzó en 1972 y está considerado entre los más importantes del ámbito internacional.\n" +
            "    \n" +
            "    A través de este formulario web los interesados deberán adjuntar la documentación solicitada, y seguir todos los pasos indicados en las Bases del Concurso para ser admitida su inscripción.";
    private static final String IMAGE_URL = "http://www.santandercreativa.com/ekh2983gd29837gt_uploads/web_2/eventos_images/image_evento_thumb_46523_2021-07-05_16_48_59.jpg";
    private static final String DATE = "Sábado 31/07/2021, todo el día. ";
    private static final String CATEGORIA = "Música";
    private static final String LINK = "http://www.santandercreativa.com/mobile/index.php?evento=abierto-el-plazo-de-inscripcion";

    @BeforeClass
    public static void setUp() {
        EventsRepository.setLocalSource();
        IdlingRegistry.getInstance().register(EventsRepository.getIdlingResource());
    }

    @Rule
    public ActivityScenarioRule<EventsActivity> activityRule =
            new ActivityScenarioRule(EventsActivity.class);

    @Test
    public void eventDetailTest(){
        onData(anything()).inAdapterView(withId(R.id.eventsListView)).atPosition(0).perform(click()); //Open first event from the list
        onView(withId(R.id.event_detail_title)).check(matches(withText(TITLE))); //Check event name
        //TODO Check event image
        onView(withId(R.id.event_detail_imagen)).check(matches(with));
        onView(withId(R.id.event_detail_date)).check(matches(withText(DATE))); //TODO Check event date
        onView(withId(R.id.event_detail_description)).check(matches(withText(DESC))); //Check event desc
        onView(withId(R.id.event_detail_categoria)).check(matches(withText(CATEGORIA))); //Check event category
        onView(withId(R.id.event_detail_link)).check(matches(withText(LINK))); //Check event link
    }
}
