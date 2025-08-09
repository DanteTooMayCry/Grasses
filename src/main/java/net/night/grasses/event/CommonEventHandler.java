package net.night.grasses.event;

import biomesoplenty.block.HugeCloverPetalBlock;
import biomesoplenty.block.HugeLilyPadBlock;
import biomesoplenty.block.properties.QuarterProperty;
import com.teamremastered.endrem.blocks.ERFrameProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.night.grasses.Grasses;
import net.night.grasses.block.plants.superclasses.ParentTintedBushBlock;
import net.night.grasses.config.AdditionalDropConfig;
import net.night.grasses.enums.ColorType;
import net.night.grasses.config.GrassesConfig;
import net.night.grasses.entity.ai.goal.EatGrassesBlockGoal;
import net.night.grasses.init.ItemsRegister;
import net.night.grasses.item.AutomaticPrunerItem;
import net.night.grasses.item.DyeingBoneMealItem;
import net.night.grasses.item.DyeingTool;
import net.night.grasses.enums.GrassesQuarterProperty;
import net.night.grasses.util.ModTags;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

import static biomesoplenty.api.block.BOPBlocks.*;
import static biomesoplenty.block.HugeLilyPadBlock.QUARTER;
import static com.teamremastered.endrem.blocks.AncientPortalFrame.EYE;
import static com.teamremastered.endrem.registers.ERBlocks.ANCIENT_PORTAL_FRAME;
import static net.minecraft.advancements.CriteriaTriggers.ITEM_USED_ON_BLOCK;
import static net.minecraft.tags.BlockTags.*;
import static net.minecraft.world.level.block.Blocks.*;
import static net.minecraft.world.level.block.DoublePlantBlock.HALF;
import static net.minecraft.world.level.block.EndPortalFrameBlock.HAS_EYE;
import static net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING;
import static net.minecraft.world.level.block.SlabBlock.TYPE;
import static net.minecraft.world.level.block.piston.PistonBaseBlock.EXTENDED;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.SLAB_TYPE;
import static net.minecraft.world.level.block.state.properties.DoubleBlockHalf.LOWER;
import static net.night.grasses.Grasses.*;
import static net.night.grasses.data.ModData.*;
import static net.night.grasses.data.ModMethods.*;
import static net.night.grasses.event.CommonEventsMethods.*;
import static net.night.grasses.init.BlocksRegister.*;
import static net.night.grasses.init.BlocksRegisterBoP.*;
import static net.night.grasses.util.ModTags.Blocks.*;


@Mod.EventBusSubscriber(modid = Grasses.MOD_ID)
public class CommonEventHandler {

    public static AdditionalDropConfig additionalDropConfig;

    public static Map<Integer, BlockPos> logHashMapGlobal = new HashMap<>();
    public static Map<Integer, BlockPos> vinesHashMapGlobal = new HashMap<>();
    public static Map<Integer, BlockPos> leavesCrownHashMapGlobal = new HashMap<>();

    public static Map<BlockPos, Integer> fungusCrownPosWithDistance = new HashMap<>();
    public static Map<Integer, BlockPos> fungusLogHashMapNear = new HashMap<>();
    public static Map<Integer, BlockPos> fungusLowestLogsInNearby = new HashMap<>();
    public static Map<Integer, BlockPos> fungusBlocksThatReplacedStem = new HashMap<>();
    public static List<BlockPos> fungusHighestLogsInNearby = new ArrayList<>();

    public static Pair<Block, Integer> logInfoForWAILA;
    public static Pair<Block, Integer> crownInfoForWAILA;
    public static Pair<Block, Integer> vineInfoForWAILA;
    public static Pair<Block, Integer> fungusSLInfoForWAILA;

    private static boolean changeSprintKeyPush;
    private static BlockPos currentBlockPos = null;
    private static Item currentItem = null;

    public static List<Vec3i> xyzCoords = Arrays.asList(
            new Vec3i(1, 0, 0),
            new Vec3i(0, 0, 1),
            new Vec3i(0, 0, -1),
            new Vec3i(-1, 0, 0),
            new Vec3i(0, 1, 0),
            new Vec3i(0, -1, 0),
            new Vec3i(-1, 0, -1),
            new Vec3i(1, 0, -1),
            new Vec3i(-1, 0, 1),
            new Vec3i(1, 0, 1)
    );

