package net.luckyowlstudios.cozyhome.block.util;

import net.luckyowlstudios.cozyhome.block.util.enums.VerticalConnection;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class ModBlockStateProperties {
    public static final EnumProperty<VerticalConnection> VERTICAL_CONNECTION = EnumProperty.create("vertical_connection", VerticalConnection.class);
}
