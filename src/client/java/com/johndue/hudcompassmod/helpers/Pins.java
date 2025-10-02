package com.johndue.hudcompassmod.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import net.minecraft.client.MinecraftClient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;

public class Pins {
    private static MinecraftClient client = MinecraftClient.getInstance();
    private static PlayerInventory inventory = client.player.getInventory();
    
    private static ArrayList<CompassPin> pinList = new ArrayList<>();

    private static final Map<Item, Integer> BUNDLE_COLOR_MAP = new HashMap<>() {{
        put(Items.BUNDLE, 0xeb4034);
        put(Items.WHITE_BUNDLE, 0xFFFFFF);
        put(Items.ORANGE_BUNDLE, 0xf08d3a);
        put(Items.MAGENTA_BUNDLE, 0xB24CD8);
        put(Items.LIGHT_BLUE_BUNDLE, 0xc24eed);
        put(Items.YELLOW_BUNDLE, 0xE5E533);
        put(Items.LIME_BUNDLE, 0x7FCC19);
        put(Items.PINK_BUNDLE, 0xF27FA5);
        put(Items.GRAY_BUNDLE, 0x575757);
        put(Items.LIGHT_GRAY_BUNDLE, 0x999999);
        put(Items.CYAN_BUNDLE, 0x0fb5b8);
        put(Items.PURPLE_BUNDLE, 0x8f47c9);
        put(Items.BLUE_BUNDLE, 0x264be0);
        put(Items.BROWN_BUNDLE, 0x6e5238);
        put(Items.GREEN_BUNDLE, 0x81bd46);
        put(Items.RED_BUNDLE, 0xeb4034);
        put(Items.BLACK_BUNDLE, 0x191919);

    }};

    private static void parseCompass(ItemStack item, int color) {
        GlobalPos lodestoneGlobalPos = item.get(DataComponentTypes.LODESTONE_TRACKER).target().get();
        String pinName = item.getName().getLiteralString();

        CompassPin pin = new CompassPin(lodestoneGlobalPos, pinName != null ? pinName : "|", color);

        pinList.add(pin);
    }

    private static void processBundle(ItemStack bundle) {
        int bundleColor = BUNDLE_COLOR_MAP.getOrDefault(bundle.getItem(), 0xeb4034);

        bundle.get(DataComponentTypes.BUNDLE_CONTENTS).iterate().forEach(bundleItem -> {

            if (bundleItem.getItem().equals(Items.COMPASS)
                    && bundleItem.get(DataComponentTypes.LODESTONE_TRACKER) != null
                    && bundleItem.get(DataComponentTypes.LODESTONE_TRACKER).target().isPresent()) 
                {
                parseCompass(bundleItem, bundleColor);
            } else if (bundleItem.isIn(ItemTags.BUNDLES)) {
                processBundle(bundleItem);
            }
        });
    }

    public static void inventoryChanged() {
        if (!pinList.isEmpty()) pinList.clear();

        inventory = client.player.getInventory();

        inventory.forEach(itemStack -> {
            if (itemStack.getItem().equals(Items.COMPASS) && itemStack.get(DataComponentTypes.LODESTONE_TRACKER) != null && itemStack.get(DataComponentTypes.LODESTONE_TRACKER).target().isPresent()) {
                parseCompass(itemStack, 0xeb4034);

            } else if (itemStack.isIn(ItemTags.BUNDLES) && itemStack.getName().getString().toLowerCase().equals("compass bundle")) {
                processBundle(itemStack);

            }
        });
    }

    public static ArrayList<CompassPin> getPins() {
        return pinList;
    }
    public static ArrayList<CompassPin> getPins(World dimension) {
        return (ArrayList<CompassPin>)pinList.stream().filter(pin -> pin.getDimensionKey().equals(dimension.getRegistryKey())).collect(Collectors.toList());
    }
    public static ArrayList<CompassPin> getPins(RegistryKey<World> dimension) {
        return (ArrayList<CompassPin>)pinList.stream().filter(pin -> pin.getDimensionKey().equals(dimension)).collect(Collectors.toList());
    }
}
