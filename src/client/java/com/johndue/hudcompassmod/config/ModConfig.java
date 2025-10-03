package com.johndue.hudcompassmod.config;

public class ModConfig {
    public boolean compassEnabled = true;
    public boolean deathMarker = true;
    public int compassWidth = 50;
    public int compassFOV = 200;

    private static ModConfig INSTANCE = ConfigManager.load();
    public static ModConfig get() {
        return INSTANCE;
    }
}