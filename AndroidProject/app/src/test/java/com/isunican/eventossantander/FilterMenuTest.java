package com.isunican.eventossantander;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import android.os.Build;

import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.presenter.events.EventsPresenter;
import com.isunican.eventossantander.view.events.EventsActivity;
import com.isunican.eventossantander.view.events.IEventsContract;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1})
public class FilterMenuTest {

    private IEventsContract.View view = mock(EventsActivity.class);
    private IEventsContract.Presenter presenter;

    @Before
    public void setup() {
        presenter = new EventsPresenter(view);
    }

    @Test
    public void closeFilterMenu() {
        presenter.onFilterMenuClicked(true);
        verify(view, times(1)).closeFilterMenuView();
    }

    @Test
    public void openFilterMenu() {
        presenter.onFilterMenuClicked(false);
        verify(view, times(1)).openFilterMenuView();
    }
}
