package net.luckystudio.cozyhome.block.custom.water_holding_blocks.sink;

import net.luckystudio.cozyhome.platform.CozyHomePlatform;
import net.luckystudio.cozyhome.block.custom.water_holding_blocks.AbstractWaterHoldingBlockEntity;
import net.luckystudio.cozyhome.block.util.ModBlockEntityTypes;
import net.luckystudio.cozyhome.block.util.ModProperties;
import net.luckystudio.cozyhome.block.util.enums.ContainsBlock;
import net.luckystudio.cozyhome.block.util.interfaces.WaterHoldingBlock;
import net.luckystudio.cozyhome.util.ModScreenTexts;
import net.luckystudio.cozyhome.block.util.LiquidHelper;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.*;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractSinkBlock extends BlockWithEntity implements WaterHoldingBlock {

    // Boolean properties
    public static final BooleanProperty TRIGGERED = Properties.TRIGGERED;

    // Direction properties
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    // Enum properties
    public static final EnumProperty<ContainsBlock> CONTAINS = ModProperties.CONTAINS;

    // Integer properties
    public static final IntProperty LEVEL = ModProperties.FILLED_LEVEL_0_3;

    public AbstractSinkBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(TRIGGERED, false)
                .with(FACING, Direction.NORTH)
                .with(CONTAINS, ContainsBlock.NONE)
                .with(LEVEL, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder.add(TRIGGERED, FACING, CONTAINS, LEVEL));
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        Item item = stack.getItem();
        ContainsBlock contents = state.get(CONTAINS);
        int level = state.get(LEVEL);

        // --- 0. Check if the block has water and the item is a soup ---
        if (WaterHoldingBlock.trySoup(item, world, pos, player, hand, contents)) {
            return ItemActionResult.SUCCESS;
        }

        // --- 1. Filling a bucket from a full block (any fluid that has a bucket) ---
        if (item == Items.BUCKET && level == 3 && contents != ContainsBlock.NONE) {
            Fluid fluid = LiquidHelper.getFluid(state, world, pos);
            ItemStack filledBucket = LiquidHelper.filledBucket(fluid);
            if (filledBucket.isEmpty()) return WaterHoldingBlock.toggleSwitch(state, world, pos, player);
            player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, filledBucket));
            player.incrementStat(Stats.USE_CAULDRON);
            player.incrementStat(Stats.USED.getOrCreateStat(item));
            world.playSound(null, pos, LiquidHelper.fillSound(fluid), SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.setBlockState(pos, LiquidHelper.withFluid(world, pos, state, Fluids.EMPTY).with(LEVEL, 0), 3);
            world.emitGameEvent(null, GameEvent.FLUID_PICKUP, pos);
            return ItemActionResult.SUCCESS;
        }

        // --- 2. Pouring any bucket of liquid (vanilla or modded) into the block ---
        Fluid bucketFluid = LiquidHelper.fluidInBucket(stack);
        if (bucketFluid != Fluids.EMPTY && level < 3 && LiquidHelper.canMix(LiquidHelper.getFluid(state, world, pos), bucketFluid)) {
            player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.BUCKET)));
            player.incrementStat(Stats.FILL_CAULDRON);
            player.incrementStat(Stats.USED.getOrCreateStat(item));
            world.setBlockState(pos, LiquidHelper.withFluid(world, pos, state, bucketFluid).with(LEVEL, 3), 3);
            world.playSound(null, pos, LiquidHelper.emptySound(bucketFluid), SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
            return ItemActionResult.SUCCESS;
        }

        // --- 3. Using a water bottle to fill the block ---
        if (item == Items.POTION && ((contents == ContainsBlock.WATER && level < 3) || contents == ContainsBlock.NONE)) {
            PotionContentsComponent potionContentsComponent = stack.get(DataComponentTypes.POTION_CONTENTS);
            if (potionContentsComponent != null && potionContentsComponent.matches(Potions.WATER)) {
                player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
                player.incrementStat(Stats.USE_CAULDRON);
                player.incrementStat(Stats.USED.getOrCreateStat(item));
                world.setBlockState(pos, state.with(LEVEL, level + 1).with(CONTAINS, ContainsBlock.WATER), 3);
                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
                return ItemActionResult.SUCCESS;
            } else {
                return WaterHoldingBlock.toggleSwitch(state, world, pos, player);
            }
        }

        // --- 4. Filling a bottle from the block ---
        if (item == Items.GLASS_BOTTLE && contents == ContainsBlock.WATER && level > 0) {
            int newLevel = level - 1;
            ContainsBlock newContents = newLevel == 0 ? ContainsBlock.NONE : contents;
            player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, PotionContentsComponent.createStack(Items.POTION, Potions.WATER)));
            player.incrementStat(Stats.USE_CAULDRON);
            player.incrementStat(Stats.USED.getOrCreateStat(item));
            world.setBlockState(pos, state.with(LEVEL, level - 1).with(CONTAINS, newContents), 3);
            world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.emitGameEvent(null, GameEvent.FLUID_PICKUP, pos);
            return ItemActionResult.SUCCESS;
        }
        return WaterHoldingBlock.toggleSwitch(state, world, pos, player);
    }

    // CARLOS IS GAY

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntityTypes.SINK_BLOCK_ENTITY, SinkBlockEntity::tick);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState()
                .with(CONTAINS, ContainsBlock.NONE)
                .with(FACING, ctx.getHorizontalPlayerFacing())
                .with(TRIGGERED, false);
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public float getLiquidLevelHeight(BlockState state) {
        int level = state.get(LEVEL);
        return switch (level) {
            case 1 -> 0.438f;
            case 2 -> 0.688f;
            case 3 -> 0.938f;
            default -> 0.125f;
        };
    }

    @Override
    public boolean isFull(BlockState state) {
        return state.get(LEVEL) == 3;
    }

    @Override
    public void addLiquid(BlockState state, World world, BlockPos pos, BlockState pullState, Direction pullDirection) {
        BlockPos pullPos = pos.offset(pullDirection);
        Fluid fluid = LiquidHelper.pullableFluid(pullState);
        if (fluid == Fluids.EMPTY || !LiquidHelper.canMix(LiquidHelper.getFluid(state, world, pos), fluid)) return;
        int level = state.get(LEVEL);

        if (pullState.getBlock() == Blocks.WATER_CAULDRON) {
            // One level of water per cauldron level
            LeveledCauldronBlock.decrementFluidLevel(pullState, world, pullPos);
            world.setBlockState(pos, LiquidHelper.withFluid(world, pos, state, fluid).with(LEVEL, Math.min(3, level + 1)), 3);
            return;
        }
        if (pullState.getBlock() == Blocks.LAVA_CAULDRON) {
            world.setBlockState(pullPos, Blocks.CAULDRON.getDefaultState(), 3);
            world.setBlockState(pos, LiquidHelper.withFluid(world, pos, state, fluid).with(LEVEL, 3), 3);
            return;
        }
        if (fluid == Fluids.WATER) {
            // Water sources are infinite: fill one level at a time without using them up
            world.setBlockState(pos, LiquidHelper.withFluid(world, pos, state, fluid).with(LEVEL, Math.min(3, level + 1)), 3);
            return;
        }
        // Lava and any other fluid: one source block fills the sink
        LiquidHelper.drainSource(world, pullPos, pullState, fluid);
        world.setBlockState(pos, LiquidHelper.withFluid(world, pos, state, fluid).with(LEVEL, 3), 3);
    }

    @Override
    public void removeLiquid(BlockState state, World world, BlockPos pos) {
        int level = state.get(LEVEL);
        int newLevel = Math.max(0, level - 1); // ensures level never goes below 0
        BlockState newState = newLevel == 0 ? LiquidHelper.withFluid(world, pos, state, Fluids.EMPTY) : state;
        world.setBlockState(pos, newState.with(LEVEL, newLevel), 3);
    }

    @Override
    public List<Direction> getDirectionsToPull(BlockState state) {
        Direction behind = state.get(FACING);
        return List.of(behind);
    }

    @Override
    public Direction pullingDirection(BlockState state, World world, BlockPos pos) {
        for (Direction direction : getDirectionsToPull(state)) {
            Fluid fluid = LiquidHelper.pullableFluid(world.getBlockState(pos.offset(direction)));
            if (fluid != Fluids.EMPTY && LiquidHelper.canMix(LiquidHelper.getFluid(state, world, pos), fluid)) {
                return direction;
            }
        }
        return null;
    }

    // Handles entity effects based on the block’s fill state (LAVA, POWDER_SNOW, WATER)
    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        ContainsBlock fillState = state.get(CONTAINS);

        // If block contains lava, apply burn damage and set the entity on fire
        if (fillState == ContainsBlock.LAVA && entity instanceof LivingEntity) {
            entity.damage(world.getDamageSources().hotFloor(), 4.0F);
            entity.setOnFireFor(3);
        }

        // Call the superclass method after handling custom effects
        super.onSteppedOn(world, pos, state, entity);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        if (world.getBlockEntity(pos) instanceof AbstractWaterHoldingBlockEntity blockEntity) {
            if (world.isClient && blockEntity.soupTime > 0) {
                MinecraftClient client = MinecraftClient.getInstance();
                ParticleManager particleManager = client.particleManager;

                // The particle ID (e.g. "supplementaries:suds") — must be registered with a factory!
                Identifier id = Identifier.of("supplementaries", "suds");
                ParticleType<?> type = Registries.PARTICLE_TYPE.get(id);

                if (type != null) {
                    double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 0.3;
                    double y = pos.getY() + getLiquidLevelHeight(state);
                    double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.3;

                    particleManager.addParticle((ParticleEffect) type, x, y, z, 0.0, 0.01, 0.0);
                }
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        tooltip.add(ScreenTexts.EMPTY);
        tooltip.add(Text.translatable("tooltip.cozyhome.interact_with_hand_while_sneaking").formatted(Formatting.GRAY));
        tooltip.add(ModScreenTexts.entry().append(Text.translatable("tooltip.cozyhome.toggle_switch")));
        if (CozyHomePlatform.isModLoaded("supplementaries")) {
            tooltip.add(Text.translatable("tooltip.cozyhome.interact_with_soup").formatted(Formatting.GRAY));
            tooltip.add(ModScreenTexts.entry().append(Text.translatable("tooltip.cozyhome.add_bubbles")));
        }
    }
}
