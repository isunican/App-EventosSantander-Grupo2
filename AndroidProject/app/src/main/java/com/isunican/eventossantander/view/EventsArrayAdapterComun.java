package com.isunican.eventossantander.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;

import com.isunican.eventossantander.R;
import com.isunican.eventossantander.model.Event;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

public interface EventsArrayAdapterComun {
    
    public static void assingData (TextView titleTxt,TextView categoriaTxt,TextView dateTxt,
                                   ImageView iconTxt,ImageView imageTxt, Event event, Context context){
        // Assign values to TextViews
        titleTxt.setText(event.getNombre());
        categoriaTxt.setText(event.getCategoria());
        dateTxt.setText(event.getFecha());

        // Assign values to TextViews
        titleTxt.setText(event.getNombre());
        dateTxt.setText(event.getFecha());

        // Assign image
        if (HtmlCompat.fromHtml(event.getNombre(), HtmlCompat.FROM_HTML_MODE_LEGACY).toString().isEmpty()) {
            imageTxt.setVisibility(View.GONE);
            iconTxt.setImageResource(getImageIdForEvent(event,context));
        }
        else{
            iconTxt.setVisibility(View.GONE);
            Picasso.get().load(event.getImagen()).into(imageTxt);
        }
    }
    public static void setImageFav(ImageButton btnEventFav,boolean favorito){
        if (favorito) {
            btnEventFav.setImageResource(R.drawable.ic_baseline_star_24);
            btnEventFav.setTag(R.drawable.ic_baseline_star_24);
        } else {
            btnEventFav.setImageResource(R.drawable.ic_baseline_star_border_24);
            btnEventFav.setTag(R.drawable.ic_baseline_star_border_24);
        }
    }

    /**
     * Determines the image resource id that must be used as the icon for a given event.
     * @param event The event the image must be used for
     * @return the image resource id for the event
     */
    private static int getImageIdForEvent(Event event, Context context) {
        int id = context.getResources().getIdentifier(
                getNormalizedCategory(event),
                "drawable",
                context.getPackageName());

        // fallback image in case of unrecognized category
        if (id == 0) {
            id = context.getResources().getIdentifier(
                    "otros",
                    "drawable",
                    context.getPackageName());
        }
        return id;
    }



    private static String getNormalizedCategory(Event event) {
        return StringUtils.deleteWhitespace(
                StringUtils.stripAccents(event.getCategoria()))
                .toLowerCase();
    }
}
