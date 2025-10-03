package com.johndue.hudcompassmod;

import com.johndue.hudcompassmod.config.ConfigScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class HUDCompassModMenu implements ModMenuApi {
    public HUDCompassModMenu() {
    }

    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (screen) -> {
            return new ConfigScreen(screen);
        };
    }
}
