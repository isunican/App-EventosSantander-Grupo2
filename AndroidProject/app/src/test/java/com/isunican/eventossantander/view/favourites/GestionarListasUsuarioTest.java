    package com.isunican.eventossantander.view.favourites;

    import static org.mockito.Mockito.verify;
    import static org.mockito.Mockito.when;

    import android.content.Context;
    import android.content.SharedPreferences;

    import com.isunican.eventossantander.model.Event;

    import org.junit.Assert;
    import org.junit.Before;
    import org.junit.Rule;
    import org.junit.Test;
    import org.mockito.Mock;
    import org.mockito.junit.MockitoJUnit;
    import org.mockito.junit.MockitoRule;

    import java.util.ArrayList;
    import java.util.List;

    public class GestionarListasUsuarioTest {
        private GestionarListasUsuario2 gestionFavs;
        private List<Event> events;

        @Mock
        private Context context;

        @Mock
        private SharedPreferences sharedPref;

        @Mock
        private SharedPreferences.Editor editor;

        @Rule
        public MockitoRule rule = MockitoJUnit.rule();

        @Before
        public void setup() {
            // Programa el comportamiento de los mocks.
            when (context.getSharedPreferences("favourites", Context.MODE_PRIVATE)).thenReturn(sharedPref);
            when (sharedPref.getString("favourites", "")).thenReturn("");
            when (sharedPref.edit()).thenReturn(editor);
            // Crea la clase a probar.
            gestionFavs = new GestionarListasUsuario2(context);

            // Lista de eventos con la que se van a hacer las pruebas.
            events = new ArrayList<>();
            Event e1 = new Event();
            e1.setCategoria("Cultura científica");
            e1.setIdentificador(0);
            Event e2 = new Event();
            e2.setIdentificador(1);
            Event e3 = new Event();
            e3.setCategoria("Cultura científica");
            e3.setIdentificador(2);
            Event e4 = new Event();
            e4.setIdentificador(3);
            events.add(e1);
            events.add(e2);
            events.add(e3);
            events.add(e4);
        }

        /**
         * Historia de Usuario: Marcar Evento.
         * Identificador: "UT.2".
         * Autor: Ivan Gonzalez del Pozo.
         */
        @Test
        public void testSetFavourite() {
            // Identificador: "UT.2a"
            gestionFavs.setFavourite(3, events);
            verify(editor).putString("favourites", "3,");

            // Identificador: "UT.2b"
            gestionFavs.setFavourite(0, events);
            verify(editor).putString("favourites", "0,");

            // Identificador: "UT.2c"
            try {
                gestionFavs.setFavourite(-1, events);
                Assert.fail();
            } catch (IndexOutOfBoundsException e) {
                Assert.assertTrue(true);    // Succes
            }

            // Identificador: "UT.2d"
            try {
                gestionFavs.setFavourite(9999999, events);
                Assert.fail();
            } catch (IndexOutOfBoundsException e) {
                Assert.assertTrue(true);    // Succes
            }
        }
    }
