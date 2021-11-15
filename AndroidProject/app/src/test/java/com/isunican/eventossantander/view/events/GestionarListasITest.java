    package com.isunican.eventossantander.view.events;

    import static org.mockito.Mockito.times;
    import static org.mockito.Mockito.verify;
    import static org.mockito.Mockito.when;

    import android.content.Context;
    import android.content.SharedPreferences;
    import android.os.Build;

    import androidx.test.core.app.ApplicationProvider;

    import org.junit.Assert;
    import org.junit.Before;
    import org.junit.Rule;
    import org.junit.Test;
    import org.junit.runner.RunWith;
    import org.mockito.Mock;
    import org.mockito.junit.MockitoJUnit;
    import org.mockito.junit.MockitoRule;
    import org.robolectric.RobolectricTestRunner;
    import org.robolectric.annotation.Config;

    @RunWith(RobolectricTestRunner.class)
    @Config(sdk = {Build.VERSION_CODES.O_MR1})
    public class GestionarListasITest {

        private GestionarListas gestionarListas;
        private String nombreLista;

        private Context context = ApplicationProvider.getApplicationContext();

        @Rule
        public MockitoRule rule = MockitoJUnit.rule();

        @Before
        public void setup() {
            // Creacion de la clase a probar
            gestionarListas = new GestionarListas(context);
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
            Assert.assertEquals(nombreLista, "Conciertos");

            // Identificador: "UT.2b"
            nombreLista = gestionarListas.createList("Conciertos");
            Assert.assertEquals(nombreLista, "Conciertos(1)");

            // Identificador: "UT.2c"
            nombreLista = gestionarListas.createList("");
        }
    }
