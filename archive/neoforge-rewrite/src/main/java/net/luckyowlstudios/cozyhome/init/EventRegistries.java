package net.luckyowlstudios.cozyhome.init;

import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;

@EventBusSubscriber
public class EventRegistries {

    @SubscribeEvent
    public static void furnaceFuelBurnTimeEvent(FurnaceFuelBurnTimeEvent event) {
        ItemStack itemstack = event.getItemStack();

        // All planked walls - 200 burn time
        if (itemstack.getItem() == ModBlocks.OAK_PLANKED_WALL.asItem()) event.setBurnTime(200);
        if (itemstack.getItem() == ModBlocks.SPRUCE_PLANKED_WALL.asItem()) event.setBurnTime(200);
        if (itemstack.getItem() == ModBlocks.BIRCH_PLANKED_WALL.asItem()) event.setBurnTime(200);
        if (itemstack.getItem() == ModBlocks.JUNGLE_PLANKED_WALL.asItem()) event.setBurnTime(200);
        if (itemstack.getItem() == ModBlocks.ACACIA_PLANKED_WALL.asItem()) event.setBurnTime(200);
        if (itemstack.getItem() == ModBlocks.DARK_OAK_PLANKED_WALL.asItem()) event.setBurnTime(200);
        if (itemstack.getItem() == ModBlocks.MANGROVE_PLANKED_WALL.asItem()) event.setBurnTime(200);
        if (itemstack.getItem() == ModBlocks.CHERRY_PLANKED_WALL.asItem()) event.setBurnTime(200);
        if (itemstack.getItem() == ModBlocks.BAMBOO_PLANKED_WALL.asItem()) event.setBurnTime(200);

        // All counters - 300 burn time
        if (itemstack.getItem() == ModBlocks.OAK_COUNTER.asItem()) event.setBurnTime(300);
        if (itemstack.getItem() == ModBlocks.SPRUCE_COUNTER.asItem()) event.setBurnTime(300);
        if (itemstack.getItem() == ModBlocks.BIRCH_COUNTER.asItem()) event.setBurnTime(300);
        if (itemstack.getItem() == ModBlocks.JUNGLE_COUNTER.asItem()) event.setBurnTime(300);
        if (itemstack.getItem() == ModBlocks.ACACIA_COUNTER.asItem()) event.setBurnTime(300);
        if (itemstack.getItem() == ModBlocks.DARK_OAK_COUNTER.asItem()) event.setBurnTime(300);
        if (itemstack.getItem() == ModBlocks.MANGROVE_COUNTER.asItem()) event.setBurnTime(300);
        if (itemstack.getItem() == ModBlocks.CHERRY_COUNTER.asItem()) event.setBurnTime(300);
        if (itemstack.getItem() == ModBlocks.BAMBOO_COUNTER.asItem()) event.setBurnTime(300);
        if (itemstack.getItem() == ModBlocks.CRIMSON_COUNTER.asItem()) event.setBurnTime(300);
        if (itemstack.getItem() == ModBlocks.WARPED_COUNTER.asItem()) event.setBurnTime(300);

        // All railings - 50 burn time
        if (itemstack.getItem() == ModBlocks.OAK_RAILING.asItem()) event.setBurnTime(50);
        if (itemstack.getItem() == ModBlocks.SPRUCE_RAILING.asItem()) event.setBurnTime(50);
        if (itemstack.getItem() == ModBlocks.BIRCH_RAILING.asItem()) event.setBurnTime(50);
        if (itemstack.getItem() == ModBlocks.JUNGLE_RAILING.asItem()) event.setBurnTime(50);
        if (itemstack.getItem() == ModBlocks.ACACIA_RAILING.asItem()) event.setBurnTime(50);
        if (itemstack.getItem() == ModBlocks.DARK_OAK_RAILING.asItem()) event.setBurnTime(50);
        if (itemstack.getItem() == ModBlocks.MANGROVE_RAILING.asItem()) event.setBurnTime(50);
        if (itemstack.getItem() == ModBlocks.CHERRY_RAILING.asItem()) event.setBurnTime(50);
        if (itemstack.getItem() == ModBlocks.BAMBOO_RAILING.asItem()) event.setBurnTime(50);
        if (itemstack.getItem() == ModBlocks.CRIMSON_RAILING.asItem()) event.setBurnTime(50);
        if (itemstack.getItem() == ModBlocks.WARPED_RAILING.asItem()) event.setBurnTime(50);
    }
}
