package com.isunican.eventossantander.presenter.events;

import java.util.Map;

import okhttp3.internal.Util;

public class Options {
    private boolean orderChecked = false;
    private boolean filtersChecked = false;
    private final Map<String, Boolean> categoriesSelected;
    private final Utilities.OrderType orderTypeSelected;
    private final boolean dateFirst;

    public Options(Map<String, Boolean> categoriesSelected, Utilities.OrderType orderTypeSelected, boolean isDateFirst) {
        this.categoriesSelected = categoriesSelected;
        this.orderTypeSelected = orderTypeSelected;
        this.dateFirst = isDateFirst;
        if (!categoriesSelected.isEmpty()) {
            filtersChecked = true;
        }
        if (orderTypeSelected != null) {
            orderChecked = true;
        }
    }

    public Map<String, Boolean> getFilterOptions() {
        return categoriesSelected;
    }

    public Utilities.OrderType getOrderTypeOptions() {
        return orderTypeSelected;
    }

    public boolean isOrderChecked() {
        return orderChecked;
    }

    public boolean isFilterChecked() {
        return filtersChecked;
    }

    public boolean isDateFirst() {
        return dateFirst;
    }
}
