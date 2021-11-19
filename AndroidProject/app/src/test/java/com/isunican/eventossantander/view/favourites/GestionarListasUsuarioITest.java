    package com.isunican.eventossantander.view.favourites;

    import android.content.Context;
    import android.os.Build;

    import androidx.test.core.app.ApplicationProvider;

    import org.junit.After;
    import org.junit.Assert;
    import org.junit.Before;
    import org.junit.Rule;
    import org.junit.Test;
    import org.junit.runner.RunWith;
    import org.mockito.junit.MockitoJUnit;
    import org.mockito.junit.MockitoRule;
    import org.robolectric.RobolectricTestRunner;
    import org.robolectric.annotation.Config;

    @RunWith(RobolectricTestRunner.class)
    @Config(sdk = {Build.VERSION_CODES.O_MR1})
    public class GestionarListasUsuarioITest {

        private GestionarListasUsuario gestionarListas;
        private String nombreLista;

        private Context context = ApplicationProvider.getApplicationContext();

        @Rule
        public MockitoRule rule = MockitoJUnit.rule();

        @Before
        public void setUp() {
            // Creacion de la clase a probar
            gestionarListas = new GestionarListasUsuario(context);

            gestionarListas.cleanSetPreferences(context);
        }

        @After
        public void clean() {
            gestionarListas.cleanSetPreferences(context);
        }

        /**
         * Historia de Usuario: Crear lista.
         * Identificador: "IT.2".
         * Autora: Marta Obregon Ruiz.
         */
        @Test
        public void testCreateList() {
            // Identificador: "IT.2a"
            nombreLista = gestionarListas.createList("Conciertos");
            Assert.assertEquals("Conciertos", nombreLista);

            // Identificador: "IT.2b"
            nombreLista = gestionarListas.createList("Conciertos");
            Assert.assertEquals("Conciertos(1)", nombreLista);

            // Identificador: "IT.2c"
            nombreLista = gestionarListas.createList("");
            Assert.assertEquals("", nombreLista);

            // Identificador: "IT.2d"
            nombreLista = gestionarListas.createList(null);
            Assert.assertEquals(null, nombreLista);
        }
    }
