package com.isunican.eventossantander.view.events;

    import android.annotation.SuppressLint;
    import android.app.AlertDialog;
    import android.app.Dialog;
    import android.content.Context;
    import android.content.DialogInterface;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ArrayAdapter;
    import android.widget.ImageButton;
    import android.widget.ImageView;
    import android.widget.LinearLayout;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.core.text.HtmlCompat;

    import com.isunican.eventossantander.R;
    import com.isunican.eventossantander.model.Event;
    import com.isunican.eventossantander.view.EventsArrayAdapterComun;
    import com.isunican.eventossantander.view.favourites.IGestionarListasUsuario;
    import com.squareup.picasso.Picasso;

    import org.apache.commons.lang3.StringUtils;

    import java.util.List;

    public class EventArrayAdapter extends ArrayAdapter<Event> {

        private IGestionarListasUsuario sharedPref;
        IEventsContract.Presenter presenter;
        private final List<Event> events;

        public EventArrayAdapter(@NonNull EventsActivity activity, int resource, @NonNull List<Event> objects) {
            super(activity, resource, objects);
            this.events = objects;
            sharedPref = activity.getSharedPref();
            presenter = activity.getPresenter();
        }

        @SuppressLint("ResourceType")
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
            ImageButton btnAddList = view.findViewById(R.id.btn_add_list_event);
            LinearLayout container = view.findViewById(R.id.list_item_container);

            container.setOnClickListener(view12 -> presenter.onEventClicked(position));

            // Coloco la imagen correspondiente dependiendo de si el evento estaba marcado como favorito o no
            boolean favorito = sharedPref.isFavourite(id);
            EventsArrayAdapterComun.setImageFav(btnEventFav,favorito);

            EventsArrayAdapterComun.assingData(titleTxt,categoriaTxt,dateTxt,iconTxt,imageTxt,event,getContext());




            //Handler to control the addList button


            btnAddList.setOnClickListener(view1 -> {
                int eventId = position;
                //Obtengo listas de favoritos creadas
                CharSequence[] listas = sharedPref.getLists().toArray(new CharSequence[0]);


                //Creo popup con las listas obtenidas
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Escoge una lista para añadir el evento");
                builder.setItems(listas, (dialogInterface, i) -> {
                    String listaEscogida = listas[i].toString();
                    if(presenter.onAddEventClicked(eventId,sharedPref,listaEscogida)){
                        Toast.makeText(getContext(),"Se ha añadido un evento a la lista " + listaEscogida,Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancelar", (dialogInterface, i) -> dialogInterface.cancel());
                Dialog dialog = builder.create();
                dialog.show();
            });

            // Handler to control the favourite button
            btnEventFav.setOnClickListener(view12 -> {
                int eventId = position;

                if (!favorito) {
                    presenter.onFavouriteClicked(eventId, favorito, sharedPref);
                    btnEventFav.setImageResource(R.drawable.ic_baseline_star_24);
                    btnEventFav.setTag(R.drawable.ic_baseline_star_24);

                } else {
                    // No implementado
                }
            });

            return view;
        }
    }

