package com.isunican.eventossantander.view.favourites;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
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
    private GestionarListasUsuario gestionarListasUsuario;
    private List<Event> events;
    private final String NOMBRE_LISTA_EXISTE = "Lista1";

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
        gestionarListasUsuario = new GestionarListasUsuario(context);

        // Lista de eventos con la que se van a hacer las pruebas.
        events = new ArrayList<>();
        Event e0 = new Event();
        e0.setCategoria("Cultura científica");
        e0.setIdentificador(0);
        Event e1 = new Event();
        e1.setIdentificador(1);
        Event e2 = new Event();
        e2.setCategoria("Cultura científica");
        e2.setIdentificador(2);
        Event e3 = new Event();
        e3.setIdentificador(3);
        events.add(e0);
        events.add(e1);
        events.add(e2);
        events.add(e3);
    }

    /**
     * Historia de Usuario: Marcar Evento.
     * Identificador: "UT.2".
     * Autor: Ivan Gonzalez del Pozo.
     */
    @Test
    public void testSetFavourite() {
        // Identificador: "UT.2a"
        gestionarListasUsuario.setFavourite(3, events);
        verify(editor).putString("favourites", "3,");

        // Identificador: "UT.2b"
        gestionarListasUsuario.setFavourite(0, events);
        verify(editor).putString("favourites", "0,");

        // Identificador: "UT.2c"
        try {
            gestionarListasUsuario.setFavourite(-1, events);
            Assert.fail();
        } catch (IndexOutOfBoundsException e) {
            Assert.assertTrue(true);    // Succes
        }

        // Identificador: "UT.2d"
        try {
            gestionarListasUsuario.setFavourite(9999999, events);
            Assert.fail();
        } catch (IndexOutOfBoundsException e) {
            Assert.assertTrue(true);    // Succes
        }
    }

    /**
     * Historia de Usuario: Anhadir evento a lista.
     * Identificador: "UT.2".
     * Autor: Sara Grela Carrera.
     */
    @Test
    public void testAddEvent() {

        String nombreListaNoExiste = "Lista2";

        when(sharedPref.edit()).thenReturn(editor);

        // Identificador: "UT.2a"
        when(context.getSharedPreferences(NOMBRE_LISTA_EXISTE, Context.MODE_PRIVATE)).thenReturn(sharedPref);
        when(sharedPref.getString(NOMBRE_LISTA_EXISTE, "")).thenReturn("");
        assertEquals(true,gestionarListasUsuario.addEvent(1, events, NOMBRE_LISTA_EXISTE));
        verify(editor).putString(NOMBRE_LISTA_EXISTE, "1,");

        // Identificador: "UT.2b"
        when(sharedPref.getString(NOMBRE_LISTA_EXISTE, "")).thenReturn("1,");
        assertEquals(false, gestionarListasUsuario.addEvent(1, events, NOMBRE_LISTA_EXISTE));
        verify(editor, never()).putString(NOMBRE_LISTA_EXISTE, "1,1,");

        // Identificador: "UT.2c"
        assertEquals(true, gestionarListasUsuario.addEvent(2, events, NOMBRE_LISTA_EXISTE));
        verify(editor).putString(NOMBRE_LISTA_EXISTE, "1,2,");

        // Identificador: "UT.2d"
        when(sharedPref.getString(NOMBRE_LISTA_EXISTE, "")).thenReturn("1,2,");
        assertEquals(true, gestionarListasUsuario.addEvent(0, events, NOMBRE_LISTA_EXISTE));
        verify(editor).putString(NOMBRE_LISTA_EXISTE, "1,2,0,");

        // Identificador: "UT.2e"
        when(sharedPref.getString(NOMBRE_LISTA_EXISTE, "")).thenReturn("1,2,0,");
        assertEquals(true, gestionarListasUsuario.addEvent(events.size()-1, events, NOMBRE_LISTA_EXISTE));
        String id = String.valueOf(events.size()-1) + ",";
        verify(editor).putString(NOMBRE_LISTA_EXISTE, "1,2,0," + id);

    }
}
