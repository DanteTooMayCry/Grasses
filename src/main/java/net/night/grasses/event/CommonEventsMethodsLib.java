package net.night.grasses.event;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.night.grasses.block.bars.TintedVineInBars;
import net.night.grasses.block.bars.VineInBars;
import net.night.grasses.block.leaves.superclasses.ParentTintedLeavesBlock;
import net.night.grasses.block.plants.TintedVine;
import net.night.grasses.colorManagers.ColorType;
import net.night.grasses.config.GrassesConfig;
import net.night.grasses.item.AutomaticPrunerItem;
import net.night.grasses.item.DyeingBoneMealItem;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.List;

import static biomesoplenty.api.block.BOPBlocks.*;
import static net.minecraft.advancements.CriteriaTriggers.ITEM_USED_ON_BLOCK;
import static net.minecraft.tags.BlockTags.*;
import static net.minecraft.tags.BlockTags.WARPED_STEMS;
import static net.minecraft.world.level.block.Blocks.*;
import static net.minecraft.world.level.block.Blocks.SUGAR_CANE;
import static net.minecraft.world.level.block.CrossCollisionBlock.EAST;
import static net.minecraft.world.level.block.CrossCollisionBlock.NORTH;
import static net.minecraft.world.level.block.CrossCollisionBlock.SOUTH;
import static net.minecraft.world.level.block.DoublePlantBlock.HALF;
import static net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING;
import static net.minecraft.world.level.block.LeavesBlock.DISTANCE;
import static net.minecraft.world.level.block.SlabBlock.TYPE;
import static net.minecraft.world.level.block.VineBlock.*;
import static net.minecraft.world.level.block.VineBlock.WEST;
import static net.minecraft.world.level.block.state.properties.SlabType.BOTTOM;
import static net.night.grasses.Grasses.isBOPLoaded;
import static net.night.grasses.block.plants.TintedSugarCane.biomesColorSourcePropertiesUpdate;
import static net.night.grasses.data.DataLib.*;
import static net.night.grasses.data.MethodsLib.*;
import static net.night.grasses.event.CommonEventHandler.*;
import static net.night.grasses.init.BlocksRegister.*;
import static net.night.grasses.init.BlocksRegisterBoP.*;
import static net.night.grasses.util.ModTags.Blocks.*;

public class CommonEventsMethodsLib {

    //Methods for (itemStackInMainHand.getItem() instanceof BoneMealItem && fertileCondition)

    public static void performTintedBoneMealForSeagrass(ServerLevel serverLevel, ItemStack itemStack, BlockPos blockPos, BlockState blockState, Direction clickedSide) {
        RandomSource randomsource = serverLevel.getRandom();
        ColorType colorType = getColorTypeFromNBT(itemStack);
        label78:
        for(int i = 0; i < 128; ++i) {
            BlockPos blockPos1 = blockPos;
            BlockState grassPlantState = SEAGRASS_TINTED.get().defaultBlockState();

            for (int j = 0; j < i / 16; ++j) {
                blockPos1 = blockPos1.offset(randomsource.nextInt(3) - 1, (randomsource.nextInt(3) - 1) * randomsource.nextInt(3) / 2, randomsource.nextInt(3) - 1);
                if (serverLevel.getBlockState(blockPos1).isCollisionShapeFullBlock(serverLevel, blockPos1)) {
                    continue label78;
                }
            }

            Holder<Biome> holder = serverLevel.getBiome(blockPos1);
            if (holder.is(BiomeTags.PRODUCES_CORALS_FROM_BONEMEAL)) {
                if (i == 0 && clickedSide != null && clickedSide.getAxis().isHorizontal()) {
                    grassPlantState = BuiltInRegistries.BLOCK.getTag(WALL_CORALS).flatMap((p_204098_) -> p_204098_.getRandomElement(serverLevel.random)).map((p_204100_) -> p_204100_.value().defaultBlockState()).orElse(grassPlantState);
                    if (grassPlantState.hasProperty(BaseCoralWallFanBlock.FACING)) {
                        grassPlantState = grassPlantState.setValue(BaseCoralWallFanBlock.FACING, clickedSide);
                    }
                } else if (randomsource.nextInt(4) == 0) {
                    grassPlantState = BuiltInRegistries.BLOCK.getTag(UNDERWATER_TINTED_BONEMEALS).flatMap((p_204091_) -> p_204091_.getRandomElement(serverLevel.random)).map((p_204095_) -> p_204095_.value().defaultBlockState()).orElse(grassPlantState);
                }
            }

            if (grassPlantState.is(WALL_CORALS, (p_204093_) -> p_204093_.hasProperty(BaseCoralWallFanBlock.FACING))) {
                for(int k = 0; !grassPlantState.canSurvive(serverLevel, blockPos1) && k < 4; ++k) {
                    grassPlantState = grassPlantState.setValue(BaseCoralWallFanBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(randomsource));
                }
            }

            if (grassPlantState.canSurvive(serverLevel, blockPos1)) {
                BlockState blockstate1 = serverLevel.getBlockState(blockPos1);
                if (blockstate1.is(WATER) && serverLevel.getFluidState(blockPos1).getAmount() == 8) {
                    if (grassPlantState.hasBlockEntity()) {
                        keepData(blockPos1, SEAGRASS, colorType);
                    }
                    serverLevel.setBlock(blockPos1, grassPlantState, 3);

                } else if (blockstate1.is(SEAGRASS_TINTED.get()) && randomsource.nextInt(10) == 0) {
                    ((BonemealableBlock)SEAGRASS_TINTED.get()).performBonemeal(serverLevel, randomsource, blockPos1, blockstate1);
                }
            }
        }
    }

    public static void performTintedBoneMealForGrass(ServerLevel serverLevel, ItemStack itemStack, BlockPos blockPos, BlockState blockState) {

        RandomSource randomSource = RandomSource.create();
        BlockPos blockPosAbove = blockPos.above();
        BlockState grassPlantState = GRASS_TINTED.get().defaultBlockState();
        Optional<Holder.Reference<PlacedFeature>> optional = serverLevel.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolder(VegetationPlacements.GRASS_BONEMEAL);
        ColorType colorType = getColorTypeFromNBT(itemStack);

        label49:
        for(int i = 0; i < 128; ++i) {
            BlockPos blockPosAbove1 = blockPosAbove;

            for(int j = 0; j < i / 16; ++j) {
                blockPosAbove1 = blockPosAbove1.offset(randomSource.nextInt(3) - 1, (randomSource.nextInt(3) - 1) * randomSource.nextInt(3) / 2, randomSource.nextInt(3) - 1);
                if (!serverLevel.getBlockState(blockPosAbove1.below()).is(blockState.getBlock()) || serverLevel.getBlockState(blockPosAbove1).isCollisionShapeFullBlock(serverLevel, blockPosAbove1)) {
                    continue label49;
                }
            }

            BlockState blockStateAbove = serverLevel.getBlockState(blockPosAbove1);
            if (blockStateAbove.is(grassPlantState.getBlock()) && randomSource.nextInt(10) == 0) {
                ((BonemealableBlock) grassPlantState.getBlock()).performBonemeal(serverLevel, randomSource, blockPosAbove1, blockStateAbove);
            }

            if (blockStateAbove.isAir()) {
                Holder<PlacedFeature> holder;
                if (randomSource.nextInt(8) == 0) {
                    List<ConfiguredFeature<?, ?>> list = serverLevel.getBiome(blockPosAbove1).value().getGenerationSettings().getFlowerFeatures();
                    if (list.isEmpty()) {
                        continue;
                    }

                    holder = ((RandomPatchConfiguration)list.get(0).config()).feature();
                } else {
                    if (!optional.isPresent()) {
                        continue;
                    }

                    holder = optional.get();
                }

                holder.value().place(serverLevel, serverLevel.getChunkSource().getGenerator(), randomSource, blockPosAbove1);

                BlockState plant = serverLevel.getBlockState(blockPosAbove1);

                if (plant.is(GRASS)) {
                    keepData(blockPosAbove1, GRASS, colorType);
                    serverLevel.setBlockAndUpdate(blockPosAbove1, GRASS_TINTED.get().defaultBlockState());
                }
            }
        }
    }

