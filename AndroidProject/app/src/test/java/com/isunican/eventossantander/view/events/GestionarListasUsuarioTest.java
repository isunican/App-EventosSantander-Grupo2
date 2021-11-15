package com.isunican.eventossantander.view.events;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import com.isunican.eventossantander.view.favourites.GestionarListasUsuario;


public class GestionarListasUsuarioTest {

    private GestionarListasUsuario GestionarListasUsuario;
    private String nombreLista1;
    private String nombreLista2;
    private final String NOMBRE_LISTA_1 = "Lista1";

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
        // Programacion del comportamiento de los mocks
        when (context.getSharedPreferences("LISTS", Context.MODE_PRIVATE)).thenReturn(sharedPref);
        when (context.getSharedPreferences("LISTS", Context.MODE_PRIVATE).edit()).thenReturn(editor);
        when (sharedPref.contains("Conciertos")).thenReturn(false);
        when (sharedPref.edit()).thenReturn(editor);
        // Creacion de la clase a probar
        GestionarListasUsuario = new GestionarListasUsuario(context);
    }

    /**
     * Historia de Usuario: Crear lista.
     * Identificador: "UT.2".
     * Autora: Marta Obregon Ruiz.
     */
    @Test
    public void testCreateList() {
        // Identificador: "UT.2a"
        nombreLista1 = GestionarListasUsuario.createList("Conciertos");
        Assert.assertEquals(nombreLista1, "Conciertos");
        verify(editor).putString("Conciertos", "");
        verify(editor).putString("Conciertos", "Conciertos");
        verify(editor, times(2)).apply();

        // Identificador: "UT.2b"
        nombreLista1 = GestionarListasUsuario.createList("Conciertos");
        Assert.assertEquals(nombreLista1, "Conciertos(1)");
        verify(editor).putString("Conciertos(1)", "");
        verify(editor).putString("Conciertos(1)", "Conciertos(1)");
        verify(editor, times(2)).apply();

        // Identificador: "UT.2c"
        nombreLista1 = GestionarListasUsuario.createList("");
    }

    /**
     * Historia de Usuario: Anhadir evento a lista.
     * Identificador: "UT.2".
     * Autora: Sara Grela Carrera
     */
    @Test
    public void AddEventTest() {
        // Creamos la lista
        nombreLista2 = GestionarListasUsuario.createList(NOMBRE_LISTA_1);

        // Identificador: "UT.2a"
        GestionarListasUsuario.addEvent(1, any(), NOMBRE_LISTA_1);
        verify(editor).putString(NOMBRE_LISTA_1, "1,");

        // Identificador: "UT.2b"

        // Identificador: "UT.2c"
        nombreLista1 = GestionarListasUsuario.createList("");
    }
}
