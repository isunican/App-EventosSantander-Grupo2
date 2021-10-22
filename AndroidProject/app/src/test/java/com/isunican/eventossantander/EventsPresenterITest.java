package com.isunican.eventossantander;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

import static java.lang.Thread.sleep;

import android.os.Build;

import androidx.test.espresso.IdlingRegistry;

import com.isunican.eventossantander.model.Event;
import com.isunican.eventossantander.model.EventsRepository;
import com.isunican.eventossantander.presenter.events.EventsPresenter;
import com.isunican.eventossantander.view.events.EventsActivity;
import com.isunican.eventossantander.view.events.IEventsContract;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1})
public class EventsPresenterITest {
    @Mock
    private IEventsContract.View view;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    private IEventsContract.Presenter presenter;

    @Test
    public void loadEventsCorrect() throws InterruptedException {
        ArgumentCaptor<List<Event>> eventos = ArgumentCaptor.forClass(List.class) ;
        EventsRepository.setLocalSource();
        presenter = new EventsPresenter(view);
        sleep(5000);
        verify(view).onEventsLoaded(eventos.capture());
        assertEquals(345, eventos.getValue().size());

    }

    @Test
    public void loadEventsNoCorret() throws InterruptedException {
        ArgumentCaptor<List<Event>> eventos = ArgumentCaptor.forClass(List.class) ;
        EventsRepository.setFakeSource();
        presenter = new EventsPresenter(view);
        sleep(5000);
        verify(view).onLoadError();
    }
}
