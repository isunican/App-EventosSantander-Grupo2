package com.isunican.eventossantander.presenter.events;

import java.util.Set;

public class Options {
    private boolean orderChecked = false;
    private boolean filtersChecked = false;
    private final Set<String> categoriesSelected;
    private final EventsPresenter.OrderType orderTypeSelected;
    private final boolean dateFirst;

    public Options(Set<String> categoriesSelected, EventsPresenter.OrderType orderTypeSelected, boolean isDateFirst) {
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

    public Set<String> getFilterOptions() {
        return categoriesSelected;
    }

    public EventsPresenter.OrderType getOrderTypeOptions() {
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
