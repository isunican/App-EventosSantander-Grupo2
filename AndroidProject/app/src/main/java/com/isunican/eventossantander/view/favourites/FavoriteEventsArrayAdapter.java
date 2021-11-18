package com.isunican.eventossantander.view.favourites;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.isunican.eventossantander.R;
import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.view.EventsArrayAdapterComun;


import java.util.List;

public class FavoriteEventsArrayAdapter extends ArrayAdapter<Event> {

    private final List<Event> events;
    IFavoriteEventsContract.Presenter presenter;
    private IGestionarListasUsuario sharedPref;

    public FavoriteEventsArrayAdapter(@NonNull FavoriteEventsActivity activity, int resource, @NonNull List<Event> objects) {
        super(activity, resource, objects);
        sharedPref = activity.getSharedPref();
        presenter = activity.getPresenter();
        this.events = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Event event = events.get(position);
        int id = event.getIdentificador();

        // Create item view
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.events_listview_item, null);

        // Link subviews
        TextView titleTxt = view.findViewById(R.id.item_event_title);
        TextView categoriaTxt = view.findViewById(R.id.item_event_categoria);
        TextView dateTxt = view.findViewById(R.id.item_event_date);
        ImageView iconTxt = view.findViewById(R.id.item_event_icon);
        ImageView imageTxt = view.findViewById(R.id.item_event_image);
        ImageButton btnEventFav = view.findViewById(R.id.btn_event_fav);
        ImageButton btnAddEventList = view.findViewById(R.id.btn_add_list_event);
        LinearLayout container = view.findViewById(R.id.list_item_container);
        container.setOnClickListener(view1 -> presenter.onEventClicked(position));
        btnAddEventList.setVisibility(View.GONE);
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onEventClicked(position);
            }
        });

        // Coloco la imagen correspondiente dependiendo de si el evento estaba marcado como favorito o no
        EventsArrayAdapterComun.setImageFav(btnEventFav,sharedPref.isFavourite(id));


        EventsArrayAdapterComun.assingData(titleTxt,categoriaTxt,dateTxt,iconTxt,imageTxt,event,getContext());

        return view;
    }


}
