package com.isunican.eventossantander.view.eventsdetail;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import com.isunican.eventossantander.R;
import com.isunican.eventossantander.model.Event;
import com.squareup.picasso.Picasso;

public class EventsDetailActivity extends AppCompatActivity {

    public static final String INTENT_EVENT = "INTENT_EVENT";
    private TextView eventTitleText;
    private ImageView eventImageImage;
    private TextView eventDateText;
    private TextView eventDescriptionText;
    private TextView eventCategoriaText;
    private TextView eventLinkText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_detail);

        // Link to view elements
        eventTitleText = findViewById(R.id.event_detail_title);
        eventImageImage = findViewById(R.id.event_detail_imagen);
        eventDateText = findViewById(R.id.event_detail_date);
        eventDescriptionText = findViewById(R.id.event_detail_description);
        eventCategoriaText = findViewById(R.id.event_detail_categoria);
        eventLinkText = findViewById(R.id.event_detail_link);

        // Get Event from the intent that triggered this activity
        Event event = getIntent().getExtras().getParcelable(INTENT_EVENT);

        rellenarInformacionEvento(event);

    }

    private void rellenarInformacionEvento(Event event){
        // Set information
        if (event.getNombre().isEmpty()){
            eventTitleText.setVisibility(View.GONE);
        } else {
            eventTitleText.setText(HtmlCompat.fromHtml(event.getNombre(), HtmlCompat.FROM_HTML_MODE_LEGACY));   // title
        }

        if (event.getImagen().isEmpty()){
            eventImageImage.setVisibility(View.GONE);
        } else {
            Picasso.get().load(event.getImagen()).into(eventImageImage);        // image
        }

        if (event.getFecha().isEmpty()){
            eventDateText.setVisibility(View.GONE);
        } else {
            eventDateText.setText(event.getFecha());                            // date
        }

        if (event.getDescripcion().isEmpty()){
            eventDescriptionText.setVisibility(View.GONE);
        } else {
            eventDescriptionText.setText(HtmlCompat.fromHtml(event.getDescripcion(), HtmlCompat.FROM_HTML_MODE_LEGACY));// description
        }

        if (event.getCategoria().isEmpty()){
            eventCategoriaText.setVisibility(View.GONE);
        } else {
            eventCategoriaText.setText(event.getCategoria());                   // Categoria
        }

        if (event.getEnlace().isEmpty()){
            eventLinkText.setVisibility(View.GONE);
        } else {
            // TODO switch con cada posibilidad y su color correspondiente
            eventLinkText.setText(event.getEnlace());                           // Enlace
            eventLinkText.setMovementMethod(LinkMovementMethod.getInstance());  // Hace el enlace seleccionable
        }

    }
}