package com.isunican.eventossantander.view.events;

import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.isunican.eventossantander.R;
import com.isunican.eventossantander.model.Event;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class EventArrayAdapter extends ArrayAdapter<Event> {

    private final List<Event> events;
    private final EventsActivity activity;

    public EventArrayAdapter(@NonNull EventsActivity activity, int resource, @NonNull List<Event> objects) {
        super(activity, resource, objects);
        this.activity = activity;
        this.events = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Event event = events.get(position);

        // Create item view
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.events_listview_item, null);

        // Link subviews
        TextView titleTxt = view.findViewById(R.id.item_event_title);
        TextView categoriaTxt = view.findViewById(R.id.item_event_categoria);
        TextView dateTxt = view.findViewById(R.id.item_event_date);
        ImageView iconTxt = view.findViewById(R.id.item_event_icon);
        ImageView imageTxt = view.findViewById(R.id.item_event_image);

        // Assign values to TextViews
        titleTxt.setText(event.getNombre());
        categoriaTxt.setText(event.getCategoria());
        dateTxt.setText(event.getFecha());



        // Assign values to TextViews
        titleTxt.setText(event.getNombre());
        dateTxt.setText(event.getFecha());

        // Assign image
        if (Html.fromHtml(event.getNombre()).toString().isEmpty()) {
            imageTxt.setVisibility(View.GONE);
            iconTxt.setImageResource(getImageIdForEvent(event));
        }
        else{
            iconTxt.setVisibility(View.GONE);
            Picasso.get().load(event.getImagen()).into(imageTxt);
        }
        return view;
    }

    /**
     * Determines the image resource id that must be used as the icon for a given event.
     * @param event
     * @return the image resource id for the event
     */
    private int getImageIdForEvent(Event event) {
        int id = getContext().getResources().getIdentifier(
                getNormalizedCategory(event),
                "drawable",
                getContext().getPackageName());

        // fallback image in case of unrecognized category
        if (id == 0) {
            id = getContext().getResources().getIdentifier(
                    "otros",
                    "drawable",
                    getContext().getPackageName());
        }
        return id;
    }

    private static String getNormalizedCategory(Event event) {
        return StringUtils.deleteWhitespace(
                StringUtils.stripAccents(event.getCategoria()))
                .toLowerCase();
    }
}
