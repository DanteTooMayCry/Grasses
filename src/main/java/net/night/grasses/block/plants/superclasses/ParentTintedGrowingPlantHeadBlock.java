package net.night.grasses.block.plants.superclasses;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;
import net.night.grasses.block.blockEntity.TintedBlockEntity;
import net.night.grasses.colorManagers.ColorType;
import net.night.grasses.config.GrassesConfig;
import net.night.grasses.data.MethodsLib;
import net.night.grasses.item.DyeingBoneMealItem;

import java.util.List;

import static biomesoplenty.api.block.BOPBlocks.HIGH_GRASS;
import static biomesoplenty.api.block.BOPBlocks.HIGH_GRASS_PLANT;
import static net.minecraft.world.level.block.Blocks.*;
import static net.night.grasses.Grasses.isBOPLoaded;
import static net.night.grasses.data.MethodsLib.*;
import static net.night.grasses.init.BlocksRegister.*;
import static net.night.grasses.init.BlocksRegisterBoP.*;

public class ParentTintedGrowingPlantHeadBlock extends GrowingPlantHeadBlock implements EntityBlock {
    protected final double growPerTickProbability;

    protected ParentTintedGrowingPlantHeadBlock(Properties pProperties, Direction pGrowthDirection, VoxelShape pShape, boolean pScheduleFluidTicks, double pGrowPerTickProbability) {
        super(pProperties, pGrowthDirection, pShape, pScheduleFluidTicks, pGrowPerTickProbability);
        this.growPerTickProbability = pGrowPerTickProbability;
        this.registerDefaultState(this.defaultBlockState().setValue(FERTILE, Boolean.TRUE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FERTILE);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new TintedBlockEntity(blockPos, blockState);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemStack, @javax.annotation.Nullable BlockGetter level, List<Component> tooltip, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, tooltip, tooltipFlag);
        additionalHoverText(itemStack, tooltip);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState blockState, HitResult hitResult, BlockGetter blockGetter, BlockPos blockPos, Player player) {
        return MethodsLib.getCloneItemStackBE((Level) blockGetter, blockPos, blockState);
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockStateOld, boolean pMovedByPiston) {
        onPlaceCounterPart(level, blockPos, blockState);
        super.onPlace(blockState, level, blockPos, blockStateOld, pMovedByPiston);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        ItemStack itemStack = context.getItemInHand();
        BlockPos blockPosClicked = context.getClickedPos();
        BlockPos clickedBelow = blockPosClicked.below();

        Block blockFromHand = ((BlockItem) context.getItemInHand().getItem()).getBlock(); //

        if (isBOPLoaded || !tintedBOPPlantBlockList.contains(blockFromHand)) {
            Block blockToKeep = AIR;
            Block blockToKeepBelow = AIR;
            if (blockFromHand.equals(KELP_TINTED.get())) {
                blockToKeep = KELP;
                blockToKeepBelow = KELP_PLANT;
            } else if (blockFromHand.equals(HIGH_GRASS_TINTED.get())) {
                blockToKeep = HIGH_GRASS;
                blockToKeepBelow = HIGH_GRASS_PLANT;
            }

            if (itemStack.is(this.getHeadBlock().asItem())) {
                Block blockBelow = level.getBlockState(blockPosClicked.below()).getBlock();
                if (blockBelow.equals(this.getHeadBlock())) {
                    keepData(clickedBelow, blockToKeepBelow, getColorType(level, clickedBelow));
                }
                keepData(blockPosClicked, blockToKeep, getColorTypeFromNBT(itemStack));
            }
        }
        else {
            if (itemStack.is(this.getHeadBlock().asItem())) {
                Block blockBelow = level.getBlockState(blockPosClicked.below()).getBlock();
                if (blockBelow.equals(this.getHeadBlock())) {
                    keepColorType.put(clickedBelow, getColorTypeFromNBT(context.getItemInHand()));
                }
                keepColorType.put(blockPosClicked, getColorTypeFromNBT(context.getItemInHand()));
            }
        }

        return super.getStateForPlacement(context);
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState pNewState, boolean pMovedByPiston) {

        BlockPos blockPosBelow = blockPos.below();
        Block blockStateBelow = level.getBlockState(blockPosBelow).getBlock();
        Block blockToKeep = AIR;

        if (blockStateBelow.equals(KELP_PLANT_TINTED.get())) {
            blockToKeep = KELP;
        } else if (blockStateBelow.equals(HIGH_GRASS_PLANT_TINTED.get())) {
            blockToKeep = HIGH_GRASS;
        }

        if (blockStateBelow.equals(this.getBodyBlock()))
            keepData(blockPosBelow, blockToKeep, getColorType(level, blockPosBelow));

        super.onRemove(blockState, level, blockPos, pNewState, pMovedByPiston);
    }

