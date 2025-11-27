package com.esprim.Foyer.entities;

import java.io.Serializable;
import java.util.Objects;

public class ReservationId implements Serializable {
    private String idReservation;

    public ReservationId() {}

    public ReservationId(String idReservation) {
        this.idReservation = idReservation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationId that = (ReservationId) o;
        return Objects.equals(idReservation, that.idReservation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReservation);
    }
}
