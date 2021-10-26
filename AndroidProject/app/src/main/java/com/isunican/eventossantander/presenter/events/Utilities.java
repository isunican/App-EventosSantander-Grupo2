package com.isunican.eventossantander.presenter.events;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

public class Utilities {

    public static final String CONNECTION_ERROR_MESSAGE = "Error de conexión a Internet. Verifique su configuración de red.";


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
        return builder.create();
    }
}
