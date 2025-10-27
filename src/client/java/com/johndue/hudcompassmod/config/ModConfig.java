package com.johndue.hudcompassmod.config;

public class ModConfig {
    public boolean compassEnabled = true;
    public boolean deathMarker = true;
    public int compassWidth = 50;
    public int compassFOV = 200;

    public double directionsBrightness = 0.8;
    public double directionsOpacity = 1;
    public NorthHighlightColors highlightNorth = NorthHighlightColors.Red;

    private static ModConfig INSTANCE = ConfigManager.load();
    public static ModConfig get() {
        return INSTANCE;
    }
}