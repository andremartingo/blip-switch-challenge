package com.ppbf.solutions.models;

import java.util.Objects;

public class Event {

    public String name;
    public Market market;

    public Event(String name, Market market) {
        this.name = name;
        this.market = market;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, market);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Event event = (Event) o;
        return Objects.equals(name, event.name) &&
            Objects.equals(market, event.market);
    }

    @Override
    public String toString() {
        return "Event{" +
            "name='" + name + '\'' +
            ", market=" + market +
            '}';
    }
}
