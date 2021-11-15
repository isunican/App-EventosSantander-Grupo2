    package com.isunican.eventossantander.presenter.events;

    import android.app.AlertDialog;
    import android.app.Dialog;
    import android.content.Context;

    import org.junit.Before;
    import org.junit.Rule;
    import org.junit.Test;
    import org.mockito.Mock;
    import org.mockito.junit.MockitoJUnit;
    import org.mockito.junit.MockitoRule;


    public class UtilitiesTest {

        private Utilities utilities;
        private Dialog dialog;

        @Mock
        private Context context;

        @Mock
        AlertDialog.Builder builder;

        @Rule
        public MockitoRule rule = MockitoJUnit.rule();

        @Before
        public void setup() {
            // Programacion del comportamiento de los mocks


            // Creacion de la clase a probar
            utilities = new Utilities();
        }

        /**
         * Historia de Usuario: Crear lista.
         * Identificador: "UT.1".
         * Autora: Marta Obregon Ruiz.
         */
        @Test
        public void testCreateListPopUp() {
            /*

            alternativamente, a ese editText que creas, al que llamas "input"
            le puedes asignar desde el propio código un tag
            (setTag)
            y luego en el test usar un matcher por tag
            (withTag creo que es)
             */
            // Identificador: "UT.1a"
            dialog = utilities.createListPopUp(context, "Introduzca el título de la lista a crear", 2);

            // Assert.assertTrue(dialog.get
            //Assert.assertEquals(dialog.);
        }
    }
