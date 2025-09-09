package net.night.grasses.enums;

import net.minecraft.util.StringRepresentable;

public enum GrassesQuarterProperty implements StringRepresentable {
    SOUTH_EAST("south_east"),
    SOUTH_WEST("south_west"),
    NORTH_WEST("north_west"),
    NORTH_EAST("north_east");

    private final String name;

    private GrassesQuarterProperty(String p_61743_) {
        this.name = p_61743_;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.name;
    }

    public String getSerializedName() {
        return this.name;
    }
}
