package com.isunican.eventossantander.presenter.events;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.EditText;
import android.widget.Toast;

import com.isunican.eventossantander.view.favourites.GestionarListasUsuario;

public class Utilities {

    public enum OrderType { DATE_ASC, DATE_DESC }

    public static final String CONNECTION_ERROR_MESSAGE = "Error de conexión a Internet. Verifique su configuración de red.";
    public static final String EMPTY_FAVOURITE_MESSAGE = "No hay eventos favoritos.";
    public static final String ACEPTAR = "Aceptar";
    public static final String CANCELAR = "Cancelar";
    public static final String ERROR_EVENT_ALREADY_EXISTS = "El evento seleccionado ya está en la lista";
    public static final String ERROR_EVENT_INDEX_OUT_OF_BOUNDS = "El evento seleccionado ya está en la lista";
    private static Dialog dialogo;

    /**
     * Crea un nuevo cuadro de dialogo segun los parametros especificados
     * @param context el contexto de la actividad
     * @param message el titulo del dialogo
     * @param numButtons si es uno, sera el de Aceptar; si son 2, sera el de Cancelar; en otro
     *                   caso, solo tendra el de Cancelar.
     * @return
     */
    public static Dialog createPopUp(Context context, String message, Integer numButtons) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        switch (numButtons) {
            case 1:
                builder.setPositiveButton(ACEPTAR, (dialog, id) -> {
                    //Not implemented
                });
                break;
            case 2:
                builder.setPositiveButton(ACEPTAR, (dialog, id) -> {
                    //Not implemented
                });
                builder.setNegativeButton(CANCELAR, (dialog, id) -> {
                    //Not implemented
                });
                break;
            default:
                builder.setNegativeButton(CANCELAR, (dialog, id) -> {
                    //Not implemented
                });
        }
        // Create the AlertDialog object and return it
        dialogo = builder.create();
        return builder.create();
    }

    /**
     * Crea un nuevo cuadro de dialogo para crear una lista
     * @param context el contexto de la actividad
     * @param title el titulo del dialogo
     * @param numButtons si es uno, sera el de Aceptar; si son 2, sera el de Cancelar; en otro
     *                   caso, solo tendra el de Cancelar.
     * @return
     */
    public static Dialog createListPopUp(Context context, String title, Integer numButtons) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        EditText input = new EditText(context);
        final String[] result = new String[1];
        input.setTag("InputDialog");
        GestionarListasUsuario gestionarListas = new GestionarListasUsuario(context);
        builder.setTitle(title);
        builder.setView(input);
        switch (numButtons) {
            case 1:
                builder.setPositiveButton(ACEPTAR, (dialog, id) -> {
                    //Not implemented
                });
                break;
            case 2:
                builder.setPositiveButton(ACEPTAR, (dialog, id) -> {
                    result[0] = gestionarListas.createList(input.getText().toString());
                    if(result[0].isEmpty()) {
                        Toast.makeText(context, "No se ha creado la lista, introduzca un nombre válido.", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(context, "Se ha creado la lista " + result[0] + " con éxito", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton(CANCELAR, (dialog, id) -> {
                    //Not implemented
                });
                break;
            default:
                builder.setNegativeButton(CANCELAR, (dialog, id) -> {
                    //Not implemented
                });
        }
        // Create the AlertDialog object and return it
        dialogo = builder.create();
        return builder.create();
    }

    /**
     * Only for test purposes
     * @param context
     * @return the dialog created
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public static Dialog getDialog() {
        return dialogo;
    }
}
