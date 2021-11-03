package com.isunican.eventossantander;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import android.os.Build;

import com.isunican.eventossantander.presenter.events.EventsPresenter;
import com.isunican.eventossantander.presenter.events.Options;
import com.isunican.eventossantander.presenter.events.Utilities;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.HashMap;
import java.util.Map;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1})
public class OrderMethodTest {

    private static Options options, options2;

    @BeforeClass
    public static void setup() {
        Map<String,Boolean> categories = new HashMap<>();

        options = new Options(categories, Utilities.OrderType.DATE_ASC, false);
        options2 = new Options(categories, null, false);
    }

    @Test
    public void getOrderCategoriesTest() {
        assertEquals(Utilities.OrderType.DATE_ASC, options.getOrderTypeOptions());
    }

    @Test
    public void getNullOrderCategoriesTest() {
        assertNull(options2.getOrderTypeOptions());
    }
}
