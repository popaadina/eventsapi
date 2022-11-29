package com.app.eventsmanagementapi.dto;

import java.util.Date;
import java.util.List;

public class Filter {

    private List<String> categories;

    private List<Date> dates;

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<Date> getDates() {
        return dates;
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
    }
}
