package com.isunican.eventossantander.presenter.events;


import static org.junit.Assert.assertTrue;

import android.os.Build;

import com.isunican.eventossantander.presenter.events.Options;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.HashMap;
import java.util.Map;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1})
public class FilterMethodTest {

    private static Options options, options2;

    @BeforeClass
    public static void setup() {
        Map<String,Boolean> categories = new HashMap<>();
        Map<String,Boolean> categoriesEmpty = new HashMap<>();

        categories.put("Infantil", true);

        options = new Options(categories, null, false);
        options2 = new Options(categoriesEmpty, null, false);
    }

    @Test
    public void getFilterCategoriesTest() {
        assertTrue(options.getFilterOptions().get("Infantil"));

    }

    @Test
    public void getEmptyFilterCategoriesTest() {
        assertTrue(options2.getFilterOptions().isEmpty());

    }
}
