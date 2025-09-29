package com.johndue.hudcompassmod.helpers;

import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CompassPin {
    private GlobalPos pos;
    private String name;
    private int rgb_color;

    public CompassPin(GlobalPos pos, String name, int color) {
        this.pos = pos;
        this.name = name;
        this.rgb_color = color;
    }
    public CompassPin(RegistryKey<World> dimension, BlockPos block_pos, String name, int color) {
        this.pos = GlobalPos.create(dimension, block_pos);
        this.name = name;
        this.rgb_color = color;
    }
    public CompassPin(World dimension, BlockPos block_pos, String name, int color) {
        this.pos = GlobalPos.create(dimension.getRegistryKey(), block_pos);
        this.name = name;
        this.rgb_color = color;
    }

    public RegistryKey<World> getDimensionKey(){
        return this.pos.dimension();
    }
    public BlockPos getPos() {
        return this.pos.pos();
    }
    public String getName() {
        return this.name;
    }
    public int getColor() {
        return this.rgb_color;
    }

    public double getAngle(Vec3d player_pos) {
        Vec3d relative_pos = Vec3d.ofCenter(this.pos.pos()).subtract(player_pos);
        return Math.atan2(relative_pos.x, relative_pos.z)/Math.PI;
    }
}