    @Override
    public List<ItemStack> getDrops(BlockState blockState, LootParams.Builder builder) {
        return prepareDropWithColor(super.getDrops(blockState, builder), builder, this.asItem());
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {

        boolean changeColorPermission = GrassesConfig.CommonConfig.ALLOW_CHANGE_PLANTS_COLOR.get();
        boolean changeIntoVanillaPermission = GrassesConfig.CommonConfig.ALLOW_CHANGE_TINTED_PLANTS_INTO_NOT_GRASSES.get();

        int interactionResult = MethodsLib.useOnPlant(blockState, level, blockPos, player, interactionHand, blockHitResult,
                changeColorPermission, changeIntoVanillaPermission, false, SoundEvents.GRASS_BREAK);

        if (interactionResult == 0)
            return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
        else
            return InteractionResult.sidedSuccess(level.isClientSide);
    }

    //=================================================================

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource randomSource) {
        return 0;
    }

    @Override
    protected boolean canGrowInto(BlockState blockState) {
        return false;
    }

    @Override
    protected Block getBodyBlock() {
        return this;
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        if(!isFertileState(blockState)) {
            serverLevel.setBlock(blockPos, blockState.setValue(FERTILE, true), 3);
        } else {
            BlockPos blockPosGrowDirection = blockPos.relative(this.growthDirection);
            int i = Math.min(blockState.getValue(AGE) + 1, 8);
            int j = this.getBlocksToGrowWhenBonemealed(randomSource);
            ColorType colorType = ColorType.PLAINS;

            if (isBOPLoaded || !tintedBOPPlantBlockList.contains(blockState.getBlock())) {
                Block blockToKeepAbove = AIR;
                Block blockToKeep = AIR;

                if (blockState.getBlock().equals(KELP_TINTED.get())) {
                    blockToKeepAbove = KELP;
                    blockToKeep = KELP_PLANT;
                } else if (blockState.getBlock().equals(HIGH_GRASS_TINTED.get())) {
                    blockToKeepAbove = HIGH_GRASS;
                    blockToKeep = HIGH_GRASS_PLANT;
                }

                assert Minecraft.getInstance().player != null;
                ItemStack itemStack = Minecraft.getInstance().player.getMainHandItem();

                keepData(blockPos.immutable(), blockToKeep, getColorType(serverLevel, blockPos)); //

                if (itemStack.getItem() instanceof DyeingBoneMealItem)
                    colorType = getColorTypeFromNBT(itemStack);
                else if (!keepColorType.containsKey(blockPos.above()))
                    colorType = keepColorType.get(blockPos);

                for (int k = 0; k < j && this.canGrowInto(serverLevel.getBlockState(blockPosGrowDirection)); ++k) {
                    keepData(blockPosGrowDirection.immutable(), blockToKeepAbove, colorType); //

                    if (!serverLevel.isClientSide)
                        if (k < (j - 1))
                            serverLevel.setBlockAndUpdate(blockPosGrowDirection, this.getBodyBlock().defaultBlockState());
                        else
                            serverLevel.setBlockAndUpdate(blockPosGrowDirection, blockState.setValue(AGE, i));
                    blockPosGrowDirection = blockPosGrowDirection.relative(this.growthDirection);
                    i = Math.min(i + 1, 8);
                }
            }
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState blockState) {
        return isFertileState(blockState) && super.isRandomlyTicking(blockState);
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel level, BlockPos blockPos, RandomSource randomSource) {
        if (blockState.getValue(AGE) < 25 && ForgeHooks.onCropsGrowPre(level, blockPos.relative(this.growthDirection), level.getBlockState(blockPos.relative(this.growthDirection)), randomSource.nextDouble() < this.growPerTickProbability)) {
            BlockPos blockPosAbove = blockPos.relative(this.growthDirection);
            if (this.canGrowInto(level.getBlockState(blockPosAbove))) {
                Block blockToKeepAbove = AIR;
                Block blockToKeep = AIR;
                if (isBOPLoaded || !tintedBOPPlantBlockList.contains(blockState.getBlock())) {

                    if (blockState.getBlock().equals(KELP_TINTED.get())) {
                        blockToKeepAbove = KELP;
                        blockToKeep = KELP_PLANT;
                    } else if (blockState.getBlock().equals(HIGH_GRASS_PLANT_TINTED.get())) {
                        blockToKeepAbove = HIGH_GRASS;
                        blockToKeep = HIGH_GRASS_PLANT;
                    }
                }

                keepData(blockPosAbove, blockToKeepAbove, getColorType(level, blockPos));
                keepData(blockPos, blockToKeep, keepColorType.get(blockPosAbove));
                level.setBlockAndUpdate(blockPosAbove, this.getGrowIntoState(blockState, level.random));
                ForgeHooks.onCropsGrowPost(level, blockPosAbove, level.getBlockState(blockPosAbove));
            }
        }

    }
}
