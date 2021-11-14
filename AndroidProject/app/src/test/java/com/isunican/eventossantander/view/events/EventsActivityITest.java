    package com.isunican.eventossantander.view.events;

    import static org.mockito.Mockito.times;
    import static org.mockito.Mockito.verify;
    import static org.mockito.Mockito.when;

    import android.content.Context;
    import android.content.SharedPreferences;
    import android.view.Menu;
    import android.view.MenuInflater;
    import android.view.MenuItem;
    import android.widget.Toolbar;

    import com.google.android.material.navigation.NavigationView;
    import com.isunican.eventossantander.R;
    import com.isunican.eventossantander.presenter.events.Utilities;

    import org.junit.Assert;
    import org.junit.Before;
    import org.junit.Rule;
    import org.junit.Test;
    import org.mockito.Mock;
    import org.mockito.junit.MockitoJUnit;
    import org.mockito.junit.MockitoRule;


    public class EventsActivityITest {

        private EventsActivity eventsActivity;
        private Utilities utilities;
        private Context context;
        private Menu menu;
        private MenuItem item;
        private boolean result;

        @Before
        public void setup() {
            // Creacion de la clase a probar
            eventsActivity = new EventsActivity();
            // Creacion de los objetos auxiliares
            eventsActivity.getMenuInflater().inflate(R.menu.menu, menu);
            item = menu.findItem(R.id.crear_lista);
        }

        /**
         * Historia de Usuario: Crear lista.
         * Identificador: "IT.1".
         * Autora: Marta Obregon Ruiz.
         */
        @Test
        public void testOnOptionsItemSelected() {
            // Identificador: "IT.1a"
            result = eventsActivity.onOptionsItemSelected(item);
            Assert.assertEquals(result, true);
            //verify(utilities).createInputPopUp(this, "Introduzca el título de la lista a crear", 2);
        }
    }
