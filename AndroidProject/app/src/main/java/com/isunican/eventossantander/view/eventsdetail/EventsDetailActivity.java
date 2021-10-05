package com.isunican.eventossantander.view.eventsdetail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
        eventTitleText.setText(event.getNombre());                      // title
        Picasso.get().load(event.getImagen()).into(eventImageImage);    // image
        eventDateText.setText(event.getFecha());                        // date
        eventDescriptionText.setText(event.getDescripcion());           // description
        eventCategoriaText.setText(event.getCategoria());               // categoria
            // TODO switch con cada posibilidad y su color correspondiente
        eventLinkText.setText(event.getEnlace());                       // enlace
    }
}