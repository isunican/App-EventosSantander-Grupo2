package com.isunican.eventossantander;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.isunican.eventossantander.presenter.events.EventsPresenter;
import com.isunican.eventossantander.presenter.events.Options;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class OrderMethodTest {

    private static Options options, options2;

    @BeforeClass
    public static void setup() {
        Map<String,Boolean> categories = new HashMap<>();

        options = new Options(categories, EventsPresenter.OrderType.DATE_ASC, false);
        options2 = new Options(categories, null, false);
    }

    @Test
    public void getOrderCategoriesTest() {
        assertEquals(EventsPresenter.OrderType.DATE_ASC, options.getOrderTypeOptions());
    }

    @Test
    public void getNullOrderCategoriesTest() {
        assertNull(options2.getOrderTypeOptions());
    }
}