    @SubscribeEvent
    public static void onGrassesClickedWithTool(BlockEvent.BlockToolModificationEvent event) {
        boolean isHoe = event.getToolAction() == ToolActions.HOE_TILL;
        boolean isShovel = event.getToolAction() == ToolActions.SHOVEL_FLATTEN;

        if (event.isCanceled() || (!isHoe && !isShovel))
            return;

        Level level = event.getContext().getLevel();
        BlockState clickedBlockState = level.getBlockState(event.getContext().getClickedPos());

        boolean isModGrass = clickedBlockState.is(ModTags.Blocks.ALL_MOD_GRASS_BLOCKS);
        boolean isModSlabGrass = clickedBlockState.is(ModTags.Blocks.ALL_MOD_GRASS_SLABS);
        boolean isModDirtSlab = clickedBlockState.is(DIRT_SLAB_BLOCK.get());
        boolean isModCoarseDirtSlab = clickedBlockState.is(COARSE_DIRT_SLAB_BLOCK.get());
        boolean isModRootedDirtSlab = clickedBlockState.is(ROOTED_DIRT_SLAB_BLOCK.get());

        BlockPos blockPos = event.getPos();
        BlockState blockState = event.getState();

        if (isModGrass) {
            BlockState newBlockFarmland = FARMLAND.defaultBlockState();
            BlockState newBlockPath = DIRT_PATH.defaultBlockState();

            if (isHoe) {
                if (newBlockFarmland.canSurvive(level, blockPos)) {
                    event.setFinalState(newBlockFarmland);
                }
            } else if (isShovel) {
                if (newBlockPath.canSurvive(level, blockPos)) {
                    event.setFinalState(newBlockPath);
                }
            }
        } else if (isModSlabGrass || isModDirtSlab || isModCoarseDirtSlab || isModRootedDirtSlab) {
            BlockState newBlockFarmlandSlab = FARMLAND_SLAB_BLOCK.get().defaultBlockState().setValue(TYPE, blockState.getValue(TYPE));
            BlockState newBlockDirtSlab = DIRT_SLAB_BLOCK.get().defaultBlockState().setValue(TYPE, blockState.getValue(TYPE));
            BlockState newBlockDirtPathSlab = DIRT_PATH_SLAB_BLOCK.get().defaultBlockState().setValue(TYPE, blockState.getValue(TYPE));

            if (isHoe) {
                if (isModRootedDirtSlab){
                    Block.popResourceFromFace(level, blockPos, Objects.requireNonNull(event.getContext().getClickedFace()), new ItemStack(Items.HANGING_ROOTS));
                    event.setFinalState(newBlockDirtSlab);
                }
                else if (newBlockFarmlandSlab.canSurvive(level, blockPos)) {
                    event.setFinalState(newBlockFarmlandSlab);
                }
            } else if (isShovel) {
                if (newBlockDirtPathSlab.canSurvive(level, blockPos)) {
                    event.setFinalState(newBlockDirtPathSlab);
                }
            }
        }
    }


