package com.isunican.eventossantander.presenter.events;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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
public class UtilitiesTest {

    private Utilities utilities;
    private Dialog dialog;

    private Context context = ApplicationProvider.getApplicationContext();

    @Mock
    AlertDialog.Builder builder;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Before
    public void setup() {
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
        // Identificador: "UT.1a"
        // Compruebo que el dialogo se crea correctamente sin generar ninguna excepcion
        try {
            dialog = utilities.createListPopUp(context, "Introduzca el título de la lista a crear", 2);
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.fail();
        }
        // Compruebo que no es nulo
        Assert.assertTrue(dialog != null);

        // Identificador: "UT.1b"
        try {
            dialog = utilities.createListPopUp(context, "", 2);
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertTrue(dialog != null);

        // Identificador: "UT.1c"
        try {
            dialog = utilities.createListPopUp(context, "Introduzca el título de la lista a crear", 2);
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertTrue(dialog != null);

        // Identificador: "UT.1d"
        try {
            dialog = utilities.createListPopUp(context, "Introduzca el título de la lista a crear", 3);
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertTrue(dialog != null);
    }
}
