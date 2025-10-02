package com.johndue.hudcompassmod;

import java.util.Optional;

import com.johndue.hudcompassmod.helpers.CardinalDirections;
import com.johndue.hudcompassmod.helpers.CompassPin;
import com.johndue.hudcompassmod.helpers.MathMethods;
import com.johndue.hudcompassmod.helpers.Pins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.Window;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.Vec3d;

public class CompassRenderer {

    private static int compass_width = 250;

    private static MinecraftClient client = MinecraftClient.getInstance();
    private static TextRenderer textRenderer = client.textRenderer;
    private static Window compassWindow = client.getWindow();


    public static void render(DrawContext context, RenderTickCounter tickCounter) {
        int window_center_x = compassWindow.getScaledWidth()/2;
        Vec3d cam_pos = client.getCameraEntity().getRotationVector();
        double cam_angle = Math.atan2(cam_pos.x, cam_pos.z)/Math.PI;

        // Draw fixed directions
        for (CardinalDirections direction : CardinalDirections.values()) {
            int offset = MathMethods.getCompassOffset(cam_angle, direction.getOffset(), compass_width);
            int text_width = textRenderer.getWidth(direction.getLabel());

            // Draw only if its visible
            if (Math.abs(offset) - text_width/2 < compass_width/2) {
                int rgb_color = direction.getLabel().equals("N") ? 0xffffff : 0xaaaaaa;
                int rgba_color = (MathMethods.getAlphaFromOffset(offset, compass_width, text_width) << 24) | rgb_color;

                context.drawText(textRenderer, direction.getLabel(), window_center_x + offset - text_width/2, 2, rgba_color, false);
            }
        }

        // Draw all pins from lodestones
        for (CompassPin pin : Pins.getPins(client.player.getWorld())) {
            int offset = MathMethods.getCompassOffset(cam_angle, pin.getAngle(client.player.getPos()), compass_width);
            int text_width = textRenderer.getWidth(pin.getName());

            // Draw only if its visible
            if (Math.abs(offset) - text_width/2 < compass_width/2) {
                int rgba_color = (MathMethods.getAlphaFromOffset(offset, compass_width, text_width) << 24) | pin.getColor();

                context.drawText(textRenderer, pin.getName(), window_center_x + offset - text_width/2, 2, rgba_color, false);
            }
        }

        // Draw last death
        Optional<GlobalPos> lastDeath = client.player.getLastDeathPos();
        if (lastDeath.isPresent()) {
            GlobalPos lastDeathPos = lastDeath.get();
            if (lastDeathPos.dimension().equals(client.player.getWorld().getRegistryKey())) {

                Vec3d relative_pos = Vec3d.ofCenter(lastDeathPos.pos()).subtract(client.player.getPos());

                int offset = MathMethods.getCompassOffset(cam_angle, Math.atan2(relative_pos.x, relative_pos.z)/Math.PI, compass_width);
                int text_width = textRenderer.getWidth("☠");

                // Draw only if its visible
                if (Math.abs(offset) - text_width/2 < compass_width/2) {
                    int rgba_color = (MathMethods.getAlphaFromOffset(offset, compass_width, text_width) << 24) | 0x444444;

                    context.drawText(textRenderer, "☠", window_center_x + offset - text_width/2, 2, rgba_color, false);
                }
            }
        }
    }
}
