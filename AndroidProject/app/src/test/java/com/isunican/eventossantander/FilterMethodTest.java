package com.isunican.eventossantander;

import com.isunican.eventossantander.presenter.events.Options;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;


public class FilterMethodTest {

    private static Options options, options2;

    @BeforeClass
    public static void setup() {
        Set<String> categories = new HashSet<>();
        Set<String> categoriesEmpty = new HashSet<>();

        categories.add("Infantil");

        options = new Options(categories, null, false);
        options2 = new Options(categoriesEmpty, null, false);
    }

    @Test
    public void getFilterCategoriesTest() {
        assert(options.getFilterOptions().contains("Infantil"));
    }

    @Test
    public void getEmptyFilterCategoriesTest() {
        assert(options2.getFilterOptions().isEmpty());
    }
}