    @SubscribeEvent
    public static void cuttingDownTree (BlockEvent.BreakEvent event) {

        if (event.isCanceled()) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack itemStack = player.getMainHandItem();
        boolean hasSilkTouch = EnchantmentHelper.hasSilkTouch(itemStack);
        BlockPos blockPos = event.getPos();
        Level level = player.level();
        BlockState blockState = level.getBlockState(blockPos);

        if(itemStack.getItem() instanceof AxeItem && hasSilkTouch && GrassesConfig.CommonConfig.ALLOW_CHOP_TREE_AT_ONCE.get()) {

            boolean isTreeOrStemLog = blockState.is(LOGS) && !blockState.is(BLACKLIST_LOGS);

            if (isTreeOrStemLog) {
                event.setCanceled(true);

                for (Map.Entry<Integer, BlockPos> logBlockPos : logHashMapGlobal.entrySet()) {

                    if (fungusBlocksThatReplacedStem.containsValue(logBlockPos.getValue())) {
                        fungusBlocksThatReplacedStem.values().remove(logBlockPos.getValue());
                        if (fungusCrownPosWithDistance.isEmpty())
                            level.destroyBlock(logBlockPos.getValue(), true);
                    } else
                        level.destroyBlock(logBlockPos.getValue(), true);
                }

                if(player instanceof ServerPlayer)
                    ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockPos, itemStack);
            }
        } else
            spawnAdditionalDrops(level, player, blockPos, itemStack, blockState);
    }

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {

        if (event.isCanceled())
            return;

        Optional<BlockPos> position = event.getPosition();

        if (position.isPresent()) {
            ItemStack itemStack = event.getEntity().getMainHandItem();
            boolean hasSilkTouch = EnchantmentHelper.hasSilkTouch(itemStack);

            if (itemStack.getItem() instanceof AxeItem && hasSilkTouch && event.getState().is(LOGS) && !event.getState().is(BLACKLIST_LOGS) && GrassesConfig.CommonConfig.ALLOW_CHOP_TREE_AT_ONCE.get()) {

                if (!logHashMapGlobal.isEmpty()) {
                    double multi = 1 / Math.pow(logHashMapGlobal.size(), 1 / 1.1F);
                    event.setNewSpeed((float) (event.getNewSpeed() * multi));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {

        Player player               = event.player;
        Level level                 = event.player.level();
        BlockPos blockPosLookingAt  = getLookingAtBlockPos(level, player);

        if (blockPosLookingAt != null && event.side.isServer()) {

            boolean isSprintKeyPush       = Minecraft.getInstance().options.keySprint.isDown();
            ItemStack itemStackInMainHand = player.getMainHandItem();
            Item itemInHand               = itemStackInMainHand.getItem();
            boolean hasSilkTouch          = EnchantmentHelper.hasSilkTouch(itemStackInMainHand);

            if (currentBlockPos == null || changeSprintKeyPush != isSprintKeyPush || currentBlockPos.getX() != blockPosLookingAt.getX() || currentBlockPos.getY() != blockPosLookingAt.getY() || currentBlockPos.getZ() != blockPosLookingAt.getZ() || !currentItem.equals(itemInHand)) {
                currentBlockPos = blockPosLookingAt;
                changeSprintKeyPush = isSprintKeyPush;
                currentItem = itemInHand;
                BlockState blockStateLookingAt = level.getBlockState(blockPosLookingAt);

                boolean lookingAtStemsLog = blockStateLookingAt.is(CRIMSON_STEMS) || blockStateLookingAt.is(WARPED_STEMS);
                boolean lookingAtLogs = blockStateLookingAt.is(LOGS_THAT_BURN);

                if (lookingAtStemsLog && (itemInHand instanceof AutomaticPrunerItem || (itemInHand instanceof AxeItem && hasSilkTouch)))
                    prepareDataForFungus(level, blockStateLookingAt, blockPosLookingAt);
                else if (lookingAtLogs && (itemInHand instanceof AutomaticPrunerItem || (itemInHand instanceof AxeItem && hasSilkTouch) || itemInHand instanceof DyeingTool))
                    prepareDataForTree(level, blockPosLookingAt);
            }
        }
    }

    @SubscribeEvent
    public static void onRightHandClickBlockWithItem(PlayerInteractEvent.RightClickBlock event) {

        if (event.isCanceled())
            return;

        InteractionHand interactionHand = event.getHand();
        if (interactionHand != InteractionHand.MAIN_HAND)
            return;

        BlockPos blockPos = event.getPos();
        Level level = event.getEntity().level();
        Player player = event.getEntity();
        ItemStack itemStackInMainHand = player.getMainHandItem();
        BlockState blockState = level.getBlockState(blockPos);


        boolean hitLogs                     = blockState.is(LOGS);
        boolean hitStemsLog                 = blockState.is(CRIMSON_STEMS) || blockState.is(WARPED_STEMS);
        boolean hitVanillaEndFrame          = blockState.is(END_PORTAL_FRAME);
        boolean hitModEndFrame              = blockState.is(END_PORTAL_FRAME_BLOCK.get());
        boolean hitEREndFrame               = isERLoaded && blockState.is(ANCIENT_PORTAL_FRAME.get());
        boolean hasSilkAndChanneling        = EnchantmentHelper.hasSilkTouch(itemStackInMainHand) && EnchantmentHelper.hasChanneling(itemStackInMainHand);
        boolean fertileCondition            = !blockState.hasProperty(FERTILE) || isFertileState(blockState);
        boolean isSprintKeyPush             = Minecraft.getInstance().options.keySprint.isDown();

        if (hitStemsLog && itemStackInMainHand.getItem() instanceof AutomaticPrunerItem && GrassesConfig.CommonConfig.ALLOW_CUT_WART_AT_ONCE.get()){
            destroyHugeFungusCrown(level, player,itemStackInMainHand, blockPos, event);
        }
        else if (hitLogs && itemStackInMainHand.getItem() instanceof AutomaticPrunerItem) {
            changeOrDestroyLeaves(level, player, blockState, itemStackInMainHand, blockPos, event, false);
        }
        else if (hitLogs && itemStackInMainHand.getItem() instanceof DyeingTool) {
            changeOrDestroyLeaves(level, player, blockState, itemStackInMainHand, blockPos, event, true);
        }
        else if (hitModEndFrame && itemStackInMainHand.getItem().equals(ItemsRegister.NETHERITE_AUTO_PRUNER.get()) && hasSilkAndChanneling && GrassesConfig.CommonConfig.ALLOW_USE_NETHERITE_AUTO_PRUNER_ON_END_FRAME.get()) {

            level.setBlockAndUpdate(blockPos, AIR.defaultBlockState());
            Block.popResourceFromFace(level, blockPos, Objects.requireNonNull(event.getFace()), new ItemStack(END_PORTAL_FRAME_BLOCK.get()));
            if (blockState.getValue(HAS_EYE).equals(Boolean.TRUE))
                Block.popResourceFromFace(level, blockPos, Objects.requireNonNull(event.getFace()), new ItemStack(Items.ENDER_EYE));
            deactivatePortal(level, blockPos);

            itemStackInMainHand.hurtAndBreak(1, player, (e) -> {
                e.broadcastBreakEvent(interactionHand);
            });
            level.addDestroyBlockEffect(blockPos, blockState);
            level.playSound(null, blockPos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.playSound(null, blockPos, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
            player.swing(interactionHand, true);

            if(player instanceof ServerPlayer)
                ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockPos, itemStackInMainHand);
        }
        else if (itemStackInMainHand.getItem() instanceof ShearsItem) {

            boolean hasSilkTouch                = EnchantmentHelper.hasSilkTouch(itemStackInMainHand);

            boolean hitVanillaGrass             = blockState.is(GRASS_BLOCK);
            boolean hitVanillaMycelium          = blockState.is(MYCELIUM);
            boolean hitVanillaPodzol            = blockState.is(PODZOL);
            boolean hitStickyPiston             = blockState.is(STICKY_PISTON);
            boolean hitNotGrassesLeaves         = matchingCounterpartsLeaves.containsKey(blockState.getBlock()) && hasSilkTouch && GrassesConfig.CommonConfig.ALLOW_CHANGE_NOT_GRASSES_LEAVES_INTO_TINTED_SEVERALLY.get();
            boolean hitNotGrassesPlants         = matchingCounterpartsPlants.containsKey(blockState.getBlock()) && hasSilkTouch && GrassesConfig.CommonConfig.ALLOW_CHANGE_NOT_GRASSES_PLANTS_INTO_TINTED.get();
            boolean hitNotGrassesVines          = matchingCounterpartsVines.containsKey(blockState.getBlock()) && hasSilkTouch && GrassesConfig.CommonConfig.ALLOW_CHANGE_NOT_GRASSES_VINES_INTO_TINTED_SEVERALLY.get();
            boolean hitVanillaPot               = matchingCounterpartsVanillaPotted.containsKey(blockState.getBlock()) && hasSilkTouch && !tintedPlantsThatCanBePotted.contains(itemStackInMainHand.getItem()) && GrassesConfig.CommonConfig.ALLOW_CHANGE_POTTED_NOT_GRASSES_PLANTS_INTO_TINTED.get();
            boolean hitVanillaNylium            = blockState.is(CRIMSON_NYLIUM) || blockState.is(WARPED_NYLIUM);
            boolean hitTreeSapling              = blockState.is(SAPLINGS) && !blockState.is(MANGROVE_PROPAGULE);

            boolean advItemUsedOnBlock          = false;
            boolean usedShearsOnCorrectBlock    = false;
            int hurtAndBreak                    = 1;
            Block hitBlock                      = blockState.getBlock();

            if (hitVanillaGrass || hitVanillaMycelium || hitVanillaNylium || hitVanillaPodzol) {
                event.setCanceled(true);

                usedShearsOnCorrectBlock = true;
                level.playSound(null, blockPos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0f, 1.0f);

                if (hasSilkTouch) {
                    if (hitVanillaGrass)
                        level.setBlockAndUpdate(blockPos, GROW_GRASS_BLOCK.get().defaultBlockState());
                    else if (hitVanillaMycelium)
                        level.setBlockAndUpdate(blockPos, GROW_MYCELIUM_BLOCK.get().defaultBlockState());
                    else if (hitVanillaPodzol)
                        level.setBlockAndUpdate(blockPos, GROW_PODZOL_BLOCK.get().defaultBlockState());
                    else if (hitVanillaNylium) {
                        if (blockState.is(CRIMSON_NYLIUM))
                            level.setBlockAndUpdate(blockPos, GROW_CRIMSON_NYLIUM_BLOCK.get().defaultBlockState());
                        if (blockState.is(WARPED_NYLIUM))
                            level.setBlockAndUpdate(blockPos, GROW_WARPED_NYLIUM_BLOCK.get().defaultBlockState());
                    }
                    advItemUsedOnBlock = true;
                } else {
                    if (hitVanillaGrass || hitVanillaMycelium || hitVanillaPodzol) {
                        level.setBlockAndUpdate(blockPos, Blocks.DIRT.defaultBlockState());
                        if (hitVanillaGrass)
                            Block.popResourceFromFace(level, blockPos, Objects.requireNonNull(event.getFace()), new ItemStack(Items.GRASS));
                        else if (hitVanillaMycelium) {
                            int i = Mth.nextInt(RandomSource.create(), 0, 1);
                            ItemStack itemStack1 = new ItemStack(Items.BROWN_MUSHROOM);
                            if (i == 0)
                                itemStack1 = new ItemStack(Items.RED_MUSHROOM);
                            Block.popResourceFromFace(level, blockPos, Objects.requireNonNull(event.getFace()), itemStack1);
                        } else if (hitVanillaPodzol) {
                            Block.popResourceFromFace(level, blockPos, Objects.requireNonNull(event.getFace()),  new ItemStack(Items.FERN));
                        }
                    }
                    else if (hitVanillaNylium) {
                        level.setBlockAndUpdate(blockPos, NETHERRACK.defaultBlockState());
                        if (blockState.is(CRIMSON_NYLIUM))
                            Block.popResourceFromFace(level, blockPos, Objects.requireNonNull(event.getFace()), new ItemStack(Items.CRIMSON_ROOTS));
                        if (blockState.is(WARPED_NYLIUM))
                            Block.popResourceFromFace(level, blockPos, Objects.requireNonNull(event.getFace()), new ItemStack(Items.WARPED_ROOTS));
                    }
                }

            } else if (hitStickyPiston && blockState.getValue(EXTENDED).equals(Boolean.FALSE) && hasSilkTouch && GrassesConfig.CommonConfig.ALLOW_USE_SHEAR_ON_STICKY_PISTON.get()) {
                event.setCanceled(true);

                Block.popResourceFromFace(level, blockPos, Objects.requireNonNull(event.getFace()), new ItemStack(Items.SLIME_BALL));
                level.setBlockAndUpdate(blockPos, PISTON.withPropertiesOf(blockState));
                ParticleUtils.spawnParticlesOnBlockFaces(level, blockPos, ParticleTypes.COMPOSTER, UniformInt.of(4, 6));

                usedShearsOnCorrectBlock = true;
                advItemUsedOnBlock = true;

            } else if ((hitVanillaEndFrame || hitModEndFrame) && hasSilkAndChanneling && GrassesConfig.CommonConfig.ALLOW_USE_SHEAR_ON_END_FRAME.get() && blockState.getValue(HAS_EYE).equals(Boolean.TRUE)) {
                level.addDestroyBlockEffect(blockPos, blockState);
                level.setBlockAndUpdate(blockPos, blockState.getBlock().withPropertiesOf(blockState).setValue(HAS_EYE, Boolean.valueOf(false)));
                Block.popResourceFromFace(level, blockPos, Objects.requireNonNull(event.getFace()), new ItemStack(Items.ENDER_EYE));
                deactivatePortal(level, blockPos);
                usedShearsOnCorrectBlock = true;
                advItemUsedOnBlock = true;

            } else if(hitEREndFrame && !blockState.getValue(EYE).equals(ERFrameProperties.EMPTY) && hasSilkAndChanneling && GrassesConfig.CommonConfig.ALLOW_USE_SHEAR_ON_ER_END_FRAME.get()) {

                String eye = blockState.getValue(EYE).toString();
                Item itemEye = ForgeRegistries.ITEMS.getValue(new ResourceLocation("endrem:".concat(eye)));

                level.addDestroyBlockEffect(blockPos, blockState);
                level.setBlockAndUpdate(blockPos, blockState.getBlock().withPropertiesOf(blockState).setValue(EYE, ERFrameProperties.EMPTY));
                assert itemEye != null;
                Block.popResourceFromFace(level, blockPos, Objects.requireNonNull(event.getFace()), new ItemStack(itemEye));
                deactivatePortal(level, blockPos);
                usedShearsOnCorrectBlock = true;
                advItemUsedOnBlock = true;
            } else if (hitNotGrassesLeaves) {
                event.setCanceled(true);
                Block leavesBlock = matchingCounterpartsLeaves.get(blockState.getBlock());
                keepData(blockPos, blockState.getBlock(), ColorType.PLAINS);
                level.setBlockAndUpdate(blockPos, leavesBlock.withPropertiesOf(blockState));

                level.addDestroyBlockEffect(blockPos, blockState);
                advItemUsedOnBlock = true;
                usedShearsOnCorrectBlock = true;
            } else if (hitNotGrassesVines) {
                event.setCanceled(true);
                Block leavesBlock = matchingCounterpartsVines.get(blockState.getBlock());
                keepData(blockPos, blockState.getBlock(), ColorType.PLAINS);
                level.setBlock(blockPos, leavesBlock.withPropertiesOf(blockState), 19);

                level.addDestroyBlockEffect(blockPos, blockState);
                advItemUsedOnBlock = true;
                usedShearsOnCorrectBlock = true;
            } else if (hitNotGrassesPlants) {
                event.setCanceled(true);
                Block plantsBlock = matchingCounterpartsPlants.get(blockState.getBlock());
                advItemUsedOnBlock = true;
                usedShearsOnCorrectBlock = true;

                if (isBOPLoaded && hitBlock instanceof HugeLilyPadBlock) {
                    List<BlockPos> blockPosList = checkHugeLily(blockPos, blockState);

                    for (BlockPos blockPosToSet : blockPosList) {
                        BlockState blockStateCurrent = level.getBlockState(blockPosToSet);
                        QuarterProperty oldValue = blockStateCurrent.getValue(QUARTER);
                        GrassesQuarterProperty newValue = mapFromQuarterPropertyToGrasses(oldValue);

                        if (!level.isClientSide) {
                            keepData(blockPosToSet, blockState.getBlock(), ColorType.PLAINS);
                            level.setBlock(blockPosToSet, plantsBlock.withPropertiesOf(blockStateCurrent).setValue(GRASSES_QUARTER, newValue), 19);
                        }
                        level.addDestroyBlockEffect(blockPosToSet, blockStateCurrent);
                    }
                } else if (isBOPLoaded || !BOPPlantsBlocksList.contains(hitBlock)) {
                    keepData(blockPos, blockState.getBlock(), ColorType.PLAINS);
                    level.setBlock(blockPos, plantsBlock.withPropertiesOf(blockState), plantsChangedIntoTintedWithFlag.get(blockState.getBlock()));
                    level.addDestroyBlockEffect(blockPos, blockState);

                    if (hitBlock instanceof DoublePlantBlock) {
                        BlockPos blockPosSecondPart = blockState.getValue(HALF).equals(LOWER) ? blockPos.above() : blockPos.below();
                        keepData(blockPosSecondPart, blockState.getBlock(), ColorType.PLAINS);
                        level.setBlock(blockPosSecondPart, plantsBlock.withPropertiesOf(level.getBlockState(blockPosSecondPart)), plantsChangedIntoTintedWithFlag.get(blockState.getBlock()));
                    } else if (isBOPLoaded && hitBlock instanceof HugeCloverPetalBlock) {
                        List<BlockPos> blockPosList = new ArrayList<>();
                        Direction direction = blockState.getValue(FACING);
                        BlockPos blockPos2 = blockPos.offset(direction.getOpposite().getNormal());
                        BlockPos blockPos3 = blockPos.offset(direction.getClockWise().getNormal());
                        BlockPos blockPos4 = blockPos3.offset(direction.getOpposite().getNormal());

                        if (setHugeClover(level, blockPos2, blockState.getBlock(), plantsBlock)) blockPosList.add(blockPos2);
                        if (setHugeClover(level, blockPos3, blockState.getBlock(), plantsBlock)) blockPosList.add(blockPos3);
                        if (setHugeClover(level, blockPos4, blockState.getBlock(), plantsBlock)) blockPosList.add(blockPos4);

                        for (BlockPos blockPosOnList : blockPosList) {
                            level.addDestroyBlockEffect(blockPosOnList, level.getBlockState(blockPosOnList));
                            hurtAndBreak++;
                        }
                    }
                }

            } else if (hitVanillaPot) {
                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.FAIL);
                keepData(blockPos, blockState.getBlock(), ColorType.PLAINS);
                level.setBlock(blockPos, matchingCounterpartsVanillaPotted.get(blockState.getBlock()).defaultBlockState(), 3);
                advItemUsedOnBlock = true;
                usedShearsOnCorrectBlock = true;
            } else if (hitTreeSapling && GrassesConfig.CommonConfig.ALLOW_USE_SHEAR_ON_TREE_SAPLING.get()) {

                level.setBlock(blockPos, DEAD_BUSH.defaultBlockState(), 3);
                advItemUsedOnBlock = true;
                usedShearsOnCorrectBlock = true;
            }

            if (usedShearsOnCorrectBlock) {
                itemStackInMainHand.hurtAndBreak(hurtAndBreak, player, (e) -> {
                    e.broadcastBreakEvent(interactionHand);
                });
                level.addDestroyBlockEffect(blockPos, blockState);
                level.playSound(null, blockPos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
                player.swing(interactionHand, true);

                if(player instanceof ServerPlayer && advItemUsedOnBlock)
                    ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockPos, itemStackInMainHand);
            }
        }
        else if (itemStackInMainHand.getItem() instanceof BoneMealItem && fertileCondition) {
            BlockPos clickedFace = blockPos.relative(event.getHitVec().getDirection());
            boolean isGrass      = blockState.is(GRASS_BLOCK) || blockState.is(ALL_MOD_GRASS) || blockState.is(OTHER_GRASS_BLOCKS);
            boolean isWater      = level.getBlockState(clickedFace).is(WATER);
            int fluidState       = level.getFluidState(clickedFace).getAmount();
            int countInHand      = itemStackInMainHand.getCount();

            if (((isGrass || (isWater && fluidState == 8)) && itemStackInMainHand.getItem() == Items.BONE_MEAL)) {
                return;
            }

            boolean isCactus            = blockState.is(CACTUS) && GrassesConfig.CommonConfig.ALLOW_USE_BONE_MEAL_ON_CACTUS.get();
            boolean isGrassesCactus     = blockState.is(CACTUS_TINTED.get()) && GrassesConfig.CommonConfig.ALLOW_USE_BONE_MEAL_ON_MOD_CACTUS.get();
            boolean isSugarCane         = blockState.is(SUGAR_CANE) && GrassesConfig.CommonConfig.ALLOW_USE_BONE_MEAL_ON_SUGAR_CANE.get();
            boolean isGrassesSugarCane  = blockState.is(SUGAR_CANE_TINTED.get()) && GrassesConfig.CommonConfig.ALLOW_USE_BONE_MEAL_ON_MOD_SUGAR_CANE.get();
            boolean isCactusLike        = (blockState.is(CACTUS_LIKE_PLANTS_1) || blockState.is(CACTUS_LIKE_PLANTS_2) || blockState.is(CACTUS_LIKE_PLANTS_3)) && GrassesConfig.CommonConfig.ALLOW_USE_BONE_MEAL_ON_CACTUS_LIKE_PLANTS.get();
            boolean isVine              = matchingCounterpartsVines.containsKey(blockState.getBlock()) && GrassesConfig.CommonConfig.ALLOW_USE_BONE_MEAL_ON_VANILLA_VINES.get();
            boolean isGrassesVine       = matchingCounterpartsVines.containsValue(blockState.getBlock()) && GrassesConfig.CommonConfig.ALLOW_USE_BONE_MEAL_ON_MOD_VINES.get();
            boolean isLily              = blockState.is(LILY_PAD) && GrassesConfig.CommonConfig.ALLOW_USE_BONE_MEAL_ON_VANILLA_LILY_PAD.get();
            boolean isGrassesLily       = blockState.is(LILY_TINTED.get()) && GrassesConfig.CommonConfig.ALLOW_USE_BONE_MEAL_ON_MOD_LILY_PAD.get();
            boolean isFlower            = blockState.is(SMALL_FLOWERS) && GrassesConfig.CommonConfig.ALLOW_USE_BONE_MEAL_ON_SMALL_FLOWERS.get();

            boolean isVanillaMycelium   = blockState.is(MYCELIUM) && GrassesConfig.CommonConfig.ALLOW_USE_BONE_MEAL_ON_VANILLA_MYCELIUM.get();
            boolean isGrassesMycelium   = (blockState.is(MYCELIUM_SLAB_BLOCK.get()) || blockState.is(GROW_MYCELIUM_BLOCK.get())) && GrassesConfig.CommonConfig.ALLOW_USE_BONE_MEAL_ON_MOD_MYCELIUM.get();
            boolean isVanillaPodzol     = blockState.is(PODZOL) && GrassesConfig.CommonConfig.ALLOW_USE_BONE_MEAL_ON_VANILLA_PODZOL.get();
            boolean isGrassesPodzol     = (blockState.is(PODZOL_SLAB_BLOCK.get()) || blockState.is(GROW_PODZOL_BLOCK.get())) && GrassesConfig.CommonConfig.ALLOW_USE_BONE_MEAL_ON_MOD_PODZOL.get();
            boolean isVanillaSoulSand   = blockState.is(SOUL_SAND) && GrassesConfig.CommonConfig.ALLOW_USE_BONE_MEAL_ON_VANILLA_SOUL_SAND.get();
            boolean isGrassesSoulSand   = blockState.is(SOUL_SAND_SLAB_BLOCK.get()) && GrassesConfig.CommonConfig.ALLOW_USE_BONE_MEAL_ON_MOD_SOUL_SAND.get();
            boolean isWitherRose        = blockState.is(WITHER_ROSE);

            boolean isTintedBOPPlant    = isBOPLoaded && tintedBOPPlantBlockList.contains(blockState.getBlock()) && blockState.getBlock() instanceof ParentTintedBushBlock && !blockState.is(HUGE_LILY_PAD_TINTED.get()) && GrassesConfig.CommonConfig.ALLOW_USE_BONE_MEAL_ON_BOP_PLANTS.get();
            boolean isBOPPlant          = isBOPLoaded && BOPPlantsBlocksList.contains(blockState.getBlock()) && blockState.getBlock() instanceof BushBlock && !blockState.is(HUGE_LILY_PAD) && GrassesConfig.CommonConfig.ALLOW_USE_BONE_MEAL_ON_BOP_PLANTS.get();

            boolean isNetherrackSlab    = blockState.is(NETHERRACK_SLAB_BLOCK.get());
            boolean advItemUsedOnBlock  = false;

            if ((isGrass || (isWater && fluidState == 8 && blockState.isSolidRender(level, blockPos))) && itemStackInMainHand.getItem() instanceof DyeingBoneMealItem) {
                event.setCanceled(true);
                Direction direction  = event.getFace();
                assert direction != null;
                boolean flag         = blockState.isFaceSturdy(level, blockPos, direction);

                if (level instanceof ServerLevel serverLevel) {
                    if (isWater && fluidState == 8 && flag)
                        performTintedBoneMealForSeagrass(serverLevel, itemStackInMainHand, clickedFace, blockState, direction);
                    else
                        performTintedBoneMealForGrass(serverLevel, itemStackInMainHand, blockPos, blockState);

                    level.playSound(null, blockPos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    level.levelEvent(LevelEvent.PARTICLES_PLANT_GROWTH, blockPos, 0);

                    if (!player.isCreative()) {
                        itemStackInMainHand.shrink(1);
                    }
                }
            }
            else if (
                    ((isCactus || isGrassesCactus || isSugarCane || isGrassesSugarCane || isCactusLike) && growPlantOneStageAbove(level, blockPos, blockState, itemStackInMainHand, player)) ||
                            ((isVine || isGrassesVine) && growVineOneStageBelow(level, blockPos, blockState, itemStackInMainHand, player)) ||
                            ((isLily || isGrassesLily || (isFlower && !isWitherRose) || isVanillaMycelium || isGrassesMycelium || isVanillaPodzol || isGrassesPodzol ||
                                    isVanillaSoulSand || isGrassesSoulSand || isTintedBOPPlant || isBOPPlant) && spreadPlants(level, blockPos, blockState, itemStackInMainHand, event))) {

                level.playSound(null, blockPos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.levelEvent(LevelEvent.PARTICLES_PLANT_GROWTH, blockPos, 0);

                if (!player.isCreative() && countInHand == itemStackInMainHand.getCount()) {
                    itemStackInMainHand.shrink(1);
                }
            } else if (isNetherrackSlab) {

                boolean flag = false;
                boolean flag1 = false;
                SlabType slabType = blockState.getValue(SLAB_TYPE);
                RandomSource randomSource = RandomSource.create();

                for(BlockPos blockpos : BlockPos.betweenClosed(blockPos.offset(-1, -1, -1), blockPos.offset(1, 1, 1))) {
                    BlockState blockstate = level.getBlockState(blockpos);
                    if (blockstate.is(WARPED_NYLIUM_BLOCKS) || blockstate.is(WARPED_NYLIUM)) {
                        flag1 = true;
                    }

                    if (blockstate.is(CRIMSON_NYLIUM_BLOCKS) || blockstate.is(CRIMSON_NYLIUM)) {
                        flag = true;
                    }

                    if (flag1 && flag) {
                        break;
                    }
                }

                if (flag1 && flag) {
                    level.setBlock(blockPos, randomSource.nextBoolean() ? WARPED_NYLIUM_SLAB_BLOCK.get().defaultBlockState().setValue(SLAB_TYPE, slabType) : CRIMSON_NYLIUM_SLAB_BLOCK.get().defaultBlockState().setValue(SLAB_TYPE, slabType), 3);
                    advItemUsedOnBlock = true;
                } else if (flag1) {
                    level.setBlock(blockPos, WARPED_NYLIUM_SLAB_BLOCK.get().defaultBlockState().setValue(SLAB_TYPE, slabType), 3);
                    advItemUsedOnBlock = true;
                } else if (flag) {
                    level.setBlock(blockPos, CRIMSON_NYLIUM_SLAB_BLOCK.get().defaultBlockState().setValue(SLAB_TYPE, slabType), 3);
                    advItemUsedOnBlock = true;
                }
            }
            if(player instanceof ServerPlayer && advItemUsedOnBlock)
                ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockPos, itemStackInMainHand);
        }
        else if (itemStackInMainHand.is(Items.SLIME_BALL) && GrassesConfig.CommonConfig.ALLOW_USE_SLIME_BALL_ON_PISTON.get()) {
            boolean isPiston = blockState.is(PISTON);

            if (isPiston && blockState.getValue(EXTENDED).equals(Boolean.FALSE)) {
                level.playSound(null, blockPos, SoundEvents.SLIME_BLOCK_PLACE , SoundSource.BLOCKS, 1.0F, 1.0F);
                level.setBlockAndUpdate(blockPos, STICKY_PISTON.withPropertiesOf(blockState));

                if (!player.isCreative()) {
                    itemStackInMainHand.shrink(1);
                }
                if(player instanceof ServerPlayer)
                    ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockPos, itemStackInMainHand);
            }
        }
        else if (itemStackInMainHand.is(Items.POTION) && PotionUtils.getPotion(itemStackInMainHand.getTag()) == Potions.WATER && blockState.is(CONVERTABLE_SLAB_TO_MUD)) {
            event.setCanceled(true);
            Direction direction = event.getFace();

            if (direction != Direction.DOWN) {
                level.playSound(null, blockPos, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS, 1.0F, 1.0F);
                player.setItemInHand(interactionHand, ItemUtils.createFilledResult(itemStackInMainHand, player, new ItemStack(Items.GLASS_BOTTLE)));
                player.awardStat(Stats.ITEM_USED.get(itemStackInMainHand.getItem()));
                if (!level.isClientSide) {
                    ServerLevel serverlevel = (ServerLevel) level;

                    for (int i = 0; i < 5; ++i) {
                        serverlevel.sendParticles(ParticleTypes.SPLASH, (double) blockPos.getX() + level.random.nextDouble(), (double) (blockPos.getY() + 1), (double) blockPos.getZ() + level.random.nextDouble(), 1, 0.0D, 0.0D, 0.0D, 1.0D);
                    }
                }

                level.playSound(null, blockPos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.gameEvent(null, GameEvent.FLUID_PLACE, blockPos);
                level.setBlockAndUpdate(blockPos, MUD_SLAB_BLOCK.get().defaultBlockState().setValue(TYPE, blockState.getValue(TYPE)));
            }
        }
        else if ((blockState.is(IRON_BARS) || matchingBarsWithPlant.containsKey(blockState.getBlock())) && isPlantForBars(itemStackInMainHand) && isSprintKeyPush && isSameVineBarsBlockAndSameColor(blockState, blockPos, itemStackInMainHand)) {
            event.setCanceled(true);
            plantIntoBars(level, blockState, blockPos, itemStackInMainHand, player, event.getHitVec());
            player.swing(interactionHand, true);
        }
        else if (isBOPLoaded && waterLilyCondition(itemStackInMainHand) && blockState.is(HUGE_LILY_PAD_TINTED.get()) && blockState.getValue(VARIANT_LILY).equals(0)) {

            event.setCanceled(true);

            lilyOnLiliPad(level, blockState, blockPos, itemStackInMainHand);

            level.playSound(null, blockPos, SoundEvents.LILY_PAD_PLACE , SoundSource.BLOCKS, 1.0F, 1.0F);
            player.swing(interactionHand, true);

            if (!player.isCreative()) {
                itemStackInMainHand.shrink(1);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Sheep sheep) {
            sheep.goalSelector.addGoal(5, new EatGrassesBlockGoal(sheep));
        }
    }
}

