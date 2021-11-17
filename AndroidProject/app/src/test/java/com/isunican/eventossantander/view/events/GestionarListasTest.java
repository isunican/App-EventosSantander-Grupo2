    package com.isunican.eventossantander.view.events;

    import static org.mockito.ArgumentMatchers.anyInt;
    import static org.mockito.ArgumentMatchers.anyString;
    import static org.mockito.Mockito.times;
    import static org.mockito.Mockito.verify;
    import static org.mockito.Mockito.when;

    import android.content.Context;
    import android.content.SharedPreferences;

    import com.isunican.eventossantander.view.favourites.GestionarListasUsuario;
    import com.isunican.eventossantander.view.favourites.GestionarListasUsuario2;

    import org.junit.After;
    import org.junit.Assert;
    import org.junit.Before;
    import org.junit.Rule;
    import org.junit.Test;
    import org.mockito.Mock;
    import org.mockito.junit.MockitoJUnit;
    import org.mockito.junit.MockitoRule;


    public class GestionarListasTest {

        private GestionarListasUsuario gestionarListas;
        private String nombreLista;

        @Mock
        private Context context;

        @Mock
        private SharedPreferences sharedPref;

        @Mock
        private SharedPreferences.Editor editor;

        @Rule
        public MockitoRule rule = MockitoJUnit.rule();

        @Before
        public void setUp() {
            // Programacion del comportamiento de los mocks
            when(context.getSharedPreferences("LISTS", Context.MODE_PRIVATE)).thenReturn(sharedPref);
            when(sharedPref.contains(anyString())).thenReturn(false).thenReturn(true);
            when(context.getSharedPreferences("Conciertos", Context.MODE_PRIVATE)).thenReturn(sharedPref);
            when(sharedPref.edit()).thenReturn(editor);
            when(sharedPref.getInt(anyString(), anyInt())).thenReturn(0);
            when(context.getSharedPreferences("Conciertos(1)", Context.MODE_PRIVATE)).thenReturn(sharedPref);

            // Creacion de la clase a probar
            gestionarListas = new GestionarListasUsuario(context);
        }


        /**
         * Historia de Usuario: Crear lista.
         * Identificador: "UT.2".
         * Autora: Marta Obregon Ruiz.
         */
        @Test
        public void testCreateList() {
            // Identificador: "UT.2a"
            nombreLista = gestionarListas.createList("Conciertos");
            Assert.assertEquals("Conciertos", nombreLista);

            verify(editor).putString("Conciertos", "");
            verify(editor).putInt("Conciertos", -1);
            verify(editor).putInt("Conciertos", 0);
            verify(editor, times(2)).apply();

            // Identificador: "UT.2b"
            nombreLista = gestionarListas.createList("Conciertos");
            Assert.assertEquals("Conciertos(1)", nombreLista);

            verify(editor).putString("Conciertos(1)", "");
            verify(editor).putInt("Conciertos(1)", -1);

            // Identificador: "UT.2c"
            nombreLista = gestionarListas.createList("");
            Assert.assertEquals("", nombreLista);
        }
    }
