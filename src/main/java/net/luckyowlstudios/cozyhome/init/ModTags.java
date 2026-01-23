package net.luckyowlstudios.cozyhome.init;

import net.luckyowlstudios.cozyhome.Cozyhome;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BannerPattern;

public class ModTags {

    public static final TagKey<BannerPattern> PATTERN_ITEM_HOME = createBannerTag("pattern_item/home");

    private static TagKey<BannerPattern> createBannerTag(String name) {
        return TagKey.create(Registries.BANNER_PATTERN, Cozyhome.id(name));
    }

    private static TagKey<Item> createItemTag(String name) {
        return ItemTags.create(Cozyhome.id(name));
    }

    private static TagKey<Block> createBlockTag(String name) {
        return BlockTags.create(Cozyhome.id(name));
    }

    private static TagKey<EntityType<?>> createEntityTag(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, Cozyhome.id(name));
    }
}
