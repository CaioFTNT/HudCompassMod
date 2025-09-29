package com.johndue.hudcompassmod;

import com.johndue.hudcompassmod.helpers.Pins;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import net.minecraft.component.DataComponentTypes;

public class HUDCompassClient implements ClientModInitializer {
    private int lastInventoryHash = 0;
    private int tickCounter = 0;

    private int computeInventoryHash(PlayerInventory inventory) {
		int hash = 1;
		for (int i = 0; i < inventory.size(); i++) {
			ItemStack stack = inventory.getStack(i);
			hash = 31 * hash + (stack.isEmpty() ? 0 : stack.getItem().hashCode());
			hash = 31 * hash + stack.getCount();
			hash = 31 * hash + stack.getComponents().hashCode();
			if (
				stack.getItem() == Items.BUNDLE &&
				"Compass Bundle".equals(stack.getName().getString()) &&
				stack.get(DataComponentTypes.BUNDLE_CONTENTS) != null
			) {
				for (ItemStack bundleItem : stack.get(DataComponentTypes.BUNDLE_CONTENTS).iterate()) {
					hash = 31 * hash + (bundleItem.isEmpty() ? 0 : bundleItem.getItem().hashCode());
					hash = 31 * hash + bundleItem.getCount();
					hash = 31 * hash + bundleItem.getComponents().hashCode();
				}
			}
		}
		return hash;
	}

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                tickCounter++;
                if (tickCounter % 5 == 0) { // every 5 ticks
                    PlayerInventory inv = client.player.getInventory();
                    int currentHash = computeInventoryHash(inv);
                    if (currentHash != lastInventoryHash) {
                        Pins.inventoryChanged();
                        lastInventoryHash = currentHash;
                    }
                }
            }
        });
        HudElementRegistry.attachElementBefore(VanillaHudElements.CHAT, Identifier.of("hud-compass"), CompassRenderer::render);
    }
}