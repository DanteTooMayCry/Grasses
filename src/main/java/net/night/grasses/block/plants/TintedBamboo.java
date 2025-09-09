package net.night.grasses.block.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BambooStalkBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BambooLeaves;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.night.grasses.block.blockEntity.TintedBlockEntity;
import net.night.grasses.enums.ColorType;
import net.night.grasses.config.GrassesConfig;
import net.night.grasses.data.ModMethods;
import net.night.grasses.item.DyeingBoneMealItem;
import net.night.grasses.util.ClientPlayerHelper;

import javax.annotation.Nullable;
import java.util.List;

import static net.minecraft.tags.BlockTags.BAMBOO_PLANTABLE_ON;
import static net.minecraft.world.level.block.Blocks.BAMBOO;
import static net.minecraft.world.level.block.Blocks.BAMBOO_SAPLING;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.SLAB_TYPE;
import static net.neoforged.neoforge.common.CommonHooks.onCropsGrowPost;
import static net.neoforged.neoforge.common.CommonHooks.onCropsGrowPre;
import static net.night.grasses.data.ModMethods.*;
import static net.night.grasses.init.BlocksRegister.*;

public class TintedBamboo extends BambooStalkBlock implements BonemealableBlock, EntityBlock {

    public TintedBamboo() {
        super(Properties.ofFullCopy(BAMBOO));
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
    public void appendHoverText(ItemStack itemStack, @Nullable BlockGetter level, List<Component> tooltip, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, tooltip, tooltipFlag);
        additionalHoverText(itemStack, tooltip);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState blockState, HitResult hitResult, LevelReader levelReader, BlockPos blockPos, Player player) {
        return ModMethods.getCloneItemStackBE((Level) levelReader, blockPos, BAMBOO_TINTED.get().defaultBlockState());
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockState blockStateBelow = pLevel.getBlockState(pPos.below());
        if (blockStateBelow.hasProperty(SLAB_TYPE) && blockStateBelow.getValue(SLAB_TYPE) == SlabType.BOTTOM && !GrassesConfig.COMMON_CONFIG.ALLOW_PUT_PLANTS_ON_BOTTOM_SLAB.get())
            return false;
        else
            return pLevel.getBlockState(pPos.below()).is(BAMBOO_PLANTABLE_ON);
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockStateOld, boolean pMovedByPiston) {
        onPlaceCounterPart(level, blockPos, blockState);
        super.onPlace(blockState, level, blockPos, blockStateOld, pMovedByPiston);
    }

