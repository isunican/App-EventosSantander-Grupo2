package com.isunican.eventossantander.model;

import com.isunican.eventossantander.model.Event;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

import android.os.Build;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1})
public class EventTest {

    @Test
    public void testEventCreation() {
        Event event = new Event();
        event.setDescripcionAlternativa("desc alt");
        event.setDescripcion("desc");
        assertEquals("desc alt", event.getDescripcionAlternativa());
    }
}
