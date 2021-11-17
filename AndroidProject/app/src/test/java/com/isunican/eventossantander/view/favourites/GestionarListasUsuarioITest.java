    package com.isunican.eventossantander.view.favourites;

    import static org.mockito.Mockito.verify;

    import android.content.Context;
    import android.os.Build;

    import androidx.test.core.app.ApplicationProvider;

    import com.isunican.eventossantander.view.favourites.GestionarListasUsuario;

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
         * Identificador: "UT.2".
         * Autora: Marta Obregon Ruiz.
         */
        @Test
        public void testCreateList() {
            // Identificador: "UT.2a"
            nombreLista = gestionarListas.createList("Conciertos");
            Assert.assertEquals("Conciertos", nombreLista);

            // Identificador: "UT.2b"
            nombreLista = gestionarListas.createList("Conciertos");
            Assert.assertEquals("Conciertos(1)", nombreLista);

            // Identificador: "UT.2c"
            nombreLista = gestionarListas.createList("");
            Assert.assertEquals("", nombreLista);
        }
    }
