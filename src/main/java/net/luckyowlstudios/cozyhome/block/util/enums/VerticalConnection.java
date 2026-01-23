package net.luckyowlstudios.cozyhome.block.util.enums;

import net.minecraft.util.StringRepresentable;

public enum VerticalConnection implements StringRepresentable {
    NONE("none"),
    TOP("top"),
    MIDDLE("middle"),
    BOTTOM("bottom");

    private final String name;

    VerticalConnection(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getSerializedName() {
        return this.name;
    }
}


