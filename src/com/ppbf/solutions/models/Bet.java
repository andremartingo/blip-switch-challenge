package com.ppbf.solutions.models;

import java.util.Objects;

public class Bet {

    public String competitionName;
    public Event event;

    public Bet(String competitionName, Event event) {
        this.competitionName = competitionName;
        this.event = event;
    }

    @Override
    public int hashCode() {
        return Objects.hash(competitionName, event);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bet that = (Bet) o;
        return Objects.equals(competitionName, that.competitionName) &&
            Objects.equals(event, that.event);
    }

    @Override
    public String toString() {
        return "Bet{" +
            "competitionName='" + competitionName + '\'' +
            ", event=" + event +
            '}';
    }
}