    @Override
    public List<ItemStack> getDrops(BlockState blockState, LootParams.Builder builder) {
        return prepareDropWithColor(super.getDrops(blockState, builder), builder, this.asItem());
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {

        BlockPos blockPosClicked = context.getClickedPos();
        keepColorType.put(blockPosClicked, getColorTypeFromNBT(context.getItemInHand()));

        FluidState fluidstate = context.getLevel().getFluidState(blockPosClicked);

        if (!fluidstate.isEmpty()) {
            return null;
        } else {
            BlockState blockstate = context.getLevel().getBlockState(blockPosClicked.below());
            if (blockstate.is(BlockTags.BAMBOO_PLANTABLE_ON)) {
                if (blockstate.is(BAMBOO_SAPLING_TINTED.get())) {
                    keepCounterPartType.put(blockPosClicked, BAMBOO.defaultBlockState());
                    return this.defaultBlockState().setValue(AGE, 0);
                } else if (blockstate.is(BAMBOO_TINTED.get())) {
                    keepCounterPartType.put(blockPosClicked, BAMBOO.defaultBlockState());
                    int i = blockstate.getValue(AGE) > 0 ? 1 : 0;
                    return this.defaultBlockState().setValue(AGE, i);
                } else {
                    BlockState blockstate1 = context.getLevel().getBlockState(blockPosClicked.above());

                    if (blockstate1.is(BAMBOO_TINTED.get())) {
                        keepCounterPartType.put(blockPosClicked, BAMBOO.defaultBlockState());
                        return this.defaultBlockState().setValue(AGE, blockstate1.getValue(AGE));
                    }
                    else {
                        keepCounterPartType.put(blockPosClicked, BAMBOO_SAPLING.defaultBlockState());
                        return BAMBOO_SAPLING_TINTED.get().defaultBlockState();
                    }
                }
            } else {
                return null;
            }
        }
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        if (!pState.canSurvive(pLevel, pCurrentPos))
            pLevel.scheduleTick(pCurrentPos, this, 1);

        if (pDirection == Direction.UP && pNeighborState.is(BAMBOO_TINTED.get()) && pNeighborState.getValue(AGE) > pState.getValue(AGE)) {
            keepData(pCurrentPos, BAMBOO, getColorType((Level) pLevel, pCurrentPos));
            pLevel.setBlock(pCurrentPos, pState.cycle(AGE), 3);
        }

        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if(isFertileState(blockState)) {
            if (blockState.getValue(STAGE) == 0) {
                if (serverLevel.isEmptyBlock(blockPos.above()) && serverLevel.getRawBrightness(blockPos.above(), 0) >= 9) {
                    int i = this.getHeightBelowUpToMax(serverLevel, blockPos) + 1;
                    ColorType colorType = getColorType(serverLevel, blockPos);
                    if (i < 16 && onCropsGrowPre(serverLevel, blockPos, blockState, randomSource.nextInt(3) == 0)) {
                        growTintedBamboo(blockState, serverLevel, blockPos, randomSource, i, null, colorType);
                        onCropsGrowPost(serverLevel, blockPos, blockState);
                    }
                }
            }
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState) {
        int i = this.getHeightAboveUpToMax(pLevel, pPos);
        int j = this.getHeightBelowUpToMax(pLevel, pPos);
        return i + j + 1 < 16 && pLevel.getBlockState(pPos.above(i)).getValue(STAGE) != 1;
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        if(!isFertileState(blockState)) {
            serverLevel.setBlock(blockPos, blockState.setValue(FERTILE, true), 3);
        }
        else {

            ItemStack itemStack = ClientPlayerHelper.getMainHandItem();
            ColorType colorType = getColorType(serverLevel, blockPos);

            int i = getHeightAboveUpToMaxPublic(serverLevel, blockPos);
            int j = getHeightBelowUpToMaxPublic (serverLevel, blockPos);
            int k = i + j + 1;
            int l = 1 + randomSource.nextInt(2);

            for (int i1 = 0; i1 < l; ++i1) {
                BlockPos blockpos = blockPos.above(i);
                BlockState blockstate = serverLevel.getBlockState(blockpos);
                if (k >= 16 || blockstate.getValue(STAGE) == 1 || !serverLevel.isEmptyBlock(blockpos.above())) {
                    break;
                }

                growTintedBamboo(blockstate, serverLevel, blockpos, randomSource, k, itemStack, colorType);
                ++i;
                ++k;
            }
        }
    }

    public static boolean growTintedBamboo(BlockState blockState, Level level, BlockPos blockPos, RandomSource pRandom, int pAge, ItemStack itemStack, ColorType colorType) {
        BlockState blockStateBelow = level.getBlockState(blockPos.below());
        BlockPos blockPosBelowTwo = blockPos.below(2);
        BlockState blockStateBelowTwo = level.getBlockState(blockPosBelowTwo);
        BambooLeaves bambooleaves = BambooLeaves.NONE;
        boolean nullItemStack = itemStack == null;

        if (pAge >= 1) {
            if (blockStateBelow.is(BAMBOO_TINTED.get()) && blockStateBelow.getValue(LEAVES) != BambooLeaves.NONE) {
                bambooleaves = BambooLeaves.LARGE;

                if (blockStateBelowTwo.is(BAMBOO_TINTED.get())) {

                    keepData(blockPosBelowTwo, BAMBOO, getColorType(level, blockPosBelowTwo));
                    keepData(blockPos.below(), BAMBOO, getColorType(level, blockPos.below()));
                    level.setBlock(blockPosBelowTwo, blockStateBelowTwo.setValue(LEAVES, BambooLeaves.NONE), 3);
                    level.setBlock(blockPos.below(), blockStateBelow.setValue(LEAVES, BambooLeaves.SMALL), 3);
                }
            } else {
                bambooleaves = BambooLeaves.SMALL;
            }
        }

        int i = blockState.getValue(AGE) != 1 && !blockStateBelowTwo.is(BAMBOO_TINTED.get()) ? 0 : 1;
        int j = (pAge < 11 || !(pRandom.nextFloat() < 0.25F)) && pAge != 15 ? 0 : 1;

        if (!nullItemStack && (itemStack.getItem() instanceof DyeingBoneMealItem || itemStack.getItem().equals(BAMBOO_TINTED.get().asItem()))) {
            keepData(blockPos.above(), BAMBOO, getColorTypeFromNBT(itemStack));
        }
        else {
            keepData(blockPos.above(), BAMBOO, colorType);
        }

        level.setBlock(blockPos.above(), blockState.getBlock().defaultBlockState().setValue(AGE, Integer.valueOf(i)).setValue(LEAVES, bambooleaves).setValue(STAGE, Integer.valueOf(j)), 3);
        return true;
    }

    protected int getHeightAboveUpToMax(BlockGetter pLevel, BlockPos pPos) {
        int i;
        for(i = 0; i < 16 && pLevel.getBlockState(pPos.above(i + 1)).is(BAMBOO_TINTED.get()); ++i) {
        }

        return i;
    }

    protected int getHeightBelowUpToMax(BlockGetter pLevel, BlockPos pPos) {
        int i;
        for(i = 0; i < 16 && pLevel.getBlockState(pPos.below(i + 1)).is(BAMBOO_TINTED.get()); ++i) {
        }

        return i;
    }

    public static int getHeightAboveUpToMaxPublic(BlockGetter pLevel, BlockPos pPos) {
        int i;
        for(i = 0; i < 16 && pLevel.getBlockState(pPos.above(i + 1)).is(BAMBOO_TINTED.get()); ++i) {
        }

        return i;
    }

    public static int getHeightBelowUpToMaxPublic(BlockGetter pLevel, BlockPos pPos) {
        int i;
        for(i = 0; i < 16 && pLevel.getBlockState(pPos.below(i + 1)).is(BAMBOO_TINTED.get()); ++i) {
        }

        return i;
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {

        boolean changeColorPermission = GrassesConfig.COMMON_CONFIG.ALLOW_CHANGE_PLANTS_COLOR.get();
        boolean changeIntoVanillaPermission = GrassesConfig.COMMON_CONFIG.ALLOW_CHANGE_TINTED_PLANTS_INTO_NOT_GRASSES.get();

        int interactionResult = ModMethods.useOnPlant(blockState, level, blockPos, player, interactionHand, blockHitResult,
                changeColorPermission, changeIntoVanillaPermission, false, SoundEvents.BAMBOO_BREAK);

        if (interactionResult == 0)
            return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
        else
            return InteractionResult.sidedSuccess(level.isClientSide);

    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 60;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 60;
    }
}
