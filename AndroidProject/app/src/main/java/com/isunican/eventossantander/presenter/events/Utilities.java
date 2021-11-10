package com.isunican.eventossantander.presenter.events;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.EditText;
import android.widget.Toast;

public class Utilities {



    public enum OrderType { DATE_ASC, DATE_DESC }

    public static final String CONNECTION_ERROR_MESSAGE = "Error de conexión a Internet. Verifique su configuración de red.";
    public static final String EMPTY_FAVOURITE_MESSAGE = "No hay eventos favoritos.";
    public static final String ERROR_ADD_EVENT_LIST = "Ha ocurrido un error al introducir el evento a la lista";
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
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                break;
            case 2:
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                break;
            default:
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        }
        // Create the AlertDialog object and return it
        dialogo = builder.create();
        return builder.create();
    }

    /**
     * Crea un nuevo cuadro de dialogo segun los parametros especificados
     * @param context el contexto de la actividad
     * @param title el titulo del dialogo
     * @param numButtons si es uno, sera el de Aceptar; si son 2, sera el de Cancelar; en otro
     *                   caso, solo tendra el de Cancelar.
     * @return
     */
    public static Dialog createInputPopUp(Context context, String title, Integer numButtons) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        EditText input = new EditText(context);
        builder.setTitle(title);
        builder.setView(input);
        switch (numButtons) {
            case 1:
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                break;
            case 2:
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(context,"Se ha creado la lista " + input.getText() + " con éxito",Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                break;
            default:
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
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
