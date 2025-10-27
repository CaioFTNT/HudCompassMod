package com.johndue.hudcompassmod.config;

public enum NorthHighlightColors {
    White(0xffffff),
    Black(0x000000),
    No_highlight(null),
    Red(0xff0000),
    Green(0x00ff00),
    Blue(0x0000ff);

    private final Integer color;

    NorthHighlightColors(Integer color) {
        this.color = color;
    }

    public Integer getColor() { return this.color; }
}
