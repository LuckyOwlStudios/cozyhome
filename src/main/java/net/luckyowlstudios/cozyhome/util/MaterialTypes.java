package net.luckyowlstudios.cozyhome.util;

import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.DyeColor;

import java.util.function.IntFunction;

public enum MaterialTypes implements StringRepresentable {
    OAK(0, "oak"),
    SPRUCE(1, "spruce"),
    BIRCH(2, "birch"),
    JUNGLE(3, "jungle"),
    ACACIA(4, "acacia"),
    DARK_OAK(5, "dark_oak"),
    MANGROVE(6, "mangrove"),
    CHERRY(7, "cherry"),
    BAMBOO(8, "bamboo"),
    CRIMSON(9, "crimson"),
    WARPED(10, "warped"),
    PRINCESS(11, "princess"),
    IRON(12, "iron"),
    GLASS(13, "iron"),
    UNDEAD(14, "undead"),
    OMINOUS(15, "ominous");

    private static final IntFunction<MaterialTypes> BY_ID = ByIdMap.continuous(MaterialTypes::getId, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
    public static final StringRepresentable.EnumCodec<MaterialTypes> CODEC = StringRepresentable.fromEnum(MaterialTypes::values);
    private final String name;
    private final int id;

    MaterialTypes(int id, String name) {
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

    public static MaterialTypes byId(int colorId) {
        return BY_ID.apply(colorId);
    }
}
