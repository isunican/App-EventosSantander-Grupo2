package com.isunican.eventossantander.view.events;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.content.SharedPreferences;

import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.view.favourites.GestionarListasUsuario;

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

    private GestionarListasUsuario GestionarListasUsuario;
    private String nombreLista1;
    private final String NOMBRE_LISTA_EXISTE = "Lista1";
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
        // Lista de eventos
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

        // Creacion de la clase a probar
        GestionarListasUsuario = new GestionarListasUsuario(context);
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
        assertEquals(GestionarListasUsuario.addEvent(1, events, NOMBRE_LISTA_EXISTE), true);
        verify(editor).putString(NOMBRE_LISTA_EXISTE, "1,");

        // Identificador: "UT.2b"
        when(context.getSharedPreferences(NOMBRE_LISTA_EXISTE, Context.MODE_PRIVATE)).thenReturn(sharedPref);
        when(sharedPref.getString(NOMBRE_LISTA_EXISTE, "")).thenReturn("1,");
        assertEquals(GestionarListasUsuario.addEvent(1, events, NOMBRE_LISTA_EXISTE), false);
        verify(editor, never()).putString(NOMBRE_LISTA_EXISTE, "1,1,");

        // Identificador: "UT.2c"
        when(context.getSharedPreferences(NOMBRE_LISTA_EXISTE, Context.MODE_PRIVATE)).thenReturn(sharedPref);
        when(sharedPref.getString(NOMBRE_LISTA_EXISTE, "")).thenReturn("1,");
        assertEquals(GestionarListasUsuario.addEvent(2, events, NOMBRE_LISTA_EXISTE), true);
        verify(editor).putString(NOMBRE_LISTA_EXISTE, "1,2,");

        // Identificador: "UT.2d"
        when(context.getSharedPreferences(NOMBRE_LISTA_EXISTE, Context.MODE_PRIVATE)).thenReturn(sharedPref);
        when(sharedPref.getString(NOMBRE_LISTA_EXISTE, "")).thenReturn("1,2,");
        assertEquals(GestionarListasUsuario.addEvent(0, events, NOMBRE_LISTA_EXISTE), true);
        verify(editor).putString(NOMBRE_LISTA_EXISTE, "1,2,0,");

        // Identificador: "UT.2e"
        when(context.getSharedPreferences(NOMBRE_LISTA_EXISTE, Context.MODE_PRIVATE)).thenReturn(sharedPref);
        when(sharedPref.getString(NOMBRE_LISTA_EXISTE, "")).thenReturn("1,2,0,");
        assertEquals(GestionarListasUsuario.addEvent(events.size()-1, events, NOMBRE_LISTA_EXISTE), true);
        String id = String.valueOf(events.size()-1) + ",";
        verify(editor).putString(NOMBRE_LISTA_EXISTE, "1,2,0," + id);

    }

}
