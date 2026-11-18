package net.luckyowlstudios.cozyhome.util;

import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

import java.util.function.IntFunction;

public enum ThemedStyle implements StringRepresentable {
    NONE(0,"none"),
    HAY(1, "hay"),
    TRADER(2, "trader");

    private static final IntFunction<ThemedStyle> BY_ID = ByIdMap.continuous(ThemedStyle::getId, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
    private final String name;
    private final int id;

    ThemedStyle(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public static ThemedStyle byId(int colorId) {
        return BY_ID.apply(colorId);
    }
}
