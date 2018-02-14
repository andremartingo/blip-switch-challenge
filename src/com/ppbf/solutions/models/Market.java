package com.ppbf.solutions.models;

import java.math.BigDecimal;
import java.util.Objects;

public class Market {

    public String name;
    public long id;
    public BigDecimal odds;

    public Market(String name, long id, BigDecimal odds) {
        this.name = name;
        this.id = id;
        this.odds = odds;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, odds);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Market market = (Market) o;
        return id == market.id &&
            Objects.equals(name, market.name) &&
            Objects.equals(odds, market.odds);
    }

    @Override
    public String toString() {
        return "Market{" +
            "name='" + name + '\'' +
            ", id=" + id +
            ", odds=" + odds +
            '}';
    }
}
