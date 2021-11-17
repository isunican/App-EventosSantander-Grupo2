package com.isunican.eventossantander.presenter.events;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import android.os.Build;

import com.isunican.eventossantander.view.events.EventsActivity;
import com.isunican.eventossantander.view.events.IEventsContract;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1})
public class FilterMenuITest {

    private IEventsContract.View view = mock(EventsActivity.class);
    private IEventsContract.Presenter presenter;

    @Before
    public void setup() {
        presenter = new EventsPresenter(view);
    }

    /**
     * Historia de usuario: Anhadir boton para abrir el menu.
     * Identificador: "IT.1a"
     * Autor: Jesus Ortega Zorrilla
     */
    @Test
    public void closeFilterMenu() {
        presenter.onFilterMenuClicked(true);
        verify(view, times(1)).closeFilterMenuView();
    }

    /**
     * Historia de usuario: Anhadir boton para abrir el menu.
     * Identificador: "IT.1b"
     * Autor: Jesus Ortega Zorrilla
     */
    @Test
    public void openFilterMenu() {
        presenter.onFilterMenuClicked(false);
        verify(view, times(1)).openFilterMenuView();
    }
}
