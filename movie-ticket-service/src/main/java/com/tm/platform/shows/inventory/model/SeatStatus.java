package com.tm.platform.shows.inventory.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

public enum SeatStatus {
    AVAILABLE,
    LOCKED,
    BOOKED
}