    public static boolean growPlantOneStageAbove(Level level, BlockPos blockPos, BlockState blockState, ItemStack itemStack, Player player) {

        int worldHeight = level.getHeight();
        for (int y = blockPos.getY(); y <= worldHeight; y++) {
            BlockPos upBlockPos = new BlockPos(blockPos.getX(), y, blockPos.getZ());
            BlockState blockStateNext = level.getBlockState(upBlockPos);
            float random = RandomSource.create().nextFloat();
            float configChance = 1f;

            if (!stagePlants.contains(blockStateNext.getBlock()) && !blockStateNext.is(CACTUS_LIKE_PLANTS_1) && !blockStateNext.is(CACTUS_LIKE_PLANTS_2) && !blockStateNext.is(CACTUS_LIKE_PLANTS_3)) {
                if (blockStateNext.is(AIR)) {
                    int height = checkPlantHeight(level, y, blockPos);
                    BlockState blockStatePlantBase = level.getBlockState(upBlockPos.below());
                    BlockState blockStateNew = blockState;
                    ColorType colorType = null;

                    if (itemStack.getItem() instanceof DyeingBoneMealItem && standardTintedPlantsList.contains(blockState.getBlock()))
                        colorType = getColorTypeFromNBT(itemStack);
                    else if (standardTintedPlantsList.contains(blockState.getBlock()))
                        colorType = getColorType(level, blockPos);

                    int maxHeight = 0;
                    if (blockState.is(CACTUS) || blockState.is(CACTUS_TINTED.get())) {
                        maxHeight = GrassesConfig.CommonConfig.CACTUS_MAX_HEIGHT.get();
                    } else if (blockState.is(SUGAR_CANE))
                        maxHeight = GrassesConfig.CommonConfig.SUGAR_CANE_MAX_HEIGHT.get();
                    else if (blockState.is(SUGAR_CANE_TINTED.get())) {
                        maxHeight = GrassesConfig.CommonConfig.SUGAR_CANE_MAX_HEIGHT.get();
                        boolean biomesColorSource = biomesColorSourcePropertiesUpdate(colorType);
                        blockStateNew = blockStateNew.setValue(BIOMES_COLOR_SOURCE, biomesColorSource);
                    }
                    else if (blockState.is(CACTUS_LIKE_PLANTS_1)) {
                        maxHeight = GrassesConfig.CommonConfig.CACTUS_LIKE_PLANTS_1.get();
                        configChance = (float) GrassesConfig.CommonConfig.CACTUS_LIKE_PLANTS_1_CHANCE.get() / 100;
                    }
                    else if (blockState.is(CACTUS_LIKE_PLANTS_2)) {
                        maxHeight = GrassesConfig.CommonConfig.CACTUS_LIKE_PLANTS_2.get();
                        configChance = (float) GrassesConfig.CommonConfig.CACTUS_LIKE_PLANTS_2_CHANCE.get() / 100;
                    }
                    else if (blockState.is(CACTUS_LIKE_PLANTS_3)) {
                        maxHeight = GrassesConfig.CommonConfig.CACTUS_LIKE_PLANTS_3.get();
                        configChance = (float) GrassesConfig.CommonConfig.CACTUS_LIKE_PLANTS_3_CHANCE.get() /100;
                    }

                    if (height <= maxHeight && random <= configChance && blockState.getBlock() instanceof IPlantable && blockStatePlantBase.canSustainPlant(level, upBlockPos, Direction.UP, (IPlantable) blockState.getBlock())) {
                        if (colorType != null) {
                            if (!level.isClientSide) {
                                if (blockState.is(CACTUS_TINTED.get()))
                                    keepData(upBlockPos, CACTUS ,colorType);
                                else if (blockState.is(SUGAR_CANE_TINTED.get()))
                                    keepData(upBlockPos, SUGAR_CANE ,colorType);
                                level.setBlockAndUpdate(upBlockPos, blockStateNew);
                            }
                        } else {
                            if (!level.isClientSide)
                                level.setBlockAndUpdate(upBlockPos, blockStateNew);
                        }

                        if(player instanceof ServerPlayer)
                            ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockPos, itemStack);

                        level.levelEvent(2005, upBlockPos, 0);
                        level.levelEvent(2005, upBlockPos.above(), 0);
                        return true;
                    }
                    else
                        return false;
                }
                break;
            }
        }
        return false;
    }

    private static int checkPlantHeight (Level level, int airBlock, BlockPos blockPos) {
        int plantHeight = 1;
        int worldLowestPos = level.getMinBuildHeight();

        for (int y = airBlock-1; y >= worldLowestPos; y--) {
            BlockPos downBlockPos = new BlockPos(blockPos.getX(), y, blockPos.getZ());
            BlockState blockStateNext = level.getBlockState(downBlockPos);

            if (!stagePlants.contains(blockStateNext.getBlock()) && !blockStateNext.is(CACTUS_LIKE_PLANTS_1) && !blockStateNext.is(CACTUS_LIKE_PLANTS_2) && !blockStateNext.is(CACTUS_LIKE_PLANTS_3)) {

                return plantHeight;
            }
            else
                plantHeight++;
        }
        return plantHeight;
    }

    public static boolean growVineOneStageBelow (Level level, BlockPos blockPos, BlockState blockState, ItemStack itemStack, Player player) {
        BlockPos blockPosBelow = blockPos.below();
        BlockState blockStateBelow = level.getBlockState(blockPosBelow);
        boolean sameVine = true;

        while (sameVine) {

            if (blockStateBelow.isAir()) {
                Block plantBlock = VINE;
                if (isBOPLoaded && blockState.is(WILLOW_VINE_TINTED.get()))
                    plantBlock = WILLOW_VINE;

                if (itemStack.getItem() instanceof DyeingBoneMealItem && standardTintedPlantsList.contains(blockState.getBlock())) {
                    keepData(blockPosBelow, plantBlock, getColorTypeFromNBT(itemStack));
                    level.setBlock(blockPosBelow, blockState.getBlock().withPropertiesOf(blockState), 2);
                }
                else {
                    if (blockState.getBlock() instanceof TintedVine)
                        keepData(blockPosBelow, plantBlock, getCurrentColor(level, blockPos, 0));
                    level.setBlock(blockPosBelow, blockState.getBlock().withPropertiesOf(blockState), 2);
                }

                level.levelEvent(2005, blockPosBelow, 0);
                if(player instanceof ServerPlayer)
                    ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockPos, itemStack);
                return true;

            } else if (blockStateBelow.is(blockState.getBlock())) {
                blockPosBelow = blockPosBelow.below();
                blockStateBelow = level.getBlockState(blockPosBelow);
            } else {
                sameVine = false;
            }
        }
        return false;
    }

    public static boolean spreadPlants (Level level, BlockPos blockPos, BlockState blockState, ItemStack itemStack, PlayerInteractEvent.RightClickBlock event) {

        if ((blockState.hasProperty(FERTILE) && !blockState.getValue(FERTILE)) || event.getEntity().level().isClientSide) {
            return false;
        }
        else
            return spreadSomePlants(level, blockPos, blockState, itemStack, event.getEntity());
    }

    private static boolean spreadSomePlants (Level level, BlockPos blockPos, BlockState blockState, ItemStack itemStack, Player player) {
        int maxLoop;
        BlockPos blockPosNextTo;
        BlockPos whereToGrow;
        RandomSource randomSource = RandomSource.create();
        boolean toReturn = false;
        boolean shrink = false;

        ColorType colorType = getColorTypeFromNBT(itemStack);

        if (blockState.is(PODZOL_BLOCKS))
            maxLoop = 20;
        else
            maxLoop = 10;

        if(blockState.is(BONE_MEAL_ABLE_BLOCKS))
            blockPosNextTo = blockPos.above();
        else
            blockPosNextTo = blockPos;

        for (int x = 0; x < maxLoop; x++) {
            if (x == 0)
                whereToGrow = blockPosNextTo;
            else {
                blockPosNextTo = blockPosNextTo.offset(randomSource.nextInt(3) - 1, 0, randomSource.nextInt(3) - 1);
                whereToGrow = blockPosNextTo.offset(0, (randomSource.nextInt(3) - 1) * randomSource.nextInt(3) / 2, 0);
            }

            BlockState blockStateSpread;

            if (blockState.is(MYCELIUM_BLOCKS))
                blockStateSpread = pickMushroom();
            else if (blockState.is(PODZOL_BLOCKS))
                blockStateSpread = pickFern(colorType, itemStack, whereToGrow);
            else if (blockState.is(SOUL_SAND_BLOCKS))
                blockStateSpread = NETHER_WART.defaultBlockState();
            else
                blockStateSpread = blockState;

            BlockState blockStateNextTo = level.getBlockState(whereToGrow);
            BlockState blockStateBelowNextTo = level.getBlockState(whereToGrow.below());
            //if clicked on flower OR lilypad OR (block like podzol and block next to is same)
            if ((blockState.is(SMALL_FLOWERS) || blockState.is(LILY_PAD) || blockState.is(LILY_TINTED.get()) || blockState.getBlock().equals(blockStateBelowNextTo.getBlock()) ||
                    BOPPlantsBlocksList.contains(blockState.getBlock()) || tintedBOPPlantBlockList.contains(blockState.getBlock())) &&
                    (!blockStateBelowNextTo.hasProperty(TYPE) || (blockStateBelowNextTo.hasProperty(TYPE) && (blockStateBelowNextTo.getValue(TYPE) != BOTTOM || GrassesConfig.CommonConfig.ALLOW_PUT_PLANTS_ON_BOTTOM_SLAB.get()))) &&
                    blockStateNextTo.isAir() && blockStateSpread.canSurvive(level, whereToGrow)) {

                if (blockStateSpread.is(LARGE_FERN) || blockStateSpread.is(FERN_TALL_TINTED.get())) {
                    BlockPos blockPosNextToAbove = whereToGrow.above();
                    BlockState blockStateNextToAbove = level.getBlockState(blockPosNextToAbove);
                    if (blockStateNextToAbove.isAir()){
                        level.setBlock(blockPosNextToAbove, blockStateSpread.setValue(HALF, DoubleBlockHalf.UPPER), 19);
                    }
                    else if (blockStateSpread.is(LARGE_FERN))
                        blockStateSpread = FERN.defaultBlockState();
                    else
                        blockStateSpread = FERN_TINTED.get().defaultBlockState();
                }

                float chance = randomSource.nextFloat();
                Block plantsBlock = null;

                if (blockState.is(TORCHFLOWER) && chance > 0.05) {shrink = falseInteraction(player, shrink); continue;}
                else if(blockState.is(WATERLILY_TINTED.get()) && chance > 0.25) {shrink = falseInteraction(player, shrink); continue;}
                else if(blockState.is(TINY_CACTUS_TINTED.get()) && chance > 0.25) {shrink = falseInteraction(player, shrink); continue;}
                else if(blockState.is(SPROUT_TINTED.get()) && chance > 0.25) {shrink = falseInteraction(player, shrink); continue;}
                else if(blockState.is(BUSH_TINTED.get()) && chance > 0.25) {shrink = falseInteraction(player, shrink); continue;}
                else if (isBOPLoaded) {
                    if(blockState.is(ROSE) && chance > 0.05) {shrink = falseInteraction(player, shrink); continue;}
                    else if(blockState.is(VIOLET) && chance > 0.25) {shrink = falseInteraction(player, shrink); continue;}
                    else if(blockState.is(LAVENDER) && chance > 0.15) {shrink = falseInteraction(player, shrink); continue;}
                    else if(blockState.is(WILDFLOWER) && chance > 0.50) {shrink = falseInteraction(player, shrink); continue;}
                    else if(blockState.is(ORANGE_COSMOS) && chance > 0.15) {shrink = falseInteraction(player, shrink); continue;}
                    else if(blockState.is(PINK_DAFFODIL) && chance > 0.25) {shrink = falseInteraction(player, shrink); continue;}
                    else if(blockState.is(PINK_HIBISCUS) && chance > 0.05) {shrink = falseInteraction(player, shrink); continue;}
                    else if(blockState.is(GLOWFLOWER) && chance > 0.05) {shrink = falseInteraction(player, shrink); continue;}
                    else if(blockState.is(WILTED_LILY) && chance > 0.10) {shrink = falseInteraction(player, shrink); continue;}
                    else if(blockState.is(BURNING_BLOSSOM) && chance > 0.05) {shrink = falseInteraction(player, shrink); continue;}
                    else if(blockState.is(ENDBLOOM) && chance > 0.50) {shrink = falseInteraction(player, shrink); continue;}
                    else if(blockState.is(WATERLILY) && chance > 0.25) {shrink = falseInteraction(player, shrink); continue;}
                    else if(blockState.is(TINY_CACTUS) && chance > 0.25) {shrink = falseInteraction(player, shrink); continue;}
                    else if(blockState.is(SPROUT) && chance > 0.25) {shrink = falseInteraction(player, shrink); continue;}
                    else if(blockState.is(BUSH) && chance > 0.25) {shrink = falseInteraction(player, shrink); continue;}
                }

                if(blockState.is(WATERLILY_TINTED.get())) plantsBlock = WATERLILY;
                else if(blockState.is(TINY_CACTUS_TINTED.get())) plantsBlock = TINY_CACTUS;
                else if(blockState.is(SPROUT_TINTED.get())) plantsBlock = SPROUT;
                else if(blockState.is(BUSH_TINTED.get())) plantsBlock = BUSH;
                else if(blockState.is(FERN_TINTED.get())) plantsBlock = FERN;
                else if(blockState.is(FERN_TALL_TINTED.get())) plantsBlock = LARGE_FERN;
                else if(blockState.is(LILY_TINTED.get())) plantsBlock = LILY_PAD;

                if (itemStack.getItem() instanceof DyeingBoneMealItem && plantsBlock != null)
                    keepData(whereToGrow, plantsBlock, colorType);
                else if (plantsBlock != null)
                    keepData(whereToGrow, plantsBlock, getColorType(level, blockPos));

                level.setBlock(whereToGrow, blockStateSpread, 19);

                if(player instanceof ServerPlayer)
                    ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockPos, itemStack);
                toReturn = true;
            }
        }
        return toReturn;
    }

    private static boolean falseInteraction(Player player, boolean shrink) {

        player.swing(player.getUsedItemHand(), true);
        if (!player.isCreative() && !shrink) {
            player.getMainHandItem().shrink(1);
        }
        return true;
    }

    private static BlockState pickMushroom () {
        int randomInt = new Random().nextInt(2);

        switch (randomInt) {
            case 0: return RED_MUSHROOM.defaultBlockState();
            default: return BROWN_MUSHROOM.defaultBlockState();
        }
    }

    private static BlockState pickFern (ColorType colorType, ItemStack itemStack, BlockPos blockPos) {

        int randomInt = new Random().nextInt(8);
        Block plant;

        switch (randomInt) {
            case 0: return RED_MUSHROOM.defaultBlockState();
            case 1: return BROWN_MUSHROOM.defaultBlockState();
            case 2, 3: plant = (itemStack.getItem() instanceof DyeingBoneMealItem ? setKeepColor(FERN_TALL_TINTED.get(), blockPos, colorType) : LARGE_FERN); break;
            default: plant = (itemStack.getItem() instanceof DyeingBoneMealItem ? setKeepColor(FERN_TINTED.get(), blockPos, colorType) : FERN);
        }
        return plant.defaultBlockState();
    }

    private static Block setKeepColor(Block block, BlockPos blockPos, ColorType colorType) {

        keepColorType.put(blockPos, colorType);
        if (block instanceof DoublePlantBlock)
            keepColorType.put(blockPos.above(), colorType);

        return block;
    }

    //Fungus methods:
    public static void prepareDataForFungus(Level level, BlockState blockState, BlockPos blockPos) {

        logInfoForWAILA = new MutablePair<>(AIR, 0);
        crownInfoForWAILA = new MutablePair<>(AIR, 0);
        vineInfoForWAILA = new MutablePair<>(AIR, 0);
        fungusSLInfoForWAILA = new MutablePair<>(AIR, 0);


        Map<Integer, BlockPos> lowHighestPos;
        Map<BlockPos, Integer> fungusCrownPosWithDistance;
        boolean isSprintKeyPush = Minecraft.getInstance().options.keySprint.isDown();

        lowHighestPos = fungusLogShape(blockPos, level, true, isSprintKeyPush);
        fungusCrownPosWithDistance = fungusCrownShape(level, blockState);
        fungusVineShape(level);

        if (!lowHighestPos.isEmpty()) {
            checkIfAnotherFungusNear(level, isSprintKeyPush, lowHighestPos);
            fungusLogShape(blockPos, level, false, isSprintKeyPush);
            fungusCrownRemoveCrownBlocksFromAnotherFungus(level, blockState, fungusCrownPosWithDistance, lowHighestPos);
        }
        fungusLowestLogsInNearby.clear();
        fungusHighestLogsInNearby.clear();
    }

    public static void destroyHugeFungusCrown(Level level, Player player, ItemStack interactionHan, BlockPos blockPos, PlayerInteractEvent.RightClickBlock event) {

        if (level.isClientSide)
            return;

        if (fungusCrownPosWithDistance.isEmpty()) {
            level.playSound(null, blockPos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
            player.swing(player.getUsedItemHand(), true);
            return;
        }

        for (Map.Entry<BlockPos, Integer> crownBlockPos : fungusCrownPosWithDistance.entrySet())
            if (!fungusBlocksThatReplacedStem.containsValue(crownBlockPos.getKey()))
                level.destroyBlock(crownBlockPos.getKey(), true);

        if (player instanceof ServerPlayer)
            ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, blockPos, interactionHan);

        int hurt = (int) Math.floor(((double) fungusCrownPosWithDistance.size() / 3) + ((Math.pow(fungusCrownPosWithDistance.size(), 1.0 / 2.0)) * 2.75) - 2);

        level.playSound(null, blockPos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
        interactionHan.hurtAndBreak(hurt, event.getEntity(), (e) -> {
            e.broadcastBreakEvent(event.getHand());
        });
        event.setUseItem(Event.Result.ALLOW);
        event.getEntity().swing(event.getHand(), true);
        fungusCrownPosWithDistance.clear();
    }

    private static Map<Integer, BlockPos> fungusLogShape(BlockPos blockPos, Level level, boolean isLogFound, boolean isSprintKeyPush) {
        int maxSizeOfLogMapForDestroy = GrassesConfig.CommonConfig.STEMS_MAX_AMOUNT_AT_ONCE.get();
        int maxSizeOfStemsMapForShow = GrassesConfig.CommonConfig.STEMS_MAX_AMOUNT_AT_ONCE.get() <= 30 ? 60 : GrassesConfig.CommonConfig.STEMS_MAX_AMOUNT_AT_ONCE.get();

        Map<Integer, BlockPos> checkingLogHashMap = new HashMap<>();
        Map<Integer, BlockPos> logHashMap = new HashMap<>();
        Map<Integer, BlockPos> lowHighestPos = new HashMap<>();
        Map<Integer, BlockPos> alreadyUsedStemHashMap = new HashMap<>();
        int fungusStemsForWAILA = 0;

        BlockState blockState = level.getBlockState(blockPos);
        boolean nearbyListIsEmpty = fungusLowestLogsInNearby.isEmpty();
        BlockPos lowest = null;
        BlockPos highest = null;
        int j = 1;

        if (nearbyListIsEmpty) {
            highest = blockPos;
            lowest = blockPos;
        }

        if (!nearbyListIsEmpty) {
            checkingLogHashMap.putAll(fungusLowestLogsInNearby);
            blockPos = checkingLogHashMap.get(0);
            isLogFound = true;
        } else if (!isLogFound)
            return lowHighestPos;
        else {
            checkingLogHashMap.put(0, blockPos);
            if (blockState.is(STEMS))
                fungusStemsForWAILA++;
        }

        while (isLogFound) {

            if (alreadyUsedStemHashMap.containsValue(blockPos) && checkingLogHashMap.get(j) != null) {
                blockPos = checkingLogHashMap.get(j);
                j++;
            } else if (j != 1 && checkingLogHashMap.get(j) == null) {
                break;
            }
            BlockPos blockPosAbove = blockPos.above();
            BlockState blockStateAbove = level.getBlockState(blockPosAbove);
            BlockState blockStateCurrent = level.getBlockState(blockPos);
            if (blockStateCurrent.is(STEMS) && !blockStateAbove.is(STEMS)) {
                int i = 0;

                while (isHugeFungusBlock(blockState, blockStateAbove) && i < 11) {
                    checkingLogHashMap.put(checkingLogHashMap.size(), blockPosAbove);
                    if (!fungusBlocksThatReplacedStem.containsValue(blockPosAbove))
                        fungusBlocksThatReplacedStem.put(fungusBlocksThatReplacedStem.size(), blockPosAbove);

                    blockPosAbove = blockPosAbove.above();
                    blockStateAbove = level.getBlockState(blockPosAbove);
                    i++;

                }

                BlockState blockStateTop = level.getBlockState(blockPosAbove);

                if (blockStateTop.is(STEMS) && sameKindLogCondition(blockStateCurrent, blockStateTop, isSprintKeyPush) && !alreadyUsedStemHashMap.containsValue(blockPosAbove) && !checkingLogHashMap.containsValue(blockPosAbove)) {
                    checkingLogHashMap.put(checkingLogHashMap.size(), blockPosAbove);
                    if (nearbyListIsEmpty)
                        fungusStemsForWAILA++;
                }

                first:
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        for (int z = -1; z <= 1; z++) {
                            BlockPos blockPosNextTo = blockPos.offset(x, y, z);
                            boolean endPossibilities = checkingLogHashMap.get(j) == null && x==1 && y==1 && z==1;

                            Pair<Integer, Integer> toReturn = checkConditionAndAddToStemLists(level, blockPosNextTo, blockPos, blockStateCurrent, blockState, checkingLogHashMap, alreadyUsedStemHashMap, logHashMap, fungusStemsForWAILA, maxSizeOfLogMapForDestroy, maxSizeOfStemsMapForShow, nearbyListIsEmpty, isSprintKeyPush, endPossibilities);

                            if (toReturn.getKey() == 2) break first;
                            else isLogFound = toReturn.getKey() == 1;
                            fungusStemsForWAILA = toReturn.getValue();
                        }
                    }
                }
            }
            else if (blockStateCurrent.is(STEMS)) {
                for (int i = 0; i < 10; i++) {
                    BlockPos blockPosNextTo = blockPos.offset(xyzCoords.get(i));
                    boolean endPossibilities = checkingLogHashMap.get(j) == null && i == 9;
                    Pair<Integer, Integer> toReturn = checkConditionAndAddToStemLists(level, blockPosNextTo, blockPos, blockStateCurrent, blockState, checkingLogHashMap, alreadyUsedStemHashMap, logHashMap, fungusStemsForWAILA, maxSizeOfLogMapForDestroy, maxSizeOfStemsMapForShow, nearbyListIsEmpty, isSprintKeyPush, endPossibilities);
                    isLogFound = toReturn.getKey() == 1;
                    fungusStemsForWAILA = toReturn.getValue();
                }
            }
            alreadyUsedStemHashMap.put(alreadyUsedStemHashMap.size(), blockPos);
            if (blockStateCurrent.is(STEMS) && nearbyListIsEmpty && highest.getY() < blockPos.getY())
                highest = blockPos;
            if (blockStateCurrent.is(STEMS) && nearbyListIsEmpty && lowest.getY() > blockPos.getY() && level.getBlockState(blockPos.below()).is(NETHERRACK_BASE_BLOCKS)) // => czy napewno sprawdzać base blocks?
                lowest = blockPos;
        }

        if (nearbyListIsEmpty) {
            logHashMapGlobal.clear();
            logHashMapGlobal.putAll(checkingLogHashMap);
            logInfoForWAILA = MutablePair.of(blockState.getBlock(), fungusStemsForWAILA);

        } else {
            fungusLogHashMapNear.clear();
            fungusLogHashMapNear.putAll(checkingLogHashMap);
        }

        lowHighestPos.put(0, lowest);
        lowHighestPos.put(1, highest);
        return lowHighestPos;
    }

    private static Pair<Integer, Integer> checkConditionAndAddToStemLists (Level level, BlockPos blockPosNextTo, BlockPos blockPos, BlockState blockStateCurrent, BlockState blockState, Map<Integer, BlockPos> checkingLogHashMap, Map<Integer, BlockPos> alreadyUsedStemHashMap, Map<Integer, BlockPos> logHashMap, int fungusStemsForWAILA, int maxSizeOfLogMapForDestroy, int maxSizeOfLogMapForShow, boolean nearbyListIsEmpty, boolean isSprintKeyPush, boolean endPossibilities) {

        BlockState blockStateNextTo = level.getBlockState(blockPosNextTo);

        if (((blockStateNextTo.is(STEMS) && sameKindLogCondition(blockStateCurrent, blockStateNextTo, isSprintKeyPush)) || (isHugeFungusBlock(blockState, blockStateNextTo) && sameXZPosition(blockPosNextTo, blockPos)))
                && !alreadyUsedStemHashMap.containsValue(blockPosNextTo) && !checkingLogHashMap.containsValue(blockPosNextTo)) {

            checkingLogHashMap.put(checkingLogHashMap.size(), blockPosNextTo);

            if (checkingLogHashMap.size() == maxSizeOfLogMapForDestroy)
                logHashMap.putAll(checkingLogHashMap);

            if (fungusStemsForWAILA <= maxSizeOfLogMapForShow+1 && nearbyListIsEmpty && blockStateNextTo.is(STEMS))
                fungusStemsForWAILA++;

            if (checkingLogHashMap.size() >= maxSizeOfLogMapForDestroy && fungusStemsForWAILA > maxSizeOfLogMapForShow+1) {
                return MutablePair.of(2, fungusStemsForWAILA);
            }
        }
        else if (endPossibilities) {
            return MutablePair.of(0, fungusStemsForWAILA);
        }
        return MutablePair.of(1, fungusStemsForWAILA);
    }

    private static Map<BlockPos, Integer> fungusCrownShape(Level level, BlockState targetBlockState) {
        Map<Integer, BlockPos> logHashMap = new HashMap<>(logHashMapGlobal);
        Map<BlockPos, Integer> posWithDistance = new HashMap<>();
        Map<Integer, BlockPos> alreadyUsed = new HashMap<>();

        Block block = targetBlockState.is(WARPED_STEMS) ? WARPED_WART_BLOCK : NETHER_WART_BLOCK;
        crownInfoForWAILA = MutablePair.of(block, 0);
        vineInfoForWAILA = MutablePair.of(WEEPING_VINES, 0);
        fungusSLInfoForWAILA = MutablePair.of(SHROOMLIGHT, 0);
        vinesHashMapGlobal.clear();

        for (Map.Entry<Integer, BlockPos> logBlockPos : logHashMap.entrySet()) {

            BlockPos blockPos = logBlockPos.getValue();

            for (int x = -3; x <= 3; x++) {
                for (int z = -3; z <= 3; z++) {

                    BlockPos blockPosNextTo = blockPos.offset(x, 0, z);
                    BlockState blockStateNextTo = level.getBlockState(blockPosNextTo);

                    if (isHugeFungusBlock(targetBlockState, blockStateNextTo) && !alreadyUsed.containsValue(blockPosNextTo)) {
                        addBlockToProperList(targetBlockState, blockStateNextTo, blockPosNextTo);
                        int distance = Math.abs(blockPos.getX() - blockPosNextTo.getX()) + Math.abs(blockPos.getZ() - blockPosNextTo.getZ());
                        if (!logHashMap.containsValue(blockPosNextTo))
                            posWithDistance.put(blockPosNextTo, distance);
                        alreadyUsed.put(alreadyUsed.size(), blockPosNextTo);
                    } else if (alreadyUsed.containsValue(blockPosNextTo) && posWithDistance.containsKey(blockPosNextTo)) {
                        int actualDistance = posWithDistance.get(blockPosNextTo);
                        int distance = Math.abs(blockPos.getX() - blockPosNextTo.getX()) + Math.abs(blockPos.getZ() - blockPosNextTo.getZ());
                        if (actualDistance > distance)
                            posWithDistance.replace(blockPosNextTo, distance);
                    }
                }
            }
        }
        return posWithDistance;
    }

    private static void fungusVineShape (Level level) {

        Map<Integer, BlockPos> alreadyUsed = new HashMap<>();
        Map<Integer, BlockPos> vinesHashMap = new HashMap<>(vinesHashMapGlobal);
        int j = 0;
        BlockPos blockPos;

        if (!vinesHashMap.isEmpty()) {
            blockPos = vinesHashMap.get(0);
        } else
            return;

        boolean isVineFound = true;

        BlockPos blockPosNextTo;

        while (isVineFound) {
            if (alreadyUsed.containsValue(blockPos) && vinesHashMap.get(j) != null) {
                blockPos = vinesHashMap.get(j);
                j++;
            }

            for (int y = -1; y <= 1; y=y+2) {
                blockPosNextTo = blockPos.offset(0, y, 0);
                BlockState blockStateCurrent = level.getBlockState(blockPos);
                BlockState blockStateNextTo = level.getBlockState(blockPosNextTo);

                if (blockStateNextTo.getBlock() instanceof WeepingVinesBlock && sameKindCondition(blockStateCurrent, blockStateNextTo) && !vinesHashMap.containsValue(blockPosNextTo) && !alreadyUsed.containsValue(blockPosNextTo)) {
                    vinesHashMap.put(vinesHashMap.size(), blockPosNextTo);
                }
                else if(vinesHashMap.get(j) == null && y==1)
                    isVineFound = false;
            }
            alreadyUsed.put(alreadyUsed.size(), blockPos);
        }

        if (!vinesHashMap.isEmpty())
            vineInfoForWAILA = MutablePair.of(level.getBlockState(vinesHashMap.get(0)).getBlock(), vinesHashMap.size());
    }

    private static void checkIfAnotherFungusNear(Level level, boolean isSprintKeyPush, Map<Integer, BlockPos> lowHighestPos) {

        Map<Integer, BlockPos> alreadyUsed = new HashMap<>();

        BlockPos blockPosNextTo;
        BlockState lowestlogBlockState = level.getBlockState(lowHighestPos.get(0));
        BlockPos lowestBlockPos;
        BlockPos highestBlockPos;

        for (int x = -4; x <= 4; x++) {
            for (int z = -4; z <= 4; z++) {
                for (int y = lowHighestPos.get(0).getY(); y <= lowHighestPos.get(1).getY(); y++) {

                    blockPosNextTo = lowHighestPos.get(0).offset(x, y, z);
                    if (x >= -1 && x <= 1 && z >= -1 && z <= 1) {
                        continue;
                    }

                    BlockState blockStateNextTo = level.getBlockState(blockPosNextTo);

                    while (!blockStateNextTo.is(STEMS) && blockPosNextTo.getY() < lowHighestPos.get(1).getY()+3) {
                        blockPosNextTo = blockPosNextTo.offset(0,2,0);
                        blockStateNextTo = level.getBlockState(blockPosNextTo);
                    }

                    if (blockStateNextTo.is(STEMS) && sameKindLogCondition(lowestlogBlockState, blockStateNextTo, isSprintKeyPush) && !alreadyUsed.containsValue(blockPosNextTo)) {

                        lowestBlockPos = findLowestStem(level, blockPosNextTo, blockStateNextTo);
                        highestBlockPos = findHighestStem(level, blockPosNextTo, blockStateNextTo);

                        if (!fungusLowestLogsInNearby.containsValue(lowestBlockPos)) {
                            fungusLowestLogsInNearby.put(fungusLowestLogsInNearby.size(), lowestBlockPos);
                        }
                        if (!fungusHighestLogsInNearby.contains(lowestBlockPos)) {
                            fungusHighestLogsInNearby.add(highestBlockPos);
                        }
                    }
                    alreadyUsed.put(alreadyUsed.size(), blockPosNextTo);
                }
            }
        }
    }

    private static void fungusCrownRemoveCrownBlocksFromAnotherFungus (Level level, BlockState targetBlockState, Map<BlockPos, Integer> hashMapPosDist, Map<Integer, BlockPos> lowHighestPos) {

        Map<Integer, BlockPos> logHashMap = new HashMap<>(fungusLogHashMapNear);
        Map<Integer, BlockPos> alreadyUsed = new HashMap<>();

        for (Map.Entry<Integer, BlockPos> logBlockPos : logHashMap.entrySet()) {


            BlockPos blockPos = logBlockPos.getValue();
            int rangeTopToBlock = 0;

            for (BlockPos highestPosNearby : fungusHighestLogsInNearby) {
                if (highestPosNearby.getX() == blockPos.getX() && highestPosNearby.getZ() == blockPos.getZ()) {
                    rangeTopToBlock = highestPosNearby.getY() - blockPos.getY();
                }
            }


            for (int x = -3; x <= 3; x++) {
                for (int z = -3; z <= 3; z++) {

                    BlockPos blockPosNextTo = blockPos.offset(x, 0, z);
                    BlockState blockStateNextTo = level.getBlockState(blockPosNextTo);


                    if (isHugeFungusBlock(targetBlockState, blockStateNextTo) && (!alreadyUsed.containsValue(blockPosNextTo) || hashMapPosDist.containsKey(blockPosNextTo))) {
                        if (isInnerPos(blockPos, blockPosNextTo) && rangeTopToBlock > 5)
                            continue;

                        if (hashMapPosDist.containsKey(blockPosNextTo)) {
                            int distanceFromNear = Math.abs(blockPos.getX() - blockPosNextTo.getX()) + Math.abs(blockPos.getZ() - blockPosNextTo.getZ());
                            int distanceFromHit = hashMapPosDist.get(blockPosNextTo);
                            BlockPos hitStemHighest = lowHighestPos.get(1);

                            //realInner = Fungus blocks around stem logs not lowe than 5 block from logs top
                            int diffHighestToCheckBlock = hitStemHighest.getY() - blockPosNextTo.getY();
                            boolean isNotRealInnerBlock = (diffHighestToCheckBlock > 5);
                            boolean toHighWhenNearAnotherFungus = (hitStemHighest.getY() + 1 < blockPosNextTo.getY()) && distanceFromNear <=3;

                            if ((distanceFromNear < distanceFromHit) || (isInnerPos(hitStemHighest, blockPosNextTo) && isNotRealInnerBlock) || toHighWhenNearAnotherFungus) {
                                hashMapPosDist.remove(blockPosNextTo);
                                if (isWartBlock(targetBlockState, blockStateNextTo))
                                    crownInfoForWAILA.setValue(crownInfoForWAILA.getValue()-1);
                                else if (blockStateNextTo.is(SHROOMLIGHT))
                                    fungusSLInfoForWAILA.setValue(fungusSLInfoForWAILA.getValue()-1);
                                else if ((blockStateNextTo.is(WEEPING_VINES) || blockStateNextTo.is(WEEPING_VINES_PLANT)))
                                    vineInfoForWAILA.setValue(vineInfoForWAILA.getValue()-1);
                            }
                        }
                        alreadyUsed.put(alreadyUsed.size(), blockPosNextTo);
                    }
                }
            }
        }
        fungusCrownPosWithDistance.clear();
        fungusCrownPosWithDistance.putAll(hashMapPosDist);
    }

    private static boolean isInnerPos(BlockPos blockPos, BlockPos blockPosNextTo) {

        int x = blockPos.getX() - blockPosNextTo.getX();
        int z = blockPos.getZ() - blockPosNextTo.getZ();

        return (x == -1 || x == 0 || x == 1) && (z == -1 || z == 0 || z == 1);
    }

    private static BlockPos findLowestStem(Level level, BlockPos blockPosNextTo, BlockState blockStateNextTo) {

        BlockPos blockPosBelow = blockPosNextTo.below();
        BlockState underLowestStem = level.getBlockState(blockPosBelow);

        while (underLowestStem.is(blockStateNextTo.getBlock()) || isHugeFungusBlock(blockStateNextTo, underLowestStem)) {
            blockPosBelow = blockPosBelow.below();
            underLowestStem = level.getBlockState(blockPosBelow);
        }

        return blockPosBelow.above();
    }

    private static BlockPos findHighestStem(Level level, BlockPos blockPosNextTo, BlockState blockStateNextTo) {

        BlockPos blockPosAbove = blockPosNextTo.above();
        BlockState aboveHighestStem = level.getBlockState(blockPosAbove);

        while (aboveHighestStem.is(blockStateNextTo.getBlock()) || isHugeFungusBlock(blockStateNextTo, aboveHighestStem)) {
            blockPosAbove = blockPosAbove.above();
            aboveHighestStem = level.getBlockState(blockPosAbove);
        }

        while (!aboveHighestStem.is(blockStateNextTo.getBlock())) {
            blockPosAbove = blockPosAbove.below();
            aboveHighestStem = level.getBlockState(blockPosAbove);
        }

        return blockPosAbove;
    }

    private static void addBlockToProperList (BlockState targetBlockState, BlockState blockStateNextTo, BlockPos blockPosNextTo) {

        if (isWartBlock(targetBlockState, blockStateNextTo))
            crownInfoForWAILA.setValue(crownInfoForWAILA.getValue()+1);
        else if (blockStateNextTo.is(SHROOMLIGHT))
            fungusSLInfoForWAILA.setValue(fungusSLInfoForWAILA.getValue()+1);
        else if ((blockStateNextTo.is(WEEPING_VINES) || blockStateNextTo.is(WEEPING_VINES_PLANT)) && targetBlockState.is(CRIMSON_STEMS) && !vinesHashMapGlobal.containsValue(blockPosNextTo))
            vinesHashMapGlobal.put(vinesHashMapGlobal.size(), blockPosNextTo);
    }

    private static boolean isHugeFungusBlock (BlockState blockStateStem, BlockState blockStateNextTo) {
        return (blockStateNextTo.is(BLOCKS_ON_CRIMSON_FUNGUS) && blockStateStem.is(CRIMSON_STEMS)) ||
                (blockStateNextTo.is(BLOCKS_ON_WARPED_FUNGUS) && blockStateStem.is(WARPED_STEMS)) || blockStateNextTo.is(SHROOMLIGHT);
    }

    private static boolean isWartBlock (BlockState blockState, BlockState blockStateNextTo) {
        return (blockStateNextTo.is(NETHER_WART_BLOCK) && blockState.is(CRIMSON_STEMS)) ||
                (blockStateNextTo.is(WARPED_WART_BLOCK) && blockState.is(WARPED_STEMS));
    }

    private static boolean sameXZPosition(BlockPos blockPos, BlockPos blockPosNextTo) {

        return blockPosNextTo.getX() == blockPos.getX() && blockPosNextTo.getZ() == blockPos.getZ();
    }

    ///////////////////////////////////////////////////////////

    //Tree methods:
    public static void prepareDataForTree(Level level, BlockPos blockPos) {

        logInfoForWAILA = new MutablePair<>(AIR, 0);
        crownInfoForWAILA = new MutablePair<>(AIR, 0);
        vineInfoForWAILA = new MutablePair<>(AIR, 0);
        fungusSLInfoForWAILA = new MutablePair<>(AIR, 0);

        boolean isSprintKeyPush = Minecraft.getInstance().options.keySprint.isDown();

        treeLogShape(level, blockPos, isSprintKeyPush);
        treeLeavesShape(level);
        treeVineShape(level);

    }

    public static void changeOrDestroyLeaves(Level level, Player player, BlockState blockState, ItemStack itemStackInMainHand, BlockPos blockPos, PlayerInteractEvent.RightClickBlock event, boolean changeColor) {

        boolean hasSilkTouch        = EnchantmentHelper.hasSilkTouch(itemStackInMainHand);
        boolean hasChanneling       = EnchantmentHelper.hasChanneling(itemStackInMainHand);
        boolean hasInfinity         = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, itemStackInMainHand) > 0;
        boolean isCrouching         = event.getEntity().isCrouching();
        ColorType colorType         = ColorType.PLAINS;
        int amountChangedOrDestroyed= 0;
        boolean isPerform           = false;
        boolean advItemUsedOnBlock  = false;

        if (changeColor) {
            CompoundTag compoundtag = itemStackInMainHand.getTag();
            if (compoundtag != null)
                colorType = ColorType.valueOf(compoundtag.getCompound("BlockStateTag").getString("color_type").toUpperCase());
        }

        if (!leavesCrownHashMapGlobal.isEmpty()) {

            BlockState leavesOnTreeBlockState;

            for (Map.Entry<Integer, BlockPos> leavesBlockPos : leavesCrownHashMapGlobal.entrySet()) {

                leavesOnTreeBlockState = level.getBlockState(leavesBlockPos.getValue());

                if (!hasSilkTouch && !hasChanneling && !changeColor && isCrouching && leavesOnTreeBlockState.getBlock() instanceof ParentTintedLeavesBlock && GrassesConfig.CommonConfig.ALLOW_CHANGE_LEAVES_COLOR_AT_ONCE.get()) {
                    if (!level.isClientSide) {

                        keepData(leavesBlockPos.getValue(), counterpartIDMap.get(getCounterpart(level, leavesBlockPos.getValue())), getColorType(level, leavesBlockPos.getValue()));
                        if (leavesOnTreeBlockState.is(JUNGLE_LEAVES_BLOCK.get())) {
                            int nextVersion = leavesOnTreeBlockState.getValue(DOUBLE_ALTER)+1;
                            if (nextVersion == 4)
                                nextVersion = 0;
                            level.setBlockAndUpdate(leavesBlockPos.getValue(), leavesOnTreeBlockState.setValue(DOUBLE_ALTER, nextVersion));
                        }
                        else
                            level.setBlockAndUpdate(leavesBlockPos.getValue(), leavesOnTreeBlockState.setValue(ALTER, !leavesOnTreeBlockState.getValue(ALTER)));
                        amountChangedOrDestroyed++;
                    }
                    isPerform = true;
                    advItemUsedOnBlock = true;
                }
                else if (!hasSilkTouch && !hasChanneling && !changeColor && GrassesConfig.CommonConfig.ALLOW_CUT_LEAVES_AT_ONCE.get()) {
                    if (!level.isClientSide) {
                        level.destroyBlock(leavesBlockPos.getValue(), true);
                        amountChangedOrDestroyed++;
                        if (!vinesHashMapGlobal.isEmpty()) {
                            for (Map.Entry<Integer, BlockPos> vineBlockPos : vinesHashMapGlobal.entrySet()) {
                                level.destroyBlock(vineBlockPos.getValue(), true);
                                amountChangedOrDestroyed++;
                            }
                        }
                    }
                    isPerform = true;
                    advItemUsedOnBlock = true;
                }
                else if (!hasSilkTouch && hasChanneling && !changeColor && !isCrouching && GrassesConfig.CommonConfig.ALLOW_CUT_LEAVES_AT_ONCE.get()) {
                    if (!level.isClientSide) {
                        level.destroyBlock(leavesBlockPos.getValue(), true);
                        amountChangedOrDestroyed++;
                    }
                    isPerform = true;
                    advItemUsedOnBlock = true;
                }
                else if (hasSilkTouch || changeColor){

                    if (leavesOnTreeBlockState.is(ALL_MOD_LEAVES)) {

                        if (changeColor && (!hasChanneling || !isCrouching) && GrassesConfig.CommonConfig.ALLOW_CHANGE_LEAVES_COLOR_AT_ONCE.get()) {

                            ColorType currentColor = getCurrentColor(level, blockPos, 0);

                            if(colorType != currentColor) {

                                onlyChangeColor(level, leavesBlockPos.getValue(), leavesOnTreeBlockState, colorType, 0);
                                if (!level.isClientSide)
                                    amountChangedOrDestroyed++;
                                isPerform = true;
                                advItemUsedOnBlock = true;
                            }
                        }
                        else if (hasSilkTouch && (!hasChanneling || !isCrouching) && GrassesConfig.CommonConfig.ALLOW_CHANGE_TINTED_LEAVES_INTO_NOT_GRASSES_AT_ONCE.get()){
                            if (!level.isClientSide) {
                                Block leavesBlock = counterpartIDMap.get(getCounterpart(level, leavesBlockPos.getValue()));
                                BlockState blockStateNew = leavesBlock.defaultBlockState();
                                blockStateNew = setStandardLeavesProperties(blockStateNew, leavesOnTreeBlockState);

                                if (leavesBlock.equals(WILLOW_LEAVES_BLOCK.get()))
                                    blockStateNew = blockStateNew.setValue(MOSSY, leavesOnTreeBlockState.getValue(MOSSY));

                                level.setBlockAndUpdate(leavesBlockPos.getValue(), blockStateNew);
                                amountChangedOrDestroyed++;
                            }
                            isPerform = true;
                            advItemUsedOnBlock = true;
                        }
                    }

                    else if (matchingCounterpartsLeaves.containsKey(leavesOnTreeBlockState.getBlock()) && (!hasChanneling || !isCrouching) && !changeColor && GrassesConfig.CommonConfig.ALLOW_CHANGE_NOT_GRASSES_LEAVES_INTO_TINTED_AT_ONCE.get()) {
                        for (Map.Entry<Block, Block> leavesBlock : matchingCounterpartsLeaves.entrySet()) {
                            if (leavesOnTreeBlockState.is(leavesBlock.getKey())) {
                                BlockState blockStateNew = leavesBlock.getValue().withPropertiesOf(leavesOnTreeBlockState);
                                keepData(leavesBlockPos.getValue(), leavesOnTreeBlockState.getBlock(), ColorType.PLAINS);
                                if (!level.isClientSide) {
                                    level.setBlockAndUpdate(leavesBlockPos.getValue(), blockStateNew);
                                    amountChangedOrDestroyed++;
                                }
                                isPerform = true;
                                advItemUsedOnBlock = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (!vinesHashMapGlobal.isEmpty()) {
            for (Map.Entry<Integer, BlockPos> vineBlockPos : vinesHashMapGlobal.entrySet()) {
                BlockState vinesOnTreeBlockState = level.getBlockState(vineBlockPos.getValue());

                if(!hasSilkTouch && hasChanneling && isCrouching && itemStackInMainHand.getItem() instanceof AutomaticPrunerItem) {
                    if (!level.isClientSide) {
                        level.destroyBlock(vineBlockPos.getValue(), true);
                        amountChangedOrDestroyed++;
                    }
                    isPerform = true;
                }
                else
                {
                    for (Map.Entry<Block, Block> vinesBlock : matchingCounterpartsVines.entrySet()) {
                        if (vinesOnTreeBlockState.is(vinesBlock.getValue()) && changeColor && (!hasChanneling || isCrouching) && GrassesConfig.CommonConfig.ALLOW_CHANGE_VINES_COLOR_AT_ONCE.get()) {
                            onlyChangeColor(level, vineBlockPos.getValue(), vinesOnTreeBlockState, colorType, 0);
                            if (!level.isClientSide)
                                amountChangedOrDestroyed++;
                            isPerform = true;
                            advItemUsedOnBlock = true;
                            break;
                        } else if (vinesOnTreeBlockState.is(vinesBlock.getValue()) && hasSilkTouch && (!hasChanneling || isCrouching) && GrassesConfig.CommonConfig.ALLOW_CHANGE_TINTED_VINES_INTO_NOT_GRASSES_AT_ONCE.get()) {
                            BlockState blockStateNew = vinesBlock.getKey().defaultBlockState();
                            blockStateNew = setVinesDirectionProperties(blockStateNew, vinesOnTreeBlockState);
                            if (!level.isClientSide) {
                                level.setBlock(vineBlockPos.getValue(), blockStateNew, 19);
                                amountChangedOrDestroyed++;
                            }
                            isPerform = true;
                            break;
                        } else if (vinesOnTreeBlockState.is(vinesBlock.getKey()) && !changeColor && hasSilkTouch && (!hasChanneling || isCrouching) && GrassesConfig.CommonConfig.ALLOW_CHANGE_NOT_GRASSES_VINES_INTO_TINTED_AT_ONCE.get()) {
                            BlockState blockStateNew = vinesBlock.getValue().defaultBlockState();
                            blockStateNew = setVinesDirectionProperties(blockStateNew, vinesOnTreeBlockState);
                            keepData(vineBlockPos.getValue(), vinesOnTreeBlockState.getBlock(), ColorType.PLAINS);
                            if (!level.isClientSide) {
                                level.setBlock(vineBlockPos.getValue(), blockStateNew, 19);
                                amountChangedOrDestroyed++;
                            }
                            isPerform = true;
                            advItemUsedOnBlock = true;
                            break;
                        }
                    }
                }
            }
        }

        if (!changeColor && isPerform) {
            level.playSound(null, blockPos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
            int hurt = (int) Math.floor(((double) amountChangedOrDestroyed /3)+((Math.pow(amountChangedOrDestroyed, 1.0/2.0)) * 2.75)-2);
            itemStackInMainHand.hurtAndBreak(hurt, event.getEntity(), (e) -> {
                e.broadcastBreakEvent(event.getHand());
            });
        }
        if (changeColor && isPerform){
            level.playSound(null, blockPos, SoundEvents.AZALEA_LEAVES_HIT, SoundSource.BLOCKS, 1.0F, 1.0F);
            int hurt = (int) Math.floor(((double) amountChangedOrDestroyed /3)+((Math.pow(amountChangedOrDestroyed, 1.0/2.0)) * 2.75)-2);
            if (!hasInfinity)
                itemStackInMainHand.hurtAndBreak(hurt, event.getEntity(), (e) -> {
                    e.broadcastBreakEvent(event.getHand());
                });
        }

        if (!isPerform)
            level.playSound(null, blockPos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);

        if(player instanceof ServerPlayer && advItemUsedOnBlock)
            ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockPos, itemStackInMainHand);

        event.setUseItem(Event.Result.ALLOW);
        level.addDestroyBlockEffect(blockPos, blockState);
        event.getEntity().swing(event.getHand(), true);
    }

    private static void treeLogShape(Level level, BlockPos blockPos, boolean isSprintKeyPush) {

        int maxSizeOfLogMapForDestroy = GrassesConfig.CommonConfig.LOGS_MAX_AMOUNT_AT_ONCE.get();
        int maxSizeOfLogMapForShow = GrassesConfig.CommonConfig.LOGS_MAX_AMOUNT_AT_ONCE.get() <= 150 ? 200 : GrassesConfig.CommonConfig.LOGS_MAX_AMOUNT_AT_ONCE.get();
        Map<Integer, BlockPos> checkingLogHashMap = new HashMap<>();
        Map<Integer, BlockPos> logHashMap = new HashMap<>();
        Map<Integer, BlockPos> alreadyUsedLogHashMap = new HashMap<>();
        Map<Integer, BlockPos> vineHashMap = new HashMap<>();
        boolean isLogFound = true;
        checkingLogHashMap.put(0, blockPos);
        int treeLogsForWAILA = 1;

        int j = 1;
        while (isLogFound) {

            if (alreadyUsedLogHashMap.containsValue(blockPos) && checkingLogHashMap.get(j) != null) {
                blockPos = checkingLogHashMap.get(j);
                j++;
            }

            BlockState blockStateAbove = level.getBlockState(blockPos.above());
            BlockState blockStateCurrent = level.getBlockState(blockPos);

            if (blockStateCurrent.is(LOGS) && !blockStateAbove.is(LOGS)) {
                first:
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        for (int z = -1; z <= 1; z++) {
                            BlockPos blockPosNextTo = blockPos.offset(x, y, z);
                            boolean endPossibilities = checkingLogHashMap.size() == j && x==1 && y==1 && z==1;

                            Pair<Integer, Integer> toReturn = checkConditionAndAddToLogLists(level, blockPosNextTo, blockStateCurrent, checkingLogHashMap, vineHashMap, alreadyUsedLogHashMap, logHashMap, treeLogsForWAILA, maxSizeOfLogMapForDestroy, maxSizeOfLogMapForShow, isSprintKeyPush, endPossibilities);
                            if (toReturn.getKey() == 2) break first;
                            else isLogFound = toReturn.getKey() == 1;
                            treeLogsForWAILA = toReturn.getValue();
                        }
                    }
                }
            } else if (blockStateCurrent.is(LOGS)) {
                for (int i = 0; i < 10; i++) {
                    BlockPos blockPosNextTo = blockPos.offset(xyzCoords.get(i));
                    boolean endPossibilities = checkingLogHashMap.get(j) == null && i == 9;

                    Pair<Integer, Integer> toReturn = checkConditionAndAddToLogLists(level, blockPosNextTo, blockStateCurrent, checkingLogHashMap, vineHashMap, alreadyUsedLogHashMap, logHashMap, treeLogsForWAILA, maxSizeOfLogMapForDestroy, maxSizeOfLogMapForShow, isSprintKeyPush, endPossibilities);
                    isLogFound = toReturn.getKey() == 1;
                    treeLogsForWAILA = toReturn.getValue();
                }
            }
            alreadyUsedLogHashMap.put(alreadyUsedLogHashMap.size(), blockPos);
        }
        logHashMapGlobal.clear();
        logHashMapGlobal.putAll(checkingLogHashMap);
        vinesHashMapGlobal.clear();
        vinesHashMapGlobal.putAll(vineHashMap);
        logInfoForWAILA = MutablePair.of(level.getBlockState(blockPos).getBlock(), treeLogsForWAILA);
    }

    private static Pair<Integer, Integer> checkConditionAndAddToLogLists (Level level, BlockPos blockPosNextTo, BlockState blockStateCurrent, Map<Integer, BlockPos> checkingLogHashMap, Map<Integer, BlockPos> vineHashMap, Map<Integer, BlockPos> alreadyUsedLogHashMap, Map<Integer, BlockPos> logHashMap, int treeLogsForWAILA, int maxSizeOfLogMapForDestroy, int maxSizeOfLogMapForShow, boolean isSprintKeyPush, boolean endPossibilities) {

        BlockState blockStateNextTo = level.getBlockState(blockPosNextTo);

        if (blockStateNextTo.getBlock() instanceof VineBlock && !vineHashMap.containsValue(blockPosNextTo)) {
            vineHashMap.put(vineHashMap.size(),blockPosNextTo);
        }

        if (blockStateNextTo.is(LOGS_THAT_BURN) && sameKindLogCondition(blockStateCurrent, blockStateNextTo, isSprintKeyPush) && !alreadyUsedLogHashMap.containsValue(blockPosNextTo) && !checkingLogHashMap.containsValue(blockPosNextTo)) {

            checkingLogHashMap.put(checkingLogHashMap.size(), blockPosNextTo);

            if (checkingLogHashMap.size() < maxSizeOfLogMapForDestroy)
                logHashMap.putAll(checkingLogHashMap);

            if (treeLogsForWAILA <= maxSizeOfLogMapForShow+1 && blockStateNextTo.is(LOGS_THAT_BURN))
                treeLogsForWAILA++;

            if (checkingLogHashMap.size() >= maxSizeOfLogMapForDestroy && treeLogsForWAILA > maxSizeOfLogMapForShow+1) {
                return MutablePair.of(2, treeLogsForWAILA);
            }
        }
        else if (endPossibilities)
            return MutablePair.of(0, treeLogsForWAILA);
        return MutablePair.of(1, treeLogsForWAILA);
    }

    private static void treeLeavesShape (Level level) {

        Map<Integer, BlockPos> logHashMap = new HashMap<>(logHashMapGlobal);
        Map<Integer, BlockPos> leavesHashMap = new HashMap<>();
        Map<Integer, BlockPos> vinesHashMap = new HashMap<>(vinesHashMapGlobal);
        Map<Integer, BlockPos> alreadyUsedLogHashMap = new HashMap<>();

        int j = 0;

        for (Map.Entry<Integer, BlockPos> logBlockPos : logHashMap.entrySet()) {

            BlockState blockState = level.getBlockState(logBlockPos.getValue());
            BlockPos blockPos = logBlockPos.getValue();
            boolean isLeavesFound = true;

            BlockPos blockPosNextTo;

            while (isLeavesFound) {
                if (alreadyUsedLogHashMap.containsValue(blockPos) && leavesHashMap.get(j) != null) {
                    blockPos = leavesHashMap.get(j);
                    j++;
                }

                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        for (int z = -1; z <= 1; z++) {
                            blockPosNextTo = blockPos.offset(x, y, z);
                            BlockState blockStateCurrent = level.getBlockState(blockPos);
                            BlockState blockStateNextTo = level.getBlockState(blockPosNextTo);

                            if (blockStateNextTo.getBlock() instanceof VineBlock && !vinesHashMap.containsValue(blockPosNextTo)) {
                                vinesHashMap.put(vinesHashMap.size(), blockPosNextTo);
                            }
                            if (blockStateNextTo.getBlock() instanceof LeavesBlock && sameKindCondition(blockStateCurrent, blockStateNextTo) && blockPosNextTo != blockPos && distanceCondition(blockStateCurrent, blockStateNextTo) && !leavesHashMap.containsValue(blockPosNextTo)) {

                                leavesHashMap.put(leavesHashMap.size(), blockPosNextTo);
                            }
                            else if(leavesHashMap.get(j) == null && x==1 && y==1 && z==1)
                                isLeavesFound = false;
                        }
                    }
                }
                if (blockState.is(LOGS_THAT_BURN))
                    alreadyUsedLogHashMap.put(alreadyUsedLogHashMap.size(), blockPos);
            }
        }
        leavesCrownHashMapGlobal.clear();
        leavesCrownHashMapGlobal.putAll(leavesHashMap);
        if (!leavesHashMap.isEmpty())
            crownInfoForWAILA = MutablePair.of(level.getBlockState(leavesHashMap.get(0)).getBlock(), leavesHashMap.size());
        vinesHashMapGlobal.clear();
        vinesHashMapGlobal.putAll(vinesHashMap);
    }

    private static void treeVineShape (Level level) {

        Map<Integer, BlockPos> alreadyUsed = new HashMap<>();
        Map<Integer, BlockPos> vinesHashMap = new HashMap<>(vinesHashMapGlobal);

        int j = 0;
        BlockPos blockPos;

        if (!vinesHashMap.isEmpty()) {
            blockPos = vinesHashMap.get(0);
        } else
            return;

        boolean isVineFound = true;

        BlockPos blockPosNextTo;

        while (isVineFound) {
            if (alreadyUsed.containsValue(blockPos) && vinesHashMap.get(j) != null) {
                blockPos = vinesHashMap.get(j);
                j++;
            }

            for (int y = -1; y <= 1; y=y+2) {
                blockPosNextTo = blockPos.offset(0, y, 0);
                BlockState blockStateCurrent = level.getBlockState(blockPos);
                BlockState blockStateNextTo = level.getBlockState(blockPosNextTo);

                if (blockStateNextTo.getBlock() instanceof VineBlock && sameKindCondition(blockStateCurrent, blockStateNextTo) && !vinesHashMap.containsValue(blockPosNextTo) && !alreadyUsed.containsValue(blockPosNextTo)) {
                    vinesHashMap.put(vinesHashMap.size(), blockPosNextTo);
                }
                else if(vinesHashMap.get(j) == null && y==1)
                    isVineFound = false;
            }
            alreadyUsed.put(alreadyUsed.size(), blockPos);
        }
        vinesHashMapGlobal.clear();
        vinesHashMapGlobal.putAll(vinesHashMap);
        if (!vinesHashMap.isEmpty())
            vineInfoForWAILA = MutablePair.of(level.getBlockState(vinesHashMap.get(0)).getBlock(), vinesHashMap.size());
    }

    private static boolean sameKindCondition(BlockState blockStateCurrent, BlockState blockStateNextTo) {

       if (blockStateCurrent.getBlock() instanceof LeavesBlock) {
            if (isBOPLoaded &&
                    ((blockStateCurrent.is(OAK_LEAVES) && blockStateNextTo.is(FLOWERING_OAK_LEAVES)) ||
                     (blockStateCurrent.is(FLOWERING_OAK_LEAVES) && blockStateNextTo.is(OAK_LEAVES)) ||
                     (blockStateCurrent.is(OAK_LEAVES_BLOCK.get()) && blockStateNextTo.is(FLOWERING_OAK_LEAVES_BLOCK.get())) ||
                     (blockStateCurrent.is(FLOWERING_OAK_LEAVES_BLOCK.get()) && blockStateNextTo.is(OAK_LEAVES_BLOCK.get()))))
                return true;
            else
                return blockStateCurrent.getBlock().equals(blockStateNextTo.getBlock());
        } else if (blockStateCurrent.getBlock() instanceof VineBlock || blockStateCurrent.getBlock() instanceof WeepingVinesBlock)
            return blockStateCurrent.getBlock().equals(blockStateNextTo.getBlock());
        else if (isBlockOnTag(blockStateCurrent, vanillaLogsTagsBlocksList) || (isBOPLoaded && isBlockOnTag(blockStateCurrent, BOPLogsTagsBlocksList)))
            return matchingLogsWithLeaves.containsKey(blockStateNextTo.getBlock()) && blockStateCurrent.is(matchingLogsWithLeaves.get(blockStateNextTo.getBlock())); // if blockStateCurrent (vanilla log) is a log on specifics logTag assigned to the appropriate leaves
        else
            return false;
    }

    private static boolean isBlockOnTag(BlockState blockStateCurrent, List<TagKey<Block>> list) {

        boolean isBlockOnTag = false;

        for (TagKey<Block> tagKey : list) {
            if(blockStateCurrent.is(tagKey))
                isBlockOnTag = true;
        }

        return isBlockOnTag;
    }

    private static boolean distanceCondition (BlockState blockStateCurrent, BlockState blockStateNextTo) {
        if (blockStateCurrent.hasProperty(DISTANCE) && blockStateNextTo.hasProperty(DISTANCE)) {
            return blockStateCurrent.getValue(DISTANCE) < blockStateNextTo.getValue(DISTANCE);
        }
        return true;
    }

    private static boolean sameKindLogCondition (BlockState blockStateCurrent, BlockState blockStateNextTo, boolean isSprintKeyPush) {

        if (GrassesConfig.CommonConfig.ALLOW_CONNECT_SAME_KIND_LOG.get() && isSprintKeyPush) {

            List<TagKey<Block>> tagList = blockStateCurrent.getTags().toList();
            TagKey<Block> tagKey = null;

            for (TagKey<Block> blockTagKey : tagList) {
                tagKey = getLogTagByName(blockTagKey);
                if (tagKey != null)
                    break;
            }

            return tagKey != null && blockStateNextTo.is(tagKey);
        }
        else
            return blockStateCurrent.getBlock().equals(blockStateNextTo.getBlock());
    }

    private static TagKey<Block> getLogTagByName(TagKey<Block> tagKey) {
        if (tagKey == DARK_OAK_LOGS) return DARK_OAK_LOGS;
        else if (tagKey == OAK_LOGS) return OAK_LOGS;
        else if (tagKey == ACACIA_LOGS) return ACACIA_LOGS;
        else if (tagKey == BIRCH_LOGS) return BIRCH_LOGS;
        else if (tagKey == CHERRY_LOGS) return CHERRY_LOGS;
        else if (tagKey == JUNGLE_LOGS) return JUNGLE_LOGS;
        else if (tagKey == MANGROVE_LOGS) return MANGROVE_LOGS;
        else if (tagKey == SPRUCE_LOGS) return SPRUCE_LOGS;
        else if (tagKey == CRIMSON_STEMS) return CRIMSON_STEMS;
        else if (tagKey == WARPED_STEMS) return WARPED_STEMS;
        else return null;
    }

    private static BlockState setVinesDirectionProperties(BlockState blockState, BlockState vinesOnTreeBlockState) {

        blockState = blockState
                .setValue(UP, vinesOnTreeBlockState.getValue(UP))
                .setValue(NORTH, vinesOnTreeBlockState.getValue(NORTH))
                .setValue(SOUTH, vinesOnTreeBlockState.getValue(SOUTH))
                .setValue(EAST, vinesOnTreeBlockState.getValue(EAST))
                .setValue(WEST, vinesOnTreeBlockState.getValue(WEST));
        return blockState;
    }

    public static BlockPos getLookingAtBlockPos(Level level, Player player) {
        double reachDistance = 5.0; // Or however far you want the player to be able to look
        Vec3 eyePosition = player.getEyePosition(1.0f);
        Vec3 lookVector = player.getLookAngle();
        Vec3 reachVector = eyePosition.add(lookVector.scale(reachDistance));

        BlockHitResult rayTraceResult = level.clip(new ClipContext(
                eyePosition,
                reachVector,
                ClipContext.Block.OUTLINE,
                ClipContext.Fluid.NONE,
                player
        ));

        if (rayTraceResult.getType() == HitResult.Type.BLOCK) {
            return rayTraceResult.getBlockPos();
        }
        return null;
    }

    //Others

    public static void deactivatePortal(Level level, BlockPos blockPos) {

        Map<Integer, BlockPos> endPortalHashMap = new HashMap<>();

        boolean isPortalFound = true;
        int i = 0;

        while (isPortalFound) {

            if (!endPortalHashMap.isEmpty()) {
                blockPos = endPortalHashMap.get(i);
                i++;
            }

            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    BlockPos blockPosNextTo = blockPos.offset(x, 0, z);
                    BlockState blockStateNextTo = level.getBlockState(blockPosNextTo);

                    if (blockStateNextTo.is(END_PORTAL) && !endPortalHashMap.containsValue(blockPosNextTo)) {
                        endPortalHashMap.put (endPortalHashMap.size(), blockPosNextTo);
                    } else if (endPortalHashMap.get(i) == null && x==1 && z ==1)
                        isPortalFound = false;
                }
            }
        }

        for (Map.Entry<Integer, BlockPos> blockPosNextTo : endPortalHashMap.entrySet()) {
            level.setBlockAndUpdate(blockPosNextTo.getValue(), AIR.defaultBlockState());
            level.addDestroyBlockEffect(blockPosNextTo.getValue(), level.getBlockState(blockPosNextTo.getValue()));
            level.playSound(null, blockPosNextTo.getValue(), SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
        }

    }

    public static void lilyOnLiliPad(Level level, BlockState blockState, BlockPos blockPos, ItemStack itemStack) {

        List<BlockPos> blockPosList;
        int setLily = 1;
        if (itemStack.getItem().equals(WATERLILY.asItem()))
            setLily = 2;

        blockState = blockState.setValue(VARIANT_LILY, setLily);

        blockPosList = checkHugeLily(blockPos, blockState);

        for (BlockPos blockPosToSet : blockPosList) {
            BlockState blockStateCurrent = level.getBlockState(blockPosToSet);
            keepData(blockPosToSet, HUGE_LILY_PAD, getColorType(level, blockPos));
            level.setBlock(blockPosToSet, blockStateCurrent.setValue(VARIANT_LILY, setLily), 19);
        }
        level.setBlock(blockPos, blockState, 19);
    }

    public static boolean waterLilyCondition(ItemStack itemStack) {

        return itemStack.getItem().equals(WATERLILY_TINTED.get().asItem()) || itemStack.getItem().equals(WATERLILY.asItem());
    }

    public static boolean isPlantForBars(ItemStack itemStack) {

        for (Map.Entry<Block, Block> plantInBars : matchingBarsWithPlant.entrySet()) {
            if (itemStack.getItem().equals(plantInBars.getValue().asItem())) {
                return true;
            }
        }
        return false;
    }

    public static void plantIntoBars(Level level, BlockState blockState, BlockPos blockPos, ItemStack itemStack, Player player, BlockHitResult hitVector) {

        boolean north = blockState.getValue(NORTH);
        boolean east = blockState.getValue(EAST);
        boolean south = blockState.getValue(SOUTH);
        boolean west = blockState.getValue(CrossCollisionBlock.WEST);

        Direction facing = player.getDirection();
        boolean fNorth = facing.equals(Direction.NORTH);
        boolean fEast = facing.equals(Direction.EAST);
        boolean fSouth = facing.equals(Direction.SOUTH);
        boolean fWest = facing.equals(Direction.WEST);

        Block itemInHandAsBlock = ((BlockItem) itemStack.getItem()).getBlock();

        boolean westEast = ((west || east) && !north && !south) && (fEast || fWest);
        boolean northSouth = (south || north) && !east && !west && (fNorth || fSouth);

        if (itemInHandAsBlock instanceof VineBlock || itemInHandAsBlock instanceof TintedVine) {

            if (westEast) {
                if (hitVector.getLocation().z > player.getZ())
                    facing = Direction.SOUTH;
                else if (hitVector.getLocation().z <= player.getZ())
                    facing = Direction.NORTH;
            } else if (northSouth) {
                if (hitVector.getLocation().x > player.getX())
                    facing = Direction.EAST;
                else if (hitVector.getLocation().x <= player.getX())
                    facing = Direction.WEST;
            }
        }

        BlockState blockStateToSet = null;
        boolean sameBlockSameColor = false;

        for (Map.Entry<Block, Block> plantInBars : matchingBarsWithPlant.entrySet()) {
            if (itemStack.getItem() == plantInBars.getValue().asItem()) {

                if (!blockState.is(plantInBars.getKey())) {
                    blockStateToSet = plantInBars.getKey().withPropertiesOf(blockState);
                    break;
                }
                else if (isTintedBarsBlock(blockState) && hasBlockStateTag(itemStack) && getColorType(level, blockPos) != getColorTypeFromNBT(itemStack)) {
                    blockStateToSet = plantInBars.getKey().withPropertiesOf(blockState);
                    sameBlockSameColor = true;
                    break;
                }
            }
        }

        if (blockStateToSet != null) {

            if (isTintedBarsBlock(blockStateToSet)) {
                Block block = getKey(matchingCounterpartsPlants, ((BlockItem) itemStack.getItem()).getBlock());
                keepData(blockPos, getKey(matchingBarsWithPlant, block), getColorTypeFromNBT(itemStack));
            }

            if (!blockState.is(IRON_BARS) && !player.isCreative()) {
                ItemStack itemStackContent = new ItemStack(matchingBarsWithPlant.get(blockState.getBlock()));
                if (isTintedBarsBlock(blockState))
                    addToInventoryWithColor(level, blockPos, player, itemStackContent, getColorType(level, blockPos), 0, itemStack);
                else
                    addToInventory(level, player, itemStackContent);
            }

            if(blockStateToSet.getBlock() instanceof VineInBars || blockStateToSet.getBlock() instanceof TintedVineInBars) {
                blockStateToSet = blockStateToSet.setValue(FACING, facing);
            }

            if (sameBlockSameColor)
                updateColorOnEntityBlock(level, blockStateToSet, blockPos, keepColorType.get(blockPos));
            else
                level.setBlockAndUpdate(blockPos, blockStateToSet);

            level.playSound(null, blockPos, SoundEvents.GRASS_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!player.isCreative())
                itemStack.shrink(1);
        }
        else
            level.playSound(null, blockPos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    public static boolean isSameVineBarsBlockAndSameColor (BlockState blockState, BlockPos blockPos, ItemStack itemStack) {

        Block matchingBarsBlock = getKey(matchingBarsWithPlant, ((BlockItem) itemStack.getItem()).getBlock());

        boolean isSame = blockState.getBlock().equals(matchingBarsBlock);

        if (matchingBarsBlock instanceof VineInBars && isSame)
            return false;
        else return !(matchingBarsBlock instanceof TintedVineInBars) || !isSame || !compareColors(blockPos, itemStack);
    }

}
