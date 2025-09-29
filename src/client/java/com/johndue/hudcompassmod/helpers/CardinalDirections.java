package com.johndue.hudcompassmod.helpers;

public enum CardinalDirections {
    N("N", 1.0),
    NE("NE", 0.75),
    E("E", 0.5),
    SE("SE", 0.25),
    S("S", 0.0),
    SW("SW", -0.25),
    W("W", -0.5),
    NW("NW", -0.75);

    private final String label;
    private final double offset;

    CardinalDirections(String label, double offset) {
        this.label = label;
        this.offset = offset;
    }

    public String getLabel() {
        return label;
    }
    public double getOffset() {
        return offset;
    }
    public boolean isOrdinal() {
        return this.offset % 0.5 != 0;
    }
}