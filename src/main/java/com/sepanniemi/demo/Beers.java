package com.sepanniemi.demo;

import java.util.Arrays;
import java.util.List;

public record Beers(List<Beer> beers) {
    static Beers defaults() {
        List<Beer> beers = Arrays.asList(
                new Beer("NEDIPA", "KOVA LUU", "Sonnisaaren"),
                new Beer("IPA", "VIIKSISIEPPO", "Sonnisaaren")
        );
        return new Beers(beers);
    }
}
